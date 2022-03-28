package edu.wpi.team_u.BackEnd.Employee;

public class Employee {

  String employeeID;
  String occupation;
  int reports;
  boolean onDuty;

  public Employee(String employeeID, String occupation, int reports, boolean onDuty) {
    this.employeeID = employeeID;
    this.occupation = occupation;
    this.onDuty = onDuty;
    this.reports = reports;
  }

  public Employee(String employeeID) {
    this.employeeID = employeeID;
    this.onDuty = false;
    this.reports = 0;
    this.occupation = null;
  }

  public String getEmployeeID() {
    return employeeID;
  }

  public void setEmployeeID(String employeeID) {
    this.employeeID = employeeID;
  }

  public String getOccupation() {
    return occupation;
  }

  public void setOccupation(String occupation) {
    this.occupation = occupation;
  }

  public int getReports() {
    return reports;
  }

  public void setReports(int reports) {
    this.reports = reports;
  }

  public boolean getOnDuty() {
    return onDuty;
  }

  public void setOnDuty(boolean onDuty) {
    this.onDuty = onDuty;
  }
}
