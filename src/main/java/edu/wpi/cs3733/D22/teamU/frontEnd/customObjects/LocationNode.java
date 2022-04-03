package edu.wpi.cs3733.D22.teamU.frontEnd.customObjects;

import edu.wpi.cs3733.D22.teamU.BackEnd.Location.Location;
import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class LocationNode extends Circle {
  private Location location;

  public LocationNode(Location location, double x, double y, AnchorPane pane) throws IOException {
    super();
    this.location = location;
    setCenterX(x);
    setCenterY(y);
    setColor();
    setRadius(5);

    AnchorPane popupPane = new AnchorPane();
    popupPane
        .getChildren()
        .add(
            FXMLLoader.load(
                getClass()
                    .getClassLoader()
                    .getResource("edu/wpi/cs3733/D22/teamU/views/popup.fxml")));
    popupPane.setLayoutX(x);
    popupPane.setLayoutY(y);

    for (Node n : ((AnchorPane) popupPane.getChildren().get(0)).getChildren()) {
      if (n instanceof GridPane) {
        GridPane gp = (GridPane) n;
        for (Node n2 : gp.getChildren()) {
          if (n2 instanceof TextField) {
            TextField tf = (TextField) n2;
            switch (tf.getId()) {
              case "nodeID":
                tf.setText(location.getNodeID());
                break;
              case "floor":
                tf.setText(location.getFloor());
                break;
              case "ycoord":
                tf.setText(String.valueOf(location.getYcoord()));
                break;
              case "xcoord":
                tf.setText(String.valueOf(location.getXcoord()));
                break;
              case "building":
                tf.setText(location.getBuilding());
                break;
              case "nodeType":
                tf.setText(location.getNodeType());
                break;
              case "longName":
                tf.setText(location.getLongName());
                break;
              case "shortName":
                tf.setText(location.getShortName());
                break;
              default:
                break;
            }
          }
        }
      }
    }
    EventHandler<MouseEvent> event =
        new EventHandler<MouseEvent>() {
          public void handle(MouseEvent e) {
            if (pane.getChildren().contains(popupPane)) {
              pane.getChildren().remove(popupPane);
            } else {
              pane.getChildren().add(popupPane);
            }
          }
        };

    setOnMouseClicked(event);
  }

  private void setColor() {

    switch (location.getNodeType()) {
      case "PATI":
        setFill(Color.RED);
        break;
      case "STOR":
        setFill(Color.ORANGE);
        break;
      case "DIRT":
        setFill(Color.YELLOW);
        break;
      case "HALL":
        setFill(Color.GREEN);
        break;
      case "ELEV":
        setFill(Color.BLUE);
        break;
      case "REST":
        setFill(Color.BLUEVIOLET);
        break;
      case "STAI":
        setFill(Color.PURPLE);
        break;
      case "DEPT":
        setFill(Color.ROSYBROWN);
        break;
      case "LABS":
        setFill(Color.SILVER);
        break;
      case "INFO":
        setFill(Color.WHEAT);
        break;
      case "CONF":
        setFill(Color.BLACK);
        break;
      case "EXIT":
        setFill(Color.DARKRED);
        break;
      case "RETL":
        setFill(Color.MAGENTA);
        break;
      case "SERV":
        setFill(Color.INDIANRED);
        break;
      default:
        setFill(Color.YELLOWGREEN);
    }
  }

  public Location getLocation() {
    return location;
  }
}
