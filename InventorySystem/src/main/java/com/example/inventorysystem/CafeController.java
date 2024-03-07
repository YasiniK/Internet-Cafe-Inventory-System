package com.example.inventorysystem;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.*;


public class CafeController {
    @FXML
    public Button AddItemButton;
    @FXML
    public Button UpdateItemButton;
    @FXML
    public Button DeleteItemButton;
    @FXML
    public Button SaveItemButton;
    @FXML
    public Button ViewItemButton;
    @FXML
    public Button dealersButton;
    @FXML
    public Button ExitButton;


    @FXML
    public void AddItemButtonClick(ActionEvent e) throws IOException {
        Stage addItemStage = new Stage();
        FXMLLoader addLoader = new FXMLLoader(AddItemController.class.getResource("Add_Items.fxml"));
        Scene scene = new Scene(addLoader.load(), 510, 600);
        addItemStage.setTitle("Add Items!");
        addItemStage.setScene(scene);
        addItemStage.show();

        Stage previousStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        previousStage.close();
    }

    @FXML
    private void UpdateItemButtonClick(ActionEvent e) throws IOException {
        Stage viewItemStage = new Stage();
        FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("View_Items.fxml"));
        AnchorPane root = viewLoader.load();
        Scene scene = new Scene(root, 755, 503);
        viewItemStage.setTitle("Update Items");
        viewItemStage.setScene(scene);
        viewItemStage.show();

        // Close the current window
        Stage previousStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        previousStage.close();
    }

    @FXML
    public void DeleteItemButtonClick(ActionEvent e) throws IOException {
        Stage viewItemStage = new Stage();
        FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("View_Items.fxml"));
        AnchorPane root = viewLoader.load();
        Scene scene = new Scene(root, 755, 503);
        viewItemStage.setTitle("Delete Items");
        viewItemStage.setScene(scene);
        viewItemStage.show();

        // Close the current window
        Stage previousStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        previousStage.close();
    }

    @FXML
    public void ViewItemButtonClick(ActionEvent e) throws IOException {
        Stage viewItemStage = new Stage();
        FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("View_Items.fxml"));
        AnchorPane root = viewLoader.load();
        Scene scene = new Scene(root, 755, 503);
        viewItemStage.setTitle("View Items");
        viewItemStage.setScene(scene);
        viewItemStage.show();

        // Close the current window
        Stage previousStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        previousStage.close();
    }

    @FXML
    public void SaveItemButtonClick() {
        ObservableList<Item> itemList = AddItemController.getItemList();

        // Check if the item list is empty
        if (itemList.isEmpty()) {
            // Display an error message to the user
            showAlert(Alert.AlertType.ERROR);
            return;
        }
        try {
            // Create a new file or overwrite the existing one
            File file = new File("Coursework/items.txt");
            FileWriter fileWriter = new FileWriter(file);

            // Write each item's details to the file
            for (Item item : itemList) {
                fileWriter.write(
                        item.getCode() + "," +
                                item.getName() + "," +
                                item.getBrand() + "," +
                                item.getPrice() + "," +
                                item.getQuantity() + "," +
                                item.getCategory() + "," +
                                item.getPurchasedDate() + "," +
                                item.getImage() + "\n");
            }

            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        showAlert();
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Items saved successfully!");
        alert.showAndWait();
    }

    private void showAlert(Alert.AlertType alertType) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("No items to save.");
        alert.showAndWait();
    }

    @FXML
    public void dealersButtonClick(ActionEvent e) throws IOException {
        Stage dealersStage = new Stage();
        FXMLLoader dealersLoader = new FXMLLoader(getClass().getResource("random_select_dealers.fxml"));
        AnchorPane root = dealersLoader.load();
        Scene scene = new Scene(root, 600, 400);
        dealersStage.setTitle("Random Dealers");
        dealersStage.setScene(scene);
        dealersStage.show();

        // Close the current window
        Stage previousStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        previousStage.close();
    }


    public void ExitButtonClick(ActionEvent event) {
        System.exit(0);
    }
}