package edu.wpi.cs3733.D22.teamU.frontEnd.controllers;

import com.jfoenix.controls.JFXHamburger;
import edu.wpi.cs3733.D22.teamU.BackEnd.Location.Location;
import edu.wpi.cs3733.D22.teamU.BackEnd.Udb;
import edu.wpi.cs3733.D22.teamU.DBController;
import edu.wpi.cs3733.D22.teamU.frontEnd.javaFXObjects.LocationNode;
import edu.wpi.cs3733.D22.teamU.frontEnd.services.map.MapUI;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MapController extends ServiceController {
  /*Edit Remove Popup*/
  public TextField popupNodeID;
  public TextField popupXCoord;
  public TextField popupFloor;
  public TextField popupYCoord;
  public TextField popupBuilding;
  public TextField popupNodeType;
  public TextField popupLongName;
  public TextField popupShortName;
  AnchorPane popupEditPane;

  /*Add Popup*/
  AnchorPane popupAddPane;
  TextField addNodeID;
  TextField addXcoord;
  TextField addYcoord;
  TextField addLongName;
  TextField addShortName;
  ComboBox addNodeTypeCombo;
  ComboBox addBuildingCombo;
  ComboBox addFloorCombo;
  Button addButton;
  ObservableList<String> nodeTypeList =
      FXCollections.observableArrayList(
          "PATI", "STOR", "DIRT", "HALL", "ELEV", "REST", "STAI", "DEPT", "LABS", "INFO", "CONF",
          "EXIT", "RETL", "SERV");
  ObservableList<String> buildingList = FXCollections.observableArrayList("Tower");
  ObservableList<String> floorList =
      FXCollections.observableArrayList("G", "L1", "L2", "1", "2", "3");

  // @FXML ScrollPane imagesPane;
  @FXML AnchorPane lowerLevel1Pane;
  @FXML AnchorPane lowerLevel2Pane;
  @FXML AnchorPane floor1Pane;
  @FXML AnchorPane floor2Pane;
  @FXML AnchorPane floor3Pane;
  @FXML ImageView image;
  @FXML JFXHamburger hamburger;
  @FXML VBox vBoxPane;
  @FXML TableView<MapUI> mapTable;
  @FXML TableColumn<MapUI, String> nodeID;
  @FXML TableColumn<MapUI, Integer> x;
  @FXML TableColumn<MapUI, Integer> y;
  @FXML TableColumn<MapUI, String> floor;
  @FXML TableColumn<MapUI, String> building;
  @FXML TableColumn<MapUI, String> nodeType;
  @FXML TableColumn<MapUI, String> longName;
  @FXML TableColumn<MapUI, String> shortName;
  @FXML Pane pane;
  @FXML Pane assistPane;
  @FXML Button addBTN;
  ObservableList<MapUI> mapUI = FXCollections.observableArrayList();
  Udb udb = DBController.udb;

  HashMap<String, LocationNode> locations;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    super.initialize(location, resources);
    locations = new HashMap<>();
    setUpMap();
    mapUI.clear();
    for (Location loc : udb.locationImpl.locations) {
      mapUI.add(
          new MapUI(
              loc.getNodeID(),
              loc.getXcoord(),
              loc.getYcoord(),
              loc.getFloor(),
              loc.getBuilding(),
              loc.getNodeType(),
              loc.getLongName(),
              loc.getShortName()));

      double x = floor3Pane.getPrefWidth() / 5000.0 * (double) loc.getXcoord();
      double y = floor3Pane.getPrefHeight() / 3400.0 * (double) loc.getYcoord();
      String s = loc.getFloor();
      LocationNode ln;
      try {
        AnchorPane temp = new AnchorPane();
        switch (s) {
          case "L1":
            temp = lowerLevel1Pane;
            break;
          case "L2":
            temp = lowerLevel2Pane;
            break;
          case "1":
            temp = floor1Pane;
            break;
          case "2":
            temp = floor2Pane;
            break;
          case "3":
            temp = floor3Pane;
            break;
        }
        ln = new LocationNode(loc, x, y, temp);
        ln.setOnMouseClicked(this::popupOpen);
        locations.put(loc.getNodeID(), ln);
        temp.getChildren().add(ln);

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    mapTable.setItems(mapUI);
    popupAddPane = new AnchorPane();
    try {
      popupAddPane
          .getChildren()
          .add(
              FXMLLoader.load(
                  getClass()
                      .getClassLoader()
                      .getResource("edu/wpi/cs3733/D22/teamU/views/addLocPopUp.fxml")));
      popupAddPane.setLayoutX(200);
      popupAddPane.setLayoutY(200);

    } catch (IOException e) {
      e.printStackTrace();
    }

    popupEditPane = new AnchorPane();
    try {
      popupEditPane
          .getChildren()
          .add(
              FXMLLoader.load(
                  Objects.requireNonNull(
                      getClass()
                          .getClassLoader()
                          .getResource("edu/wpi/cs3733/D22/teamU/views/popup.fxml"))));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setUpMap() {
    nodeID.setCellValueFactory(new PropertyValueFactory<>("nodeID"));
    x.setCellValueFactory(new PropertyValueFactory<>("x"));
    y.setCellValueFactory(new PropertyValueFactory<>("y"));
    floor.setCellValueFactory(new PropertyValueFactory<>("floor"));
    building.setCellValueFactory(new PropertyValueFactory<>("building"));
    nodeType.setCellValueFactory(new PropertyValueFactory<>("nodeType"));
    longName.setCellValueFactory(new PropertyValueFactory<>("longName"));
    shortName.setCellValueFactory(new PropertyValueFactory<>("shortName"));
    // mapTable.setItems(getMapList());
  }

  @Override
  public void addRequest() {}

  @Override
  public void removeRequest() {}

  @Override
  public void updateRequest() {}

  public void popUpAdd(MouseEvent mouseEvent) throws IOException {

    Pane pane = (Pane) addBTN.getParent();
    if (pane.getChildren().contains(popupAddPane)) {
      pane.getChildren().remove(popupAddPane);
    } else {
      pane.getChildren().add(popupAddPane);
      for (Node n : ((AnchorPane) popupAddPane.getChildren().get(0)).getChildren()) {
        if (n instanceof GridPane) {
          GridPane gp = (GridPane) n;
          for (Node n2 : gp.getChildren()) {
            if (n2 instanceof ComboBox) {
              ComboBox cb = (ComboBox) n2;
              switch (cb.getId()) {
                case "addBuildingCombo":
                  addBuildingCombo = cb;
                  addBuildingCombo.setItems(buildingList);
                  break;
                case "addNodeTypeCombo":
                  addNodeTypeCombo = cb;
                  addNodeTypeCombo.setItems(nodeTypeList);
                  break;
                case "addFloorCombo":
                  addFloorCombo = cb;
                  addFloorCombo.setItems(floorList);
                  break;
              }
            } else if (n2 instanceof Button && n2.getId().equals("addButton")) {
              addButton = (Button) n2;
              addButton.setOnMouseClicked(this::popupAddLocation);
            } else if (n2 instanceof TextField) {
              TextField tf = (TextField) n2;
              switch (tf.getId()) {
                case "addNodeID":
                  addNodeID = tf;
                  break;
                case "addXcoord":
                  addXcoord = tf;
                  break;
                case "addYcoord":
                  addYcoord = tf;
                  break;
                case "addLongName":
                  addLongName = tf;
                  break;
                case "addShortName":
                  addShortName = tf;
                  break;
              }
            }
          }
        }
      }
    }
  }

  public void popupOpen(MouseEvent mouseEvent) {
    LocationNode locationNode = (LocationNode) mouseEvent.getSource();
    Location location = locationNode.getLocation();
    AnchorPane pane = locationNode.getPane();
    if (pane.getChildren().contains(popupEditPane)) {
      pane.getChildren().remove(popupEditPane);
    }

    popupEditPane.setLayoutX(locationNode.getX());
    popupEditPane.setLayoutY(locationNode.getY());

    for (Node n : ((AnchorPane) popupEditPane.getChildren().get(0)).getChildren()) {
      if (n instanceof Button) {
        Button b2 = (Button) n;
        if (b2.getId().equals("exit")) {
          b2.setOnMouseClicked(this::Exit);
        }
      } else if (n instanceof GridPane) {
        GridPane gp = (GridPane) n;
        for (Node n2 : gp.getChildren()) {
          if (n2 instanceof TextField) {
            TextField tf = (TextField) n2;
            switch (tf.getId()) {
              case "popupNodeID":
                popupNodeID = tf;
                popupNodeID.setText(location.getNodeID());
                break;
              case "popupFloor":
                popupFloor = tf;
                popupFloor.setText(location.getFloor());
                break;
              case "popupYCoord":
                popupYCoord = tf;
                popupYCoord.setText(String.valueOf(location.getYcoord()));
                break;
              case "popupXCoord":
                popupXCoord = tf;
                popupXCoord.setText(String.valueOf(location.getXcoord()));
                break;
              case "popupBuilding":
                popupBuilding = tf;
                popupBuilding.setText(location.getBuilding());
                break;
              case "popupNodeType":
                popupNodeType = tf;
                popupNodeType.setText(location.getNodeType());
                break;
              case "popupLongName":
                popupLongName = tf;
                popupLongName.setText(location.getLongName());
                break;
              case "popupShortName":
                popupShortName = tf;
                popupShortName.setText(location.getShortName());
                break;
              default:
                break;
            }
          } else if (n2 instanceof Button) {
            Button b = (Button) n2;
            switch (b.getId()) {
              case "edit":
                b.setOnMouseClicked(this::popupEdit);
                break;
              case "remove":
                b.setOnMouseClicked(this::popupRemove);
                break;
              default:
                break;
            }
          }
        }
      }
    }
    pane.getChildren().add(popupEditPane);
  }

  public void Exit(MouseEvent actionEvent) {
    popupEditPane.relocate(Integer.MIN_VALUE, Integer.MIN_VALUE);
  }

  public void popupEdit(MouseEvent actionEvent) {
    Location l =
        new Location(
            popupNodeID.getText(),
            Integer.parseInt(popupXCoord.getText()),
            Integer.parseInt(popupYCoord.getText()),
            popupFloor.getText(),
            popupBuilding.getText(),
            popupNodeType.getText(),
            popupLongName.getText(),
            popupShortName.getText());

    try {

      Location old = udb.locationImpl.list().get(udb.locationImpl.list().indexOf(l));
      l.setEquipment(old.getEquipment());
      udb.locationImpl.edit(l);
      LocationNode lnOld = locations.get(l.getNodeID());
      double x = lnOld.getPane().getPrefWidth() / 5000.0 * (double) l.getXcoord();
      double y = lnOld.getPane().getPrefHeight() / 3400.0 * (double) l.getYcoord();

      LocationNode lnNew = new LocationNode(l, x, y, lnOld.getPane());
      locations.put(l.getNodeID(), lnNew);
      lnNew.setOnMouseClicked(this::popupOpen);
      Exit(actionEvent);
      lnOld.getPane().getChildren().remove(lnOld);
      lnNew.getPane().getChildren().add(lnNew);
      // toMap(actionEvent);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void popupRemove(MouseEvent actionEvent) {
    Location l =
        new Location(
            popupNodeID.getText(),
            Integer.parseInt(popupXCoord.getText()),
            Integer.parseInt(popupYCoord.getText()),
            popupFloor.getText(),
            popupBuilding.getText(),
            popupNodeType.getText(),
            popupLongName.getText(),
            popupShortName.getText());

    try {
      udb.locationImpl.remove(l);
      LocationNode lnOld = locations.get(l.getNodeID());
      locations.remove(l.getNodeID());
      Exit(actionEvent);
      lnOld.getPane().getChildren().remove(lnOld);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void popupAddLocation(MouseEvent mouseEvent) {

    Location l =
        new Location(
            addNodeID.getText(),
            Integer.parseInt(addXcoord.getText()),
            Integer.parseInt(addYcoord.getText()),
            addFloorCombo.getValue().toString(),
            addBuildingCombo.getValue().toString(),
            addNodeTypeCombo.getValue().toString(),
            addLongName.getText(),
            addShortName.getText());
    try {
      udb.locationImpl.add(l);
      String s = l.getFloor();
      AnchorPane temp = new AnchorPane();
      switch (s) {
        case "L1":
          temp = lowerLevel1Pane;
          break;
        case "L2":
          temp = lowerLevel2Pane;
          break;
        case "1":
          temp = floor1Pane;
          break;
        case "2":
          temp = floor2Pane;
          break;
        case "3":
          temp = floor3Pane;
          break;
      }
      double x = temp.getPrefWidth() / 5000.0 * (double) l.getXcoord();
      double y = temp.getPrefHeight() / 3400.0 * (double) l.getYcoord();
      LocationNode ln = new LocationNode(l, x, y, temp);
      ln.setOnMouseClicked(this::popupOpen);
      locations.put(l.getNodeID(), ln);
      temp.getChildren().add(ln);
      popUpAdd(mouseEvent);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void test(ZoomEvent zoomEvent) {}
}
