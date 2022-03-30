package edu.wpi.team_u.frontEnd.controllers;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import edu.wpi.team_u.BackEnd.Equipment.Equipment;
import edu.wpi.team_u.BackEnd.Udb;
import edu.wpi.team_u.Uapp;
import edu.wpi.team_u.frontEnd.equipmentDelivery.EquipmentUI;
import edu.wpi.team_u.frontEnd.services.IService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.SneakyThrows;

public class EquipmentDeliverySystemController implements Initializable, IService {

  @FXML JFXHamburger hamburger;
  @FXML VBox vBoxPane;
  @FXML TabPane tabPane;
  @FXML Pane backgroundPane;
  @FXML AnchorPane anchor;
  @FXML Pane assistPane;
  @FXML TableColumn<EquipmentUI, String> nameCol;
  @FXML TableColumn<EquipmentUI, Integer> inUse;
  @FXML TableColumn<EquipmentUI, Integer> available;
  @FXML TableColumn<EquipmentUI, Integer> total;
  @FXML TableView<EquipmentUI> table;
  ObservableList<EquipmentUI> equipmentUI = FXCollections.observableArrayList();

  Udb udb = new Udb();

  @SneakyThrows
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    HamburgerBasicCloseTransition closeTransition = new HamburgerBasicCloseTransition(hamburger);
    String csvLocationFile = "src/main/resources/TowerLocations.csv";
    String csvEmployee = "src/main/resources/TowerEmployees.csv";
    String csvEquipment = "src/main/resources/TowerEquipment.csv";

    String[] CSVfiles = {csvLocationFile, csvEmployee, csvEquipment};

    udb.start("admin", "admin", CSVfiles);
    closeTransition.setRate(-1);
    hamburger.addEventHandler(
        MouseEvent.MOUSE_CLICKED,
        e -> {
          // if (((e.getY() <= 25) && (e.getY() >= 0)) && ((e.getX() <= 25) && (e.getX() >= 0))) {
          closeTransition.setRate(closeTransition.getRate() * -1);
          closeTransition.play();
          vBoxPane.setVisible(!vBoxPane.isVisible());
          tabPane.setDisable(!tabPane.isDisable());
          if (tabPane.isDisable()) {
            hamburger.setPrefWidth(200);
            tabPane.setEffect(new GaussianBlur(10));
            assistPane.setDisable(true);
          } else {
            tabPane.setEffect(null);
            hamburger.setPrefWidth(77);
            assistPane.setDisable(false);
          }
        });
    setUpTable();
  }

  private void setUpTable() {
    nameCol.setCellValueFactory(new PropertyValueFactory<EquipmentUI, String>("equipmentName"));
    inUse.setCellValueFactory(new PropertyValueFactory<EquipmentUI, Integer>("amountInUse"));
    available.setCellValueFactory(
        new PropertyValueFactory<EquipmentUI, Integer>("amountAvailable"));
    total.setCellValueFactory(new PropertyValueFactory<EquipmentUI, Integer>("totalAmount"));

    table.setItems(getEquipmentList());
  }

  public void increase(ActionEvent actionEvent) throws IOException {
    try {
      udb.EquipmentImpl.editEquipValue("src/main/resources/TowerEquipment.csv", "Masks", 1, 4);
      System.out.println(udb.EquipmentImpl.EquipmentList.get(1).getAmount());
      getEquipmentList();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    table.refresh();
  }

  private ObservableList<EquipmentUI> getEquipmentList() {
    equipmentUI.clear();
    equipmentUI.add(new EquipmentUI("bandiad", 23, 25, 26));
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

  @Override
  public void addRequest() {}

  @Override
  public void removeRequest() {}

  @Override
  public void updateRequest() {}

  @Override
  public void displayRequest() {}

  public void toHome(ActionEvent actionEvent) throws IOException {
    Scene scene = Uapp.getScene("edu/wpi/team_u/views/HomePage.fxml");
    Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    appStage.setScene(scene);
    appStage.show();
  }

  public void toLabRequest(ActionEvent actionEvent) throws IOException {
    Scene scene = Uapp.getScene("edu/wpi/team_u/views/labRequestServices.fxml");
    Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    appStage.setScene(scene);
    appStage.show();
  }

  public void toMealDelivery(ActionEvent actionEvent) throws IOException {
    Scene scene = Uapp.getScene("edu/wpi/team_u/views/mealDelivery.fxml");
    Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    appStage.setScene(scene);
    appStage.show();
  }

  public void toGiftAndFloral(ActionEvent actionEvent) throws IOException {
    Scene scene = Uapp.getScene("edu/wpi/team_u/views/giftFloralService.fxml");
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

  public void toMedicineDelivery(ActionEvent actionEvent) throws IOException {
    Scene scene = Uapp.getScene("edu/wpi/team_u/views/medicineDelivery.fxml");
    Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    appStage.setScene(scene);
    appStage.show();
  }

  public void toMap(ActionEvent actionEvent) throws IOException {
    Scene scene = Uapp.getScene("edu/wpi/team_u/views/map.fxml");
    Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
    appStage.setScene(scene);
    appStage.show();
  }
}
