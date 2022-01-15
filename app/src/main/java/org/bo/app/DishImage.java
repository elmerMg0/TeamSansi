package org.bo.app;

import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.bo.list.Item.ItemDTO;

import java.io.File;
import java.io.InputStream;

public class DishImage extends VBox {

    private boolean isInOrdenDetail;
    private VBox orderDetail;
    private ItemDTO dish;
    public DishImage(ItemDTO dish,VBox orderDetail) {
        this.dish = dish;
        this.orderDetail = orderDetail;

        Label name = new Label(dish.getName());
        String path = new File(dish.getPathImage()).toURI().toString();
        Image image = new Image(path);
        ImageView imageView = new ImageView(image);

        imageView.setFitHeight(280);
        imageView.setFitWidth(280);
        imageView.setCursor(Cursor.HAND);
        this.getChildren().addAll(name, imageView);
        this.setOnMouseClicked(event -> clickWindow());
    }

    private void clickWindow() {
        System.out.println("Click");
        isInOrdenDetail = !isInOrdenDetail;
        if(isInOrdenDetail){
            ((OrderDetail)orderDetail).getTableOrders().getItems().add(dish);
        }else{
            ((OrderDetail)orderDetail).getTableOrders().getItems().remove(dish);
        }
    }
}
