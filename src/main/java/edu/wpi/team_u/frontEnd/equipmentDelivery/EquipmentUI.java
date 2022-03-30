package edu.wpi.team_u.frontEnd.equipmentDelivery;

public class EquipmentUI {
  private String equipmentName;
  private int amountInUse;
  private int amountAvailable;
  private int totalAmount;

  public EquipmentUI(String name, int inUse, int available, int total) {
    equipmentName = name;
    amountInUse = inUse;
    amountAvailable = available;
    totalAmount = total;
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
}
