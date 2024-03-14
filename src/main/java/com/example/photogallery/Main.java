package com.example.photogallery;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.InputStream;
import java.util.Objects;

public class Main extends Application {

    Stage primaryStage;
    int currentImageIndex;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;

        VBox root = new VBox();
        root.setSpacing(10);

        Label label = new Label("Nature Addicts Gallery");
        label.getStyleClass().add("label");

        TilePane tiles = new TilePane();
        tiles.setPrefColumns(3);
        tiles.setPrefRows(3);
        tiles.setPadding(new Insets(10));
        tiles.setHgap(10);
        tiles.setVgap(10);
        tiles.getStyleClass().add("tile-pane");

        for (int i = 1; i <= 9; i++) {
            String source = "/com/example/photogallery/" + i + ".jpg";
            Image image = loadImage(source);
            if (image != null) {
                ImageView imageView = new ImageView();
                imageView.getStyleClass().add("image-view");
                imageView.setImage(image);
                imageView.setFitHeight(250);
                imageView.setFitWidth(250);
                imageView.setPreserveRatio(true);

                int display = i;
                imageView.setOnMouseClicked(event -> displayFullImage(display));

                tiles.getChildren().addAll(imageView);
            }
        }

        root.getChildren().addAll(label, tiles);

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/photogallery/style.css")).toExternalForm());

        stage.setScene(scene);
        stage.setTitle("My Photo Gallery");
        stage.show();
    }

    private Image loadImage(String source) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(source);
            if (inputStream != null) {
                return new Image(inputStream);
            } else {
                System.err.println("Error loading image: InputStream is null for source: " + source);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void displayFullImage(int imageIndex) {
        currentImageIndex = imageIndex;

        String source = "/com/example/photogallery/" + imageIndex + ".jpg";
        Image image = loadImage(source);

        if (image != null) {
            ImageView fullImageView = new ImageView(image);
            fullImageView.setFitHeight(500);
            fullImageView.setFitWidth(500);
            fullImageView.setPreserveRatio(true);

            Button MainMenu = new Button("Main Menu");
            MainMenu.setOnAction(event -> returnToMainMenu());
            MainMenu.getStyleClass().add("button");

            HBox arrowsBox = new HBox();
            arrowsBox.setAlignment(Pos.CENTER);
            arrowsBox.setSpacing(20);

            Button leftArrowButton = new Button("\u2190");
            leftArrowButton.setOnAction(event -> displayPreviousImage());
            leftArrowButton.getStyleClass().add("button");

            Button rightArrowButton = new Button("\u2192");
            rightArrowButton.setOnAction(event -> displayNextImage());
            rightArrowButton.getStyleClass().add("button");

            arrowsBox.getChildren().addAll(leftArrowButton, fullImageView, rightArrowButton);

            VBox root = new VBox(arrowsBox, MainMenu);
            root.setAlignment(Pos.CENTER);
            root.setSpacing(20);

            Scene scene = new Scene(root, 700, 600);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/photogallery/style.css")).toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Full Size Image");
        }
    }

    public void displayPreviousImage() {
        if (currentImageIndex > 1) {
            currentImageIndex--;
            displayFullImage(currentImageIndex);
        }
    }

    public void displayNextImage() {
        if (currentImageIndex < 9) {
            currentImageIndex++;
            displayFullImage(currentImageIndex);
        }
    }

    public void returnToMainMenu() {

        start(primaryStage);
    }

    public static void main(String[] args) {

        launch(args);
    }
}
