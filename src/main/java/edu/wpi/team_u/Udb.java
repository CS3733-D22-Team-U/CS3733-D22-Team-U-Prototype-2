package edu.wpi.team_u;

import java.io.*;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Udb {

  public ArrayList<Location> locations = new ArrayList<Location>();
  private LocationDaoImpl locationImpl = new LocationDaoImpl();

  public static void main(String[] args) throws IOException, SQLException {
    Udb udb = new Udb();
    String username = args[0];
    String password = args[1];
    String csvFile;
    try {
      csvFile = args[2];
    } catch (Exception e) {
      System.out.println("Please define file path for .csv file for Tower Locations");
      return;
    }
    udb.start(username, password, csvFile);
  }

  public void start(String username, String password, String csvFile)
      throws IOException, SQLException {
    locationImpl.DB_LOC = locationImpl.DB_LOC + "user=" + username + ";password=" + password + ";";
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
      Connection connection = DriverManager.getConnection(locationImpl.DB_LOC + "create=true;");
      Statement exampleStatement = connection.createStatement();

      exampleStatement.executeUpdate(
          "CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.connection.requireAuthentication',true)");
      exampleStatement.executeUpdate(
          "CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.database.sqlAuthorization', true)");
      exampleStatement.executeUpdate(
          "CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY("
              + "'derby.authentication.provider', 'BUILTIN')");
      exampleStatement.executeUpdate(
          "CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.user.admin', 'admin')");
      exampleStatement.executeUpdate(
          "CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY("
              + "'derby.database.defaultConnectionMode', 'fullAccess')");
      connection.close();
    } catch (Exception e) {
      System.out.println("Wrong Username/Password");
      return;
    }
    locationImpl.CSVToJava(csvFile);
    locationImpl.JavaToSQL();
    menu(csvFile);
  }

  // This function is called in main the starts the menu where a client can access and or change
  // data in our SQL data base
  // This calls all of our private functions
  private void menu(String locFile) throws IOException, SQLException {
    System.out.println(
        "1 - Location Information\n"
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
        locationImpl.CSVToJava(locFile);
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
        locationImpl.CSVToJava(locFile); // t
        for (int i = 0; i < this.locations.size(); i++) {
          if (locations.get(i).nodeID.equals(inputNodeID)) {
            locations.get(i).floor = inputNewFloor;
            locations.get(i).nodeType = inputNewType;
          }
        }
        locationImpl.JavaToSQL(); // t
        locationImpl.SQLToJava(); // t
        locationImpl.JavaToCSV(locations, locFile); // t
        menu(locFile);
        break;
      case 3:
        // add a new entry to the SQL table
        // prompt for ID
        System.out.println("Enter the new location ID");
        String newNodeID = userInput.nextLine();
        Location newLocation = new Location(newNodeID);
        locations.add(newLocation);
        locationImpl.JavaToSQL();
        locationImpl.SQLToJava();
        locationImpl.JavaToCSV(locations, locFile);
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
        locationImpl.JavaToSQL();
        locationImpl.SQLToJava();
        locationImpl.JavaToCSV(locations, locFile);
        menu(locFile);
        break;
      case 5:
        // takes entries from SQL table and an input name, from there it makes a new CSV file
        System.out.println("Enter CSV file location name");
        Scanner sc = new Scanner(System.in);
        String CSVName = sc.nextLine();
        String csvFilePath = "./" + CSVName + ".csv";

        try {
          new File(csvFilePath);
          locationImpl.SQLToJava();
          locationImpl.JavaToCSV(this.locations, csvFilePath);

        } catch (IOException e) {
          System.out.println(e.fillInStackTrace());
        }
        menu(locFile);
        break;
      case 6:
        // exits the whole menu
        break;
      default:
        menu(locFile);
        break;
    }
  }
}
