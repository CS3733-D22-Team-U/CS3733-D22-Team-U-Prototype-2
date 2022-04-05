package edu.wpi.cs3733.D22.teamU.BackEnd;

/**
 * ask about Harsh's override idea to simplify all the functions the tower locations master CSV does
 * NOT have unique nodes ask about changing all of our array lists to hash maps
 */
import edu.wpi.cs3733.D22.teamU.BackEnd.Employee.EmployeeDaoImpl;
import edu.wpi.cs3733.D22.teamU.BackEnd.Equipment.EquipmentDaoImpl;
import edu.wpi.cs3733.D22.teamU.BackEnd.Location.LocationDaoImpl;
import edu.wpi.cs3733.D22.teamU.BackEnd.Request.RequestDaoImpl;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.Scanner;

public class Udb {

  public String DB_LOC = "jdbc:derby:UDB;";
  public LocationDaoImpl locationImpl;
  public EquipmentDaoImpl EquipmentImpl;
  public EmployeeDaoImpl EmployeeImpl;
  public RequestDaoImpl requestImpl;

  public static String copyFile(InputStream inputPath, String outputPath) throws IOException {
    File f = new File(outputPath);
    f.createNewFile();
    Files.copy(inputPath, f.getAbsoluteFile().toPath(), StandardCopyOption.REPLACE_EXISTING);
    inputPath.close();
    return outputPath;
  }

  public Udb(String username, String password, String[] CSVfiles) throws IOException, SQLException {
    String authentication = DB_LOC + "user=" + username + ";password=" + password + ";";
    locationImpl = new LocationDaoImpl(authentication, CSVfiles[0]);
    EmployeeImpl = new EmployeeDaoImpl(authentication, CSVfiles[1]);
    EquipmentImpl = new EquipmentDaoImpl(authentication, CSVfiles[2]);
    requestImpl = new RequestDaoImpl(authentication, CSVfiles[3]);

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

    locationImpl.CSVToJava();
    locationImpl.JavaToSQL();
    EmployeeImpl.CSVToJava();
    EmployeeImpl.JavaToSQL();

    EquipmentImpl.CSVToJava();
    EquipmentImpl.JavaToSQL();
    requestImpl.CSVToJava();
  }

  // This function is called in main the starts the menu where a client can access and or change
  // data in our SQL data base
  // This calls all of our private functions

  public void menu(String[] CSVfiles) throws IOException, SQLException {

    Scanner userInput = new Scanner(System.in);

    System.out.println(
        "What database would you like to chose: \n"
            + "1 - Locations\n"
            + "2 - Employees\n"
            + "3 - Equipment\n"
            + "4 - Request\n"
            + "5 - Quit");

    switch (userInput.nextInt()) {
      case 1:
        locationMenu(CSVfiles);
        break;
      case 2:
        employeesMenu(CSVfiles);
        break;
      case 3:
        equipmentMenu(CSVfiles);
        break;
      case 4:
        requestMenu(CSVfiles);
        break;
      case 5:
        // exits whole menu
        break;
    }
  }

  private void locationMenu(String[] CSVfiles) throws SQLException, IOException {
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
        locationMenu(CSVfiles);
        break;
      case 2:
        locationImpl.edit(CSVfiles[0]);
        locationMenu(CSVfiles);
        break;
      case 3:
        locationImpl.add(CSVfiles[0]);
        locationMenu(CSVfiles);
        break;
      case 4:
        locationImpl.removeLoc(CSVfiles[0]);
        locationMenu(CSVfiles);
        break;
      case 5:
        locationImpl.saveLocTableAsCSV();
        locationMenu(CSVfiles);
        break;
      case 6:
        // menu
        menu(CSVfiles);
        break;
      default:
        System.out.println("Something went wrong");
        break;
    }
  }

  private void employeesMenu(String[] CSVfiles) throws SQLException, IOException {
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
        employeesMenu(CSVfiles);
        break;
      case 2:
        EmployeeImpl.editEmployee(CSVfiles[1]);
        employeesMenu(CSVfiles);
        break;
      case 3:
        EmployeeImpl.addEmployee(CSVfiles[1]);
        employeesMenu(CSVfiles);
        break;
      case 4:
        EmployeeImpl.removeEmployee(CSVfiles[1]);
        employeesMenu(CSVfiles);
        break;
      case 5:
        EmployeeImpl.saveEmployeeTableAsCSV();
        employeesMenu(CSVfiles);
        break;
      case 6:
        // menu
        menu(CSVfiles);
        break;
    }
  }

  private void equipmentMenu(String[] CSVfiles) throws SQLException, IOException {
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
        EquipmentImpl.printEquipTableInTerm(CSVfiles[2]);
        equipmentMenu(CSVfiles);
        break;
      case 2:
        EquipmentImpl.editEquipValue(CSVfiles[2]);
        equipmentMenu(CSVfiles);
        break;
      case 3:
        EquipmentImpl.addEquip(CSVfiles[2]);
        equipmentMenu(CSVfiles);
        break;
      case 4:
        EquipmentImpl.removeEquip(CSVfiles[2]);
        equipmentMenu(CSVfiles);
        break;
      case 5:
        EquipmentImpl.saveEquipTableAsCSV();
        equipmentMenu(CSVfiles);
        break;
      case 6:
        // menu
        menu(CSVfiles);
        break;
    }
  }

  private void requestMenu(String[] CSVfiles) throws IOException, SQLException {
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
        requestImpl.printTable();
        requestMenu(CSVfiles);
        break;
      case 2:
        String inputID;
        String name;
        int pri;
        System.out.println("Input request ID: ");
        inputID = requestInput.nextLine();

        System.out.println("Input request name: ");
        name = requestInput.nextLine();

        System.out.println("Input request pri: ");
        pri = requestInput.nextInt();

        requestImpl.edit(inputID, name, 1, "N/A", "N/A", "N/A", "N/A", pri);
        requestMenu(CSVfiles);
        break;
      case 3:
        System.out.println("Input request ID: ");
        inputID = requestInput.nextLine();

        System.out.println("Input request name: ");
        name = requestInput.nextLine();

        System.out.println("Input request pri: ");
        pri = requestInput.nextInt();

        requestImpl.add(inputID, name, 1, "N/A", "N/A", "N/A", "N/A", pri);
        requestMenu(CSVfiles);
        break;
      case 4:
        System.out.println("Input request ID: ");
        inputID = requestInput.nextLine();

        requestImpl.removeRequest(inputID);
        requestMenu(CSVfiles);
        break;
      case 5:
        requestImpl.saveLocTableAsCSV();
        requestMenu(CSVfiles);
        break;
      case 6:
        // menu
        menu(CSVfiles);
        break;
    }
  }
}
