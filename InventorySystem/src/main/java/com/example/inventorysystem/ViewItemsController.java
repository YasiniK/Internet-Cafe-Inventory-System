package com.example.inventorysystem;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;


public class ViewItemsController {

    @FXML
    private TableView<Item> tableView;
    @FXML
    private TableColumn<Item, String> itemCodeColumn;
    @FXML
    private TableColumn<Item, String> itemNameColumn;
    @FXML
    private TableColumn<Item, String> itemBrandColumn;
    @FXML
    private TableColumn<Item, Double> itemPriceColumn;
    @FXML
    private TableColumn<Item, Integer> quantityColumn;
    @FXML
    private TableColumn<Item, String> categoryColumn;
    @FXML
    private TableColumn<Item, LocalDate> purchasedDateColumn;
    @FXML
    private TableColumn<Item, Image> itemImageColumn;
    public Button HomeButton;
    @FXML
    public Button DeleteButton;
    @FXML
    public Button selectButton;
    private static final ObservableList<Item> itemList = FXCollections.observableArrayList();


    @FXML
    private void initialize() {
        // Set up cell value factories for each TableColumn
        itemCodeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        itemBrandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        itemPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        purchasedDateColumn.setCellValueFactory(new PropertyValueFactory<>("purchasedDate"));
        itemImageColumn.setCellValueFactory(cellData -> {
            Item item = cellData.getValue();
            Image itemImage = item.getItemImage();
            return new SimpleObjectProperty<>(itemImage);
        });
        itemImageColumn.setCellFactory(param -> new ImageTableCell<>());

        // Load the items and sort them by item code in ascending order
        ObservableList<Item> itemList = AddItemController.getItemList();


        sortItemsByItemCode(itemList);

        // Set the sorted list to the TableView
        tableView.setItems(itemList);
    }

    //sorted item code according to ascending order
    private void sortItemsByItemCode(ObservableList<Item> itemList) {
        int n = itemList.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Item currentItem = itemList.get(j);
                Item nextItem = itemList.get(j + 1);

                // Compare the item codes and swap if needed
                if (currentItem.getCode().compareTo(nextItem.getCode()) > 0) {
                    itemList.set(j, nextItem);
                    itemList.set(j + 1, currentItem);
                }
            }
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

    public void selectButtonClick(ActionEvent e) throws IOException {
        // Get the selected row data from the table
        Item selectedRowData = tableView.getSelectionModel().getSelectedItem();

        // Check if a row is selected
        if (selectedRowData == null) {
            // If no row is selected, show an error message to the user
            showAlert();
            return;
        }

        // Load the "Update_items.fxml" file
        FXMLLoader updateLoader = new FXMLLoader(getClass().getResource("Update_items.fxml"));
        Parent root = updateLoader.load();
        UpdateItemFieldsController updateItemsController = updateLoader.getController();

        // Set the tableView reference in the UpdateItemFieldsController
        updateItemsController.setTableView(tableView);

        // Pass the selected row data to the controller
        updateItemsController.setRowData(selectedRowData);

        // Create and display the stage
        Stage updateItemStage = new Stage();
        updateItemStage.setTitle("Update Items!");
        updateItemStage.setScene(new Scene(root, 510, 600));
        updateItemStage.show();

        // Close the previous stage
        Stage previousStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        previousStage.close();
    }


    public class ImageTableCell<S, T> extends TableCell<S, T> {

        private final ImageView imageView = new ImageView();

        public ImageTableCell() {
            // Set any required configurations for the ImageView (e.g., size, aspect ratio, etc.)
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            setGraphic(imageView);
        }

        @Override
        protected void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setGraphic(null);
            } else {
                imageView.setImage((Image) item);
                setGraphic(imageView);
            }
        }

    }

    public void DeleteItemClick(ActionEvent event) {
        Item selectedItem = tableView.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showErrorAlert();
            return;
        }

        // Show confirmation dialog before deleting the item
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected item?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText(null);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            // Remove the item from the table and itemList
            tableView.getItems().remove(selectedItem);
            itemList.remove(selectedItem);

            // Show the delete successful alert
            showSuccessAlert();
            // Save the updated items list to the text file after deleting the item
            saveItemsToFile();
        }
    }

    private void saveItemsToFile() {
        try {
            // Create a new file or overwrite the existing one
            File file = new File("items.txt");
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
            // Handle any exceptions that may occur during the file writing process
        }
    }



    private void showErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText("Please select an item to delete.");
        alert.showAndWait();
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please select an item to update.");
        alert.showAndWait();
    }
    private void showSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Item deleted successfully!");
        alert.showAndWait();
    }
}


