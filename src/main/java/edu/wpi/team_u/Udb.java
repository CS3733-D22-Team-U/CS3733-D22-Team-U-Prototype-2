package edu.wpi.team_u;

import java.sql.*;
import java.util.ArrayList;

public class Udb {

    private void JavaToSQL(){
        int j = 0;

        System.out.println("-------Embedded Apache Derby Connection Testing --------");
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Apache Derby Driver not found. Add the classpath to your module.");
            System.out.println("For IntelliJ do the following:");
            System.out.println("File | Project Structure, Modules, Dependency tab");
            System.out.println("Add by clicking on the green plus icon on the right of the window");
            System.out.println("Select JARs or directories. Go to the folder where the database JAR is located");
            System.out.println("Click OK, now you can compile your program and run it.");
            e.printStackTrace();
            return;
        }

        System.out.println("Apache Derby driver registered!");
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:derby:UDB;create=true");
            Statement exampleStatement = connection.createStatement();
            exampleStatement.execute("CREATE TABLE Locations(nodeID varchar(18) primary key, " +
                                                                "xcoord int not null," +
                                                                "ycoord int not null," +
                                                                "floor varchar(3) not null," +
                                                                "building varchar(6)," +
                                                                "nodeType varchar(6)," +
                                                                "longName varchar(900) not null," +
                                                                "shortName varchar(600))");
            while(j < locations.size - 1){
                Location currLoc = locations.get(j);
                exampleStatement.execute("INSERT INTO Locations VALUES(currLoc.nodeID," +
                                                                          "currLoc.xcoord," +
                                                                          "currLoc.ycoor,d" +
                                                                          "currLoc.floor," +
                                                                          "currLoc.building," +
                                                                          "currLoc.nodeType," +
                                                                          "currLoc.longName," +
                                                                          "currLoc.shortName)");
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

    private void SQLToJava() {

    }
}
