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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.time.LocalDate;


public class AddItemController {
    @FXML
    public javafx.scene.image.ImageView ImageView;
    @FXML
    public Button AddButton;
    @FXML
    public Button HomeButton;
    @FXML
    public TextField itemCodeField;
    @FXML
    public TextField itemNameField;
    @FXML
    public TextField itemBrandField;
    @FXML
    public TextField priceField;
    @FXML
    public TextField quantityField;
    @FXML
    public TextField categoryField;
    @FXML
    private DatePicker purchasedDateField;
    @FXML
    public Button UploadImageButton;
    @FXML
    public Button SaveItemButton;

    private static final ObservableList<Item> itemList = FXCollections.observableArrayList();


    public static ObservableList<Item> getItemList() {
        return itemList;
    }

    @FXML
    public void AddItemClick(ActionEvent e) {
        // Retrieve user inputs from the input fields in Add_Items.fxml
        String itemCode = itemCodeField.getText().trim();
        String itemName = itemNameField.getText().trim();
        String itemBrand = itemBrandField.getText().trim();
        String priceText = priceField.getText().trim();
        String quantityText = quantityField.getText().trim();
        String category = categoryField.getText().trim();
        LocalDate purchasedDate = purchasedDateField.getValue();
        Image itemImage = ImageView.getImage();

        // Check if any field is empty
        if (emptyFields(itemCode, itemName, itemBrand, priceText, quantityText, category, purchasedDate)){
            showErrorAlert("Fill all the fields.");
            return;
        }

        // Validate price
        double price;
        try {
            price = Double.parseDouble(priceText);
            if (price <= 0) {
                showErrorAlert("Price must be a positive number.");
                return;
            }
        } catch (NumberFormatException ex) {
            showErrorAlert("Invalid price format. Please enter a valid number.");
            return;
        }

        // Validate quantity
        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) {
                showErrorAlert("Quantity must be a positive integer.");
                return;
            }
        } catch (NumberFormatException ex) {
            showErrorAlert("Invalid quantity format. Please enter a valid number.");
            return;
        }

        // Check if item code already exists in itemList
        for (Item product : itemList) {
            if (product.getCode().equals(itemCode)) {
                showErrorAlert("Duplicate Item Code. Item code " + itemCode + " already exists. Please enter a unique item code.");
                return;
            }
        }

        // Clear the input fields
        itemCodeField.clear();
        itemNameField.clear();
        itemBrandField.clear();
        priceField.clear();
        quantityField.clear();
        categoryField.clear();
        purchasedDateField.setValue(null);
        ImageView.setImage(null);

        // After adding a new item, display success message
        showAlert("Item added successfully!");

        // Create a new Item object with user inputs
        Item newItem = new Item(itemCode, itemName, itemBrand, price, quantity, category, purchasedDate, itemImage);

        // Add the new item to the itemList
        itemList.add(newItem);
    }


    public static boolean emptyFields( String itemCode, String itemName, String itemBrand, String priceText, String quantityText, String category, LocalDate purchasedDate){
        return (itemCode.isEmpty() || itemName.isEmpty() || itemBrand.isEmpty() || priceText.isEmpty()
                || quantityText.isEmpty() || category.isEmpty() || purchasedDate == null);
    }

    @FXML
    public void onUploadImageClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.jpeg", "*.png"));
        File selectedFile = fileChooser.showOpenDialog(UploadImageButton.getScene().getWindow());

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            ImageView.setImage(image);
        }
    }

    @FXML
    public void SaveItemButtonClick() {
        // Check if the item list is empty
        if (itemList.isEmpty()) {
            // Display an error message to the user
            showAlert();
            return;
        }
        showAlert("Items saved successfully!");
        saveItemsToFile();
    }

    private void saveItemsToFile() {
        try {
            // Create a new file or overwrite the existing file
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

    private void showErrorAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("No items to save.");
        alert.showAndWait();
    }
}
