package edu.wpi.cs3733.D22.teamU.frontEnd.controllers;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MedicineDeliveryController extends ServiceController {

  @FXML JFXCheckBox Advil;
  @FXML JFXCheckBox Alprozalam;
  @FXML JFXCheckBox AmphetamineSalt;
  @FXML JFXCheckBox Atorvastatin;
  @FXML JFXCheckBox Lisinopril;
  @FXML JFXCheckBox Metformin;
  @FXML JFXCheckBox specialCheck;

  @FXML TextArea specialReq;
  @FXML TextField patientName;
  @FXML TextField staffName;
  @FXML Text resetDone;
  @FXML Text processText;
  // Text status;

  public Button backButton;

  @FXML JFXHamburger hamburger;
  @FXML VBox vBoxPane;
  @FXML Pane pane;
  @FXML Pane assistPane;

  public void clear() {
    Advil.setSelected(false);
    Alprozalam.setSelected(false);
    AmphetamineSalt.setSelected(false);
    Atorvastatin.setSelected(false);
    Lisinopril.setSelected(false);
    Metformin.setSelected(false);
    specialCheck.setSelected(false);

    specialReq.setText("");
    patientName.setText("");
    staffName.setText("");

    resetDone.setText("Cleared requests!");
    resetDone.setVisible(true);
    new Thread(
            () -> {
              try {
                Thread.sleep(1500); // milliseconds
                Platform.runLater(
                    () -> {
                      resetDone.setVisible(false);
                    });
              } catch (InterruptedException ie) {
              }
            })
        .start();
  }

  public void process() {
    processText.setText("Processing...");
    processText.setVisible(true);
    new Thread(
            () -> {
              try {
                Thread.sleep(4000); // milliseconds
                Platform.runLater(
                    () -> {
                      processText.setText("Done!");
                    });
              } catch (InterruptedException ie) {
              }
            })
        .start();
  }

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

  @Override
  public void addRequest() {}

  @Override
  public void removeRequest() {}

  @Override
  public void updateRequest() {}
}
