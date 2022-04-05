package edu.wpi.cs3733.D22.teamU.BackEnd.Request;

import edu.wpi.cs3733.D22.teamU.BackEnd.DataDao;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class RequestDaoImpl implements DataDao<Request> {
  public String DB_LOC;
  public ArrayList<Request> requestList = new ArrayList<Request>();
  public String csvFile;

  public RequestDaoImpl(String db_loc, String csvfile) {
    DB_LOC = db_loc;
    csvFile = csvfile;
  }

  @Override
  public ArrayList<Request> list() {
    return requestList;
  }

  /**
   * Reads CSV file and puts the Equipment into an array list: EquipmentList
   *
   * @throws IOException
   */
  public void CSVToJava() throws IOException {
    requestList = new ArrayList<Request>();
    String s;
    File file = new File(csvFile);
    BufferedReader br = new BufferedReader(new FileReader(file));
    br.readLine();
    while ((s = br.readLine()) != null) {
      String[] row = s.split(",");
      if (row.length == 8) {
        requestList.add(
            new Request(
                row[0],
                row[1],
                Integer.parseInt(row[2]),
                row[3],
                row[4],
                row[5],
                row[6],
                Integer.parseInt(row[7])));
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
    fw.append("Name");
    fw.append(",");
    fw.append("Amount");
    fw.append(",");
    fw.append("Type");
    fw.append(",");
    fw.append("Destination");
    fw.append(",");
    fw.append("Date");
    fw.append(",");
    fw.append("Time");
    fw.append(",");
    fw.append("Priority");
    fw.append("\n");

    for (int i = 0;
        i < requestList.size();
        i++) { // ask about how this was working without and = sign
      fw.append(requestList.get(i).getID());
      fw.append(",");
      fw.append(requestList.get(i).getName());
      fw.append(",");
      fw.append(Integer.toString(requestList.get(i).getAmount()));
      fw.append(",");
      fw.append(requestList.get(i).getType());
      fw.append(",");
      fw.append(requestList.get(i).getDestination());
      fw.append(",");
      fw.append(requestList.get(i).getDate());
      fw.append(",");
      fw.append(requestList.get(i).getTime());
      fw.append(",");
      fw.append(Integer.toString(requestList.get(i).getPri()));
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
        exampleStatement.execute("Drop table Request");
      } catch (Exception e) {
        System.out.println("didn't drop table");
      }

      exampleStatement.execute(
          "CREATE TABLE Request("
              + "ID varchar(10) not null,"
              + "name varchar(50) not null, "
              + "amount int not null,"
              + "typeOfRequest varchar(10),"
              + "destination varchar(10) not null,"
              + "date varchar(10) not null,"
              + "time varchar(10) not null,"
              + "pri int not null)");

      for (int j = 0; j < requestList.size(); j++) {
        Request currReq = requestList.get(j);
        exampleStatement.execute(
            "INSERT INTO Request VALUES("
                + "'"
                + currReq.getID()
                + "','"
                + currReq.getName()
                + "',"
                + currReq.getAmount()
                + ",'"
                + currReq.getType()
                + "','"
                + currReq.getDestination()
                + "','"
                + currReq.getDate()
                + "','"
                + currReq.getTime()
                + "',"
                + currReq.getPri()
                + ")");
      }

      connection.close();

    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return;
    }
  }

  public void SQLToJava() {
    requestList = new ArrayList<Request>();

    try {
      Connection connection = null;
      connection = DriverManager.getConnection(DB_LOC);

      Statement exampleStatement = connection.createStatement();

      try {
        ResultSet results;
        results = exampleStatement.executeQuery("SELECT * FROM Request");

        while (results.next()) {
          String id = results.getString("ID");
          String name = results.getString("name");
          int amount = results.getInt("amount");
          String type = results.getString("typeOfRequest");
          String destination = results.getString("destination");
          String date = results.getString("date");
          String time = results.getString("time");
          int pri = results.getInt("pri");

          Request SQLRow = new Request(id, name, amount, type, destination, date, time, pri);

          requestList.add(SQLRow);
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
    System.out.println(
        "ID |\t Name |\t Amount |\t Type |\t Destination |\t Date |\t Time |\t Priority");
    for (Request request : this.requestList) {
      System.out.println(
          request.ID
              + " | \t"
              + request.name
              + " | \t"
              + request.amount
              + " | \t"
              + request.typeOfRequest
              + " | \t"
              + request.destination
              + " | \t"
              + request.date
              + " | \t"
              + request.time
              + " | \t"
              + request.pri);
    }
    // menu
  }

  public void edit(
      String csvFile,
      String inputID,
      String inputName,
      int inputNewAmount,
      String newType,
      String newDestination,
      String date,
      String time,
      int pri)
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
    for (int i = 0; i < this.requestList.size(); i++) {
      if (this.requestList.get(i).getID().equals(inputID)) {
        this.requestList.get(i).name = inputName;
        this.requestList.get(i).amount = inputNewAmount;
        this.requestList.get(i).typeOfRequest = newType;
        this.requestList.get(i).destination = newDestination;
        this.requestList.get(i).date = date;
        this.requestList.get(i).time = time;
        this.requestList.get(i).pri = pri;
      }
    }
    ; // t
    this.JavaToCSV(csvFile); // t
  }

  @Override
  public void edit(Request data) throws IOException {
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
   * Prompts user for the name of a new item and then adds it to the csv file and database
   *
   * @param csvFile
   * @throws IOException
   */
  public void add(
      String csvFile,
      String id,
      String newName,
      int amount,
      String newType,
      String newDestination,
      String date,
      String time,
      int pri)
      throws IOException {
    // add a new entry to the SQL table
    // prompt for ID

    Request newEquipment =
        new Request(id, newName, amount, newType, newDestination, date, time, pri);
    this.requestList.add(newEquipment);
    this.JavaToCSV(csvFile);
  }

  @Override
  public void add(Request data) throws IOException {
    // add a new entry to the SQL table
    try {
      requestList.get(search(data.ID));
      System.out.println("An Object With This ID Already Exists");
    } catch (Exception e) {
      Request newRequest = data;
      this.requestList.add(newRequest);
      this.JavaToSQL();
      this.JavaToCSV(csvFile);
    }
  }

  /**
   * Prompts user for the name of the item they wish to remove and then removes that item from the
   * database and csv file
   *
   * @param csvFile
   * @throws IOException
   */
  public void removeRequest(String csvFile, String id) throws IOException {
    // removes entries from SQL table that match input node
    // prompt for ID

    for (int i = this.requestList.size() - 1; i >= 0; i--) {
      if (this.requestList.get(i).getID().equals(id)) {
        this.requestList.remove(i);
      }
    }
    this.JavaToCSV(csvFile);
  }

  @Override
  public void remove(Request data) throws IOException {
    // removes entries from SQL table that match input node
    try {
      this.requestList.remove(search(data.ID));
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
}
