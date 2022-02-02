package org.bo.app;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class TableImage extends VBox {
    private ImageView imageView;
    private String path;
    public TableImage(int number,ViewTables viewTables) throws SQLException, IOException {


        path = new File("src/main/java/org/bo/app/img/table/Table"+ number+ ".png").toURI().toString();
        Image image = new Image(path);
        imageView = new ImageView(image);

        imageView.setFitWidth(150);
        imageView.setFitHeight(150);

        this.getChildren().addAll(imageView);
        this.setPadding(new Insets(4,1,4,1));
      
    }

}
