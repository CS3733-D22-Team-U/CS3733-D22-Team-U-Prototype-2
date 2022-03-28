package edu.wpi.team_u;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface LocationDao {

  public ArrayList<Location> CSVToJava(String csvFile, ArrayList<Location> locations)
      throws IOException;

  public ArrayList<Location> JavaToSQL(ArrayList<Location> locations);

  public ArrayList<Location> SQLToJava(ArrayList<Location> locations) throws SQLException;

  public void JavaToCSV(ArrayList<Location> locations, String csvFilem) throws IOException;
}
