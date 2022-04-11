package edu.wpi.cs3733.D22.teamU.frontEnd.controllers;

import edu.wpi.cs3733.D22.teamU.BackEnd.Location.Location;
import edu.wpi.cs3733.D22.teamU.BackEnd.Udb;
import edu.wpi.cs3733.D22.teamU.DBController;
import edu.wpi.cs3733.D22.teamU.frontEnd.Uapp;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LocationPopupController {
  public Button exit;
  public AnchorPane pane;
  public TextField nodeID;
  public TextField floor;
  public TextField ycoord;
  public TextField xcoord;
  public TextField building;
  public TextField nodeType;
  public TextField longName;
  public TextField shortName;
  public Button edit;
  public Button remove;
  private Udb udb = DBController.udb;

  public void Exit(ActionEvent actionEvent) {
    // pane.setDisable(true);
    ((Pane) pane.getParent()).getChildren().remove(pane);
  }

  public void popupEdit(MouseEvent actionEvent) throws IOException {
    Location l =
        new Location(
            nodeID.getText(),
            Integer.parseInt(xcoord.getText()),
            Integer.parseInt(ycoord.getText()),
            floor.getText(),
            building.getText(),
            nodeType.getText(),
            longName.getText(),
            shortName.getText());

    try {
      Location old = udb.locationImpl.list().get(udb.locationImpl.list().indexOf(l));
      l.setEquipment(old.getEquipment());
    } catch (Exception e) {

    }

    udb.locationImpl.edit(l);
    toMap(actionEvent);
  }

  public void popupRemove(MouseEvent actionEvent) throws IOException {
    Location l =
        new Location(
            nodeID.getText(),
            Integer.parseInt(xcoord.getText()),
            Integer.parseInt(ycoord.getText()),
            floor.getText(),
            building.getText(),
            nodeType.getText(),
            longName.getText(),
            shortName.getText());
    udb.locationImpl.remove(l);
    toMap(actionEvent);
  }

  private void toMap(MouseEvent mouseEvent) throws IOException {
    Scene scene = Uapp.getScene("edu/wpi/cs3733/D22/teamU/views/map.fxml");
    Stage appStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
    appStage.setScene(scene);
    appStage.show();
  }
}
