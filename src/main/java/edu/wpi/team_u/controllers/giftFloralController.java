package edu.wpi.team_u.controllers;

import edu.wpi.team_u.Uapp;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class giftFloralController {
    public Button servicePage;

    public void toServicePage(ActionEvent actionEvent) throws IOException {
        Scene scene = Uapp.getScene("edu/wpi/team_u/views/app.fxml");
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();
    }
}
