package edu.wpi.team_u.BackEnd.EquipmentRequest;

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
