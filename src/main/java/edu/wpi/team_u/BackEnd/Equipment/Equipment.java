package edu.wpi.team_u.BackEnd.Equipment;

public class Equipment {
  String Name;
  int Amount;
  int InUse;
  int Available = Amount - InUse;

  public Equipment(String name) {
    this.Name = name;
    this.Amount = 1;
    this.InUse = 0;
  }

  public Equipment(String name, int amount, int inuse) {
    this.Amount = amount;
    this.Name = name;
    this.InUse = inuse;
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
