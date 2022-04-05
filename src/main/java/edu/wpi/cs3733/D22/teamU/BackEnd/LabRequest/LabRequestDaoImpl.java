package edu.wpi.cs3733.D22.teamU.BackEnd.LabRequest;

import edu.wpi.cs3733.D22.teamU.BackEnd.DataDao;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LabRequestDaoImpl implements DataDao<LabRequest> {
  public String DB_LOC;
  public ArrayList<LabRequest> labRequestsList = new ArrayList<LabRequest>();
  public String csvFile;

  public LabRequestDaoImpl(String db_loc, String csvfile) {
    DB_LOC = db_loc;
    csvFile = csvfile;
  }

  @Override
  public ArrayList<LabRequest> list() {
    return labRequestsList;
  }

  /**
   * Reads CSV file and puts the Equipment into an array list: EquipmentList
   *
   * @throws IOException
   */
  public void CSVToJava() throws IOException {
    labRequestsList = new ArrayList<LabRequest>();
    String s;
    File file = new File(csvFile);
    BufferedReader br = new BufferedReader(new FileReader(file));
    br.readLine();
    while ((s = br.readLine()) != null) {
      String[] row = s.split(",");
      if (row.length == 3) {
        labRequestsList.add(new LabRequest(row[0], row[1], row[2], row[3], row[4], row[5]));
      }
    }
  }

  /**
   * Copies the array list: EquipmentList and writes it into the CSV file
   *
   * @param csvFile
   * @throws IOException
   */
  public void JavaToCSV(String csvFile) throws IOException {
    PrintWriter fw = new PrintWriter(new File(csvFile));

    fw.append("ID");
    fw.append(",");
    fw.append("Patient");
    fw.append(",");
    fw.append("Staff");
    fw.append(",");
    fw.append("LabType");
    fw.append(",");
    fw.append("Date");
    fw.append(",");
    fw.append("Time");
    fw.append("\n");

    for (int i = 0;
        i < labRequestsList.size();
        i++) { // ask about how this was working without and = sign
      fw.append(labRequestsList.get(i).getID());
      fw.append(",");
      fw.append(labRequestsList.get(i).getPatient());
      fw.append(",");
      fw.append(labRequestsList.get(i).getStaff());
      fw.append(",");
      fw.append(labRequestsList.get(i).getLabType());
      fw.append(",");
      fw.append(labRequestsList.get(i).getDate());
      fw.append(",");
      fw.append(labRequestsList.get(i).getTime());
      fw.append("\n");
    }
    fw.close();
  }

  public void JavaToSQL() {

    try {
      Connection connection = null;
      connection = DriverManager.getConnection(DB_LOC);

      Statement exampleStatement = connection.createStatement();
      try {
        exampleStatement.execute("Drop table LabRequest");
      } catch (Exception e) {
        System.out.println("didn't drop table");
      }

      exampleStatement.execute(
          "CREATE TABLE LabRequest("
              + "ID varchar(10) not null,"
              + "patient varchar(50) not null, "
              + "staff varchar(50) not null,"
              + "labType varchar(50),"
              + "date varchar(10) not null,"
              + "time varchar(10) not null)");

      for (int j = 0; j < labRequestsList.size(); j++) {
        LabRequest currLab = labRequestsList.get(j);
        exampleStatement.execute(
            "INSERT INTO LabRequest VALUES("
                + "'"
                + currLab.getID()
                + "','"
                + currLab.getPatient()
                + "','"
                + currLab.getStaff()
                + "','"
                + currLab.getLabType()
                + "','"
                + currLab.getDate()
                + "','"
                + currLab.getTime()
                + "')");
      }

      connection.close();

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return;
    }
  }

  public void SQLToJava() {
    labRequestsList = new ArrayList<LabRequest>();

    try {
      Connection connection = null;
      connection = DriverManager.getConnection(DB_LOC);

      Statement exampleStatement = connection.createStatement();

      try {
        ResultSet results;
        results = exampleStatement.executeQuery("SELECT * FROM LabRequest");

        while (results.next()) {
          String id = results.getString("ID");
          String patient = results.getString("patient");
          String staff = results.getString("staff");
          String labType = results.getString("labType");
          String date = results.getString("date");
          String time = results.getString("time");

          LabRequest SQLRow = new LabRequest(id, patient, staff, labType, date, time);

          labRequestsList.add(SQLRow);
        }
      } catch (SQLException e) {
        System.out.println("request not found");
      }

      connection.close();

    } catch (SQLException e) {
      System.out.println("Database does not exist.");
    }
  }

  public void printTable() throws IOException {
    // csv to java
    CSVToJava();
    // display locations and attributes
    System.out.println("ID |\t Patient |\t Staff |\t Type |\t Date |\t Time");
    for (LabRequest request : this.labRequestsList) {
      System.out.println(
          request.ID
              + " | \t"
              + request.patient
              + " | \t"
              + request.staff
              + " | \t"
              + request.labType
              + " | \t"
              + request.date
              + " | \t"
              + request.time);
    }
    // menu
  }

  /*
  public void edit(String ID, String patient, String staff, String labType, String date, String time)
          throws IOException, SQLException {
      // takes entries from SQL table that match input node and updates it with a new floor and
      // location type
      // input ID
      // Scanner s = new Scanner(System.in);
      // System.out.println("Please input the name: ");
      // String inputName = s.nextLine();
      // input new floor
      // System.out.println("New Amount: ");
      // String inputNewAmount = s.nextLine();
      // input new location type
      // System.out.println("New In Use type");
      // String inputInUse = s.nextLine();
      // this.CSVToJava(csvFile); // t
      for (int i = 0; i < this.labRequestsList.size(); i++) {
          if (this.labRequestsList.get(i).getID().equals(ID)) {
              this.labRequestsList.get(i).patient = patient;
              this.labRequestsList.get(i).staff = staff;
              this.labRequestsList.get(i).labType = labType;
              this.labRequestsList.get(i).date = date;
              this.labRequestsList.get(i).time = time;
          }
      }
      this.JavaToCSV(csvFile);
  }
  */

  @Override
  public void edit(LabRequest data) throws IOException {
    // takes entries from SQL table that match input node and updates it with a new floor and
    // location type
    // input ID
    try {
      list().set(search(data.ID), data);
      this.JavaToSQL(); // t
      this.JavaToCSV(csvFile); // t
    } catch (Exception e) {
      System.out.println("This Object Does Not Exist");
    }
  }

  /**
   * Prompts user for the information of a new lab request and then adds it to the csv file and
   * database
   *
   * @param csvFile
   * @throws IOException
   */
  /*public void add(
                  String id,
                  String patient,
                  String staff,
                  String labType,
                  String date,
                  String time)
          throws IOException {
      LabRequest newLabRequest =
              new LabRequest(id, patient, staff, labType, date, time);
      this.labRequestsList.add(newLabRequest);
      this.JavaToCSV(csvFile);
  }*/

  @Override
  public void add(LabRequest data) throws IOException {
    // add a new entry to the SQL table
    try {
      labRequestsList.get(search(data.ID));
      System.out.println("A Request With This ID Already Exists");
    } catch (Exception e) {
      LabRequest newLabRequest = data;
      this.labRequestsList.add(newLabRequest);
      this.JavaToSQL();
      this.JavaToCSV(csvFile);
    }
  }

  /**
   * Prompts user for the name of the item they wish to remove and then removes that item from the
   * database and csv file
   *
   * @throws IOException
   */
  /*public void removeRequest(String id) throws IOException {
      // removes entries from SQL table that match input node
      // prompt for ID

      for (int i = this.requestList.size() - 1; i >= 0; i--) {
          if (this.requestList.get(i).getID().equals(id)) {
              this.requestList.remove(i);
          }
      }
      this.JavaToCSV(csvFile);
  }*/

  @Override
  public void remove(LabRequest data) throws IOException {
    // removes entries from SQL table that match input node
    try {
      this.labRequestsList.remove(search(data.ID));
      this.JavaToSQL();
      this.JavaToCSV(csvFile);
    } catch (Exception e) {
      System.out.println("This Data Point Was Not Found");
    }
  }

  public void saveLocTableAsCSV() {
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

  @Override
  public int search(String id) { // TODO search
    int index = -1;
    for (int i = 0; i < list().size(); i++) if (id.equals(list().get(i).ID)) index = i;
    return index;
  }

  public LabRequest askUser() {
    Scanner labInput = new Scanner(System.in);

    String inputID;
    String inputPatient;
    String inputStaff;
    String inputType;
    String inputDate;
    String inputTime;

    System.out.println("Input lab ID: ");
    inputID = labInput.nextLine();

    System.out.println("Input lab patient: ");
    inputPatient = labInput.nextLine();

    System.out.println("Input lab staff: ");
    inputStaff = labInput.nextLine();

    System.out.println("Input lab labType: ");
    inputType = labInput.nextLine();

    System.out.println("Input lab date: ");
    inputDate = labInput.nextLine();

    System.out.println("Input lab time: ");
    inputTime = labInput.nextLine();

    return new LabRequest(inputID, inputPatient, inputStaff, inputType, inputDate, inputTime);
  }
}
