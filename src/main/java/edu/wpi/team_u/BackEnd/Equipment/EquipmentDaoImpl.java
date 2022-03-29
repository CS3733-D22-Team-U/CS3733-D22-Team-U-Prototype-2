package edu.wpi.team_u.BackEnd.Equipment;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class EquipmentDaoImpl implements EquipmentDao {
  public String DB_LOC;
  public ArrayList<Equipment> EquipmentList = new ArrayList<Equipment>();

  public EquipmentDaoImpl(String db_loc) {
    this.DB_LOC = db_loc;
  }

  public void CSVToJava(String csvFile) throws IOException {
    EquipmentList = new ArrayList<Equipment>();
    String s;
    File file = new File(csvFile);
    BufferedReader br = new BufferedReader(new FileReader(file));
    br.readLine();
    while ((s = br.readLine()) != null) {
      String[] row = s.split(",");
      if (row.length == 4) {
        EquipmentList.add(
            new Equipment(row[0], Integer.parseInt(row[1]), Integer.parseInt(row[2])));
      }
    }
  }

  public void JavaToSQL() {

    try {
      Connection connection = null;
      connection = DriverManager.getConnection(DB_LOC);

      Statement exampleStatement = connection.createStatement();
      try {
        exampleStatement.execute("Drop table EquipmentList");
      } catch (Exception e) {
        System.out.println("didn't drop table");
      }

      // do
      exampleStatement.execute(
          "CREATE TABLE EquipmentList(name varchar(18) not null, "
              + "amount int not null,"
              + "inUse int not null,"
              + "available int not null,");

      for (int j = 0; j < EquipmentList.size(); j++) {
        Equipment currLoc = EquipmentList.get(j);
        exampleStatement.execute(
            "INSERT INTO Locations VALUES("
                + "'"
                + currLoc.getName()
                + "',"
                + currLoc.getAmount()
                + ","
                + currLoc.getInUse()
                + ",'"
                + currLoc.getAvailable()
                + "')");
      }

      connection.close();

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return;
    }
  }

  public void SQLToJava() throws SQLException {
    EquipmentList = new ArrayList<Equipment>();

    try {
      Connection connection = null;
      connection = DriverManager.getConnection(DB_LOC);

      Statement exampleStatement = connection.createStatement();

      try {
        ResultSet results;
        results = exampleStatement.executeQuery("SELECT * FROM Locations");

        while (results.next()) {
          String name = results.getString("name");
          int amount = results.getInt("amount");
          int inUse = results.getInt("inUse");

          Equipment SQLRow = new Equipment(name, amount, inUse);

          EquipmentList.add(SQLRow);
        }
      } catch (SQLException e) {
        System.out.println("Locations not found");
      }

      connection.close();

    } catch (SQLException e) {
      System.out.println("Database does not exist.");
    }
  }

  public void JavaToCSV(String csvFile) throws IOException {
    PrintWriter fw = new PrintWriter(new File(csvFile));

    fw.append("Name");
    fw.append(",");
    fw.append("Amount");
    fw.append(",");
    fw.append("In Use");
    fw.append(",");
    fw.append("Available");
    fw.append("\n");

    for (int i = 0;
        i < EquipmentList.size();
        i++) { // ask about how this was working without and = sign
      fw.append(EquipmentList.get(i).getName());
      fw.append(",");
      fw.append(Integer.toString(EquipmentList.get(i).getAmount()));
      fw.append(",");
      fw.append(Integer.toString(EquipmentList.get(i).getInUse()));
      fw.append(",");
      fw.append(Integer.toString(EquipmentList.get(i).getAvailable()));
      fw.append("\n");
    }
    fw.close();
  }

  public void printEquipTableInTerm(String csvFile) throws IOException {
    // csv to java
    this.CSVToJava(csvFile);
    // display locations and attributes
    System.out.println(
        "Node |\t X |\t Y |\t Level |\t Building |\t Type |\t Long Name |\t Short Name");
    for (Equipment equipment : EquipmentList) {
      System.out.println(
          equipment.getName()
              + " | \t"
              + equipment.getAmount()
              + " | \t"
              + equipment.getAvailable()
              + " | \t"
              + equipment.getInUse()
              + " | \t");
    }
    // menu
  }

  public void editEquipValue(String csvFile, Scanner userInput) throws IOException, SQLException {
    // takes entries from SQL table that match input node and updates it with a new floor and
    // location type
    // input ID
    System.out.println("Please input the name: ");
    String inputName = userInput.nextLine();
    // input new floor
    System.out.println("New floor: ");
    String inputNewAmount = userInput.nextLine();
    // input new location type
    System.out.println("New location type");
    String inputInUse = userInput.nextLine();
    this.CSVToJava(csvFile); // t
    for (int i = 0; i < this.EquipmentList.size(); i++) {
      if (this.EquipmentList.get(i).getName().equals(inputName)) {
        this.EquipmentList.get(i).Amount = Integer.parseInt(inputNewAmount);
        this.EquipmentList.get(i).InUse = Integer.parseInt(inputInUse);
      }
    }
    this.JavaToSQL();
    ; // t
    this.SQLToJava(); // t
    this.JavaToCSV(csvFile); // t
  }

  public void addEquip(String csvFile, Scanner userInput) throws IOException, SQLException {
    // add a new entry to the SQL table
    // prompt for ID
    System.out.println("Enter the new location ID");
    String newName = userInput.nextLine();
    Equipment newEquipment = new Equipment(newName);
    this.EquipmentList.add(newEquipment);
    this.JavaToSQL();
    this.SQLToJava();
    this.JavaToCSV(csvFile);
  }

  public void removeEquip(String csvFile, Scanner userInput) throws IOException, SQLException {
    // removes entries from SQL table that match input node
    // prompt for ID
    System.out.println("Input ID for to delete location: ");
    String userNodeID = userInput.nextLine(); // remove locations that match user input
    for (int i = this.EquipmentList.size() - 1; i >= 0; i--) {
      if (this.EquipmentList.get(i).getName().equals(userNodeID)) {
        this.EquipmentList.remove(i);
      }
    }
    this.JavaToSQL();
    this.SQLToJava();
    this.JavaToCSV(csvFile);
  }

  public void saveEquipTableAsCSV(Scanner userInput) throws SQLException {
    // takes entries from SQL table and an input name, from there it makes a new CSV file
    System.out.println("Enter CSV file location name");

    String CSVName = userInput.nextLine();
    String csvFilePath = "./" + CSVName + ".csv";

    try {
      new File(csvFilePath);
      this.SQLToJava();
      this.JavaToCSV(csvFilePath);

    } catch (IOException e) {
      System.out.println(e.fillInStackTrace());
    }
  }
}
