package edu.wpi.cs3733.D22.teamU.frontEnd.controllers;

import com.jfoenix.controls.JFXCheckBox;
//import edu.wpi.cs3733.D22.teamU.BackEnd.MedicineRequest.MedicineRequest;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import edu.wpi.cs3733.D22.teamU.BackEnd.Udb;
import edu.wpi.cs3733.D22.teamU.DBController;
import edu.wpi.cs3733.D22.teamU.frontEnd.services.lab.LabUI;
//import edu.wpi.cs3733.D22.teamU.frontEnd.services.medicine.MedicineUI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MedicineDeliveryController extends ServiceController {

  @FXML
  JFXHamburger hamburger;
  @FXML JFXCheckBox Advil;
  @FXML JFXCheckBox Alprozalam;
  @FXML JFXCheckBox AmphetamineSalt;
  @FXML JFXCheckBox Atorvastatin;
  @FXML JFXCheckBox Lisinopril;
  @FXML JFXCheckBox Metformin;
  @FXML JFXCheckBox specialCheck;
  @FXML Button clearButton;
  @FXML TextArea specialReq;
  @FXML TextField patientName;
  @FXML TextField staffName;
  @FXML TextField advilTxt;
  @FXML TextField IDtxt;
  @FXML TextField alproTxt;
  @FXML TextField saltTxt;
  @FXML TextField atorvTxt;
  @FXML TextField lisinTxt;
  @FXML TextField metTxt;
  @FXML TextArea specialReqTxt;
  @FXML Text reset;
  @FXML Text processText;
  // Text status;
  public Button backButton;
  @FXML VBox medVbox;
  @FXML VBox nameVbox;
  @FXML VBox vBoxPane;
  @FXML Pane pane;
  @FXML Pane assistPane;
  @FXML
  TableColumn<LabUI, String> ReqID;
  @FXML TableColumn<LabUI, String> ReqPatient;
  @FXML TableColumn<LabUI, String> ReqStaff;
  @FXML TableColumn<LabUI, String> ReqMed;
  @FXML TableColumn<LabUI, String> ReqAmount;
  @FXML TableColumn<LabUI, String> ReqDate;
  @FXML TableColumn<LabUI, String> ReqTime;
  @FXML VBox requestHolder;
  @FXML
  ObservableList<LabUI> labUIRequests = FXCollections.observableArrayList();
  //ObservableList<JFXCheckBox> checkBoxes = FXCollections.observableArrayList();
  @FXML TableView<LabUI> activeRequestTable;


  private static final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  Udb udb = DBController.udb;

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
    //setUpActiveRequests();
    //for (Node checkbox : requestHolder.getChildren()) {
      //checkBoxes.add((JFXCheckBox) checkbox);
    //}

  }

  /*
  private void setUpActiveRequests() {
    ReqID.setCellValueFactory(new PropertyValueFactory<>("id"));
    ReqPatient.setCellValueFactory(new PropertyValueFactory<>("patientName"));
    ReqStaff.setCellValueFactory(new PropertyValueFactory<>("staffName"));
    ReqMed.setCellValueFactory(new PropertyValueFactory<>("medType"));
    ReqAmount.setCellValueFactory(new PropertyValueFactory<>("medAmount"));
    ReqDate.setCellValueFactory(new PropertyValueFactory<>("requestDate"));
    ReqTime.setCellValueFactory(new PropertyValueFactory<>("requestTime"));
    //activeRequestTable.setItems(getActiveRequestList());
  }

   */

  /*
  private ObservableList<MedicineUI> getActiveRequestList() {
    for (LabRequest request : udb.Impl.labRequestsList) {
      labUIRequests.add(
              new LabUI(
                      request.getID(),
                      request.getPatientName(),
                      request.getStaff(),
                      request.getLabType(),
                      request.getDate(),
                      request.getTime()));
    }
    return labUIRequests;
  }

   */



  public void enableTxt() {

    if (Advil.isSelected()) {
      advilTxt.setDisable(false);
    }

    if (Alprozalam.isSelected()) {
      alproTxt.setDisable(false);
    }

    if (AmphetamineSalt.isSelected()) {
      saltTxt.setDisable(false);
    }

    if (Atorvastatin.isSelected()) {
      atorvTxt.setDisable(false);
    }

    if (Lisinopril.isSelected()) {
      lisinTxt.setDisable(false);
    }

    if (Metformin.isSelected()) {
      metTxt.setDisable(false);
    }

    if (specialCheck.isSelected()) {
      specialReqTxt.setDisable(false);
    }
  }

  public void clear() {
    Advil.setSelected(false);
    Alprozalam.setSelected(false);
    AmphetamineSalt.setSelected(false);
    Atorvastatin.setSelected(false);
    Lisinopril.setSelected(false);
    Metformin.setSelected(false);
    specialCheck.setSelected(false);

    patientName.setText("");
    staffName.setText("");
    IDtxt.setText("");

    advilTxt.setText("");
    alproTxt.setText("");
    saltTxt.setText("");
    atorvTxt.setText("");
    lisinTxt.setText("");
    metTxt.setText("");
    specialReqTxt.setText("");
    processText.setText("");

    reset.setText("Cleared requests!");
    reset.setVisible(true);

    new Thread(
            () -> {
              try {
                Thread.sleep(1500); // milliseconds
                Platform.runLater(
                    () -> {
                      reset.setVisible(false);
                    });
              } catch (InterruptedException ie) {
              }
            })
        .start();
  }
  //
  //    lisinTxt.equals("") && metTxt.equals("") && specialReqTxt.equals(""))
  public void reqFields() {

    if (staffName.getText().equals("")
        || patientName.getText().equals("")
        || IDtxt.getText().equals("")
        || (advilTxt.getText().equals("")
                && alproTxt.getText().equals("")
                && saltTxt.getText().equals(""))
            && atorvTxt.getText().equals("")
            && lisinTxt.getText().equals("")
            && metTxt.getText().equals("")
            && specialReqTxt.getText().equals("")) {
      processText.setText("Please fill out all required fields!");
      processText.setVisible(true);
    } else {
      process();
    }
  }

  public void process() {
    processText.setText("Processing...");
    processText.setVisible(true);
    new Thread(
            () -> {
              try {
                Thread.sleep(2000); // milliseconds
                Platform.runLater(
                    () -> {
                      processText.setText(
                          "Staff Name: "
                              + staffName.getText()
                              + "\n"
                              + "Patient Name: "
                              + patientName.getText()
                              + "\n"
                              + "Order ID: "
                              + IDtxt.getText()
                              + "\n"
                              + ""
                              + "\n"
                              + "Medicine Order: "
                              + "\n"
                              + ""
                              + "\n"
                              + "Advil: "
                              + advilTxt.getText()
                              + "\n"
                              + "Alprozalam: "
                              + alproTxt.getText()
                              + "\n"
                              + "Amphetamine Salt: "
                              + saltTxt.getText()
                              + "\n"
                              + "Atorvastatin: "
                              + atorvTxt.getText()
                              + "\n"
                              + "Lisinopril: "
                              + lisinTxt.getText()
                              + "\n"
                              + "Metformin: "
                              + metTxt.getText()
                              + "\n"
                              + "Special Request: "
                              + request());
                    });
              } catch (InterruptedException ie) {
              }
            })
        .start();
  }

  private String request() {
    String request = "";
    if (specialReqTxt.equals("")) {
      request = "No response";
    } else {
      request = specialReqTxt.getText();
    }
    return request;
  }



  @Override
  public void addRequest() {}

  @Override
  public void removeRequest() {}

  @Override
  public void updateRequest() {}
}
