package edu.wpi.cs3733.D22.teamU.frontEnd.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class DashboardController extends ServiceController {

  @FXML Button navButton;
  @FXML AnchorPane animatePane;
  @FXML ImageView navPaneArrow;

  @FXML Button clockButton;
  @FXML AnchorPane clockPane;
  @FXML ImageView clockPaneArrow;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    handleNavPaneAnimation();
    handleClockPaneAnimation();
  }

  private void handleNavPaneAnimation() {
    TranslateTransition openNav = new TranslateTransition(new Duration(350), animatePane);
    openNav.setToX(0);
    TranslateTransition closeNav = new TranslateTransition(new Duration(350), animatePane);
    navButton.setOnAction(
        (ActionEvent evt) -> {
          navPaneArrow.setRotate(navPaneArrow.getRotate() * -1);
          if (animatePane.getTranslateX() != 0) {
            openNav.play();
          } else {
            closeNav.setToX(846);
            closeNav.play();
          }
        });
  }

  private void handleClockPaneAnimation() {
    TranslateTransition openNav = new TranslateTransition(new Duration(350), clockPane);
    TranslateTransition closeNav = new TranslateTransition(new Duration(350), clockPane);
    openNav.setToX(-936);

    clockButton.setOnAction(
        (ActionEvent evt) -> {
          clockPaneArrow.setRotate(clockPaneArrow.getRotate() * -1);
          openNav.play();
        });
  }

  public void toSettings(ActionEvent actionEvent) {
    System.out.println("Going to settings");
  }

  public void logOut(ActionEvent actionEvent) {
    System.out.println("Logging out");
  }

  @Override
  public void addRequest() {}

  @Override
  public void removeRequest() {}

  @Override
  public void updateRequest() {}
}
