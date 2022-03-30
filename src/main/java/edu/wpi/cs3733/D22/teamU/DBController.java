package edu.wpi.cs3733.D22.teamU;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.SQLException;

public class DBController {

  public static void main(String[] args) throws IOException, SQLException {
    String username, password;
    try {
      username = args[0];
      password = args[1];
    } catch (Exception e) {
      username = "admin";
      password = "admin";
    }

    File folder = new File("csvTables");
    folder.mkdir();
    InputStream csvLocationFile =
        Main.class
            .getClassLoader()
            .getResourceAsStream("edu/wpi/cs3733/D22/teamU/csvTables/TowerLocations.csv");
    String location = copyFile(csvLocationFile, "csvTables/TowerLocations.csv");
    InputStream csvEmployee =
        Main.class
            .getClassLoader()
            .getResourceAsStream("edu/wpi/cs3733/D22/teamU/csvTables/TowerEmployees.csv");
    String employee = copyFile(csvEmployee, "csvTables/TowerEmployees.csv");
    InputStream csvEquipment =
        Main.class
            .getClassLoader()
            .getResourceAsStream("edu/wpi/cs3733/D22/teamU/csvTables/TowerEquipment.csv");
    String equipment = copyFile(csvEquipment, "csvTables/TowerEquipment.csv");

    String[] CSVfiles = {location, employee, equipment};

    // Udb udb = new Udb(username, password, CSVfiles);

    // udb.menu(CSVfiles); //Uncomment this to start terminal menu
  }

  public static String copyFile(InputStream inputPath, String outputPath) throws IOException {
    File f = new File(outputPath);
    // f.createNewFile();
    try {
      Files.copy(inputPath, f.getAbsoluteFile().toPath());
    } catch (Exception e) {
      // Doesn't override if files already exist
    }
    inputPath.close();
    return outputPath;
  }
}
