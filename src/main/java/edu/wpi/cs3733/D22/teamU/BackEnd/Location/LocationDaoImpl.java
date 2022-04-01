package edu.wpi.cs3733.D22.teamU.BackEnd.Location;

import edu.wpi.cs3733.D22.teamU.BackEnd.DataDao;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LocationDaoImpl implements DataDao<Location> {

  // make constant in locationDao
  public String DB_LOC;
  public ArrayList<Location> locations = new ArrayList<Location>();
  public String csvFile;

  /**
   * Contructor for LocationDaoImpl
   *
   * @param db_loc
   */
  public LocationDaoImpl(String db_loc, String csvFile) {
    DB_LOC = db_loc;
    this.csvFile = csvFile;
  }

  // Takes in a CSV file and converts it to java objects
  /**
   * Reads CSV file and puts the Locations into an array list: locations
   *
   * @throws IOException
   */
  @Override
  public void CSVToJava() throws IOException {
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

  @Override
  public ArrayList<Location> list() {
    return locations;
  }

  /**
   * Reads the array list: locations, then opens up a connection to the UDB database, then it
   * creates a new table called in the UDB database table: Locations. It then inserts the array
   * list: Locations into the UDB database table: Locations
   */
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
  /**
   * Clears the array list: locations and then reads the UDB database table: Locations then copies
   * the to the cleared array list
   *
   * @throws SQLException
   */
  @Override
  public void SQLToJava() {
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

  @Override
  public void printTable() {

  }

  @Override
  public void edit(Location data) {

  }

  @Override
  public void add(Location data) {

  }

  @Override
  public void remove(Location data) {

  }

  @Override
  public void search(Location data) {

  }

  // This function converts the java objects of our CSV data into a new CSV file
  /**
   * Copies the array list: locations and writes it into the CSV file
   *
   * @param csvFile
   * @throws IOException
   */
  @Override
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

  /**
   * Prints out the Contents of the CSV file TowerLocations.csv
   *
   * @param csvFile
   * @throws IOException
   */
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

  /**
   * Asks user for nodeID they wish to edit and then ask to change the floor and the node type, it
   * then changes the values in the database and csv file
   *
   * @param csvFile
   * @throws IOException
   * @throws SQLException
   */
  public void editLocValue(String csvFile) throws IOException, SQLException {
    // takes entries from SQL table that match input node and updates it with a new floor and
    // location type
    // input ID
    Scanner s = new Scanner(System.in);

    System.out.println("Please input the node ID: ");
    String inputNodeID = s.nextLine();
    // input new floor
    System.out.println("New floor: ");
    String inputNewFloor = s.nextLine();
    // input new location type
    System.out.println("New location type");
    String inputNewType = s.nextLine();
    this.CSVToJava(csvFile); // t
    for (int i = 0; i < this.locations.size(); i++) {
      if (this.locations.get(i).nodeID.equals(inputNodeID)) {
        this.locations.get(i).floor = inputNewFloor;
        this.locations.get(i).nodeType = inputNewType;
      }
    }
    this.JavaToSQL();
    this.SQLToJava();
    this.JavaToCSV(csvFile);
  }

  public void editLocValue(String nodeID, String floor, String nodeType, String csvFile)
      throws IOException, SQLException {
    // takes entries from SQL table that match input node and updates it with a new floor and
    // location type
    // input ID
    this.CSVToJava(csvFile); // t
    for (int i = 0; i < this.locations.size(); i++) {
      if (this.locations.get(i).nodeID.equals(nodeID)) {
        this.locations.get(i).floor = floor;
        this.locations.get(i).nodeType = nodeType;
      }
    }
    this.JavaToSQL(); // t
    this.SQLToJava(); // t
    this.JavaToCSV(csvFile); // t
  }

  /**
   * Prompts user for the nodeID of a new room and then adds it to the csv file and database
   *
   * @param csvFile
   * @throws IOException
   * @throws SQLException
   */
  public void addLoc(String csvFile) throws IOException, SQLException {
    // add a new entry to the SQL table
    // prompt for ID
    Scanner s = new Scanner(System.in);
    System.out.println("Enter the new location ID");
    String newNodeID = s.nextLine();
    Location newLocation = new Location(newNodeID);
    this.locations.add(newLocation);
    this.JavaToSQL();
    this.SQLToJava();
    this.JavaToCSV(csvFile);
  }

  public void addLoc(String nodeID, String csvFile) throws IOException, SQLException {
    // add a new entry to the SQL table
    Location newLocation = new Location(nodeID);
    this.locations.add(newLocation);
    this.JavaToSQL();
    this.SQLToJava();
    this.JavaToCSV(csvFile);
  }

  /**
   * Prompts user for the nodeID of the room they wish to remove and then removes that room from the
   * database and csv file
   *
   * @param csvFile
   * @throws IOException
   * @throws SQLException
   */
  public void removeLoc(String csvFile) throws IOException, SQLException {
    // removes entries from SQL table that match input node
    // prompt for ID
    Scanner s = new Scanner(System.in);
    System.out.println("Input ID for to delete location: ");
    String userNodeID = s.nextLine(); // remove locations that match user input
    for (int i = this.locations.size() - 1; i >= 0; i--) {
      if (this.locations.get(i).nodeID.equals(userNodeID)) {
        this.locations.remove(i);
      }
    }
    this.JavaToSQL();
    this.SQLToJava();
    this.JavaToCSV(csvFile);
  }

  public void removeLoc(String nodeID, String csvFile) throws IOException, SQLException {
    // removes entries from SQL table that match input node
    for (int i = this.locations.size() - 1; i >= 0; i--) {
      if (this.locations.get(i).nodeID.equals(nodeID)) {
        this.locations.remove(i);
      }
    }
    this.JavaToSQL();
    this.SQLToJava();
    this.JavaToCSV(csvFile);
  }

  /**
   * Prompts user for the name of a new file and then creates the new file in the project folder
   * then it copies the database table: Locations into the CSV file
   *
   * @throws SQLException
   */
  public void saveLocTableAsCSV() throws SQLException {
    // takes entries from SQL table and an input name, from there it makes a new CSV file
    // prompt for user input
    Scanner s = new Scanner(System.in);

    System.out.println("Enter CSV file location name");

    String CSVName = s.nextLine();
    String csvFilePath = "./" + CSVName + ".csv";

    try {
      new File(csvFilePath);
      this.SQLToJava();
      this.JavaToCSV(csvFilePath);

    } catch (IOException e) {
      System.out.println(e.fillInStackTrace());
    }
  }

  public void saveLocTableAsCSV(String locationName) throws SQLException {
    // takes entries from SQL table and an input name, from there it makes a new CSV file
    String csvFilePath = "./" + locationName + ".csv";

    try {
      new File(csvFilePath);
      this.SQLToJava();
      this.JavaToCSV(csvFilePath);

    } catch (IOException e) {
      System.out.println(e.fillInStackTrace());
    }
  }
}
