package edu.wpi.team_u.BackEnd.Location;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public interface LocationDao {

  public void CSVToJava(String csvFile) throws IOException;

  public void JavaToSQL();

  public void SQLToJava() throws SQLException;

  public void JavaToCSV(String csvFile) throws IOException;

  public void printLocTableInTerm(String csvFile) throws IOException;

  public void editLocValue(String csvFile, Scanner userInput) throws IOException, SQLException;

  public void addLoc(String csvFile, Scanner userInput) throws IOException, SQLException;

  public void removeLoc(String csvFile, Scanner userInput) throws IOException, SQLException;

  public void saveLocTableAsCSV(Scanner userInput) throws SQLException;
}
