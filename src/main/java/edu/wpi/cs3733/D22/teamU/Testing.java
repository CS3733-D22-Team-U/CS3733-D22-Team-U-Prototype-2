package edu.wpi.cs3733.D22.teamU;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.wpi.cs3733.D22.teamU.BackEnd.Location.Location;
import edu.wpi.cs3733.D22.teamU.BackEnd.Location.LocationDaoImpl;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
  public void locJavaToSQLTest() throws SQLException {
    locationImpl.JavaToSQL();
    boolean testResults = true;

    Connection connection = null;
    connection = DriverManager.getConnection(DB_LOC);

    Statement locTestStatement = connection.createStatement();

    try {
      locTestStatement.executeQuery("SELECT nodeID FROM Locations WHERE nodeID = 'TEST'");
    } catch (Exception e) {

    }
  }

  @Test
  public void locSQLToJavaTest() {}

  @Test
  public void locEditTest() {}

  @Test
  public void locAddTest() {}

  @Test
  public void locRemoveTest() throws SQLException, IOException {
    locationImpl.removeLoc(locTestCSV);
    ArrayList<Location> testLocationArray = locationImpl.locations;
    // remove TEST node
    // assertNull()
  }

  @Test
  public void locMakeCSVTest() {}

  // ----------------------- Equipment test
  @Test
  public void equipmentCSVToJavaTest() throws IOException {
    locationImpl.CSVToJava(locTestCSV);
  }

  @Test
  public void equipmentJavaToCSVTest() {}

  @Test
  public void equipmentJavaToSQLTest() throws SQLException {
    locationImpl.JavaToSQL();

    Connection connection = null;
    connection = DriverManager.getConnection(DB_LOC + "user=" + username + ";password=" + password + ";");

    Statement locTestStatement = connection.createStatement();

    try {
      locTestStatement.executeQuery("SELECT nodeID FROM Locations WHERE nodeID = 'TEST1'");
    } catch (Exception e) {
      testResults = false;
    }

    assertEquals(true, testResults);
  }

  @Test
  public void equipmentSQLToJavaTest() {}

  @Test
  public void equipmentEditTest() {}

  @Test
  public void equipmentAddTest() {}

  @Test
  public void equipmentRemoveTest() {}

  @Test
  public void equipmentMakeCSVTest() {}
}
