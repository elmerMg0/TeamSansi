package org.bo.app;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.InputStream;

public class DishImage extends VBox {

    public DishImage() {
        ImageView imageView;
        String path = new File("src/main/java/org/bo/app/img/pique.jpeg").toURI().toString();
        Image image = new Image(path);
        imageView = new ImageView(image);

        this.getChildren().addAll(imageView);
    }

}
