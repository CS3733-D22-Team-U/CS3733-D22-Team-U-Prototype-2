package edu.wpi.cs3733.D22.teamU.frontEnd.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import edu.wpi.cs3733.D22.teamU.frontEnd.Uapp;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomePageController extends ServiceController {

  @Override
  public void initialize(URL location, ResourceBundle resources) {}

  @Override
  public void addRequest() {}

  @Override
  public void removeRequest() {}

  @Override
  public void updateRequest() {}


  public void toCloseApp(ActionEvent actionEvent) {
    Platform.exit();
  }
}
