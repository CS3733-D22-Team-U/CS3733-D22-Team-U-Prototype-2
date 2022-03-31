package edu.wpi.cs3733.D22.teamU.frontEnd.equipmentDelivery;

public class EquipmentUI {
  private String equipmentName;
  private int amountInUse;
  private int amountAvailable;
  private int totalAmount;

  private int requestAmount;
  private String requestDate;
  private String requestTime;

  public EquipmentUI(String name, int inUse, int available, int total) {
    equipmentName = name;
    amountInUse = inUse;
    amountAvailable = available;
    totalAmount = total;
  }

  public EquipmentUI(String name, int request, String date, String timestamp) {
    equipmentName = name;
    requestAmount = request;
    requestDate = date;
    requestTime = timestamp;
  }

  public int getRequestAmount() {
    return requestAmount;
  }

  public String getEquipmentName() {
    return equipmentName;
  }

  public int getAmountInUse() {
    return amountInUse;
  }

  public void setAmountInUse(int amountInUse) {
    this.amountInUse = amountInUse;
  }

  public int getAmountAvailable() {
    return amountAvailable;
  }

  public void setAmountAvailable(int amountAvailable) {
    this.amountAvailable = amountAvailable;
  }

  public int getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(int totalAmount) {
    this.totalAmount = totalAmount;
  }

  public String getRequestDate() {
    return requestDate;
  }

  public String getRequestTime() {
    return requestTime;
  }
}
