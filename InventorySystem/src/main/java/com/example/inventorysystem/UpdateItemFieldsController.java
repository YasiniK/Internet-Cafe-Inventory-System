package com.example.inventorysystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;


public class UpdateItemFieldsController {

    @FXML
    public TextField itemCodeUpdateField;

    @FXML
    public TextField itemNameUpdateFiled;

    @FXML
    public TextField itemBrandUpdateFiled;

    @FXML
    public TextField itemPriceUpdateFiled;

    @FXML
    public TextField itemQuantityUpdateFiled;

    @FXML
    public TextField itemCategoryUpdateFiled;

    @FXML
    public DatePicker itemPurchasedDateUpdateFiled;

    @FXML
    public Button UpdateButton;
    @FXML
    public Button HomeButton;

    @FXML
    public Button newUploadImageButton;

    @FXML
    public ImageView itemImageUpdateFiled;

    private static final ObservableList<Item> itemList = FXCollections.observableArrayList();
    private TableView<Item> tableView;

    // Setter method to set the tableView
    public void setTableView(TableView<Item> tableView) {
        this.tableView = tableView;
    }

    @FXML
    private void UpdateItemClick() {
        // Get the entered values from the text fields and date picker
        String code = itemCodeUpdateField.getText();
        String name = itemNameUpdateFiled.getText();
        String brand = itemBrandUpdateFiled.getText();

        // Validate the name field
        if (name.isEmpty()) {
            showValidationError("Name field cannot be empty.");
            return;
        }

        // Validate the brand field
        if (brand.isEmpty()) {
            showValidationError("Brand field cannot be empty.");
            return;
        }

        // Validate the price field
        double price;
        try {
            price = new DoubleStringConverter().fromString(itemPriceUpdateFiled.getText());
            if (price <= 0) {
                showValidationError("Price must be a positive value.");
                return;
            }
        } catch (NumberFormatException e) {
            showValidationError("Price must be a number.");
            return;
        }

        // Validate the quantity field
        int quantity;
        try {
            quantity = new IntegerStringConverter().fromString(itemQuantityUpdateFiled.getText());
            if (quantity <= 0) {
                showValidationError("Quantity must be a positive number.");
                return;
            }
        } catch (NumberFormatException e) {
            showValidationError("Quantity must be a valid number.");
            return;
        }

        String category = itemCategoryUpdateFiled.getText();
        LocalDate purchasedDate = itemPurchasedDateUpdateFiled.getValue();
        Image newItemImage = itemImageUpdateFiled.getImage();

        // Validate the category field
        if (category.isEmpty()) {
            showValidationError("Category field cannot be empty.");
            return;
        }

        // Validate the purchased date field
        if (purchasedDate == null) {
            showValidationError("Purchased Date field cannot be empty.");
            return;
        }

        // Get the selected item from the list
        Item selectedItem = tableView.getSelectionModel().getSelectedItem();

        // Check if the item code is being changed
        if (!code.equals(selectedItem.getCode())) {
            showValidationError("Item code cannot be changed.");
            return;
        }

        // Update the item details
        selectedItem.setName(name);
        selectedItem.setBrand(brand);
        selectedItem.setPrice(price);
        selectedItem.setQuantity(quantity);
        selectedItem.setCategory(category);
        selectedItem.setPurchasedDate(purchasedDate);
        selectedItem.setImage(newItemImage);

        // Clear the input fields
        itemCodeUpdateField.clear();
        itemNameUpdateFiled.clear();
        itemBrandUpdateFiled.clear();
        itemPriceUpdateFiled.clear();
        itemQuantityUpdateFiled.clear();
        itemCategoryUpdateFiled.clear();
        itemPurchasedDateUpdateFiled.setValue(null);
        itemImageUpdateFiled.setImage(null);

        // Show the update successful alert
        showAlert();

        // Refresh the item table to display the updated data
        if (itemList != null) {
            itemList.setAll(itemList);
        }
    }


    // Populates the input fields with data from the selected item for updating
    public void setRowData(Item rowData) {
        itemCodeUpdateField.setText(rowData.getCode());
        itemNameUpdateFiled.setText(rowData.getName());
        itemBrandUpdateFiled.setText(rowData.getBrand());
        itemPriceUpdateFiled.setText(String.valueOf(rowData.getPrice()));
        itemQuantityUpdateFiled.setText(String.valueOf(rowData.getQuantity()));
        itemCategoryUpdateFiled.setText(rowData.getCategory());
        itemPurchasedDateUpdateFiled.setValue(rowData.getPurchasedDate());
        itemImageUpdateFiled.setImage(rowData.getItemImage());
    }

    public void onUploadImageClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.jpeg", "*.png"));
        File selectedFile = fileChooser.showOpenDialog(newUploadImageButton.getScene().getWindow());

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            itemImageUpdateFiled.setImage(image);
        }
    }

    @FXML
    private void HomeButtonClick(ActionEvent e) throws IOException {
        Stage openStage = new Stage();
        FXMLLoader homeLoader = new FXMLLoader(CafeController.class.getResource("John Internet Cafe.fxml"));
        Scene scene = new Scene(homeLoader.load(), 579, 400);
        openStage.setTitle("John’s Internet Cafe");
        openStage.setScene(scene);
        openStage.show();

        Stage previousStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        previousStage.close();
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Item details updated successfully!");
        alert.showAndWait();
    }

    private void showValidationError(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(s);
        alert.showAndWait();
    }
}

