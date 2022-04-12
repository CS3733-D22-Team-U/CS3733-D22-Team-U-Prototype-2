package edu.wpi.cs3733.D22.teamU.BackEnd.Request.MedicineRequest;

import edu.wpi.cs3733.D22.teamU.BackEnd.Employee.Employee;
import edu.wpi.cs3733.D22.teamU.BackEnd.Request.Request;

public class MedicineRequest extends Request {
    String patientName;
    String location;
    String status;

    public MedicineRequest(
            String ID,
            String name,
            String patientName,
            String status,
            Employee employee,
            String location,
            String date,
            String time
    ){
        this.ID = ID;
        this.name = name;
        this.patientName = patientName;
        this.status = status;
        this.employee = employee;
        this.location = location;
        this.date = date;
        this.time = time;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
