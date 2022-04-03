package edu.wpi.cs3733.D22.teamU.BackEnd.Request;

public class Request {
  String ID;
  String name;
  int amount;
  String typeOfRequest;
  String destination;
  String date;
  String time;


  public Request(String ID, String name, int amount, String typeOfRequest, String destination, String date, String time) {
    this.ID = ID;
    this.name = name;
    this.amount = amount;
    this.typeOfRequest = typeOfRequest;
    this.destination = destination;
    this.date = date;
    this.time = time;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
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

  public String getType() { return this.typeOfRequest;}

  public String getDestination() { return this.destination;}

  public void setType(String newType) { this.typeOfRequest = newType; }

  public void setDestination(String newDestination) { this.destination = newDestination; }

  public String getID() { return this.ID; }

  public void setID(String newID) { this.ID = newID; }
}
