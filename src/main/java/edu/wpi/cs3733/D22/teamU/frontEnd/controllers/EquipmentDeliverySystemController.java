package edu.wpi.cs3733.D22.teamU.frontEnd.controllers;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextArea;
import edu.wpi.cs3733.D22.teamU.BackEnd.Equipment.Equipment;
import edu.wpi.cs3733.D22.teamU.BackEnd.Request.EquipRequest.EquipRequest;
import edu.wpi.cs3733.D22.teamU.BackEnd.Request.Request;
import edu.wpi.cs3733.D22.teamU.BackEnd.Udb;
import edu.wpi.cs3733.D22.teamU.DBController;
import edu.wpi.cs3733.D22.teamU.frontEnd.services.equipmentDelivery.EquipmentUI;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import lombok.SneakyThrows;

public class EquipmentDeliverySystemController extends ServiceController {

  @FXML TabPane tabPane;
  @FXML TableColumn<EquipmentUI, String> nameCol;
  @FXML TableColumn<EquipmentUI, Integer> inUse;
  @FXML TableColumn<EquipmentUI, Integer> available;
  @FXML TableColumn<EquipmentUI, Integer> total;
  @FXML TableView<EquipmentUI> table;
  @FXML VBox requestHolder;
  @FXML Text requestText;
  @FXML Button clearButton;
  @FXML Button submitButton;
  @FXML TableColumn<EquipmentUI, String> activeReqID;
  @FXML TableColumn<EquipmentUI, String> activeReqName;
  @FXML TableColumn<EquipmentUI, Integer> activeReqAmount;
  @FXML TableColumn<EquipmentUI, String> activeReqType;
  @FXML TableColumn<EquipmentUI, String> activeReqDestination;
  @FXML TableColumn<EquipmentUI, String> activeDate;
  @FXML TableColumn<EquipmentUI, String> activeTime;
  @FXML TableColumn<EquipmentUI, Integer> activePriority;

  @FXML TableView<EquipmentUI> activeRequestTable;
  @FXML VBox inputFields;
  @FXML VBox locationInput;

  ObservableList<EquipmentUI> equipmentUI = FXCollections.observableArrayList();
  ObservableList<JFXCheckBox> checkBoxes = FXCollections.observableArrayList();
  ObservableList<JFXTextArea> checkBoxesInput = FXCollections.observableArrayList();
  ObservableList<JFXTextArea> locInput = FXCollections.observableArrayList();

  ObservableList<EquipmentUI> equipmentUIRequests = FXCollections.observableArrayList();
  Udb udb = DBController.udb;

  private static final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  @SneakyThrows
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    super.initialize(location, resources);
    setUpAllEquipment();
    setUpActiveRequests();

    for (Node checkBox : requestHolder.getChildren()) {
      checkBoxes.add((JFXCheckBox) checkBox);
    }
    for (Node textArea : inputFields.getChildren()) {
      checkBoxesInput.add((JFXTextArea) textArea);
    }
    for (Node textArea : locationInput.getChildren()) {
      locInput.add((JFXTextArea) textArea);
    }

    for (int i = 0; i < checkBoxesInput.size(); i++) {
      int finalI = i;
      checkBoxesInput
          .get(i)
          .disableProperty()
          .bind(
              Bindings.createBooleanBinding(
                  () -> !checkBoxes.get(finalI).isSelected(),
                  checkBoxes.stream().map(CheckBox::selectedProperty).toArray(Observable[]::new)));
      locInput
          .get(i)
          .disableProperty()
          .bind(
              Bindings.createBooleanBinding(
                  () -> !checkBoxes.get(finalI).isSelected(),
                  checkBoxes.stream().map(CheckBox::selectedProperty).toArray(Observable[]::new)));
    }
    clearButton
        .disableProperty()
        .bind(
            Bindings.createBooleanBinding(
                () -> checkBoxes.stream().noneMatch(JFXCheckBox::isSelected),
                checkBoxes.stream().map(CheckBox::selectedProperty).toArray(Observable[]::new)));

    submitButton
        .disableProperty()
        .bind(
            Bindings.createBooleanBinding(
                () -> checkBoxes.stream().noneMatch(JFXCheckBox::isSelected),
                checkBoxes.stream().map(CheckBox::selectedProperty).toArray(Observable[]::new)));
  }

  private void setUpAllEquipment() {
    nameCol.setCellValueFactory(new PropertyValueFactory<EquipmentUI, String>("equipmentName"));
    inUse.setCellValueFactory(new PropertyValueFactory<EquipmentUI, Integer>("amountInUse"));
    available.setCellValueFactory(
        new PropertyValueFactory<EquipmentUI, Integer>("amountAvailable"));
    total.setCellValueFactory(new PropertyValueFactory<EquipmentUI, Integer>("totalAmount"));
    table.setItems(getEquipmentList());
  }

  private void setUpActiveRequests() {
    activeReqID.setCellValueFactory(new PropertyValueFactory<>("id"));
    activeReqName.setCellValueFactory(new PropertyValueFactory<>("equipmentName"));
    activeReqAmount.setCellValueFactory(new PropertyValueFactory<>("requestAmount"));
    activeReqType.setCellValueFactory(new PropertyValueFactory<>("type"));
    activeReqDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
    activeDate.setCellValueFactory(new PropertyValueFactory<>("requestDate"));
    activeTime.setCellValueFactory(new PropertyValueFactory<>("requestTime"));
    activePriority.setCellValueFactory(new PropertyValueFactory<>("priority"));
    activeRequestTable.setItems(getActiveRequestList());
  }

  private ObservableList<EquipmentUI> newRequest(
      String id,
      String name,
      int amount,
      String destination,
      String date,
      String time,
      int priority) {
    equipmentUIRequests.add(new EquipmentUI(id, name, amount, destination, date, time, priority));
    return equipmentUIRequests;
  }

  private ObservableList<EquipmentUI> getEquipmentList() {
    equipmentUI.clear();
    for (Equipment equipment : udb.EquipmentImpl.EquipmentList) {
      equipmentUI.add(
          new EquipmentUI(
              equipment.getName(),
              equipment.getInUse(),
              equipment.getAvailable(),
              equipment.getAmount()));
    }

    return equipmentUI;
  }

  private ObservableList<EquipmentUI> getActiveRequestList() {
    for (EquipRequest equipRequest : udb.equipRequestImpl.equipRequestList) {
      equipmentUIRequests.add(
          new EquipmentUI(
              equipRequest.getID(),
              equipRequest.getName(),
              equipRequest.getAmount(),
              equipRequest.getDestination(),
              equipRequest.getDate(),
              equipRequest.getTime(),
              equipRequest.getPri()));
    }
    return equipmentUIRequests;
  }

  @Override
  public void addRequest() {
    StringBuilder startRequestString = new StringBuilder("Your request for : ");

    String endRequest = " has been placed successfully";
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    int requestAmount = 0;
    for (int i = 0; i < checkBoxes.size(); i++) {
      if (checkBoxes.get(i).isSelected()) {
        String inputString = "";

        // todo find a way to make it so we cant submit when amount or location are empty
        // not a great way to do it but it sorta works
        while (locInput.get(i).getText().trim().equals("")) {
          //
        }

        if (checkBoxesInput.get(i).getText().trim().equals("")) {
          inputString = "0";
        } else {
          inputString = checkBoxesInput.get(i).getText().trim();
        }
        String room = locInput.get(i).getText();

        requestAmount = Integer.parseInt(inputString);

        startRequestString
            .append(requestAmount)
            .append(" ")
            .append(checkBoxes.get(i).getText())
            .append("(s) to room ")
            .append(room)
            .append(", ");

        double rand = Math.random() * 10000;

        EquipmentUI request =
            new EquipmentUI(
                (int) rand + "",
                checkBoxes.get(i).getText(),
                requestAmount,
                room,
                sdf3.format(timestamp).substring(0, 10),
                sdf3.format(timestamp).substring(11),
                1);

        activeRequestTable.setItems(
            newRequest(
                request.getId(),
                request.getEquipmentName(),
                request.getRequestAmount(),
                request.getDestination(),
                request.getRequestDate(),
                request.getRequestTime(),
                1));
        try {
          udb.add( // TODO Have random ID and enter Room Destination
              new Request(
                  request.getId(),
                  request.getEquipmentName(),
                  request.getRequestAmount(),
                  request.getType(),
                  request.getDestination(),
                  request.getRequestDate(),
                  request.getRequestTime(),
                  1));

        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    requestText.setText(startRequestString + endRequest);
    requestText.setVisible(true);
    new Thread(
            () -> {
              try {
                Thread.sleep(3500); // milliseconds
                Platform.runLater(
                    () -> {
                      requestText.setVisible(false);
                    });
              } catch (InterruptedException ie) {
              }
            })
        .start();
  }

  @Override
  public void removeRequest() {}

  @Override
  public void updateRequest() {}

  public void clearRequest() {
    for (int i = 0; i < checkBoxes.size(); i++) {
      checkBoxes.get(i).setSelected(false);
      checkBoxesInput.get(i).clear();
    }
    requestText.setText("Cleared Requests!");
    requestText.setVisible(true);
    new Thread(
            () -> {
              try {
                Thread.sleep(1500); // milliseconds
                Platform.runLater(
                    () -> {
                      requestText.setVisible(false);
                    });
              } catch (InterruptedException ie) {
              }
            })
        .start();
  }
}
