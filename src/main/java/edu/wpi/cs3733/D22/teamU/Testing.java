package edu.wpi.cs3733.D22.teamU;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import edu.wpi.cs3733.D22.teamU.BackEnd.Location.Location;
import edu.wpi.cs3733.D22.teamU.BackEnd.Udb;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

public class Testing {

  String locTestCSV;
  String[] CSVfiles = {
    "csvTables/TowerLocations.csv",
    "csvTables/TowerEmployees.csv",
    "csvTables/TowerEquipment.csv",
    "csvTables/TowerEquipmentRequests.csv",
    "csvTables/TESTTowerLocations.csv"
  };
  Udb udb = new Udb("admin", "admin", CSVfiles);

  public String DB_LOC = "jdbc:derby:UDB;";

  public Testing() throws SQLException, IOException {}

  @Test
  public void locCSVToJavaTest() throws IOException, NullPointerException {

    // djd
    // System.out.println("Testing location csv to java");
    udb.locationImpl.CSVToJava(CSVfiles[4]);
    String[] answer = {"TEST", "100", "200", "HELL", "jjjj", "jUNIT", "jUNITSUCKS"};
    assertEquals(udb.locationImpl.locations.get(0).getNodeID(), answer[0]);
    // System.out.println();
    assertEquals(true, true);
    /*for (int i = 0; i < 8; i++) {
      assertEquals(locationImpl.locations.get(0), answer);
    }*/
  }

  @Test
  public void locJavaToCSVTest() throws IOException {
    /*File file = new File(locTestCSV);
    udb.locationImpl.JavaToCSV(locTestCSV);
    String[] answer = {"TEST", "100", "200", "HELL", "jjjj", "jUNIT", "jUNITSUCKS"};
    BufferedReader br = new BufferedReader(new FileReader(file));
    br.readLine();
    String s = br.readLine();
    String[] row = s.split(",");
    assertEquals(answer, row);*/
  }

  @Test
  public void locJavaToSQLTest() throws SQLException {
    /*locationImpl.JavaToSQL();
    boolean testResults = true;

    Connection connection = null;
    connection = DriverManager.getConnection(DB_LOC);

    Statement locTestStatement = connection.createStatement();

    try {
      locTestStatement.executeQuery("SELECT nodeID FROM Locations WHERE nodeID = 'TEST'");
    } catch (Exception e) {

    }*/
  }

  @Test
  public void locSQLToJavaTest() {}

  @Test
  public void locEditTest() {}

  @Test
  public void locAddTest() {}

  @Test
  public void locRemoveTest() throws SQLException, IOException {
    ArrayList<Location> baseLocationArray = udb.locationImpl.locations;
    udb.locationImpl.removeLoc("TEST", CSVfiles[4]);
    ArrayList<Location> testLocationArray = udb.locationImpl.locations;
    // remove TEST node
    assertNotEquals(baseLocationArray, testLocationArray, "hasn't deleted");
  }

  @Test
  public void locMakeCSVTest() {}

  // ----------------------- Equipment test
  @Test
  public void equipmentCSVToJavaTest() {
    // locationImpl.CSVToJava(locTestCSV);
  }

  @Test
  public void equipmentJavaToCSVTest() {}

  @Test
  public void equipmentJavaToSQLTest() {
    /*locationImpl.JavaToSQL();

     Connection connection = null;
    // connection = DriverManager.getConnection(DB_LOC + "user=" + username + ";password=" + password + ";");

     Statement locTestStatement = connection.createStatement();

     try {
       locTestStatement.executeQuery("SELECT nodeID FROM Locations WHERE nodeID = 'TEST1'");
     } catch (Exception e) {
       //testResults = false;
     }

     //assertEquals(true, testResults);*/
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
