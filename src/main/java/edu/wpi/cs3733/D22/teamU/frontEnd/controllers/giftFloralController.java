package edu.wpi.cs3733.D22.teamU.frontEnd.controllers;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class giftFloralController extends ServiceController {

  @FXML JFXHamburger hamburger;
  @FXML VBox vBoxPane;
  @FXML Pane pane;
  @FXML Pane assistPane;
  @FXML TextField senderField;
  @FXML TextField recieverField;
  @FXML TextField staffField;
  @FXML TextField roomField;
  @FXML TextField requestID;
  @FXML Text processingText;
  @FXML JFXTextArea messageText;
  @FXML CheckBox balloonBox;
  @FXML CheckBox flowerBox;
  @FXML CheckBox plantBox;
  @FXML CheckBox basketBox;

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

  public void submit(ActionEvent actionEvent) {
    processingText.setText("Processing...");
    processingText.setVisible(true);
    new Thread(
            () -> {
              try {
                Thread.sleep(1500); // milliseconds
                Platform.runLater(
                    () -> {
                      processingText.setText("Done");
                    });
                Thread.sleep(1500); // milliseconds
                Platform.runLater(
                    () -> {
                      processingText.setVisible(false);
                      balloonBox.setSelected(false);
                      plantBox.setSelected(false);
                      flowerBox.setSelected(false);
                      basketBox.setSelected(false);
                      senderField.setText("");
                      recieverField.setText("");
                      staffField.setText("");
                      roomField.setText("");
                      requestID.setText("");
                      messageText.setText("");
                    });
              } catch (InterruptedException ie) {
              }
            })
        .start();
  }

  public void clear(ActionEvent actionEvent) {
    balloonBox.setSelected(false);
    plantBox.setSelected(false);
    flowerBox.setSelected(false);
    basketBox.setSelected(false);
    senderField.setText("");
    recieverField.setText("");
    staffField.setText("");
    roomField.setText("");
    requestID.setText("");
    messageText.setText("");
  }

  @Override
  public void addRequest() {}

  @Override
  public void removeRequest() {}

  @Override
  public void updateRequest() {}
}
