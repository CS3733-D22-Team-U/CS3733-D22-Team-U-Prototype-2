package edu.wpi.team_u.controllers;

import edu.wpi.team_u.Uapp;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AppController implements Initializable {

  public TextField nameField;
  public Label outputLabel;
  public Button laundryService;
  public Button equipmentDelivery;

  private ImageView equipmentDeliveryIcon;
  @FXML
  void sayHello() {
    String name = nameField.getText();
    outputLabel.setText("Hello, " + name + "!");
  }

  public void toLaundryService(ActionEvent actionEvent) throws IOException {
    Scene scene = Uapp.getScene("edu/wpi/team_u/views/laundryService.fxml");
    Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    appStage.setScene(scene);
    appStage.show();
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

  public void togiftFloralService(ActionEvent actionEvent) throws IOException {
    Scene scene = Uapp.getScene("edu/wpi/team_u/views/giftFloralService.fxml");
    Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    appStage.setScene(scene);
    appStage.show();
  }

  public void toDeliverEquipmentController(ActionEvent actionEvent) throws IOException {
    Scene scene = Uapp.getScene("edu/wpi/team_u/views/equipmentDelivery.fxml");
    Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    appStage.setScene(scene);
    appStage.show();
  }

  public void toSecurityService(ActionEvent actionEvent) throws IOException {
    Scene scene = Uapp.getScene("edu/wpi/team_u/views/labRequestServices.fxml");
    Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    appStage.setScene(scene);
    appStage.show();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    File file = new File("edu/wpi/team_u/icons/doctoricon.png");
    Image image = new Image(file.toURI().toString());
    equipmentDeliveryIcon.setImage(image);
  }
}
