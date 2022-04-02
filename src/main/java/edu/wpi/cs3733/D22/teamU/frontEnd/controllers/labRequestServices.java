package edu.wpi.cs3733.D22.teamU.frontEnd.controllers;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class labRequestServices extends ServiceController {
  @FXML JFXHamburger hamburger;
  @FXML VBox vBoxPane;
  @FXML Label requestProcessing;

  @FXML CheckBox bloodTest;
  @FXML CheckBox covidTest;
  @FXML CheckBox pregnancyTest;
  @FXML CheckBox urineTest;
  @FXML CheckBox drugScreenTest;
  @FXML CheckBox otherCheck;
  @FXML TextArea otherField;
  @FXML TextField patientNameField;
  @FXML TextField staffMemberField;
  @FXML Pane pane;
  @FXML Pane assistPane;

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
  }

  public Button backButton;

  public void orderButton(ActionEvent actionEvent) throws IOException {
    requestProcessing.setText("Request processing...");
    requestProcessing.setVisible(true);
    new Thread(
            () -> {
              try {
                Thread.sleep(1500); // milliseconds
                Platform.runLater(
                    () -> {
                      requestProcessing.setText("Request complete.");
                      bloodTest.setSelected(false);
                      covidTest.setSelected(false);
                      pregnancyTest.setSelected(false);
                      otherCheck.setSelected(false);
                      drugScreenTest.setSelected(false);
                      urineTest.setSelected(false);
                      patientNameField.setText("");
                      staffMemberField.setText("");
                      otherField.setText("");
                    });
                Thread.sleep(1500);
                Platform.runLater(
                    () -> {
                      requestProcessing.setVisible(false);
                    });
              } catch (InterruptedException ie) {
              }
            })
        .start();
  }

  public void clearButton(ActionEvent actionEvent) throws IOException {
    bloodTest.setSelected(false);
    covidTest.setSelected(false);
    pregnancyTest.setSelected(false);
    otherCheck.setSelected(false);
    drugScreenTest.setSelected(false);
    urineTest.setSelected(false);
    patientNameField.setText("");
    staffMemberField.setText("");
    otherField.setText("");
  }

  @Override
  public void addRequest() {}

  @Override
  public void removeRequest() {}

  @Override
  public void updateRequest() {}
}
