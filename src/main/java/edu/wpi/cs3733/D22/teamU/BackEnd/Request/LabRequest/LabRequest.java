package edu.wpi.cs3733.D22.teamU.BackEnd.Request.LabRequest;

import edu.wpi.cs3733.D22.teamU.BackEnd.Request.Request;

public class LabRequest extends Request {

  String patient;
  String staff;

  public LabRequest(
      String ID, String patient, String staff, String name, String date, String time) {
    this.ID = ID;
    this.patient = patient;
    this.staff = staff;
    this.name = name;
    this.date = date;
    this.time = time;
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
}
