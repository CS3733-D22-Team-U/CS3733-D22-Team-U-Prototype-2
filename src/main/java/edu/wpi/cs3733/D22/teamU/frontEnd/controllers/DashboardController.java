package edu.wpi.cs3733.D22.teamU.frontEnd.controllers;

import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class DashboardController extends ServiceController {

  @FXML Button navButton;
  @FXML AnchorPane animatePane;
  @FXML ImageView navPaneArrow;

  @FXML Button clockButton;
  @FXML AnchorPane clockPane;
  @FXML ImageView clockPaneArrow;

  @FXML AnchorPane anchor;

  @FXML ButtonBar topRow;
  @FXML ButtonBar bottomRow;

  @FXML Pane backgroundPane;

  @FXML Text time;
  @FXML Text date;
  private static final String HOVERED_BUTTON = "-fx-border-color: #029ca6";

  private static final SimpleDateFormat sdf3 = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    handleNavPaneAnimation();
    handleClockPaneAnimation();
    handleNavButtons();
    handeDateTime();
  }

  private void handeDateTime() {
    Thread timeThread =
        new Thread(
            () -> {
              for (; ; ) {
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String timeStampdate = sdf3.format(timestamp).substring(0, 10);
                String timeStampTime = sdf3.format(timestamp).substring(11);
                time.setText(timeStampTime);
                date.setText(timeStampdate);
              }
            });
    timeThread.start();
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
            TranslateTransition closeOther = new TranslateTransition(new Duration(350), clockPane);
            closeOther.setToX(0);
            closeOther.play();
          }
        });
  }

  private void handleNavButtons() {
    for (Node node : topRow.getButtons()) {
      Button button = (Button) node;
      String initStyle = button.getStyle();
      button.setStyle(initStyle);
      button.setOnMouseEntered(e -> button.setStyle(initStyle + HOVERED_BUTTON));
      button.setOnMouseExited(e -> button.setStyle(initStyle));
    }
    for (Node node : bottomRow.getButtons()) {
      Button button = (Button) node;
      String initStyle = button.getStyle();
      button.setStyle(initStyle);
      button.setOnMouseEntered(e -> button.setStyle(initStyle + HOVERED_BUTTON));
      button.setOnMouseExited(e -> button.setStyle(initStyle));
    }
  }

  private void handleClockPaneAnimation() {
    TranslateTransition openNav = new TranslateTransition(new Duration(350), clockPane);
    openNav.setToX(-846);
    TranslateTransition closeNav = new TranslateTransition(new Duration(350), clockPane);

    clockButton.setOnAction(
        (ActionEvent evt) -> {
          clockPaneArrow.setRotate(clockPaneArrow.getRotate() * -1);
          if (clockPane.getTranslateX() != -846) {
            openNav.play();
            TranslateTransition closeOther =
                new TranslateTransition(new Duration(350), animatePane);
            closeOther.setToX(0);
            closeOther.play();
            navPaneArrow.setRotate(navPaneArrow.getRotate() * -1);
          } else {
            closeNav.setToX(0);
            closeNav.play();
          }
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

  public void closeNavTabs(MouseEvent mouseEvent) {
    if (mouseEvent.getY() < animatePane.getLayoutY()
        || mouseEvent.getY() > animatePane.getLayoutY() + animatePane.getHeight()) {
      if (animatePane.getTranslateX() != 0) {
        TranslateTransition closeNav = new TranslateTransition(new Duration(350), animatePane);
        closeNav.setToX(0);
        closeNav.play();
        navPaneArrow.setRotate(navPaneArrow.getRotate() * -1);
      }
      if (clockPane.getTranslateX() == -846) {
        TranslateTransition closeNav = new TranslateTransition(new Duration(350), clockPane);
        closeNav.setToX(0);
        closeNav.play();
        clockPaneArrow.setRotate(navPaneArrow.getRotate() * -1);
      }
    }
  }
}
