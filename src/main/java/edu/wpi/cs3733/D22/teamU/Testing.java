package edu.wpi.cs3733.D22.teamU;

import edu.wpi.cs3733.D22.teamU.BackEnd.Location.LocationDaoImpl;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class Testing {

  String locTestCSV;

  public Testing(String[] CSVfiles) {
    this.locTestCSV = CSVfiles[4];
  }

  public String DB_LOC = "jdbc:derby:UDB;";
  public LocationDaoImpl locationImpl = new LocationDaoImpl(DB_LOC);

  @Test
  public void locCSVToJavaTest() throws IOException {
    locationImpl.CSVToJava(locTestCSV);
  }

  @Test
  public void locJavaToCSVTest() {}

  @Test
  public void locJavaToSQLTest() {}

  @Test
  public void locSQLToJavaTest() {}

  @Test
  public void locEditTest() {}

  @Test
  public void locAddTest() {}

  @Test
  public void locRemoveTest() {}

  @Test
  public void locMakeCSVTest() {}
}
