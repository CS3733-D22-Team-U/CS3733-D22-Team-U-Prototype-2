package edu.wpi.cs3733.D22.teamU.frontEnd.controllers;

import edu.wpi.cs3733.D22.teamU.BackEnd.Location.Location;
import edu.wpi.cs3733.D22.teamU.BackEnd.Udb;
import edu.wpi.cs3733.D22.teamU.DBController;
import edu.wpi.cs3733.D22.teamU.frontEnd.Uapp;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LocationAddController {

  @FXML public TextField nodeID;
  @FXML public TextField floor;
  @FXML public TextField ycoord;
  @FXML public TextField xcoord;
  @FXML public TextField building;
  @FXML public TextField nodeType;
  @FXML public TextField longName;
  @FXML public TextField shortName;
  @FXML public Pane pane;
  private Udb udb = DBController.udb;
  @FXML ComboBox nodeTypeCombo;
  @FXML ComboBox floorCombo;
  @FXML ComboBox buildingCombo;

  ObservableList<String> nodeTypeList =
      FXCollections.observableArrayList(
          "PATI", "STOR", "DIRT", "HALL", "ELEV", "REST", "STAI", "DEPT", "LABS", "INFO", "CONF",
          "EXIT", "RETL", "SERV");

  ObservableList<String> buildingList = FXCollections.observableArrayList("Tower");
  ObservableList<String> floorList =
      FXCollections.observableArrayList("G", "L1", "L2", "1", "2", "3");

  public void initialize() {
    buildingCombo.setItems(buildingList);
    nodeTypeCombo.setItems(nodeTypeList);
    floorCombo.setItems(floorList);
  }

  public void addRequest(MouseEvent mouseEvent) throws IOException {
    udb.locationImpl.add(
        new Location(
            nodeID.getText(),
            Integer.valueOf(xcoord.getText()),
            Integer.valueOf(ycoord.getText()),
            floorCombo.getValue().toString(),
            buildingCombo.getValue().toString(),
            nodeTypeCombo.getValue().toString(),
            longName.getText(),
            shortName.getText()));
  }

  public void toMap(MouseEvent mouseEvent) throws IOException {
    Scene scene = Uapp.getScene("edu/wpi/cs3733/D22/teamU/views/map.fxml");
    Stage appStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
    appStage.setScene(scene);
    appStage.show();
  }
}
