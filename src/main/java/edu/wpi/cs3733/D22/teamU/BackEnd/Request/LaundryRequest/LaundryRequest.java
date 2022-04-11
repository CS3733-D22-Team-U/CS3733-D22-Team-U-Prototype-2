package edu.wpi.cs3733.D22.teamU.BackEnd.Request.LaundryRequest;

import edu.wpi.cs3733.D22.teamU.BackEnd.Request.Request;

public class LaundryRequest extends Request {

  String patientName;
  String staff;
  String status;
  String location;

  public LaundryRequest(
      String ID,
      String name,
      String patientName,
      String staff,
      String status,
      String location,
      String date,
      String time) {
    this.ID = ID;
    this.name = name; // type of lab request
    this.patientName = patientName;
    this.staff = staff;
    this.status = status;
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

  public String getStaff() {
    return staff;
  }

  public void setStaff(String staff) {
    this.staff = staff;
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
