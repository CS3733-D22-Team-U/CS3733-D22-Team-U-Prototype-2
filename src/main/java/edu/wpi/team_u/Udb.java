package edu.wpi.team_u;

import java.io.*;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Udb {

  public ArrayList<Location> locations = new ArrayList<Location>();
  private String DB_LOC = "jdbc:derby:UDB;";

  public static void main(String[] args) throws IOException, SQLException {
    Udb udb = new Udb();
    // String csvFile = "src/main/resources/TowerLocations.csv";
    String username = args[0];
    String password = args[1];
    String csvFile = args[2];
    udb.start(username, password, csvFile);
  }

  public void start(String username, String password, String csvFile)
      throws IOException, SQLException {
    DB_LOC = DB_LOC + "user=" + username + ";password=" + password + ";";
    System.out.println(DB_LOC);
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

    try {
      Connection connection = null;
      connection = DriverManager.getConnection(DB_LOC + "create=true");
      Statement exampleStatement = connection.createStatement();
      exampleStatement.executeUpdate(
          "CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.connection.requireAuthentication',true)");
      exampleStatement.executeUpdate(
          "CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.database.sqlAuthorization', true)");
      exampleStatement.executeUpdate(
          "CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.user.admin', 'admin')");
      connection.close();
    } catch (Exception e) {
      System.out.println(e);
      return;
    }

    storeCSVtoOBJ(csvFile);
    JavaToSQL();
    menu(csvFile);
  }

  public void storeCSVtoOBJ(String csvFile) throws IOException {
    locations = new ArrayList<Location>();
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
        System.out.println(
            "Node |\t X |\t Y |\t Level |\t Building |\t Type |\t Long Name |\t Short Name");
        for (Location location : locations) {
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
        for (int i = 0; i < this.locations.size(); i++) {
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
        JavaToSQL();
        SQLToJava();
        JavaToCSV(locations, locFile);
        menu(locFile);
        break;
      case 4:
        // removes entries from SQL table that match input node
        // prompt for ID
        System.out.println("Input ID for to delete location: ");
        String userNodeID = userInput.nextLine(); // remove locations that match user input
        for (int i = locations.size() - 1; i >= 0; i--) {
          if (locations.get(i).nodeID.equals(userNodeID)) {
            locations.remove(i);
          }
        }
        this.JavaToSQL();
        this.SQLToJava();
        this.JavaToCSV(locations, locFile);
        menu(locFile);
        break;
      case 5:
        System.out.println("Enter CSV file location name");
        Scanner sc = new Scanner(System.in);
        String CSVName = sc.nextLine();
        String csvFilePath = "../" + CSVName + ".csv";

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
}
