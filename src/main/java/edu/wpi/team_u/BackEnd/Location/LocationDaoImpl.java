package edu.wpi.team_u.BackEnd.Location;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LocationDaoImpl implements LocationDao {

  // make constant in locationDao
  public String DB_LOC;
  public ArrayList<Location> locations = new ArrayList<Location>();

  public LocationDaoImpl(String db_loc) {
    DB_LOC = db_loc;
  }

  // Takes in a CSV file and converts it to java objects
  public void CSVToJava(String csvFile) throws IOException {
    locations = new ArrayList<Location>();
    String s;
    File file = new File(csvFile);
    BufferedReader br = new BufferedReader(new FileReader(file));
    br.readLine();
    while ((s = br.readLine()) != null) {
      String[] row = s.split(",");
      if (row.length == 8)
        locations.add(
            new Location(
                row[0],
                Integer.parseInt(row[1]),
                Integer.parseInt(row[2]),
                row[3],
                row[4],
                row[5],
                row[6],
                row[7]));
    }
  }

  // This function converts all of the CSV information that is stored in Java objects and
  // puts them into the the SQL database
  public void JavaToSQL() {

    try {
      Connection connection = null;
      connection = DriverManager.getConnection(DB_LOC);

      Statement exampleStatement = connection.createStatement();
      try {
        exampleStatement.execute("Drop table Locations");
      } catch (Exception e) {
        System.out.println("didn't drop table");
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

      connection.close();

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return;
    }
  }

  // This function takes all of the SQL database information into java objects
  public void SQLToJava() throws SQLException {
    locations = new ArrayList<Location>();

    try {
      Connection connection = null;
      connection = DriverManager.getConnection(DB_LOC);

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

      connection.close();

    } catch (SQLException e) {
      System.out.println("Database does not exist.");
    }
  }

  // This function converts the java objects of our CSV data into a new CSV file
  public void JavaToCSV(String csvFile) throws IOException {
    PrintWriter fw = new PrintWriter(new File(csvFile));

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

  public void printLocTableInTerm(String csvFile) throws IOException {
    // csv to java
    this.CSVToJava(csvFile);
    // display locations and attributes
    System.out.println(
        "Node |\t X |\t Y |\t Level |\t Building |\t Type |\t Long Name |\t Short Name");
    for (Location location : this.locations) {
      System.out.println(
          location.nodeID
              + " | \t"
              + location.xcoord
              + " | \t"
              + location.ycoord
              + " | \t"
              + location.floor
              + " | \t"
              + location.building
              + " | \t"
              + location.nodeType
              + " | \t"
              + location.longName
              + " | \t"
              + location.shortName);
    }
    // menu
  }

  public void editLocValue(String csvFile, Scanner userInput) throws IOException, SQLException {
    // takes entries from SQL table that match input node and updates it with a new floor and
    // location type
    // input ID
    System.out.println("Please input the node ID: ");
    String inputNodeID = userInput.nextLine();
    // input new floor
    System.out.println("New floor: ");
    String inputNewFloor = userInput.nextLine();
    // input new location type
    System.out.println("New location type");
    String inputNewType = userInput.nextLine();
    this.CSVToJava(csvFile); // t
    for (int i = 0; i < this.locations.size(); i++) {
      if (this.locations.get(i).nodeID.equals(inputNodeID)) {
        this.locations.get(i).floor = inputNewFloor;
        this.locations.get(i).nodeType = inputNewType;
      }
    }
    this.JavaToSQL(); // t
    this.SQLToJava(); // t
    this.JavaToCSV(csvFile); // t
  }

  public void addLoc(String csvFile, Scanner userInput) throws IOException, SQLException {
    // add a new entry to the SQL table
    // prompt for ID
    System.out.println("Enter the new location ID");
    String newNodeID = userInput.nextLine();
    Location newLocation = new Location(newNodeID);
    this.locations.add(newLocation);
    this.JavaToSQL();
    this.SQLToJava();
    this.JavaToCSV(csvFile);
  }

  public void removeLoc(String csvFile, Scanner userInput) throws IOException, SQLException {
    // removes entries from SQL table that match input node
    // prompt for ID
    System.out.println("Input ID for to delete location: ");
    String userNodeID = userInput.nextLine(); // remove locations that match user input
    for (int i = this.locations.size() - 1; i >= 0; i--) {
      if (this.locations.get(i).nodeID.equals(userNodeID)) {
        this.locations.remove(i);
      }
    }
    this.JavaToSQL();
    this.SQLToJava();
    this.JavaToCSV(csvFile);
  }

  public void saveLocTableAsCSV(Scanner userInput) throws SQLException {
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
