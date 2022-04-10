package edu.wpi.cs3733.D22.teamU.BackEnd.Employee;

import edu.wpi.cs3733.D22.teamU.BackEnd.DataDao;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class EmployeeDaoImpl implements DataDao<Employee> {

  // make constant in locationDao
  public Statement statement;
  public String CSVfile;
  public ArrayList<Employee> List = new ArrayList<Employee>();

  public EmployeeDaoImpl(Statement statement, String CSVfile)
  {
    this.CSVfile = CSVfile;
    this.statement = statement;
  }

  public ArrayList<Employee> list() {
    return this.List;
  }

  /**
   * CSVToJava: converts the CSV file at the given filepath into a list of Java objects
   *
   * @throws IOException
   */
  public void CSVToJava() throws IOException {
    List = new ArrayList<Employee>();
    String s;
    File file = new File(CSVfile);
    BufferedReader br = new BufferedReader(new FileReader(file));
    br.readLine();
    while ((s = br.readLine()) != null) {
      String[] row = s.split(",");
      if (row.length == 4)
        List.add(
            new Employee(
                row[0].trim(),
                row[1].trim(),
                Integer.parseInt(row[2].trim()),
                Boolean.parseBoolean(row[3].trim())));
    }
  }

  /** JavaToSQL: takes the global list of Java objects and translates them into a SQL database */
  public void JavaToSQL() {

    try {
      statement.execute("Drop table Employees");
    } catch (Exception e) {
      System.out.println("didn't drop table");
    }
    try {
      statement.execute(
              "CREATE TABLE Employees(employeeID varchar(18) not null, "
                      + "occupation varchar(200) not null,"
                      + "reports int not null,"
                      + "onDuty boolean not null)");

      for (int j = 0; j < List.size(); j++) {
        Employee currEmp = List.get(j);
        statement.execute(
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
    }catch(SQLException e)
    {
      System.out.println("hsbd");
    }

  }

  /** SQLToJava: takes the SQL database and overwrites the global list of Java objects */
  public void SQLToJava() {
    List = new ArrayList<Employee>();
    try {
      ResultSet results;
      results = statement.executeQuery("SELECT * FROM Employees");

      while (results.next()) {
        String employeeID = results.getString("employeeID");
        String occupation = results.getString("occupation");
        int reports = results.getInt("reports");
        boolean onDuty = results.getBoolean("onDuty");

        Employee SQLRow = new Employee(employeeID, occupation, reports, onDuty);

        List.add(SQLRow);
      }

    } catch (SQLException e) {
      System.out.println("employee does not exist.");
    }
  }

  /**
   * JavaToCSV: takes the global list of Java objects and writes them into a CSV document with the
   * given filepath string
   *
   * @param csvFile
   * @throws IOException
   */
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

    for (int i = 0; i < List.size(); i++) {
      fw.append(List.get(i).employeeID);
      fw.append(",");
      fw.append(List.get(i).occupation);
      fw.append(",");
      fw.append(Integer.toString(List.get(i).reports));
      fw.append(",");
      fw.append(Boolean.toString(List.get(i).onDuty));
      fw.append("\n");
    }
    fw.close();
  }

  /**
   * printEmployeeTableInTerm: prints out the Employees from the given CSV filepath, printing a
   * table with each Employee's attributes
   *
   * @throws IOException
   */
  public void printTable() throws IOException {
    // csv to java
    CSVToJava();
    // display locations and attributes
    System.out.println("Employee ID |\t Occupation |\t Reports |\t On Duty");
    for (Employee employee : List) {
      System.out.println(
          employee.employeeID
              + " | \t"
              + employee.occupation
              + " | \t"
              + employee.reports
              + " | \t"
              + employee.onDuty);
    }
  }

  // -----------------------------Start of debugging backend
  // functions------------------------------//

  /**
   * editEmployee: makes a SQL table from the given CSV filepath, then prompts for an ID and updates
   * the employee with the matching ID, then converts the modified SQL table into a CSV, storing at
   * the original filepath
   *
   * @param csvFile
   * @throws IOException
   * @throws SQLException
   */
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

    CSVToJava(); // t
    for (int i = 0; i < this.List.size(); i++) {
      if (this.List.get(i).employeeID.equals(inputEmployeeID)) {
        this.List.get(i).occupation = inputNewOccupation;
        this.List.get(i).reports = inputNewReports;
      }
    }
    this.JavaToSQL(); // t
    this.SQLToJava(); // t
    this.JavaToCSV(csvFile); // t
  }

  /**
   * addEmployee: adds a new employee with the prompted ID to the global Java list, then updates the
   * SQL table and the CSV file at the given filepath
   *
   * @param csvFile
   * @throws IOException
   * @throws SQLException
   */
  public void addEmployee(String csvFile) throws IOException, SQLException {
    // add a new entry to the SQL table
    // prompt for ID
    Scanner s = new Scanner(System.in);
    System.out.println("Enter the new employee ID");
    String newEmployeeID = s.nextLine();
    Employee newEmployee = new Employee(newEmployeeID);
    this.List.add(newEmployee);
    this.JavaToSQL();
    this.SQLToJava();
    this.JavaToCSV(csvFile);
  }

  /**
   * removeEmployee: removes the new employee with the prompted ID from the global Java list, then
   * updates the SQL table and the CSV file at the given filepath
   *
   * @param csvFile
   * @throws IOException
   * @throws SQLException
   */
  public void removeEmployee(String csvFile) throws IOException, SQLException {
    // removes entries from SQL table that match input node
    // prompt for ID
    Scanner s = new Scanner(System.in);
    System.out.println("Input ID for to delete employee: ");
    String userEmployeeID = s.nextLine(); // remove locations that match user input
    for (int i = this.List.size() - 1; i >= 0; i--) {
      if (this.List.get(i).employeeID.equals(userEmployeeID)) {
        this.List.remove(i);
      }
    }
    this.JavaToSQL();
    this.SQLToJava();
    this.JavaToCSV(csvFile);
  }

  /**
   * saveEmployeeTableAsCSV: Converts the SQL table to a CSV file, saving it with the prompted file
   * name
   *
   * @throws SQLException
   */
  public void saveEmployeeTableAsCSV() throws SQLException {
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

  // -----------------------------End of debugging backend functions------------------------------//

  // -----------------------------Start of frontend backend
  // functions------------------------------//

  /**
   * editEmployee: makes a SQL table from the given CSV filepath, then prompts for an ID and updates
   * the employee with the matching ID, then converts the modified SQL table into a CSV, storing at
   * the original filepath
   *
   * @param data
   * @throws IOException
   */
  public void edit(Employee data) throws IOException {

    CSVToJava(); // t
    for (int i = 0; i < this.List.size(); i++) {
      if (this.List.get(i).employeeID.equals(data.getEmployeeID())) {
        this.List.get(i).occupation = data.getOccupation();
        this.List.get(i).reports = data.getReports();
      }
    }
    this.JavaToSQL(); // t
    this.SQLToJava(); // t
    this.JavaToCSV(CSVfile); // t
  }

  /**
   * addEmployee: adds a new employee with the prompted ID to the global Java list, then updates the
   * SQL table and the CSV file at the given filepath
   *
   * @param data
   * @throws IOException
   */
  public void add(Employee data) throws IOException {
    // add a new entry to the SQL table
    // prompt for ID

    Employee newEmployee = new Employee(data.getEmployeeID());
    this.List.add(newEmployee);
    this.JavaToSQL();
    this.SQLToJava();
    this.JavaToCSV(CSVfile);
  }

  /**
   * removeEmployee: removes the new employee with the prompted ID from the global Java list, then
   * updates the SQL table and the CSV file at the given filepath
   *
   * @param data
   * @throws IOException
   */
  public void remove(Employee data) throws IOException {
    // removes entries from SQL table that match input node
    // prompt for ID

    for (int i = this.List.size() - 1; i >= 0; i--) {
      if (this.List.get(i).employeeID.equals(data.getEmployeeID())) {
        this.List.remove(i);
      }
    }
    this.JavaToSQL();
    this.SQLToJava();
    this.JavaToCSV(CSVfile);
  }

  /**
   * saveEmployeeTableAsCSV: Converts the SQL table to a CSV file, saving it with the prompted file
   * name
   *
   * @throws SQLException
   */
  public void saveEmployeeTableAsCSV(String CSVName) throws SQLException {
    String csvFilePath = "./" + CSVName + ".csv";

    try {
      new File(csvFilePath);
      this.SQLToJava();
      this.JavaToCSV(csvFilePath);

    } catch (IOException e) {
      System.out.println(e.fillInStackTrace());
    }
  }

  /**
   * Search for index with the employee ID
   *
   * @param id
   * @return int
   */
  public int search(String id) {
    int index = -1;
    for (int i = 0; i < list().size(); i++) if (id.equals(list().get(i).getEmployeeID())) index = i;
    return index;
  }
}
