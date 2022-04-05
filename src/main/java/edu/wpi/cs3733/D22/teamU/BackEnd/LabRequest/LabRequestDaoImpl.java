package edu.wpi.cs3733.D22.teamU.BackEnd.LabRequest;

import edu.wpi.cs3733.D22.teamU.BackEnd.DataDao;
import edu.wpi.cs3733.D22.teamU.BackEnd.Request.Request;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class LabRequestDaoImpl implements DataDao<LabRequest> {
    public String DB_LOC;
    public ArrayList<LabRequest> labRequestsList = new ArrayList<LabRequest>();
    public String csvFile;

    public LabRequestDaoImpl(String db_loc, String csvfile) {
        DB_LOC = db_loc;
        csvFile = csvfile;
    }

    @Override
    public ArrayList<LabRequest> list() {
        return labRequestsList;
    }

    /**
     * Reads CSV file and puts the Equipment into an array list: EquipmentList
     *
     * @throws IOException
     */
    public void CSVToJava() throws IOException {
        labRequestsList = new ArrayList<LabRequest>();
        String s;
        File file = new File(csvFile);
        BufferedReader br = new BufferedReader(new FileReader(file));
        br.readLine();
        while ((s = br.readLine()) != null) {
            String[] row = s.split(",");
            if (row.length == 3) {
                labRequestsList.add(
                        new LabRequest(
                                row[0],
                                row[1],
                                row[2],
                                row[3],
                                row[4],
                                row[5]));
            }
        }
    }

    /**
     * Copies the array list: EquipmentList and writes it into the CSV file
     *
     * @param csvFile
     * @throws IOException
     */
    public void JavaToCSV(String csvFile) throws IOException {
        PrintWriter fw = new PrintWriter(new File(csvFile));

        fw.append("ID");
        fw.append(",");
        fw.append("Patient");
        fw.append(",");
        fw.append("Staff");
        fw.append(",");
        fw.append("LabType");
        fw.append(",");
        fw.append("Date");
        fw.append(",");
        fw.append("Time");
        fw.append("\n");

        for (int i = 0;
             i < labRequestsList.size();
             i++) { // ask about how this was working without and = sign
            fw.append(labRequestsList.get(i).getID());
            fw.append(",");
            fw.append(labRequestsList.get(i).getPatient());
            fw.append(",");
            fw.append(labRequestsList.get(i).getStaff());
            fw.append(",");
            fw.append(labRequestsList.get(i).getLabType());
            fw.append(",");
            fw.append(labRequestsList.get(i).getDate());
            fw.append(",");
            fw.append(labRequestsList.get(i).getTime());
            fw.append("\n");
        }
        fw.close();
    }

    public void JavaToSQL() {

        try {
            Connection connection = null;
            connection = DriverManager.getConnection(DB_LOC);

            Statement exampleStatement = connection.createStatement();
            try {
                exampleStatement.execute("Drop table Request");
            } catch (Exception e) {
                System.out.println("didn't drop table");
            }

            exampleStatement.execute(
                    "CREATE TABLE Request("
                            + "ID varchar(10) not null,"
                            + "name varchar(50) not null, "
                            + "amount int not null,"
                            + "typeOfRequest varchar(10),"
                            + "destination varchar(10) not null,"
                            + "date varchar(10) not null,"
                            + "time varchar(10) not null,"
                            + "pri int not null)");

            for (int j = 0; j < requestList.size(); j++) {
                Request currReq = requestList.get(j);
                exampleStatement.execute(
                        "INSERT INTO Request VALUES("
                                + "'"
                                + currReq.getID()
                                + "','"
                                + currReq.getName()
                                + "',"
                                + currReq.getAmount()
                                + ",'"
                                + currReq.getType()
                                + "','"
                                + currReq.getDestination()
                                + "','"
                                + currReq.getDate()
                                + "','"
                                + currReq.getTime()
                                + "',"
                                + currReq.getPri()
                                + ")");
            }

            connection.close();

        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
            return;
        }
    }

    public void SQLToJava() {
        requestList = new ArrayList<Request>();

        try {
            Connection connection = null;
            connection = DriverManager.getConnection(DB_LOC);

            Statement exampleStatement = connection.createStatement();

            try {
                ResultSet results;
                results = exampleStatement.executeQuery("SELECT * FROM Request");

                while (results.next()) {
                    String id = results.getString("ID");
                    String name = results.getString("name");
                    int amount = results.getInt("amount");
                    String type = results.getString("typeOfRequest");
                    String destination = results.getString("destination");
                    String date = results.getString("date");
                    String time = results.getString("time");
                    int pri = results.getInt("pri");

                    Request SQLRow = new Request(id, name, amount, type, destination, date, time, pri);

                    requestList.add(SQLRow);
                }
            } catch (SQLException e) {
                System.out.println("request not found");
            }

            connection.close();

        } catch (SQLException e) {
            System.out.println("Database does not exist.");
        }
    }

    public void printTable() throws IOException {
        // csv to java
        CSVToJava();
        // display locations and attributes
        System.out.println(
                "ID |\t Name |\t Amount |\t Type |\t Destination |\t Date |\t Time |\t Priority");
        for (Request request : this.requestList) {
            System.out.println(
                    request.ID
                            + " | \t"
                            + request.name
                            + " | \t"
                            + request.amount
                            + " | \t"
                            + request.typeOfRequest
                            + " | \t"
                            + request.destination
                            + " | \t"
                            + request.date
                            + " | \t"
                            + request.time
                            + " | \t"
                            + request.pri);
        }
        // menu
    }

    /*
    public void edit(String ID, String patient, String staff, String labType, String date, String time)
            throws IOException, SQLException {
        // takes entries from SQL table that match input node and updates it with a new floor and
        // location type
        // input ID
        // Scanner s = new Scanner(System.in);
        // System.out.println("Please input the name: ");
        // String inputName = s.nextLine();
        // input new floor
        // System.out.println("New Amount: ");
        // String inputNewAmount = s.nextLine();
        // input new location type
        // System.out.println("New In Use type");
        // String inputInUse = s.nextLine();
        // this.CSVToJava(csvFile); // t
        for (int i = 0; i < this.labRequestsList.size(); i++) {
            if (this.labRequestsList.get(i).getID().equals(ID)) {
                this.labRequestsList.get(i).patient = patient;
                this.labRequestsList.get(i).staff = staff;
                this.labRequestsList.get(i).labType = labType;
                this.labRequestsList.get(i).date = date;
                this.labRequestsList.get(i).time = time;
            }
        }
        this.JavaToCSV(csvFile);
    }
    */

    @Override
    public void edit(LabRequest data) throws IOException {
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

    /**
     * Prompts user for the information of a new lab request and then adds it to the csv file and database
     *
     * @param csvFile
     * @throws IOException
     */
    /*public void add(
                    String id,
                    String patient,
                    String staff,
                    String labType,
                    String date,
                    String time)
            throws IOException {
        LabRequest newLabRequest =
                new LabRequest(id, patient, staff, labType, date, time);
        this.labRequestsList.add(newLabRequest);
        this.JavaToCSV(csvFile);
    }*/

    @Override
    public void add(LabRequest data) throws IOException {
        // add a new entry to the SQL table
        try {
            labRequestsList.get(search(data.ID));
            System.out.println("A Request With This ID Already Exists");
        } catch (Exception e) {
            LabRequest newLabRequest = data;
            this.labRequestsList.add(newLabRequest);
            this.JavaToSQL();
            this.JavaToCSV(csvFile);
        }
    }

    /**
     * Prompts user for the name of the item they wish to remove and then removes that item from the
     * database and csv file
     *
     * @throws IOException
     */
    public void removeRequest(String id) throws IOException {
        // removes entries from SQL table that match input node
        // prompt for ID

        for (int i = this.requestList.size() - 1; i >= 0; i--) {
            if (this.requestList.get(i).getID().equals(id)) {
                this.requestList.remove(i);
            }
        }
        this.JavaToCSV(csvFile);
    }*/

    @Override
    public void remove(LabRequest data) throws IOException {
        // removes entries from SQL table that match input node
        try {
            this.labRequestsList.remove(search(data.ID));
            this.JavaToSQL();
            this.JavaToCSV(csvFile);
        } catch (Exception e) {
            System.out.println("This Data Point Was Not Found");
        }
    }
    }

    public void saveLocTableAsCSV() {
        // takes entries from SQL table and an input name, from there it makes a new CSV file
        // prompt for user input
        Scanner s = new Scanner(System.in);

        System.out.println("Enter CSV file location name");

        String CSVName = s.nextLine();
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

}
