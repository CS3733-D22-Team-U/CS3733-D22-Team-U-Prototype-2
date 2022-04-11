package edu.wpi.cs3733.D22.teamU.frontEnd.controllers;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;


public class AboutController extends ServiceController{

    @FXML Pane pane;
    @FXML JFXHamburger hamburger;


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
    }
}
