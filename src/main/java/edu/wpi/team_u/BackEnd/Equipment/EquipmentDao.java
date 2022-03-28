package edu.wpi.team_u.BackEnd.Equipment;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public interface EquipmentDao {

  public void CSVtoJava(String csvFile) throws IOException;

  public void JavaToSQL();

  public void SQLToJava() throws SQLException;

  public void JavaToCSV(String csvFile) throws IOException;

  public void printEquipTableInTerm(String csvFile) throws IOException;

  public void editEquipValue(String csvFile, Scanner userInput) throws IOException, SQLException;

  public void addEquip(String csvFile, Scanner userInput) throws IOException, SQLException;

  public void removeEquip(String csvFile, Scanner userInput) throws IOException, SQLException;

  public void saveEquipTableAsCSV(Scanner userInput) throws SQLException;
}
