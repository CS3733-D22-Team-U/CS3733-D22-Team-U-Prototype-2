package edu.wpi.cs3733.D22.teamU.BackEnd;

/**
 * ask about Harsh's override idea to simplify all the functions the tower locations master CSV does
 * NOT have unique nodes ask about changing all of our array lists to hash maps
 */
import edu.wpi.cs3733.D22.teamU.BackEnd.Employee.Employee;
import edu.wpi.cs3733.D22.teamU.BackEnd.Employee.EmployeeDaoImpl;
import edu.wpi.cs3733.D22.teamU.BackEnd.Equipment.Equipment;
import edu.wpi.cs3733.D22.teamU.BackEnd.Equipment.EquipmentDaoImpl;
import edu.wpi.cs3733.D22.teamU.BackEnd.Location.Location;
import edu.wpi.cs3733.D22.teamU.BackEnd.Location.LocationDaoImpl;
import edu.wpi.cs3733.D22.teamU.BackEnd.Request.EquipRequest.EquipRequest;
import edu.wpi.cs3733.D22.teamU.BackEnd.Request.EquipRequest.EquipRequestDaoImpl;
import edu.wpi.cs3733.D22.teamU.BackEnd.Request.LabRequest.LabRequest;
import edu.wpi.cs3733.D22.teamU.BackEnd.Request.LabRequest.LabRequestDaoImpl;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.Scanner;

public class Udb {

  public String DB_LOC = "jdbc:derby:UDB;";
  public Connection connection;
  public LocationDaoImpl locationImpl;
  public EquipmentDaoImpl EquipmentImpl;
  public EmployeeDaoImpl EmployeeImpl;
  public EquipRequestDaoImpl equipRequestImpl;
  public LabRequestDaoImpl labRequestImpl;

  public static String copyFile(InputStream inputPath, String outputPath) throws IOException {
    File f = new File(outputPath);
    f.createNewFile();
    Files.copy(inputPath, f.getAbsoluteFile().toPath(), StandardCopyOption.REPLACE_EXISTING);
    inputPath.close();
    return outputPath;
  }

  public Udb(String username, String password, String[] CSVfiles) throws IOException {

    Statement statement = null;
    String authentication = DB_LOC + "user=" + username + ";password=" + password + ";";

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

    // set username password
    try {
      Connection connection = DriverManager.getConnection(authentication + "create=true;");
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

    // create connection
    try {
      connection = null;
      connection = DriverManager.getConnection(authentication);

      statement = connection.createStatement();
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
    }
    //
    locationImpl = new LocationDaoImpl(statement, CSVfiles[0]);
    EmployeeImpl = new EmployeeDaoImpl(statement, CSVfiles[1]);
    EquipmentImpl = new EquipmentDaoImpl(statement, CSVfiles[2]);
    equipRequestImpl = new EquipRequestDaoImpl(statement, CSVfiles[3]);
    labRequestImpl = new LabRequestDaoImpl(statement, CSVfiles[4]);

    locationImpl.CSVToJava();
    locationImpl.JavaToSQL();

    EmployeeImpl.CSVToJava();
    EmployeeImpl.JavaToSQL();

    EquipmentImpl.CSVToJava(locationImpl.list());
    EquipmentImpl.JavaToSQL();

    equipRequestImpl.CSVToJava();
    equipRequestImpl.JavaToSQL();

    labRequestImpl.CSVToJava();
    labRequestImpl.JavaToSQL();
  }

  // Function for closing global connection FRONT END MUST CALL THIS WHEN USER HITS THE EXIT BUTTON
  // OR THE X IN THE CORNER
  public void closeConnection() throws SQLException {
    connection.close();
  }

  // ============================================================= Facade Functions

  public void edit(Object thingToAdd) throws IOException {

    switch (thingToAdd.getClass().getSimpleName()) {
      case "Location":
        locationImpl.edit((Location) thingToAdd);
        break;

      case "Equipment":
        EquipmentImpl.edit((Equipment) thingToAdd);
        break;

      case "Employee":
        EmployeeImpl.edit((Employee) thingToAdd);
        break;

      case "EquipRequst":
        equipRequestImpl.edit((EquipRequest) thingToAdd);
        break;

      case "LabRequest":
        labRequestImpl.edit((LabRequest) thingToAdd);
        break;

      default:
        System.out.println("Object not in switch case for udb.edit()");
        break;
    }
  }

  public void add(Object thingToAdd) throws IOException {

    switch (thingToAdd.getClass().getSimpleName()) {
      case "Location":
        locationImpl.add((Location) thingToAdd);
        break;

      case "Equipment":
        EquipmentImpl.add((Equipment) thingToAdd);
        break;

      case "Employee":
        EmployeeImpl.add((Employee) thingToAdd);
        break;

      case "EquipRequest":
        equipRequestImpl.add((EquipRequest) thingToAdd);
        break;

      case "LabRequest":
        labRequestImpl.add((LabRequest) thingToAdd);
        break;

      default:
        System.out.println("Object not in switch case for udb.add()");
        break;
    }
  }

  public void remove(Object thingToAdd) throws IOException {

    switch (thingToAdd.getClass().getSimpleName()) {
      case "Location":
        locationImpl.remove((Location) thingToAdd);
        break;

      case "Equipment":
        EquipmentImpl.remove((Equipment) thingToAdd);
        break;

      case "Employee":
        EmployeeImpl.remove((Employee) thingToAdd);
        break;

      case "EquipRequest":
        equipRequestImpl.remove((EquipRequest) thingToAdd);
        break;

      case "LabRequest":
        labRequestImpl.remove((LabRequest) thingToAdd);
        break;

      default:
        System.out.println("Object not in switch case for udb.remove()");
        break;
    }
  }

  // todo: would this be better with an enum?
  public void saveTableAsCSV(String thingYouWantToSave, String nameOfCSV) throws SQLException {

    switch (thingYouWantToSave) {
      case "Locations":
        locationImpl.saveTableAsCSV(nameOfCSV);
        break;

      case "Equipments":
        EquipmentImpl.saveTableAsCSV(nameOfCSV);
        break;

      case "Employees":
        EmployeeImpl.saveTableAsCSV(nameOfCSV);
        break;

      case "EquipRequests":
        equipRequestImpl.saveTableAsCSV(nameOfCSV);
        break;

      case "LabRequests":
        labRequestImpl.saveTableAsCSV(nameOfCSV);
        break;

      default:
        System.out.println("Object not in switch case for udb.saveTableAsCSV()");
        break;
    }
  }

  // This function is called in main the starts the menu where a client can access and or change
  // data in our SQL data base
  // This calls all of our private functions

  public void menu() throws IOException, SQLException {

    Scanner userInput = new Scanner(System.in);

    System.out.println(
        "What database would you like to chose: \n"
            + "1 - Locations\n"
            + "2 - Employees\n"
            + "3 - Equipment\n"
            + "4 - Request\n"
            + "5 - Lab Request\n"
            + "6 - Quit");

    switch (userInput.nextInt()) {
      case 1:
        locationMenu();
        break;
      case 2:
        employeesMenu();
        break;
      case 3:
        equipmentMenu();
        break;
      case 4:
        requestMenu();
        break;
      case 5:
        labRequestMenu();
        break;

      case 6:
        // exits whole menu
        break;
    }
  }

  private void locationMenu() throws SQLException, IOException {
    Scanner locationsInput = new Scanner(System.in);

    System.out.println(
        "1 - List Location Information\n"
            + "2 - Change Location Floor and Type\n"
            + "3 - Enter New Location\n"
            + "4 - Delete Location \n"
            + "5 - Save Location Information to CSV file\n"
            + "6 - Return to Main Menu");

    switch (locationsInput.nextInt()) {
      case 1:
        locationImpl.printTable();
        locationMenu();
        break;
      case 2:
        edit(locationImpl.askUser());
        locationMenu();
        break;
      case 3:
        add(locationImpl.askUser());
        locationMenu();
        break;
      case 4:
        remove(locationImpl.askUser());
        locationMenu();
        break;
      case 5:
        Scanner justNeedCSVName = new Scanner(System.in);

        System.out.println("Enter the name of the CSV file");
        String nameOfFile = justNeedCSVName.nextLine();

        saveTableAsCSV("Locations", nameOfFile);
        locationMenu();
        break;
      case 6:
        // menu
        menu();
        break;
      default:
        System.out.println("Something went wrong");
        break;
    }
  }

  private void employeesMenu() throws SQLException, IOException {
    Scanner employeeInput = new Scanner(System.in);

    System.out.println(
        "1 - List Employee Information\n"
            + "2 - Edit Employee Information\n"
            + "3 - Enter New Employee\n"
            + "4 - Delete Employee\n"
            + "5 - Save Employee Information to CSV\n"
            + "6 - Return to Main Menu");
    switch (employeeInput.nextInt()) {
      case 1:
        EmployeeImpl.printTable();
        employeesMenu();
        break;
      case 2:
        edit(EmployeeImpl.askUser());
        employeesMenu();
        break;
      case 3:
        add(EmployeeImpl.askUser());
        employeesMenu();
        break;
      case 4:
        remove(EmployeeImpl.askUser());
        employeesMenu();
        break;
      case 5:
        Scanner justNeedCSVName = new Scanner(System.in);

        System.out.println("Enter the name of the CSV file");
        String nameOfFile = justNeedCSVName.nextLine();

        saveTableAsCSV("Employees", nameOfFile);
        employeesMenu();
        break;
      case 6:
        // menu
        menu();
        break;
    }
  }

  private void equipmentMenu() throws SQLException, IOException {
    Scanner equipmentInput = new Scanner(System.in);

    System.out.println(
        "1 - List Equipment Information\n"
            + "2 - Edit Equipment Information\n"
            + "3 - Enter New Equipment\n"
            + "4 - Delete Equipment\n"
            + "5 - Save Equipment Information to CSV\n"
            + "6 - Return to Main Menu");
    switch (equipmentInput.nextInt()) {
      case 1:
        EquipmentImpl.printTable();
        equipmentMenu();
        break;
      case 2:
        edit(EquipmentImpl.askUser());
        equipmentMenu();
        break;
      case 3:
        add(EquipmentImpl.askUser());
        equipmentMenu();
        break;
      case 4:
        remove(EquipmentImpl.askUser());
        equipmentMenu();
        break;
      case 5:
        Scanner justNeedCSVName = new Scanner(System.in);

        System.out.println("Enter the name of the CSV file");
        String nameOfFile = justNeedCSVName.nextLine();

        saveTableAsCSV("Equipments", nameOfFile);
        equipmentMenu();
        break;
      case 6:
        // menu
        menu();
        break;
    }
  }

  private void requestMenu() throws IOException, SQLException {
    Scanner requestInput = new Scanner(System.in);

    System.out.println(
        "1 - List Equipment Information\n"
            + "2 - Edit Equipment Information\n"
            + "3 - Enter New Equipment\n"
            + "4 - Delete Equipment\n"
            + "5 - Save Equipment Information to CSV\n"
            + "6 - Return to Main Menu");
    switch (requestInput.nextInt()) {
      case 1:
        equipRequestImpl.printTable();
        requestMenu();
        break;
      case 2:
        edit(equipRequestImpl.askUser());
        requestMenu();
        break;
      case 3:
        add(equipRequestImpl.askUser());
        requestMenu();
        break;
      case 4:
        remove(equipRequestImpl.askUser());
        requestMenu();
        break;
      case 5:
        Scanner justNeedCSVName = new Scanner(System.in);

        System.out.println("Enter the name of the CSV file");
        String nameOfFile = justNeedCSVName.nextLine();

        saveTableAsCSV("Requests", nameOfFile);
        requestMenu();
        break;
      case 6:
        // menu
        menu();
        break;
    }
  }

  private void labRequestMenu() throws SQLException, IOException {

    Scanner labMenu = new Scanner(System.in);

    System.out.println(
        "1 - List Lab Request Information\n"
            + "2 - Change Lab Request\n"
            + "3 - Enter New Lab Request\n"
            + "4 - Delete Lab Request\n"
            + "5 - Save Lab Request Information to CSV file\n"
            + "6 - Return to Main Menu");

    switch (labMenu.nextInt()) {
      case 1:
        labRequestImpl.printTable();
        labRequestMenu();
        break;
      case 2:
        labRequestImpl.edit(labRequestImpl.askUser());
        labRequestMenu();
        break;
      case 3:
        add(labRequestImpl.askUser());
        labRequestMenu();
        break;
      case 4:
        labRequestImpl.remove(labRequestImpl.askUser());
        labRequestMenu();
        break;
      case 5:
        Scanner justNeedCSVName = new Scanner(System.in);

        System.out.println("Enter the name of the CSV file");
        String nameOfFile = justNeedCSVName.nextLine();

        saveTableAsCSV("LabRequests", nameOfFile);
        labRequestMenu();
        break;
      case 6:
        // menu
        menu();
        break;
      default:
        labRequestMenu();
        break;
    }
  }
}
