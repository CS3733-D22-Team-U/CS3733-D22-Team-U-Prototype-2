package edu.wpi.cs3733.D22.teamU.BackEnd.LabRequest;

public class LabRequest {

    String ID;
    String patient;
    String staff;
    String labType;
    String date;
    String time;

    public LabRequest (String ID, String patient, String staff, String labType, String date, String time){
        this.ID = ID;
        this.patient = patient;
        this.staff = staff;
        this.labType = labType;
        this.date = date;
        this. time = time;
    }


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public String getLabType() {
        return labType;
    }

    public void setLabType(String labType) {
        this.labType = labType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
