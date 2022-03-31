package edu.wpi.cs3733.D22.teamU.BackEnd.EquipmentRequest;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class RequestDaoImpl implements RequestEquipDao {
  public String DB_LOC;
  public ArrayList<RequestEquip> requestEquipList = new ArrayList<RequestEquip>();

  public RequestDaoImpl(String db_loc) {
    DB_LOC = db_loc;
  }

  /**
   * Reads CSV file and puts the Equipment into an array list: EquipmentList
   *
   * @param csvFile
   * @throws IOException
   */
  public void CSVToJava(String csvFile) throws IOException {
    requestEquipList = new ArrayList<RequestEquip>();
    String s;
    File file = new File(csvFile);
    BufferedReader br = new BufferedReader(new FileReader(file));
    br.readLine();
    while ((s = br.readLine()) != null) {
      String[] row = s.split(",");
      if (row.length == 4) {
        requestEquipList.add(new RequestEquip(row[0], Integer.parseInt(row[1]), row[2], row[3]));
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

    fw.append("Name");
    fw.append(",");
    fw.append("Amount");
    fw.append(",");
    fw.append("Date");
    fw.append(",");
    fw.append("Time");
    fw.append("\n");

    for (int i = 0;
        i < requestEquipList.size();
        i++) { // ask about how this was working without and = sign
      fw.append(requestEquipList.get(i).getName());
      fw.append(",");
      fw.append(Integer.toString(requestEquipList.get(i).getAmount()));
      fw.append(",");
      fw.append(requestEquipList.get(i).getDate());
      fw.append(",");
      fw.append(requestEquipList.get(i).getTime());
      fw.append("\n");
    }
    fw.close();
  }

  public void JavaToSQL() {
    // add later
  }

  public void SQLToJava() throws SQLException {
    // add later
  }

  public void editEquipValue(
      String csvFile, String inputName, int inputNewAmount, String date, String time)
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
    for (int i = 0; i < this.requestEquipList.size(); i++) {
      if (this.requestEquipList.get(i).getName().equals(inputName)) {
        this.requestEquipList.get(i).amount = inputNewAmount;
        this.requestEquipList.get(i).date = date;
        this.requestEquipList.get(i).time = time;
      }
    }
    ; // t
    this.JavaToCSV(csvFile); // t
  }

  /**
   * Prompts user for the name of a new item and then adds it to the csv file and database
   *
   * @param csvFile
   * @throws IOException
   */
  public void addRequest(String csvFile, String newName, int amount, String date, String time)
      throws IOException {
    // add a new entry to the SQL table
    // prompt for ID

    RequestEquip newEquipment = new RequestEquip(newName, amount, date, time);
    this.requestEquipList.add(newEquipment);
    this.JavaToCSV(csvFile);
  }

  /**
   * Prompts user for the name of the item they wish to remove and then removes that item from the
   * database and csv file
   *
   * @param csvFile
   * @throws IOException
   */
  public void removeRequest(String csvFile, String name) throws IOException {
    // removes entries from SQL table that match input node
    // prompt for ID

    for (int i = this.requestEquipList.size() - 1; i >= 0; i--) {
      if (this.requestEquipList.get(i).getName().equals(name)) {
        this.requestEquipList.remove(i);
      }
    }
    this.JavaToCSV(csvFile);
  }
}
