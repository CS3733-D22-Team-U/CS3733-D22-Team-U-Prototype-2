package edu.wpi.team_u.BackEnd.Equipment;

public class Equipment {
  String Name;
  int Amount;
  int InUse;
  int Available;

  public Equipment(String name) {
    this.Name = name;
    this.Amount = 1;
    this.InUse = 0;
    this.Available = Amount - InUse;
  }

  public Equipment(String name, int amount, int inuse) {
    this.Amount = amount;
    this.Name = name;
    this.InUse = inuse;
    this.Available = Amount - InUse;
  }

  public String getName() {
    return this.Name;
  }

  public int getAmount() {
    return this.Amount;
  }

  public int getInUse() {
    return InUse;
  }

  public int getAvailable() {
    return this.Available;
  }
}
