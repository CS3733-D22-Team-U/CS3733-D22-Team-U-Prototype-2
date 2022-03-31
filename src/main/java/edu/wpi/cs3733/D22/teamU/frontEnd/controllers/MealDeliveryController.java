package edu.wpi.cs3733.D22.teamU.frontEnd.controllers;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import edu.wpi.cs3733.D22.teamU.frontEnd.Uapp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MealDeliveryController implements Initializable {

  @FXML JFXHamburger hamburger;
  @FXML VBox vBoxPane;
  @FXML Button placeMealRequest;
  @FXML Text mealStatus;
  @FXML CheckBox veganCheckbox;
  @FXML CheckBox vegCheckbox;
  @FXML CheckBox glutenCheckbox;
  @FXML CheckBox kosherCheckbox;
  @FXML CheckBox halalCheckbox;
  @FXML CheckBox lactoseCheckbox;
  @FXML CheckBox nutsCheckbox;
  @FXML CheckBox shellfishCheckbox;
  @FXML TextField patientNameMeal;
  @FXML TextField employeeNameMeal;
  @FXML TextField locationMeal;

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

  public void toEquipmentDelivery(ActionEvent actionEvent) throws IOException {
    Scene scene = Uapp.getScene("edu/wpi/team_u/views/equipmentDelivery.fxml");
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

  public void toLaundry(ActionEvent actionEvent) throws IOException {
    Scene scene = Uapp.getScene("edu/wpi/team_u/views/laundryService.fxml");
    Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    appStage.setScene(scene);
    appStage.show();
  }

  public void placeMealRequest(ActionEvent actionEvent) {
    mealStatus.setText("Processing...");
    mealStatus.setVisible(true);
    new Thread(
            () -> {
              try {
                Thread.sleep(1500); // milliseconds
                Platform.runLater(
                    () -> {
                      mealStatus.setText("Done");
                    });
                Thread.sleep(1500); // milliseconds
                Platform.runLater(
                    () -> {
                      mealStatus.setVisible(false);
                    });
              } catch (InterruptedException ie) {
              }
            })
        .start();
  }

  public void clearMealRequest(ActionEvent actionEvent) {
    veganCheckbox.setSelected(false);
    vegCheckbox.setSelected(false);
    glutenCheckbox.setSelected(false);
    kosherCheckbox.setSelected(false);
    halalCheckbox.setSelected(false);
    lactoseCheckbox.setSelected(false);
    nutsCheckbox.setSelected(false);
    shellfishCheckbox.setSelected(false);
    patientNameMeal.setText("");
    employeeNameMeal.setText("");
    locationMeal.setText("");
  }

  //  public void toSecurityService(ActionEvent actionEvent) throws IOException {
  //    Scene scene = Uapp.getScene("edu/wpi/team_u/views/labRequestServices.fxml");
  //    Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
  //    appStage.setScene(scene);
  //    appStage.show();
  //  }
  //
  //  public void toServicePage(ActionEvent actionEvent) throws IOException {
  //    Scene scene = Uapp.getScene("edu/wpi/team_u/views/HomePage.fxml");
  //    Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
  //    appStage.setScene(scene);
  //    appStage.show();
  //  }
  // public void togiftFloralService(ActionEvent actionEvent) throws IOException {
  //  Scene scene = Uapp.getScene("edu/wpi/team_u/views/giftFloralService.fxml");
  //  Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
  //  appStage.setScene(scene);
  //  appStage.show();
  // }
}
