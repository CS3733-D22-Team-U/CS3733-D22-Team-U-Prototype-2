package edu.wpi.team_u.controllers;

import edu.wpi.team_u.Uapp;
import java.awt.*;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AppController {

  public TextField nameField;
  public Label outputLabel;
  public Button laundryService;

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
}
