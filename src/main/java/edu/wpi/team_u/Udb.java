package edu.wpi.team_u;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class Udb {

  ArrayList<Location> locations = new ArrayList<Location>();

  public void storeCSVtoOBJ(String csvFile) throws IOException {
    String s;
    File file = new File(csvFile);
    BufferedReader br = new BufferedReader(new FileReader(file));
    int i = 0;
    while ((s = br.readLine()) != null) {
      String[] row = s.split(",");

      locations.add(new Location());
      if (i > 0) {
        locations.get(i).StrtoLoc(row);
      }
      i++;
    }
    for (int j = 1; j < locations.size(); j++) {
      System.out.println(locations.get(j).nodeID);
    }
  }

  private class Location {

    String nodeID;
    int xcoord;
    int ycoord;
    String floor;
    String building;
    String nodeType;
    String longName;
    String shortName;

    void StrtoLoc(String[] row) {
      this.nodeID = row[0];
      this.xcoord = Integer.parseInt(row[1]);
      this.ycoord = Integer.parseInt(row[2]);
      this.floor = row[3];
      this.building = row[4];
      this.nodeType = row[5];
      this.longName = row[6];
      this.shortName = row[7];
    }
  }

  public void JavaToSQL() {
    int j = 1;

    System.out.println("-------Embedded Apache Derby Connection Testing --------");
    try {
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
    } catch (ClassNotFoundException e) {
      System.out.println("Apache Derby Driver not found. Add the classpath to your module.");
      System.out.println("For IntelliJ do the following:");
      System.out.println("File | Project Structure, Modules, Dependency tab");
      System.out.println("Add by clicking on the green plus icon on the right of the window");
      System.out.println(
          "Select JARs or directories. Go to the folder where the database JAR is located");
      System.out.println("Click OK, now you can compile your program and run it.");
      e.printStackTrace();
      return;
    }

    System.out.println("Apache Derby driver registered!");
    Connection connection = null;

    try {
      connection = DriverManager.getConnection("jdbc:derby:UDB;create=true");
      Statement exampleStatement = connection.createStatement();
      exampleStatement.execute("Drop table Locations");
      exampleStatement.execute(
          "CREATE TABLE Locations(nodeID varchar(18) primary key, "
              + "xcoord int not null,"
              + "ycoord int not null,"
              + "floor varchar(3) not null,"
              + "building varchar(6),"
              + "nodeType varchar(6),"
              + "longName varchar(900) not null,"
              + "shortName varchar(600))");
      while (j < locations.size()) {
        Location currLoc = locations.get(j);
        exampleStatement.execute(
            "INSERT INTO Locations(nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName) "
                + "VALUES(,'"
                + currLoc.nodeID
                + "',"
                + ""
                + currLoc.xcoord
                + ","
                + "currLoc.ycoord,"
                + "'currLoc.floor',"
                + "'currLoc.building',"
                + "'currLoc.nodeType',"
                + "'currLoc.longName',"
                + "'currLoc.shortName')");
        j++;
      }

      connection.close();
    } catch (SQLException e) {
      System.out.println("Connection failed. Check output console.");
      e.printStackTrace();
      return;
    }
    System.out.println("Apache Derby connection established!");
  }

  private void SQLToJava() {}
}
