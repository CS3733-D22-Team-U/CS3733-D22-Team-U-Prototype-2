package edu.wpi.cs3733.D22.teamU.frontEnd.controllers;

import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import edu.wpi.cs3733.D22.teamU.frontEnd.Uapp;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class labRequestHelpController extends ServiceController {
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
          backgroundPane.setDisable(!backgroundPane.isDisable());
          if (backgroundPane.isDisable()) {
            hamburger.setPrefWidth(200);
            backgroundPane.setEffect(new GaussianBlur(10));
            assistPane.setDisable(true);
          } else {
            backgroundPane.setEffect(null);
            hamburger.setPrefWidth(77);
            assistPane.setDisable(false);
          }
        });
  }

    public void toLabRequest(ActionEvent actionEvent) throws IOException {
        Scene scene = Uapp.getScene("edu/wpi/cs3733/D22/teamU/views/labRequestServices.fxml");
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }

  @Override
  public void addRequest() {}

  @Override
  public void removeRequest() {}

  @Override
  public void updateRequest() {}

  public void toCloseApp(ActionEvent actionEvent) {
    Platform.exit();
  }
}
