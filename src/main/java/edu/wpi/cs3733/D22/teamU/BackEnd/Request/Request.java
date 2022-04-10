package edu.wpi.cs3733.D22.teamU.BackEnd.Request;

import edu.wpi.cs3733.D22.teamU.BackEnd.DataDao;

public abstract class Request {
  public String ID;
  public String name;
  public String date;
  public String time;


  public String getID() {
    return ID;
  }

  public void setID(String ID) {
    this.ID = ID;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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
