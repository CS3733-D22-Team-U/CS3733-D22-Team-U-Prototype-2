package edu.wpi.cs3733.D22.teamU.BackEnd.EquipmentRequest;

public class RequestEquip {
  String name;
  int amount;
  String date;
  String time;

  public RequestEquip(String name, int amount, String date, String time) {
    this.name = name;
    this.amount = amount;
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
}
