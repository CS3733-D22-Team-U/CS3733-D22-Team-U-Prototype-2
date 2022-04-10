package edu.wpi.cs3733.D22.teamU.BackEnd.Request;

import edu.wpi.cs3733.D22.teamU.BackEnd.DataDao;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class RequestDaoImpl implements DataDao<Request> {
  public Statement statement;
  public ArrayList<Request> requestList = new ArrayList<Request>();
  public String csvFile;

  public RequestDaoImpl(Statement statement, String csvfile) {
    this.csvFile = csvfile;
    this.statement = statement;
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
      statement.execute("Drop table Request");
    } catch (Exception e) {
      System.out.println("didn't drop table");
    }

    try {
      statement.execute(
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
        statement.execute(
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
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
    }
  }

  public void SQLToJava() {
    requestList = new ArrayList<Request>();
    try {
      ResultSet results;
      results = statement.executeQuery("SELECT * FROM Request");

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
  }

  @Override
  public void edit(Request data) {
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
   * @param //csvFile
   * @throws IOException
   */
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
   * @throws IOException
   */
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

  /**
   * Prompts user for the name of a new file and then creates the new file in the project folder
   * then it copies the database table: EquipmentList into the CSV file
   *
   * @throws SQLException
   */
  public void saveTableAsCSV(String CSVName) throws SQLException {
    // takes entries from SQL table and an input name, from there it makes a new CSV file

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

  public Request askUser() {
    Scanner reqInput = new Scanner(System.in);

    String inputID = "None";
    String inputName = "N/A";
    int inputAmount = 0;
    String inputType = "N/A";
    String inputDestination = "N/A";
    String inputDate = "N/A";
    String inputTime = "N/A";
    int inputPriority = 0;

    System.out.println("Input request ID: ");
    inputID = reqInput.nextLine();

    System.out.println("Input name: ");
    inputName = reqInput.nextLine();

    return new Request(
        inputID,
        inputName,
        inputAmount,
        inputType,
        inputDestination,
        inputDate,
        inputTime,
        inputPriority);
  }
}
