package app.test.app;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageClusteringApp extends Application {

    private File selectedFile;
    private BufferedImage originalImage;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Image Clustering App");

        Button openButton = new Button("Ouvrir Image");
        Button processButton = new Button("Process");

        openButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
            );
            selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                try {
                    originalImage = ImageIO.read(selectedFile);
                    displayImage(primaryStage, originalImage);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        processButton.setOnAction(e -> {
            if (originalImage != null) {
                Flou flouGaussien7_3 = new FlouGaussien(7, 3.0);
                BufferedImage flou = flouGaussien7_3.appliquer(originalImage);

                double[][] featureArray = ImageClustering.imageToFeatureArray(flou);

                int nClusters = 10;
                AlgoClustering algorithm = new KMeansClustering();
                int[] labels = algorithm.clustering(featureArray, nClusters);

                NormeCouleurs normeCouleurs = new NormeCielab();
                BufferedImage brightenedImage = ImageClustering.createBrightenedImage(flou, 0.75);

                BufferedImage[] biomeImages = ImageClustering.createBiomeImages(flou, brightenedImage, labels, nClusters, normeCouleurs);

                displayBiomeImages(primaryStage, biomeImages);

                // Show message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Process Completed");
                alert.setHeaderText(null);
                alert.setContentText("Image clustering process completed successfully!");
                alert.showAndWait();

            }
        });

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));
        borderPane.setTop(openButton);
        BorderPane.setAlignment(openButton, javafx.geometry.Pos.CENTER);

        borderPane.setCenter(new ImageView());

        borderPane.setBottom(processButton);
        BorderPane.setAlignment(processButton, javafx.geometry.Pos.CENTER);

        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void displayImage(Stage primaryStage, BufferedImage image) {
        Image fxImage = SwingFXUtils.toFXImage(image, null);
        ImageView imageView = new ImageView(fxImage);
        imageView.setFitWidth(600);
        imageView.setFitHeight(400);

        BorderPane borderPane = (BorderPane) primaryStage.getScene().getRoot();
        borderPane.setCenter(imageView);
    }

    public static void displayBiomeImages(Stage primaryStage, BufferedImage[] biomeImages) {
        Stage stage = new Stage();
        stage.setTitle("Biome Images");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        int numCols = 5;
        int numRows = 2;
        int index = 0;

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (index < biomeImages.length) {
                    Image image = SwingFXUtils.toFXImage(biomeImages[index], null);
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(150);
                    imageView.setFitHeight(150);

                    String biomeName = ImageClustering.BIOME_NAMES[index];
                    javafx.scene.control.Label label = new javafx.scene.control.Label(biomeName);
                    label.setWrapText(true);
                    label.setMaxWidth(150);

                    VBox vBox = new VBox();
                    vBox.getChildren().addAll(imageView, label);
                    vBox.setSpacing(10);
                    vBox.setPadding(new Insets(10));

                    imageView.setOnMouseClicked(event -> {
                        displayLargeImage(image, biomeName);
                    });

                    gridPane.add(vBox, col, row);
                    index++;
                }
            }
        }

        Scene scene = new Scene(gridPane, 900, 450);
        stage.setScene(scene);
        stage.show();
    }

    private static void displayLargeImage(Image image, String title) {
        Stage stage = new Stage();
        stage.setTitle(title);

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(800);
        imageView.setFitHeight(600);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(imageView);

        Scene scene = new Scene(borderPane, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}