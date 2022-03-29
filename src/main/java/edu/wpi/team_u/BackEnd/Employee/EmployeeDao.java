package edu.wpi.team_u.BackEnd.Employee;

import java.io.IOException;
import java.sql.SQLException;

public interface EmployeeDao {

  public void CSVToJava(String csvFile) throws IOException;

  public void JavaToSQL();

  public void SQLToJava() throws SQLException;

  public void JavaToCSV(String csvFile) throws IOException;

  public void printEmployeeTableInTerm(String csvFile) throws IOException;

  public void editEmployee(String csvFile) throws IOException, SQLException;

  public void addEmployee(String csvFile) throws IOException, SQLException;

  public void removeEmployee(String csvFile) throws IOException, SQLException;

  public void saveEmployeeTableAsCSV() throws SQLException;
}
