package edu.wpi.cs3733.D22.teamU.BackEnd.Request.LaundryRequest;

import edu.wpi.cs3733.D22.teamU.BackEnd.DataDao;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LaundryRequestDaoImpl implements DataDao<LaundryRequest> {
  public Statement statement;
  public String csvFile;
  public ArrayList<LaundryRequest> laundryRequestsList = new ArrayList<LaundryRequest>();

  public LaundryRequestDaoImpl(Statement statement, String csvFile) {
    this.csvFile = csvFile;
    this.statement = statement;
  }

  @Override
  public ArrayList<LaundryRequest> list() {
    return laundryRequestsList;
  }

  @Override
  public void CSVToJava() throws IOException {
    laundryRequestsList = new ArrayList<LaundryRequest>();
    String s;
    File file = new File(csvFile);
    BufferedReader br = new BufferedReader(new FileReader(file));
    String[] header = br.readLine().split(",");
    int columns = header.length;
    while ((s = br.readLine()) != null) {
      String[] row = s.split(",");
      if (row.length == columns) {
        laundryRequestsList.add(
            new LaundryRequest(row[0], row[1], row[2], row[3], row[4], row[5], row[6], row[7]));
      }
    }
  }

  @Override
  public void JavaToSQL() {
    try {
      statement.execute("Drop table LaundryRequest");
    } catch (Exception e) {
      System.out.println("didn't drop table");
    }

    try {
      statement.execute(
          "CREATE TABLE LaundryRequest("
              + "ID varchar(10) not null,"
              + "name varchar(20) not null,"
              + "patientName varchar(20) not null,"
              + "staff varchar(20) not null,"
              + "status varchar(20) not null,"
              + "location varchar(15) not null,"
              + "date varchar(10) not null,"
              + "time varchar(10) not null)");

      for (int j = 0; j < laundryRequestsList.size(); j++) {
        LaundryRequest currLaud = laundryRequestsList.get(j);
        statement.execute(
            "INSERT INTO LaundryRequest VALUES("
                + "'"
                + currLaud.getID()
                + "','"
                + currLaud.getName()
                + "','"
                + currLaud.getPatientName()
                + "','"
                + currLaud.getStaff()
                + "','"
                + currLaud.getStatus()
                + "','"
                + currLaud.getLocation()
                + "','"
                + currLaud.getDate()
                + "','"
                + currLaud.getTime()
                + "')");
      }
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
    }
  }

  @Override
  public void SQLToJava() {
    laundryRequestsList = new ArrayList<LaundryRequest>();
    try {
      ResultSet results;
      results = statement.executeQuery("SELECT * FROM LaundryRequest");

      while (results.next()) {
        String ID = results.getString("ID");
        String name = results.getString("name");
        String patientName = results.getString("patientName");
        String staff = results.getString("staff");
        String status = results.getString("status");
        String location = results.getString("location");
        String date = results.getString("date");
        String time = results.getString("time");

        LaundryRequest SQLRow =
            new LaundryRequest(ID, name, patientName, staff, status, location, date, time);

        laundryRequestsList.add(SQLRow);
      }
    } catch (SQLException e) {
      System.out.println("Database does not exist.");
    }
  }

  @Override
  public void JavaToCSV(String csvFile) throws IOException {
    PrintWriter fw = new PrintWriter(new File(csvFile));

    fw.append("ID");
    fw.append(",");
    fw.append("Type");
    fw.append(",");
    fw.append("PatientName");
    fw.append(",");
    fw.append("Staff");
    fw.append(",");
    fw.append("Status");
    fw.append(",");
    fw.append("Location");
    fw.append(",");
    fw.append("Date");
    fw.append(",");
    fw.append("Time");
    fw.append(",");
    fw.append("\n");

    for (int i = 0; i < laundryRequestsList.size(); i++) {
      fw.append(laundryRequestsList.get(i).getID());
      fw.append(",");
      fw.append(laundryRequestsList.get(i).getName());
      fw.append(",");
      fw.append(laundryRequestsList.get(i).getPatientName());
      fw.append(",");
      fw.append(laundryRequestsList.get(i).getStaff());
      fw.append(",");
      fw.append(laundryRequestsList.get(i).getStatus());
      fw.append(",");
      fw.append(laundryRequestsList.get(i).getLocation());
      fw.append(",");
      fw.append(laundryRequestsList.get(i).getDate());
      fw.append(",");
      fw.append(laundryRequestsList.get(i).getTime());
      fw.append("\n");
    }
    fw.close();
  }

  @Override
  public void printTable() throws IOException {
    CSVToJava();
    System.out.println(
        "ID |\t Type |\t Patient Name |\t Staff |\t Status |\t Location |\t Date |\t Time");
    for (LaundryRequest request : this.laundryRequestsList) {
      System.out.println(
          request.ID
              + " | \t"
              + request.name
              + " | \t"
              + request.patientName
              + " | \t"
              + request.staff
              + " | \t"
              + request.status
              + " | \t"
              + request.location
              + " | \t"
              + request.date
              + " | \t"
              + request.time);
    }
  }

  @Override
  public void edit(LaundryRequest data) throws IOException {
    try {
      list().set(search(data.ID), data);
      this.JavaToSQL();
      this.JavaToCSV(csvFile);
    } catch (Exception e) {
      System.out.println("This Object Does Not Exist");
    }
  }

  @Override
  public void add(LaundryRequest data) throws IOException {
    try {
      laundryRequestsList.get(search(data.ID));
      System.out.println("A Request With This ID Already Exists");
    } catch (Exception e) {
      this.laundryRequestsList.add(data);
      this.JavaToSQL();
      this.JavaToCSV(csvFile);
    }
  }

  @Override
  public void remove(LaundryRequest data) throws IOException {
    try {
      this.laundryRequestsList.remove(search(data.ID));
      this.JavaToSQL();
      this.JavaToCSV(csvFile);
    } catch (Exception e) {
      System.out.println("This Data Point Was Not Found");
    }
  }

  @Override
  public int search(String id) {
    int index = -1;
    for (int i = 0; i < list().size(); i++) if (id.equals(list().get(i).ID)) index = i;
    return index;
  }

  @Override
  public void saveTableAsCSV(String nameOfCSV) throws SQLException {
    String csvFilePath = "./" + nameOfCSV + ".csv";

    try {
      new File(csvFilePath);
      this.SQLToJava();
      this.JavaToCSV(csvFilePath);

    } catch (IOException e) {
      System.out.println(e.fillInStackTrace());
    }
  }

  @Override
  public LaundryRequest askUser() {
    Scanner labInput = new Scanner(System.in);

    String inputID = "None";
    String inputName = "none";
    String inputPatient = "N/A";
    String inputStaff = "N/A";
    String inputStatus = "N/A";
    String inputLocation = "N/A";
    String inputDate = "N/A";
    String inputTime = "N/A";

    System.out.println("Input ID: ");
    inputID = labInput.nextLine();

    System.out.println("Input type: ");
    inputName = labInput.nextLine();

    return new LaundryRequest(
        inputID,
        inputName,
        inputPatient,
        inputStaff,
        inputStatus,
        inputLocation,
        inputDate,
        inputTime);
  }
}
