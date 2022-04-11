package edu.wpi.cs3733.D22.teamU.BackEnd.Request.MedicineRequest;

import edu.wpi.cs3733.D22.teamU.BackEnd.Request.Request;

public class MedicineRequest extends Request {
  String patientName;
  String status;
  String employeeName;
  String location;

  public MedicineRequest(
      String ID,
      String name,
      String patientName,
      String status,
      String employeeName,
      String location,
      String date,
      String time) {
    this.ID = ID;
    this.name = name;
    this.patientName = patientName;
    this.status = status;
    this.employeeName = employeeName;
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

  public String getEmployeeName() {
    return employeeName;
  }

  public void setEmployeeName(String employeeName) {
    this.employeeName = employeeName;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }
}
