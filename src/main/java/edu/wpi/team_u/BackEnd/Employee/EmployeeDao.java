package edu.wpi.team_u.BackEnd.Employee;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public interface EmployeeDao {

    public void CSVToJava(String csvFile) throws IOException;

    public void JavaToSQL();

    public void SQLToJava() throws SQLException;

    public void JavaToCSV(String csvFile) throws IOException;

    public void printEmployeeTableInTerm(String csvFile) throws IOException;

    public void editEmployee(String csvFile, Scanner userInput) throws IOException, SQLException;

    public void addEmployee(String csvFile, Scanner userInput) throws IOException, SQLException;

    public void removeEmployee(String csvFile, Scanner userInput) throws IOException, SQLException;

    public void saveEmployeeTableAsCSV(Scanner userInput) throws SQLException;

}
