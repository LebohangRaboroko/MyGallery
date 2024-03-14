module com.example.photogallery {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.photogallery to javafx.fxml;
    exports com.example.photogallery;
}