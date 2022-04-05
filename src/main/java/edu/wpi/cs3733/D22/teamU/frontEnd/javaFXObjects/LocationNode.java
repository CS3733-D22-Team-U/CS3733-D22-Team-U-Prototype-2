package edu.wpi.cs3733.D22.teamU.frontEnd.javaFXObjects;

import edu.wpi.cs3733.D22.teamU.BackEnd.Location.Location;
import edu.wpi.cs3733.D22.teamU.BackEnd.Udb;
import edu.wpi.cs3733.D22.teamU.DBController;
import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class LocationNode extends Group {
  private Location location;
  private Udb udb = DBController.udb;

  public LocationNode(Location location, double x, double y, AnchorPane pane) throws IOException {
    super();
    this.location = location;

    if (location.getEquipment().size() > 0) {
      Rectangle r = new Rectangle();
      r.setX(x - 5);
      r.setWidth(10);
      r.setHeight(10);
      r.setY(y - 5);
      setColor(r);
      r.setStroke(Color.BLACK);
      r.setStrokeWidth(1);
      getChildren().add(r);
    }else{
      Circle c = new Circle();
      c.setCenterY(y);
      c.setCenterX(x);
      c.setRadius(5);
      setColor(c);
      c.setStroke(Color.BLACK);
      c.setStrokeWidth(1);
      getChildren().add(c);
    }

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

  private void setColor(Shape s) {

    switch (location.getNodeType()) {
      case "PATI":
        s.setFill(Color.RED);
        break;
      case "STOR":
        s.setFill(Color.ORANGE);
        break;
      case "DIRT":
        s.setFill(Color.YELLOW);
        break;
      case "HALL":
        s.setFill(Color.GREEN);
        break;
      case "ELEV":
        s.setFill(Color.BLUE);
        break;
      case "REST":
        s.setFill(Color.BLUEVIOLET);
        break;
      case "STAI":
        s.setFill(Color.PURPLE);
        break;
      case "DEPT":
        s.setFill(Color.ROSYBROWN);
        break;
      case "LABS":
        s.setFill(Color.SILVER);
        break;
      case "INFO":
        s.setFill(Color.WHEAT);
        break;
      case "CONF":
        s.setFill(Color.BLACK);
        break;
      case "EXIT":
        s.setFill(Color.DARKRED);
        break;
      case "RETL":
        s.setFill(Color.MAGENTA);
        break;
      case "SERV":
        s.setFill(Color.INDIANRED);
        break;
      default:
        s.setFill(Color.YELLOWGREEN);
    }
  }

  public Location getLocation() {
    return location;
  }
}
