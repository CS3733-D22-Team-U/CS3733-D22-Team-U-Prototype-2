package edu.wpi.cs3733.D22.teamU.frontEnd.services;

import java.io.IOException;
import javafx.event.ActionEvent;
import javax.swing.*;

public interface IService {
  public void addRequest();

  public void removeRequest();

  public void updateRequest();

  public void displayRequest();

  public void toHome(ActionEvent actionEvent) throws IOException;

  public void toEquipmentDelivery(ActionEvent actionEvent) throws IOException;

  public void toLabRequest(ActionEvent actionEvent) throws IOException;

  public void toMealDelivery(ActionEvent actionEvent) throws IOException;

  public void toGiftAndFloral(ActionEvent actionEvent) throws IOException;

  public void toLaundry(ActionEvent actionEvent) throws IOException;

  public void toMedicineDelivery(ActionEvent actionEvent) throws IOException;

  public void toMap(ActionEvent actionEvent) throws IOException;
}
