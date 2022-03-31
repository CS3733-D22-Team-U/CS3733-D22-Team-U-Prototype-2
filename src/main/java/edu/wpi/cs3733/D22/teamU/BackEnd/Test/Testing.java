package edu.wpi.cs3733.D22.teamU.BackEnd.Test;

import edu.wpi.cs3733.D22.teamU.BackEnd.Location.LocationDaoImpl;
import org.junit.jupiter.api.Test;

import java.io.File;

public class Testing {

    public String DB_LOC = "jdbc:derby:UDB;";
    public LocationDaoImpl locationImpl = new LocationDaoImpl(DB_LOC);
    @Test
    public void locCSVToJavaTest(){

    }

    @Test
    public void locJavaToCSVTest(){

    }

    @Test
    public void locJavaToSQLTest(){

    }

    @Test
    public void locSQLToJavaTest(){

    }

    @Test
    public void locEditTest(){

    }

    @Test
    public void locAddTest(){

    }

    @Test
    public void locRemoveTest(){
    }

    @Test
    public void locMakeCSVTest(){

    }
}
