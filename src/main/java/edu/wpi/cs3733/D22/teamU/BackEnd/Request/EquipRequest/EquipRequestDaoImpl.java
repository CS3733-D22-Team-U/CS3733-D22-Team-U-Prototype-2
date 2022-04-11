package edu.wpi.cs3733.D22.teamU.BackEnd.Request.EquipRequest;

import edu.wpi.cs3733.D22.teamU.BackEnd.DataDao;
import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class EquipRequestDaoImpl implements DataDao<EquipRequest> {
  public Statement statement;
  public HashMap<String, EquipRequest> equipRequestList = new HashMap<String, EquipRequest>();
  public String csvFile;

  public EquipRequestDaoImpl(Statement statement, String csvfile) {
    this.csvFile = csvfile;
    this.statement = statement;
  }

  @Override
  public HashMap<String, EquipRequest> list() {
    return equipRequestList;
  }

  /**
   * Reads CSV file and puts the Equipment into an array list: EquipmentList
   *
   * @throws IOException
   */
  public void CSVToJava() throws IOException {
    equipRequestList = new HashMap<String, EquipRequest>();
    String s;
    File file = new File(csvFile);
    BufferedReader br = new BufferedReader(new FileReader(file));
    br.readLine();
    while ((s = br.readLine()) != null) {
      String[] row = s.split(",");
      if (row.length == 8) {
        equipRequestList.put(
            row[0],
            new EquipRequest(
                row[0],
                row[1],
                Integer.parseInt(row[2]),
                row[3],
                row[4],
                row[5],
                row[6],
                Integer.parseInt(row[7])));
      }
    }
  }

  /**
   * Copies the array list: EquipmentList and writes it into the CSV file
   *
   * @param csvFile
   * @throws IOException
   */
  public void JavaToCSV(String csvFile) throws IOException {
    PrintWriter fw = new PrintWriter(new File(csvFile));

    fw.append("ID");
    fw.append(",");
    fw.append("Name");
    fw.append(",");
    fw.append("Amount");
    fw.append(",");
    fw.append("Type");
    fw.append(",");
    fw.append("Destination");
    fw.append(",");
    fw.append("Date");
    fw.append(",");
    fw.append("Time");
    fw.append(",");
    fw.append("Priority");
    fw.append("\n");

    Set<String> keys = equipRequestList.keySet();
    for (String i : keys) {
      fw.append(equipRequestList.get(i).getID());
      fw.append(",");
      fw.append(equipRequestList.get(i).getName());
      fw.append(",");
      fw.append(Integer.toString(equipRequestList.get(i).getAmount()));
      fw.append(",");
      fw.append(equipRequestList.get(i).getType());
      fw.append(",");
      fw.append(equipRequestList.get(i).getDestination());
      fw.append(",");
      fw.append(equipRequestList.get(i).getDate());
      fw.append(",");
      fw.append(equipRequestList.get(i).getTime());
      fw.append(",");
      fw.append(Integer.toString(equipRequestList.get(i).getPri()));
      fw.append("\n");
    }
    fw.close();
  }

  public void JavaToSQL() {

    try {
      statement.execute("Drop table EquipRequest");
    } catch (Exception e) {
      System.out.println("didn't drop table");
    }

    try {
      statement.execute(
          "CREATE TABLE EquipRequest("
              + "ID varchar(10) not null,"
              + "name varchar(50) not null, "
              + "amount int not null,"
              + "typeOfRequest varchar(10),"
              + "destination varchar(10) not null,"
              + "date varchar(10) not null,"
              + "time varchar(10) not null,"
              + "pri int not null)");

      Set<String> keys = equipRequestList.keySet();
      for (String j : keys) {
        EquipRequest currReq = equipRequestList.get(j);
        statement.execute(
            "INSERT INTO EquipRequest VALUES("
                + "'"
                + currReq.getID()
                + "','"
                + currReq.getName()
                + "',"
                + currReq.getAmount()
                + ",'"
                + currReq.getType()
                + "','"
                + currReq.getDestination()
                + "','"
                + currReq.getDate()
                + "','"
                + currReq.getTime()
                + "',"
                + currReq.getPri()
                + ")");
      }
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
    }
  }

  public void SQLToJava() {
    equipRequestList = new HashMap<String, EquipRequest>();
    try {
      ResultSet results;
      results = statement.executeQuery("SELECT * FROM EquipRequest");

      while (results.next()) {
        String id = results.getString("ID");
        String name = results.getString("name");
        int amount = results.getInt("amount");
        String type = results.getString("typeOfRequest");
        String destination = results.getString("destination");
        String date = results.getString("date");
        String time = results.getString("time");
        int pri = results.getInt("pri");

        EquipRequest SQLRow =
            new EquipRequest(id, name, amount, type, destination, date, time, pri);

        equipRequestList.put(id, SQLRow);
      }
    } catch (SQLException e) {
      System.out.println("request not found");
    }
  }

  public void printTable() throws IOException {
    // csv to java
    CSVToJava();
    // display locations and attributes
    System.out.println(
        "ID |\t Name |\t Amount |\t Type |\t Destination |\t Date |\t Time |\t Priority");
    Set<String> keys = equipRequestList.keySet();
    for (String id : keys) {
      EquipRequest request = this.equipRequestList.get(id);
      System.out.println(
          request.ID
              + " | \t"
              + request.name
              + " | \t"
              + request.amount
              + " | \t"
              + request.typeOfRequest
              + " | \t"
              + request.destination
              + " | \t"
              + request.date
              + " | \t"
              + request.time
              + " | \t"
              + request.pri);
    }
  }

  @Override
  public void edit(EquipRequest data) {
    // takes entries from SQL table that match input node and updates it with a new floor and
    // location type
    // input ID
    try {
      list().replace(data.ID, data);
      this.JavaToSQL(); // t
      this.JavaToCSV(csvFile); // t
    } catch (Exception e) {
      System.out.println("This Object Does Not Exist");
    }
  }

  /**
   * Prompts user for the name of a new item and then adds it to the csv file and database
   *
   * @param //csvFile
   * @throws IOException
   */
  @Override
  public void add(EquipRequest data) throws IOException {
    // add a new entry to the SQL table
    if (equipRequestList.containsKey(data.ID)) {
      System.out.println("An Equipment Request With This ID Already Exists");
    } else {
      equipRequestList.put(data.ID, data);
      this.JavaToSQL();
      this.JavaToCSV(csvFile);
    }
  }

  /**
   * Prompts user for the name of the item they wish to remove and then removes that item from the
   * database and csv file
   *
   * @throws IOException
   */
  @Override
  public void remove(EquipRequest data) throws IOException {
    // removes entries from SQL table that match input node
    try {
      this.equipRequestList.remove(data.ID);
      this.JavaToSQL();
      this.JavaToCSV(csvFile);
    } catch (Exception e) {
      System.out.println("This Data Point Was Not Found");
    }
  }

  /**
   * Prompts user for the name of a new file and then creates the new file in the project folder
   * then it copies the database table: EquipmentList into the CSV file
   *
   * @throws SQLException
   */
  public void saveTableAsCSV(String CSVName) throws SQLException {
    // takes entries from SQL table and an input name, from there it makes a new CSV file

    String csvFilePath = "./" + CSVName + ".csv";

    try {
      new File(csvFilePath);
      this.SQLToJava();
      this.JavaToCSV(csvFilePath);

    } catch (IOException e) {
      System.out.println(e.fillInStackTrace());
    }
  }

  /*@Override
  public int search(String id) { // TODO search
    int index = -1;
    for (int i = 0; i < list().size(); i++) if (id.equals(list().get(i).ID)) index = i;
    return index;
  }*/

  public EquipRequest askUser() {
    Scanner reqInput = new Scanner(System.in);

    String inputID = "None";
    String inputName = "N/A";
    int inputAmount = 0;
    String inputType = "N/A";
    String inputDestination = "N/A";
    String inputDate = "N/A";
    String inputTime = "N/A";
    int inputPriority = 0;

    System.out.println("Input request ID: ");
    inputID = reqInput.nextLine();

    System.out.println("Input name: ");
    inputName = reqInput.nextLine();

    return new EquipRequest(
        inputID,
        inputName,
        inputAmount,
        inputType,
        inputDestination,
        inputDate,
        inputTime,
        inputPriority);
  }
}
