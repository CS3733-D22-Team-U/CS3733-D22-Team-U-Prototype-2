package edu.wpi.team_u.BackEnd;

import edu.wpi.team_u.BackEnd.Equipment.EquipmentDaoImpl;
import edu.wpi.team_u.BackEnd.Location.LocationDaoImpl;
import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Udb {

  public String DB_LOC = "jdbc:derby:UDB;";
  private LocationDaoImpl locationImpl = new LocationDaoImpl(DB_LOC);
  private EquipmentDaoImpl EquipmentImpl = new EquipmentDaoImpl(DB_LOC);

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
        locationImpl.printLocTableInTerm(locFile);
        menu(locFile);
        break;
      case 2:
        locationImpl.editLocValue(locFile, userInput);
        menu(locFile);
        break;
      case 3:
        locationImpl.addLoc(locFile, userInput);
        menu(locFile);
        break;
      case 4:
        locationImpl.removeLoc(locFile, userInput);
        menu(locFile);
        break;
      case 5:
        locationImpl.saveLocTableAsCSV(userInput);
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
