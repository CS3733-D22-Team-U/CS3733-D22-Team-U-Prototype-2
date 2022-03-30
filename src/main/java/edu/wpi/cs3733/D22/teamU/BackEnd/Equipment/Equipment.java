package edu.wpi.cs3733.D22.teamU.BackEnd.Equipment;

public class Equipment {
  String Name;
  int Amount;
  int InUse;
  int Available;

  /**
   * Contructor for Equipment datatype that only takes a name
   *
   * @param name
   */
  public Equipment(String name) {
    this.Name = name;
    this.Amount = 1;
    this.InUse = 0;
    this.Available = Amount - InUse;
  }

  /**
   * Contructor for Equipment datatype that take in name, amount, in use
   *
   * @param name
   * @param amount
   * @param inuse
   */
  public Equipment(String name, int amount, int inuse) {
    this.Amount = amount;
    this.Name = name;
    this.InUse = inuse;
    this.Available = Amount - InUse;
  }

  /**
   * Return Equipment name
   *
   * @return String
   */
  public String getName() {
    return this.Name;
  }

  /**
   * return Equipment amount
   *
   * @return int
   */
  public int getAmount() {
    return this.Amount;
  }

  /**
   * return InUse
   *
   * @return int
   */
  public int getInUse() {
    return InUse;
  }

  /**
   * return available
   *
   * @return int
   */
  public int getAvailable() {
    return this.Available;
  }
}
