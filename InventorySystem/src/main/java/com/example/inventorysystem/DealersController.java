package com.example.inventorysystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class DealersController implements Initializable {

    @FXML
    public TableView<Dealer> dealerTableView;
    @FXML
    public TableColumn<Dealer, String> name;
    @FXML
    public TableColumn<Dealer, String> location;
    @FXML
    public TableColumn<Dealer, String> contact;
    @FXML
    public Button HomeButton;
    @FXML
    public Button viewDetailsButton;

    private final ObservableList<Dealer> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        location.setCellValueFactory(new PropertyValueFactory<>("Location"));
        contact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        loadDealerData();

        dealerTableView.setItems(observableList);
    }

    private void loadDealerData() {
        String filePath = "src/main/resources/dealers.txt";
        List<Dealer> allDealers = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 15) {
                    // Extract name, location, and contact details from the file
                    String name = data[0].trim();
                    String location = data[1].trim();
                    String contact = data[2].trim();
                    // Create a new Dealer object and add it to the list of allDealers
                    allDealers.add(new Dealer(name, location, contact));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Randomly select 4 dealers
        List<Dealer> selectedDealers = new ArrayList<>();
        Random random = new Random();
        while (selectedDealers.size() < 4 && !allDealers.isEmpty()) {
            int randomIndex = random.nextInt(allDealers.size());
            selectedDealers.add(allDealers.get(randomIndex));
            allDealers.remove(randomIndex);
        }

        // Sort the selected dealers' locations in A to Z order using Bubble Sort
        int n = selectedDealers.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Dealer dealer1 = selectedDealers.get(j);
                Dealer dealer2 = selectedDealers.get(j + 1);

                if (dealer1.getLocation().compareTo(dealer2.getLocation()) > 0) {
                    // Swap dealers if the location of the current dealer is greater than the next dealer's location
                    selectedDealers.set(j, dealer2);
                    selectedDealers.set(j + 1, dealer1);
                }
            }
        }

        // Add the sorted selected dealers to the observable list for display in the table
        observableList.addAll(selectedDealers);
    }

    @FXML
    public void viewDetailsButtonClick(ActionEvent event) {
        // Get the selected dealer from the table
        Dealer selectedDealer = dealerTableView.getSelectionModel().getSelectedItem();
        if (selectedDealer == null) {
            // If no dealer is selected, show an error message to the user
            showAlert();
            return;
        }
            try {
                FXMLLoader itemLoader = new FXMLLoader(getClass().getResource("item_details.fxml"));
                Parent itemRoot = itemLoader.load();
                Scene itemScene = new Scene(itemRoot);

                // Get the controller of the new scene
                DealerItemController selectedDealerController = itemLoader.getController();

                // Get the items associated with the selected dealer
                List<DealerItemDetail> dealerItemsList = ItemDetailData.getItemsByDealer(selectedDealer);

                // Populate the TableView in the new scene with the dealer items
                selectedDealerController.populateDealerItemsTable(dealerItemsList);

                Stage currentStage = (Stage) viewDetailsButton.getScene().getWindow();
                currentStage.setScene(itemScene);
                currentStage.setTitle("Selected Dealer Item Details"); // Set a title for the new scene
                currentStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please select a dealer to view details.");
        alert.showAndWait();
    }
}










