package edu.wpi.cs3733.D22.teamU.frontEnd.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

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

  public void Exit(ActionEvent actionEvent) {
    pane.setDisable(true);
    ((Pane) pane.getParent()).getChildren().remove(pane);
  }

  public void edit(MouseEvent mouseEvent) {}

  public void remove(MouseEvent mouseEvent) {}
}
