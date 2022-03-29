package edu.wpi.team_u.BackEnd.Employee;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class EmployeeDaoImpl implements EmployeeDao {

  // make constant in locationDao
  public String DB_LOC;
  public ArrayList<Employee> employees = new ArrayList<Employee>();

  public EmployeeDaoImpl(String db_loc) {
    this.DB_LOC = db_loc;
  }

  // Takes in a CSV file and converts it to java objects
  public void CSVToJava(String csvFile) throws IOException {
    employees = new ArrayList<Employee>();
    String s;
    File file = new File(csvFile);
    BufferedReader br = new BufferedReader(new FileReader(file));
    br.readLine();
    while ((s = br.readLine()) != null) {
      String[] row = s.split(",");
      if (row.length == 4)
        employees.add(
            new Employee(
                row[0].trim(),
                row[1].trim(),
                Integer.parseInt(row[2].trim()),
                Boolean.parseBoolean(row[3].trim())));
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
        exampleStatement.execute("Drop table Employees");
      } catch (Exception e) {
        System.out.println("didn't drop table");
      }

      exampleStatement.execute(
          "CREATE TABLE Employees(employeeID varchar(18) not null, "
              + "occupation varchar(200) not null,"
              + "reports int not null,"
              + "onDuty boolean not null)");

      for (int j = 0; j < employees.size(); j++) {
        Employee currEmp = employees.get(j);
        exampleStatement.execute(
            "INSERT INTO Employees VALUES('"
                + currEmp.employeeID
                + "','"
                + currEmp.occupation
                + "',"
                + currEmp.reports
                + ",'"
                + currEmp.onDuty
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
    employees = new ArrayList<Employee>();

    try {
      Connection connection = null;
      connection = DriverManager.getConnection(DB_LOC);

      Statement exampleStatement = connection.createStatement();

      try {
        ResultSet results;
        results = exampleStatement.executeQuery("SELECT * FROM Employees");

        while (results.next()) {
          String employeeID = results.getString("employeeID");
          String occupation = results.getString("occupation");
          int reports = results.getInt("reports");
          boolean onDuty = results.getBoolean("onDuty");

          Employee SQLRow = new Employee(employeeID, occupation, reports, onDuty);

          employees.add(SQLRow);
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

    fw.append("employeeID");
    fw.append(",");
    fw.append("occupation");
    fw.append(",");
    fw.append("reports");
    fw.append(",");
    fw.append("onDuty");
    fw.append("\n");

    for (int i = 0;
        i < employees.size();
        i++) { // ask about how this was working without and = sign
      fw.append(employees.get(i).employeeID);
      fw.append(",");
      fw.append(employees.get(i).occupation);
      fw.append(",");
      fw.append(Integer.toString(employees.get(i).reports));
      fw.append(",");
      fw.append(Boolean.toString(employees.get(i).onDuty));
      fw.append("\n");
    }
    fw.close();
  }

  public void printEmployeeTableInTerm(String csvFile) throws IOException {
    // csv to java
    CSVToJava(csvFile);
    // display locations and attributes
    System.out.println("Employee ID |\t Occupation |\t Reports |\t On Duty");
    for (Employee employee : employees) {
      System.out.println(
          employee.employeeID
              + " | \t"
              + employee.occupation
              + " | \t"
              + employee.reports
              + " | \t"
              + employee.onDuty);
    }
    // menu
  }

  public void editEmployee(String csvFile) throws IOException, SQLException {
    // takes entries from SQL table that match input id and updates it with
    // a new occupation
    // a new number of reports
    Scanner s = new Scanner(System.in);
    System.out.println("Please input the employee ID: ");
    String inputEmployeeID = s.nextLine();

    // input new occupation
    System.out.println("New occupation: ");
    String inputNewOccupation = s.nextLine();
    // input number of new reports
    System.out.println("Number of new reports: ");
    int inputNewReports = s.nextInt();

    CSVToJava(csvFile); // t
    for (int i = 0; i < this.employees.size(); i++) {
      if (this.employees.get(i).employeeID.equals(inputEmployeeID)) {
        this.employees.get(i).occupation = inputNewOccupation;
        this.employees.get(i).reports = inputNewReports;
      }
    }
    this.JavaToSQL(); // t
    this.SQLToJava(); // t
    this.JavaToCSV(csvFile); // t
  }

  public void addEmployee(String csvFile) throws IOException, SQLException {
    // add a new entry to the SQL table
    // prompt for ID
    Scanner s = new Scanner(System.in);
    System.out.println("Enter the new employee ID");
    String newEmployeeID = s.nextLine();
    Employee newEmployee = new Employee(newEmployeeID);
    this.employees.add(newEmployee);
    this.JavaToSQL();
    this.SQLToJava();
    this.JavaToCSV(csvFile);
  }

  public void removeEmployee(String csvFile) throws IOException, SQLException {
    // removes entries from SQL table that match input node
    // prompt for ID
    Scanner s = new Scanner(System.in);
    System.out.println("Input ID for to delete employee: ");
    String userEmployeeID = s.nextLine(); // remove locations that match user input
    for (int i = this.employees.size() - 1; i >= 0; i--) {
      if (this.employees.get(i).employeeID.equals(userEmployeeID)) {
        this.employees.remove(i);
      }
    }
    this.JavaToSQL();
    this.SQLToJava();
    this.JavaToCSV(csvFile);
  }

  public void saveEmployeeTableAsCSV() throws SQLException {
    // takes entries from SQL table and an input name, from there it makes a new CSV file
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
}
