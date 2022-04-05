package edu.wpi.cs3733.D22.teamU.BackEnd.LabRequest;

public class LabRequest {

    String patient;
    String staff;
    String labType;

    public LabRequest (String patient, String staff, String labType){
        this.patient = patient;
        this.staff = staff;
        this.labType = labType;
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
}
