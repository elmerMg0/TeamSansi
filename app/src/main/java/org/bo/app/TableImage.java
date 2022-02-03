package org.bo.app;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.bo.app.view.DishesView;
import org.bo.app.view.InvoiceView;
import org.bo.list.Item.ItemDTO;
import org.bo.list.menu.Menu;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TableImage extends VBox {
    private ImageView imageView;
    private String path;
    private DishesView dishesView;
    private Menu menu;
    private Stage stage;
    private Scene scene;
    private int number;

    private List<ItemDTO> dishes;

    public TableImage(int number, List<ItemDTO> dishes) throws SQLException, IOException {
        this.dishes = dishes;
        this.number = number;

        this.dishesView = new DishesView(dishes);
        path = new File("src/main/java/org/bo/app/img/table/Table" + number + ".png").toURI().toString();
        Image image = new Image(path);
        imageView = new ImageView(image);

        imageView.setFitWidth(150);
        imageView.setFitHeight(150);

        this.getChildren().addAll(imageView);
        this.setPadding(new Insets(4, 1, 4, 1));

        this.stage = new Stage();
        scene = new Scene(dishesView, 1120, 600);

        this.setOnMouseClicked(event -> windowDishesView());

    }

    private void windowDishesView() {

        stage.setX(350);
        stage.setY(200);

        stage.setScene(scene);
        stage.setTitle("Mesa nro " + number);
        stage.show();
    }

}
