package com.example.inventorysystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JohnInternetCafe extends Application {

    @Override
    public void start(Stage openStage) throws Exception {
        FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("John Internet Cafe.fxml"));
        Parent root = homeLoader.load();
        Scene scene = new Scene(root, 579, 400);
        openStage.setTitle("John’s Internet Cafe");
        openStage.setScene(scene);
        openStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
