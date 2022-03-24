package edu.wpi.team_u;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) throws IOException {
    System.out.println("Michael Akstin");
    System.out.println("Harsh Patel");
    System.out.println("Timothy Klein");
    System.out.println("Joselin Barbosa");
    System.out.println("Deepti Gosukonda");
    System.out.println("William Doyle");
    System.out.println("Marko Vila");
    System.out.println("Nick Biliouris");
    System.out.println("Kody Robinson");
    System.out.println("Belisha Genin");
    // App.launch(App.class, args);

    Udb udb = new Udb();
    String csvFile = "src/main/resources/TowerLocations.csv";
    udb.menu(csvFile);
  }
}
