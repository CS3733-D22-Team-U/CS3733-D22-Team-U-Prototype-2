package edu.wpi.team_u.BackEnd.Location;

import java.io.IOException;
import java.sql.SQLException;

public interface LocationDao {

  /**
   * Reads CSV file and puts the Locations into an array list: locations
   * @param csvFile
   * @throws IOException
   */
  public void CSVToJava(String csvFile) throws IOException;

  /**
   * Reads the array list: locations, then opens up a connection to the UDB database,
   * then it creates a new table called in the UDB database table: Locations. It then inserts
   * the array list: Locations into the UDB database table: Locations
   */
  public void JavaToSQL();

  /**
   * Clears the array list: locations and then reads the UDB database table: Locations
   * then copies the to the cleared array list
   * @throws SQLException
   */
  public void SQLToJava() throws SQLException;

  /**
   * Copies the array list: locations and writes it into the CSV file
   * @param csvFile
   * @throws IOException
   */
  public void JavaToCSV(String csvFile) throws IOException;

  /**
   * Prints out the Contents of the CSV file TowerLocations.csv
   * @param csvFile
   * @throws IOException
   */
  public void printLocTableInTerm(String csvFile) throws IOException;

  /**
   * Asks user for nodeID they wish to edit and then ask to change
   * the floor and the node type, it then changes the values in
   * the database and csv file
   * @param csvFile
   * @throws IOException
   * @throws SQLException
   */
  public void editLocValue(String csvFile) throws IOException, SQLException;

  /**
   * Prompts user for the nodeID of a new room and then adds it to the csv file and database
   * @param csvFile
   * @throws IOException
   * @throws SQLException
   */
  public void addLoc(String csvFile) throws IOException, SQLException;

  /**
   * Prompts user for the nodeID of the room they wish to remove and then removes that room
   * from the database and csv file
   * @param csvFile
   * @throws IOException
   * @throws SQLException
   */
  public void removeLoc(String csvFile) throws IOException, SQLException;

  /**
   * Prompts user for the name of a new file and then creates the new file in the project folder
   * then it copies the database table: Locations into the CSV file
   * @throws SQLException
   */
  public void saveLocTableAsCSV() throws SQLException;
}
