package edu.wpi.team_u.controllers;

import java.awt.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AppController {

  public TextField nameField;
  public Label outputLabel;

  @FXML
  void sayHello() {
    String name = nameField.getText();
    outputLabel.setText("Hello, " + name + "!");
  }
}
