package edu.wpi.team_u.frontEnd.controllers;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import edu.wpi.team_u.Uapp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LaundryController implements Initializable {
  @FXML JFXHamburger hamburger;
  @FXML VBox vBoxPane;
  @FXML Text laundryStatus;
  @FXML CheckBox hangCB;
  @FXML CheckBox machineCB;
  @FXML CheckBox hypoCB;
  @FXML TextField patientNameLaundry;
  @FXML TextField employeeNameLaundry;
  @FXML TextField locationLaundry;
  @FXML TextField dropOffTF;
  @FXML TextField pickUpTF;
  @FXML TextArea notesLaundryTA;

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
        });
  }

  public void toMedicineDelivery(ActionEvent actionEvent) throws IOException {
    Scene scene = Uapp.getScene("edu/wpi/team_u/views/medicineDelivery.fxml");
    Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    appStage.setScene(scene);
  }

  public void toMealDelivery(ActionEvent actionEvent) throws IOException {
    Scene scene = Uapp.getScene("edu/wpi/team_u/views/mealDelivery.fxml");
    Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    appStage.setScene(scene);
    appStage.show();
  }

  public void toHome(ActionEvent actionEvent) throws IOException {
    Scene scene = Uapp.getScene("edu/wpi/team_u/views/HomePage.fxml");
    Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    appStage.setScene(scene);
    appStage.show();
  }

  public void toEquipmentDelivery(ActionEvent actionEvent) throws IOException {
    Scene scene = Uapp.getScene("edu/wpi/team_u/views/equipmentDelivery.fxml");
    Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    appStage.setScene(scene);
    appStage.show();
  }

  public void placeLaundryRequest(ActionEvent actionEvent) {
    laundryStatus.setText("Processing...");
    laundryStatus.setVisible(true);
    new Thread(
            () -> {
              try {
                Thread.sleep(1500); // milliseconds
                Platform.runLater(
                    () -> {
                      laundryStatus.setText("Done");
                    });
                Thread.sleep(1500); // milliseconds
                Platform.runLater(
                    () -> {
                      laundryStatus.setVisible(false);
                    });
              } catch (InterruptedException ie) {
              }
            })
        .start();
  }

  public void clearLaundryRequest(ActionEvent actionEvent) {
    hangCB.setSelected(false);
    machineCB.setSelected(false);
    hypoCB.setSelected(false);
    patientNameLaundry.setText("");
    employeeNameLaundry.setText("");
    locationLaundry.setText("");
    dropOffTF.setText("");
    pickUpTF.setText("");
    notesLaundryTA.setText("");
  }
}
