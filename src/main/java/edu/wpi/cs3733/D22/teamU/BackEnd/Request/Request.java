package edu.wpi.cs3733.D22.teamU.BackEnd.Request;

import edu.wpi.cs3733.D22.teamU.BackEnd.DataDao;

public abstract class Request {
  String ID;
  String type;
  String date;
  String time;


  public String getID() {
    return ID;
  }

  public void setID(String ID) {
    this.ID = ID;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
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
