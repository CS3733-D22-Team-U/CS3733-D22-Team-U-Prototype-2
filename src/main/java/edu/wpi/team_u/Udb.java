package edu.wpi.team_u;

import java.io.*;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Udb {

  public ArrayList<Location> locations = new ArrayList<Location>();
  private final String DB_LOC = "jdbc:derby:UDB";

  public void storeCSVtoOBJ(String csvFile) throws IOException {
    String s;
    File file = new File(csvFile);
    BufferedReader br = new BufferedReader(new FileReader(file));
    br.readLine();
    while ((s = br.readLine()) != null) {
      String[] row = s.split(",");
      if (row.length == 8) locations.add(new Location(row));
    }
  }

  public void JavaToSQL() {

    System.out.println("-------Embedded Apache Derby Connection Testing --------");
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    } catch (ClassNotFoundException e) {
      System.out.println("Apache Derby Driver not found. Add the classpath to your module.");
      System.out.println("For IntelliJ do the following:");
      System.out.println("File | Project Structure, Modules, Dependency tab");
      System.out.println("Add by clicking on the green plus icon on the right of the window");
      System.out.println(
          "Select JARs or directories. Go to the folder where the database JAR is located");
      System.out.println("Click OK, now you can compile your program and run it.");
      e.printStackTrace();
      return;
    }

    System.out.println("Apache Derby driver registered!");
    Connection connection = null;

    try {
      connection = DriverManager.getConnection(DB_LOC + ";create=true");
      Statement exampleStatement = connection.createStatement();
      try {
        exampleStatement.execute("Drop table Locations");
      } catch (Exception e) {
      }
      exampleStatement.execute(
          "CREATE TABLE Locations(nodeID varchar(18) not null, "
              + "xcoord int not null,"
              + "ycoord int not null,"
              + "floor varchar(3) not null,"
              + "building varchar(6),"
              + "nodeType varchar(6),"
              + "longName varchar(900) not null,"
              + "shortName varchar(600))");
      for (int j = 0; j < locations.size(); j++) {
        Location currLoc = locations.get(j);
        addLocation(currLoc);
      }
      connection.close();

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return;
    }
    System.out.println("Apache Derby connection established!");
  }

  public void SQLToJava() {
    locations = new ArrayList<>();
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(DB_LOC + ";");
      Statement exampleStatement = connection.createStatement();

      try {
        ResultSet results;
        results = exampleStatement.executeQuery("SELECT * FROM Locations");

        while (results.next()) {
          String nodeID = results.getString("nodeID");
          int xcoord = results.getInt("xcoord");
          int ycoord = results.getInt("ycoord");
          String floor = results.getString("floor");
          String building = results.getString("building");
          String nodeType = results.getString("nodeType");
          String longName = results.getString("longName");
          String shortName = results.getString("shortName");

          Location SQLRow = new Location();
          SQLRow.nodeID = nodeID;
          SQLRow.xcoord = xcoord;
          SQLRow.ycoord = ycoord;
          SQLRow.floor = floor;
          SQLRow.building = building;
          SQLRow.nodeType = nodeType;
          SQLRow.longName = longName;
          SQLRow.shortName = shortName;

          locations.add(SQLRow);
        }
      } catch (SQLException e) {
        System.out.println("Locations not found");
      }

    } catch (SQLException e) {
      System.out.println("Database does not exist.");
    }
  }

  public void JavaToCSV(ArrayList<Location> locations, String csvFilem) throws IOException {
    PrintWriter fw = new PrintWriter(new File(csvFilem));

    fw.append("nodeID");
    fw.append(",");
    fw.append("xcoord");
    fw.append(",");
    fw.append("ycoord");
    fw.append(",");
    fw.append("floor");
    fw.append(",");
    fw.append("building");
    fw.append(",");
    fw.append("nodeType");
    fw.append(",");
    fw.append("longName");
    fw.append(",");
    fw.append("shortName");
    fw.append("\n");

    for (int i = 0;
        i < locations.size();
        i++) { // ask about how this was working without and = sign
      fw.append(locations.get(i).nodeID);
      fw.append(",");
      fw.append(Integer.toString(locations.get(i).xcoord));
      fw.append(",");
      fw.append(Integer.toString(locations.get(i).ycoord));
      fw.append(",");
      fw.append(locations.get(i).floor);
      fw.append(",");
      fw.append(locations.get(i).building);
      fw.append(",");
      fw.append(locations.get(i).nodeType);
      fw.append(",");
      fw.append(locations.get(i).longName);
      fw.append(",");
      fw.append(locations.get(i).shortName);
      fw.append("\n");
    }
    fw.close();
  }

  public void menu(String locFile) throws IOException, SQLException {
    System.out.println(
        "1 – Location Information\n"
            + "2 – Change Floor and Type\n"
            + "3 – Enter Location\n"
            + "4 – Delete Location\n"
            + "5 – Save Locations to CSV file\n"
            + "6 – Exit Program");

    Scanner userInput = new Scanner(System.in);
    int inputNumber = Integer.parseInt(userInput.nextLine());
    switch (inputNumber) {
      case 1:
        // csv to java
        storeCSVtoOBJ(locFile);
        // display locations and attributes
        System.out.println("Node | X | Y | Level | Building | Type | Long Name | Short Name");
        for (Location location : locations) {
          System.out.printf(
              "%s | %i | %i | %s | %s | %s | %s | %s \n",
              location.nodeID,
              location.xcoord,
              location.ycoord,
              location.floor,
              location.building,
              location.nodeType,
              location.longName,
              location.shortName);
        }
        // menu
        menu(locFile);
        break;
      case 2:
        System.out.println("Please input the node ID: ");
        String inputNodeID = userInput.nextLine();
        System.out.println("New floor: ");
        String inputNewFloor = userInput.nextLine();
        System.out.println("New location type");
        String inputNewType = userInput.nextLine();
        this.storeCSVtoOBJ(locFile);
        for (int i = 1; i < this.locations.size(); i++) {
          if (locations.get(i).nodeID.equals(inputNodeID)) {
            locations.get(i).floor = inputNewFloor;
            locations.get(i).nodeType = inputNewType;
          }
        }
        this.JavaToSQL();
        this.SQLToJava();
        this.JavaToCSV(locations, locFile);
        menu(locFile);
        break;
      case 3:
        // add a new entry to the SQL table
        // prompt for ID
        System.out.println("Enter the new location ID");
        String newNodeID = userInput.nextLine();
        Location newLocation = new Location(newNodeID);
        locations.add(newLocation);
        addLocation(newLocation);
        JavaToCSV(locations, locFile);
        menu(locFile);
        break;
      case 4:
        //
        break;
      case 5:
        System.out.println("Enter CSV file location name");
        Scanner sc = new Scanner(System.in);
        String CSVName = sc.nextLine();
        String csvFilePath = "src/main/resources/" + CSVName + ".csv";

        try {
          new File(csvFilePath);
          this.SQLToJava();
          this.JavaToCSV(this.locations, csvFilePath);

        } catch (IOException e) {
          System.out.println(e.fillInStackTrace());
        }
        menu(locFile);
        break;
      case 6:
        //
        break;
      default:
        menu(locFile);
        break;
    }
  }

  public void start(String csvFile) throws IOException, SQLException {
    storeCSVtoOBJ(csvFile);
    JavaToSQL();
    menu(csvFile);
  }

  public void addLocation(Location currLoc) throws SQLException {
    Connection connection = DriverManager.getConnection(DB_LOC + ";");
    Statement exampleStatement = connection.createStatement();
    exampleStatement.execute(
        "INSERT INTO Locations VALUES("
            + "'"
            + currLoc.nodeID
            + "',"
            + currLoc.xcoord
            + ","
            + currLoc.ycoord
            + ",'"
            + currLoc.floor
            + "','"
            + currLoc.building
            + "','"
            + currLoc.nodeType
            + "','"
            + currLoc.longName
            + "','"
            + currLoc.shortName
            + "')");
  }
}
