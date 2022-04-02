package edu.wpi.cs3733.D22.teamU.frontEnd.controllers;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import edu.wpi.cs3733.D22.teamU.BackEnd.Location.Location;
import edu.wpi.cs3733.D22.teamU.BackEnd.Udb;
import edu.wpi.cs3733.D22.teamU.DBController;
import edu.wpi.cs3733.D22.teamU.frontEnd.services.map.MapUI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class MapController extends ServiceController {
  public ScrollPane imagesPane;
  public AnchorPane floor1Pane;
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

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    for (Location loc : udb.locationImpl.locations) {

      if (loc.getFloor().equals("1")) {
        Circle c = new Circle();
        Label l = new Label(loc.getNodeID());
        l.setFont(new Font("Arial", 7));
        // System.out.println(floor1Pane.getPrefHeight());
        // System.out.println(floor1Pane.getPrefWidth());
        double x = floor1Pane.getPrefWidth() / 5000.0 * (double) loc.getXcoord();
        double y = floor1Pane.getPrefHeight() / 3400.0 * (double) loc.getYcoord();
        l.setLayoutX(x);
        l.setLayoutY(y);
        c.setCenterX(x);
        c.setCenterY(y);

        switch (loc.getNodeType()) {
          case "PATI":
            c.setFill(Color.RED);
            break;
          case "STOR":
            c.setFill(Color.ORANGE);
            break;
          case "DIRT":
            c.setFill(Color.YELLOW);
            break;
          case "HALL":
            c.setFill(Color.GREEN);
            break;
          case "ELEV":
            c.setFill(Color.BLUE);
            break;
          case "REST":
            c.setFill(Color.BLUEVIOLET);
            break;
          case "STAI":
            c.setFill(Color.PURPLE);
            break;
          case "DEPT":
            c.setFill(Color.ROSYBROWN);
            break;
          case "LABS":
            c.setFill(Color.SILVER);
            break;
          case "INFO":
            c.setFill(Color.WHEAT);
            break;
          case "CONF":
            c.setFill(Color.BLACK);
            break;
          case "EXIT":
            c.setFill(Color.DARKRED);
            break;
          case "RETL":
            c.setFill(Color.MAGENTA);
            break;
          case "SERV":
            c.setFill(Color.INDIANRED);
            break;
          default:
            c.setFill(Color.YELLOWGREEN);
        }

        c.setRadius(5);
        floor1Pane.getChildren().add(c);
      }
    }

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
