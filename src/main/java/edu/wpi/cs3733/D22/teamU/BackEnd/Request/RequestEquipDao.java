package edu.wpi.cs3733.D22.teamU.BackEnd.Request;

import java.io.IOException;
import java.sql.SQLException;

public interface RequestEquipDao {
  /**
   * Reads CSV file and puts the Equipment into an array list: EquipmentList
   *
   * @param csvFile
   * @throws IOException
   */
  public void CSVToJava(String csvFile) throws IOException;

  /**
   * Copies the array list: EquipmentList and writes it into the CSV file
   *
   * @param csvFile
   * @throws IOException
   */
  public void JavaToCSV(String csvFile) throws IOException;

  /**
   * Reads the array list: locations, then opens up a connection to the UDB database, then it
   * creates a new table called in the UDB database table: Locations. It then inserts the array
   * list: Locations into the UDB database table: Locations
   */
  public void JavaToSQL();

  /**
   * Clears the array list: locations and then reads the UDB database table: Locations then copies
   * the to the cleared array list
   *
   * @throws SQLException
   */
  public void SQLToJava() throws SQLException;

  // -------------------------------Start of frontend to backend
  // functions------------------------------------------//

  /**
   * Asks user for name of item they wish to edit and then ask to change the total amount and the
   * amount in use, Then changes the values in the database and csv file
   *
   * @param csvFile
   * @throws IOException
   * @throws SQLException
   */
  public void editEquipValue(
      String csvFile, String inputName, int inputNewAmount, String date, String time)
      throws IOException, SQLException;

  /**
   * Prompts user for the name of a new item and then adds it to the csv file and database
   *
   * @param csvFile
   * @throws IOException
   * @throws SQLException
   */
  public void addRequest(String csvFile, String newName, int amount, String date, String time)
      throws IOException;

  /**
   * Prompts user for the name of the item they wish to remove and then removes that item from the
   * database and csv file
   *
   * @param csvFile
   * @throws IOException
   * @throws SQLException
   */
  public void removeRequest(String csvFile, String name) throws IOException;

  // -------------------------------End of frontend to backend
  // functions------------------------------------------//

  // -------------------------------Start of debugging backend
  // functions------------------------------------------//

}
