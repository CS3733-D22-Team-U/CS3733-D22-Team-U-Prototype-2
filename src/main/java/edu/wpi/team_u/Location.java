package edu.wpi.team_u;

public class Location {

  String nodeID;
  int xcoord;
  int ycoord;
  String floor;
  String building;
  String nodeType;
  String longName;
  String shortName;

  public Location() {}

  public Location(String nodeID) {
    this.nodeID = nodeID;
    this.xcoord = 0;
    this.ycoord = 0;
    this.floor = "N/A";
    this.building = "N/A";
    this.nodeType = "N/A";
    this.longName = "N/A";
    this.shortName = "N/A";
  }

  void StrtoLoc(String[] row) {
    this.nodeID = row[0];
    this.xcoord = Integer.parseInt(row[1]);
    this.ycoord = Integer.parseInt(row[2]);
    this.floor = row[3];
    this.building = row[4];
    this.nodeType = row[5];
    this.longName = row[6];
    this.shortName = row[7];
  }

  @Override
  public String toString() {
    return "Location{"
        + "nodeID='"
        + nodeID
        + '\''
        + ", xcoord="
        + xcoord
        + ", ycoord="
        + ycoord
        + ", floor='"
        + floor
        + '\''
        + ", building='"
        + building
        + '\''
        + ", nodeType='"
        + nodeType
        + '\''
        + ", longName='"
        + longName
        + '\''
        + ", shortName='"
        + shortName
        + '\''
        + '}';
  }
}
