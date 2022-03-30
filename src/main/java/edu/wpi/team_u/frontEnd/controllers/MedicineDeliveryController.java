package edu.wpi.team_u.frontEnd.controllers;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import edu.wpi.team_u.Uapp;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import edu.wpi.team_u.frontEnd.services.IService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MedicineDeliveryController implements Initializable, IService {

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
  @FXML
  Text resetDone;
  @FXML
  Text processText;
  //Text status;




  public Button backButton;

  @FXML
  JFXHamburger hamburger;
  @FXML
  VBox vBoxPane;

  public void clear(){
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

public void process(){
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
/*
  public void checkFields(){
    if(patientName.equals("") || staffName.equals("") ){
      status.setText("Fill out all required fields!");
      status.setVisible(true);
    }
    if((Advil.isSelected() && Atorvastatin.isSelected() && Alprozalam.isSelected() && AmphetamineSalt.isSelected() && Lisinopril.isSelected() && Metformin.isSelected() && specialCheck.isSelected()) == false){
      status.setText("Fill out all required fields!");
      status.setVisible(true);
    }

  }

 */

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

  @Override
  public void addRequest() {

  }

  @Override
  public void removeRequest() {

  }

  @Override
  public void updateRequest() {

  }

  @Override
  public void displayRequest() {

  }

  public void toHome(ActionEvent actionEvent) throws IOException {
    Scene scene = Uapp.getScene("edu/wpi/team_u/views/HomePage.fxml");
    Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    appStage.setScene(scene);
    appStage.show();
  }

  @Override
  public void toLabRequest(ActionEvent actionEvent) throws IOException {
    Scene scene = Uapp.getScene("edu/wpi/team_u/views/labRequestService.fxml");
    Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    appStage.setScene(scene);
    appStage.show();
  }

  public void backToServicePage(ActionEvent actionEvent) throws IOException {
    Scene scene = Uapp.getScene("edu/wpi/team_u/views/HomePage.fxml");
    Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    appStage.setScene(scene);
    appStage.show();
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

  @Override
  public void toMap(ActionEvent actionEvent) throws IOException {

  }

  public void toMealDelivery(ActionEvent actionEvent) throws IOException {
    Scene scene = Uapp.getScene("edu/wpi/team_u/views/mealDelivery.fxml");
    Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    appStage.setScene(scene);
    appStage.show();
  }

  @Override
  public void toGiftAndFloral(ActionEvent actionEvent) throws IOException {
    Scene scene = Uapp.getScene("edu/wpi/team_u/views/giftFloralService.fxml");
    Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    appStage.setScene(scene);
    appStage.show();
  }

  @Override
  public void toLaundry(ActionEvent actionEvent) throws IOException {
    Scene scene = Uapp.getScene("edu/wpi/team_u/views/laundryService.fxml");
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




}
