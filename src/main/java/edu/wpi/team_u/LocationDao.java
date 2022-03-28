package edu.wpi.team_u;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface LocationDao {

  public void CSVToJava(String csvFile) throws IOException;

  public void JavaToSQL();

  public void SQLToJava() throws SQLException;

  public void JavaToCSV(ArrayList<Location> locations, String csvFilem) throws IOException;
}
