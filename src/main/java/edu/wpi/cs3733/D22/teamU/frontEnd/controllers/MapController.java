package edu.wpi.cs3733.D22.teamU.frontEnd.controllers;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import edu.wpi.cs3733.D22.teamU.BackEnd.Location.Location;
import edu.wpi.cs3733.D22.teamU.BackEnd.Udb;
import edu.wpi.cs3733.D22.teamU.DBController;
import edu.wpi.cs3733.D22.teamU.frontEnd.javaFXObjects.LocationNode;
import edu.wpi.cs3733.D22.teamU.frontEnd.services.map.MapUI;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MapController extends ServiceController {
  //@FXML ScrollPane imagesPane;
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

  ObservableList<MapUI> mapUI = FXCollections.observableArrayList();
  Udb udb = DBController.udb;

  //private int startScale;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    HamburgerBasicCloseTransition closeTransition = new HamburgerBasicCloseTransition(hamburger);

    closeTransition.setRate(-1);
    hamburger.addEventHandler(
            MouseEvent.MOUSE_CLICKED,
            e -> {
              closeTransition.setRate(closeTransition.getRate() * -1);
              closeTransition.play();
              vBoxPane.setVisible(!vBoxPane.isVisible());
              pane.setDisable(!pane.isDisable());
              if (pane.isDisable()) {
                hamburger.setPrefWidth(200);
                pane.setEffect(new GaussianBlur(10));
                assistPane.setDisable(true);
              } else {
                pane.setEffect(null);
                hamburger.setPrefWidth(77);
                assistPane.setDisable(false);
              }
            });


    setUpMap();

    for (Location loc : udb.locationImpl.locations) {

      if (loc.getFloor().equals("L1")) {
        // System.out.println(floor1Pane.getPrefHeight());
        // System.out.println(floor1Pane.getPrefWidth());
        double x = lowerLevel1Pane.getPrefWidth() / 5000.0 * (double) loc.getXcoord();
        double y = lowerLevel1Pane.getPrefHeight() / 3400.0 * (double) loc.getYcoord();
        try {
          lowerLevel1Pane.getChildren().add(new LocationNode(loc, x, y, lowerLevel1Pane));
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else  if (loc.getFloor().equals("L2")) {
        double x = lowerLevel2Pane.getPrefWidth() / 5000.0 * (double) loc.getXcoord();
        double y = lowerLevel2Pane.getPrefHeight() / 3400.0 * (double) loc.getYcoord();
        try {
          lowerLevel2Pane.getChildren().add(new LocationNode(loc, x, y, lowerLevel2Pane));
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else  if (loc.getFloor().equals("1")) {
        double x = floor1Pane.getPrefWidth() / 5000.0 * (double) loc.getXcoord();
        double y = floor1Pane.getPrefHeight() / 3400.0 * (double) loc.getYcoord();
        try {
          floor1Pane.getChildren().add(new LocationNode(loc, x, y, floor1Pane));
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else  if (loc.getFloor().equals("2")) {
        double x = floor2Pane.getPrefWidth() / 5000.0 * (double) loc.getXcoord();
        double y = floor2Pane.getPrefHeight() / 3400.0 * (double) loc.getYcoord();
        try {
          floor2Pane.getChildren().add(new LocationNode(loc, x, y, floor2Pane));
        } catch (IOException e) {
          e.printStackTrace();
        }
      } else  if (loc.getFloor().equals("3")) {
        double x = floor3Pane.getPrefWidth() / 5000.0 * (double) loc.getXcoord();
        double y = floor3Pane.getPrefHeight() / 3400.0 * (double) loc.getYcoord();
        try {
          floor3Pane.getChildren().add(new LocationNode(loc, x, y, floor3Pane));
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }






  public void test() {
    System.out.println("test");
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
    mapTable.setItems(getMapList());
  }

  private ObservableList<MapUI> getMapList() {
    mapUI.clear();
    for (Location location : udb.locationImpl.locations) {
      mapUI.add(
          new MapUI(
              location.getNodeID(),
              location.getXcoord(),
              location.getYcoord(),
              location.getFloor(),
              location.getBuilding(),
              location.getNodeType(),
              location.getLongName(),
              location.getShortName()));
    }
    return mapUI;
  }

  @Override
  public void addRequest() {}

  @Override
  public void removeRequest() {}

  @Override
  public void updateRequest() {}
}
