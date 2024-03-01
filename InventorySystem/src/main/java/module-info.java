module com.example.inventorysystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.kordamp.ikonli.javafx;


    opens com.example.inventorysystem to javafx.fxml;
    exports com.example.inventorysystem;
}