package edu.wpi.team_u.BackEnd;

import edu.wpi.team_u.BackEnd.Employee.EmployeeDaoImpl;
import edu.wpi.team_u.BackEnd.Equipment.EquipmentDaoImpl;
import edu.wpi.team_u.BackEnd.Location.LocationDaoImpl;
import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Udb {

  public String DB_LOC = "jdbc:derby:UDB;";
  private LocationDaoImpl locationImpl = new LocationDaoImpl(DB_LOC);
  private EquipmentDaoImpl EquipmentImpl = new EquipmentDaoImpl(DB_LOC);
  private EmployeeDaoImpl EmployeeImpl = new EmployeeDaoImpl(DB_LOC);

  public static void main(String[] args) throws IOException, SQLException {
    Udb udb = new Udb();
    String username = args[0];
    String password = args[1];
    String csvLocationFile = "src/main/resources/TowerLocations.csv";
    String csvEmployee = "src/main/resources/TowerEmployees.csv";
    String csvEquipment = "src/main/resources/TowerEquipment.csv";

    String[] CSVfiles = {csvLocationFile, csvEmployee, csvEquipment};

    udb.start(username, password, CSVfiles);
  }

  public void start(String username, String password, String[] CSVfiles)
      throws IOException, SQLException {
    locationImpl.DB_LOC = locationImpl.DB_LOC + "user=" + username + ";password=" + password + ";";
    EmployeeImpl.DB_LOC = EmployeeImpl.DB_LOC + "user=" + username + ";password=" + password + ";";
    EquipmentImpl.DB_LOC =
        EquipmentImpl.DB_LOC + "user=" + username + ";password=" + password + ";";

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

    locationImpl.CSVToJava(CSVfiles[0]);
    locationImpl.JavaToSQL();

    EmployeeImpl.CSVToJava(CSVfiles[1]);
    EmployeeImpl.JavaToSQL();

    EquipmentImpl.CSVToJava(CSVfiles[2]);
    EquipmentImpl.JavaToSQL();

    menu(CSVfiles);
  }

  // This function is called in main the starts the menu where a client can access and or change
  // data in our SQL data base
  // This calls all of our private functions
  private void menu(String[] CSVfiles) throws IOException, SQLException {
    System.out.println(
        "1 - print+ Information\n"
            + "2 – Change Floor and Type\n"
            + "3 – Enter Location\n"
            + "4 – Delete Location\n"
            + "5 – Save Locations to CSV file\n"
            + "6 – Exit Program");

    Scanner userInput = new Scanner(System.in);
    int inputNumber = Integer.parseInt(userInput.nextLine());
    switch (inputNumber) {
      case 1:
        System.out.println(
            "What database would you like to chose: \n"
                + " 1 - Locations\n"
                + " 2 - Employees\n"
                + " 3 - Equipment");

        switch (userInput.nextInt()) {
          case 1:
            locationImpl.printLocTableInTerm(CSVfiles[0]);
            break;
          case 2:
            EmployeeImpl.printEmployeeTableInTerm(CSVfiles[1]);
            break;
          case 3:
            EquipmentImpl.printEquipTableInTerm(CSVfiles[2]);
            break;
        }
        menu(CSVfiles);
        break;
      case 2:
        System.out.println(
            "What database would you like to chose: \n"
                + " 1 - Locations\n"
                + " 2 - Employees\n"
                + " 3 - Equipment");

        switch (userInput.nextInt()) {
          case 1:
            locationImpl.editLocValue(CSVfiles[0]);
            break;
          case 2:
            EmployeeImpl.editEmployee(CSVfiles[1]);
            break;
          case 3:
            EquipmentImpl.editEquipValue(CSVfiles[2]);
            break;
        }
        menu(CSVfiles);
        break;
      case 3:
        System.out.println(
            "What database would you like to chose: \n"
                + " 1 - Locations\n"
                + " 2 - Employees\n"
                + " 3 - Equipment");

        switch (userInput.nextInt()) {
          case 1:
            locationImpl.addLoc(CSVfiles[0]);
            break;
          case 2:
            EmployeeImpl.addEmployee(CSVfiles[1]);
            break;
          case 3:
            EquipmentImpl.addEquip(CSVfiles[2]);
            break;
        }

        menu(CSVfiles);
        break;
      case 4:
        System.out.println(
            "What database would you like to chose: \n"
                + " 1 - Locations\n"
                + " 2 - Employees\n"
                + " 3 - Equipment");

        switch (userInput.nextInt()) {
          case 1:
            locationImpl.removeLoc(CSVfiles[0]);
            break;
          case 2:
            EmployeeImpl.removeEmployee(CSVfiles[1]);
            break;
          case 3:
            EquipmentImpl.removeEquip(CSVfiles[2]);
            break;
        }
        menu(CSVfiles);
        break;
      case 5:
        System.out.println(
            "What database would you like to chose: \n"
                + " 1 - Locations\n"
                + " 2 - Employees\n"
                + " 3 - Equipment");

        switch (userInput.nextInt()) {
          case 1:
            locationImpl.saveLocTableAsCSV();
            break;
          case 2:
            EmployeeImpl.saveEmployeeTableAsCSV();
            break;
          case 3:
            EquipmentImpl.saveEquipTableAsCSV();
            break;
        }
        menu(CSVfiles);
        break;
      case 6:
        // exits the whole menu
        break;
      default:
        menu(CSVfiles);
        break;
    }
  }
}
