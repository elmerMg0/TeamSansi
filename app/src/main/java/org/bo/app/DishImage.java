package org.bo.app;

import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.InputStream;

public class DishImage extends VBox {

    private String dishName;
    private String pathImage;
    private Boolean isInOrdenDetail;

    public DishImage(String dishName, String pathImage) {
        this.dishName = dishName;
        this.pathImage = pathImage;

        Label name = new Label(dishName);
        String path = new File(pathImage).toURI().toString();
        Image image = new Image(path);
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(160);
        imageView.setFitWidth(280);
        imageView.setCursor(Cursor.HAND);
        this.getChildren().addAll(name, imageView);
        this.setOnMouseClicked(event -> clickWindow());
    }

    private void clickWindow() {
        System.out.println("Click");
        isInOrdenDetail = !isInOrdenDetail;
    }

}
