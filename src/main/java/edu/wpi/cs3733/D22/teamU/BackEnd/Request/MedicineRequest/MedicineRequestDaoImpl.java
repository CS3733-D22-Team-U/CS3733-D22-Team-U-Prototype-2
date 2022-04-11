package edu.wpi.cs3733.D22.teamU.BackEnd.Request.MedicineRequest;

import edu.wpi.cs3733.D22.teamU.BackEnd.DataDao;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class MedicineRequestDaoImpl implements DataDao<MedicineRequest> {
    public Statement statement;
    public ArrayList<MedicineRequest> medicineRequestList = new ArrayList<MedicineRequest>();
    public String csvFile;

    public MedicineRequestDaoImpl(Statement statement, String csvfile)
    {
        this.csvFile = csvfile;
        this.statement = statement;
    }

    @Override
    public ArrayList<MedicineRequest> list(){
        return medicineRequestList;
    }

    public void CSVToJava() throws IOException {
        medicineRequestList = new ArrayList<MedicineRequest>();
        String s;
        File file = new File(csvFile);
        BufferedReader br = new BufferedReader(new FileReader(file));
        br.readLine();
        while ((s = br.readLine()) != null) {
            String[] row = s.split(",");
            if (row.length == 8) {
                medicineRequestList.add(
                        new MedicineRequest(
                                row[0],
                                row[1],
                                row[2],
                                row[3],
                                row[4],
                                row[5],
                                row[6],
                                row[7]));
            }
        }
    }

    public void JavaToCSV(String csvFile) throws IOException {
        PrintWriter fw = new PrintWriter(new File(csvFile));

        fw.append("ID");
        fw.append(",");
        fw.append("Name");
        fw.append(",");
        fw.append("PatientName");
        fw.append(",");
        fw.append("Status");
        fw.append(",");
        fw.append("EmployeeName");
        fw.append(",");
        fw.append("Location");
        fw.append(",");
        fw.append("Date");
        fw.append(",");
        fw.append("Time");
        fw.append("\n");

        for (int i = 0;
             i < medicineRequestList.size();
             i++) { // ask about how this was working without and = sign
            fw.append(medicineRequestList.get(i).getID());
            fw.append(",");
            fw.append(medicineRequestList.get(i).getName());
            fw.append(",");
            fw.append(medicineRequestList.get(i).getPatientName());
            fw.append(",");
            fw.append(medicineRequestList.get(i).getStatus());
            fw.append(",");
            fw.append(medicineRequestList.get(i).getEmployeeName());
            fw.append(",");
            fw.append(",");
            fw.append(medicineRequestList.get(i).getLocation());
            fw.append(medicineRequestList.get(i).getDate());
            fw.append(",");
            fw.append(medicineRequestList.get(i).getTime());
            fw.append("\n");
        }
        fw.close();
    }

    public void JavaToSQL() {

        try {
            statement.execute("Drop table MedicineRequest");
        } catch (Exception e) {
            System.out.println("didn't drop table");
        }

        try {
            statement.execute(
                    "CREATE TABLE MedicineRequest("
                            + "ID varchar(10) not null,"
                            + "name varchar(50) not null, "
                            + "patientName varchar(50) not null, "
                            + "status varchar(50) not null, "
                            + "employeeName varchar(50) not null, "
                            + "location varchar(50) not null, "
                            + "date varchar(10) not null, "
                            + "time varchar(10) not null, ");
            for (int j = 0; j < medicineRequestList.size(); j++) {
                MedicineRequest currReq = medicineRequestList.get(j);
                statement.execute(
                        "INSERT INTO MedicineRequest VALUES("
                                + "'"
                                + currReq.getID()
                                + "','"
                                + currReq.getName()
                                + "','"
                                + currReq.getPatientName()
                                + "','"
                                + currReq.getStatus()
                                + "','"
                                + currReq.getEmployeeName()
                                + "','"
                                + currReq.getLocation()
                                + "','"
                                + currReq.getDate()
                                + "','"
                                + currReq.getTime()
                                + "')");
            }
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
        }
    }

    public void SQLToJava() {
        medicineRequestList = new ArrayList<MedicineRequest>();
        try {
            ResultSet results;
            results = statement.executeQuery("SELECT * FROM MedicineRequest");

            while (results.next()) {
                String id = results.getString("ID");
                String name = results.getString("name");
                String patientName = results.getString("patientName");
                String status = results.getString("status");
                String employeeName = results.getString("employeeName");
                String location = results.getString("location");
                String date = results.getString("date");
                String time = results.getString("time");


                MedicineRequest SQLRow =
                        new MedicineRequest(id, name, patientName, status, employeeName, location, date, time);

                medicineRequestList.add(SQLRow);
            }
        } catch (SQLException e) {
            System.out.println("request not found");
        }
    }

    public void printTable() throws IOException {
        // csv to java
        CSVToJava();
        // display locations and attributes
        System.out.println(
                "ID |\t Name |\t Amount |\t Type |\t Destination |\t Date |\t Time |\t Priority");
        for (MedicineRequest request : this.medicineRequestList) {
            System.out.println(
                    request.ID
                            + " | \t"
                            + request.name
                            + " | \t"
                            + request.patientName
                            + " | \t"
                            + request.status
                            + " | \t"
                            + request.employeeName
                            + " | \t"
                            + request.location
                            + " | \t"
                            + request.date
                            + " | \t"
                            + request.time
                            );
        }
    }

    @Override
    public void edit(MedicineRequest data) {
        // takes entries from SQL table that match input node and updates it with a new floor and
        // location type
        // input ID
        try {
            list().set(search(data.ID), data);
            this.JavaToSQL(); // t
            this.JavaToCSV(csvFile); // t
        } catch (Exception e) {
            System.out.println("This Object Does Not Exist");
        }
    }

    @Override
    public void add(MedicineRequest data) throws IOException {
        // add a new entry to the SQL table
        try {
            medicineRequestList.get(search(data.ID));
            System.out.println("An Object With This ID Already Exists");
        } catch (Exception e) {
            MedicineRequest newMedicineRequest = data;
            this.medicineRequestList.add(newMedicineRequest);
            this.JavaToSQL();
            this.JavaToCSV(csvFile);
        }
    }

    @Override
    public void remove(MedicineRequest data) throws IOException {
        // removes entries from SQL table that match input node
        try {
            this.medicineRequestList.remove(search(data.ID));
            this.JavaToSQL();
            this.JavaToCSV(csvFile);
        } catch (Exception e) {
            System.out.println("This Data Point Was Not Found");
        }
    }

    public void saveTableAsCSV(String CSVName) throws SQLException {
        // takes entries from SQL table and an input name, from there it makes a new CSV file

        String csvFilePath = "./" + CSVName + ".csv";

        try {
            new File(csvFilePath);
            this.SQLToJava();
            this.JavaToCSV(csvFilePath);

        } catch (IOException e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    @Override
    public int search(String id) { // TODO search
        int index = -1;
        for (int i = 0; i < list().size(); i++) if (id.equals(list().get(i).ID)) index = i;
        return index;
    }

    public MedicineRequest askUser() {
        Scanner reqInput = new Scanner(System.in);

        String inputID = "None";
        String inputName = "N/A";
        String inputPatientName = "N/A";
        String inputStatus = "N/A";
        String inputEmployeeName = "N/A";
        String inputLocation = "N/A";
        String inputDate = "N/A";
        String inputTime = "N/A";


        System.out.println("Input request ID: ");
        inputID = reqInput.nextLine();

        System.out.println("Input name: ");
        inputName = reqInput.nextLine();

        return new MedicineRequest(
                inputID,
                inputName,
                inputPatientName,
                inputStatus,
                inputEmployeeName,
                inputLocation,
                inputDate,
                inputTime);
    }
}
