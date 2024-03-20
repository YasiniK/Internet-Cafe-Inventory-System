package com.example.inventorysystem;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class DealerItemController {
    @FXML
    public Button BackButton;
    @FXML
    private TableView<DealerItemDetail> itemDetailsTableView;
    @FXML
    private TableColumn<DealerItemDetail, String> itemName;
    @FXML
    private TableColumn<DealerItemDetail, String> itemBrand;
    @FXML
    private TableColumn<DealerItemDetail, Integer> itemPrice;
    @FXML
    private TableColumn<DealerItemDetail, Integer> itemQuantity;

    public void initialize() {
        // Set cell value factories for the columns
        itemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        itemBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        itemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        itemQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    }

    public void populateDealerItemsTable(List<DealerItemDetail> dealerItemsList) {
        // Set the items of the TableView with the dealer's items
        itemDetailsTableView.setItems(FXCollections.observableArrayList(dealerItemsList));
    }

    @FXML
    private void HomeButtonClick (ActionEvent e) throws IOException {
        Stage openStage = new Stage();
        FXMLLoader homeLoader = new FXMLLoader(CafeController.class.getResource("John Internet Cafe.fxml"));
        Scene scene = new Scene(homeLoader.load(), 579, 400);
        openStage.setTitle("John’s Internet Cafe");
        openStage.setScene(scene);
        openStage.show();

        Stage previousStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        previousStage.close();
    }

    public void backButtonClick(ActionEvent e) throws IOException {
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
}

