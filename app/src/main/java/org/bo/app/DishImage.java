package org.bo.app;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.bo.app.view.ReadView;
import org.bo.app.view.View;
import org.bo.list.Item.ItemDTO;
import java.io.File;
import javafx.geometry.Pos;

public class DishImage extends VBox {

    private boolean isInOrdenDetail;
    private VBox orderDetail;
    private ItemDTO dish;

    private GridPane menuView;

    public DishImage(ItemDTO dish, VBox orderDetail) {
        this.dish = dish;
        this.orderDetail = orderDetail;
        Label name = new Label(dish.getName());
        name.setFont(new Font("Arial",20));
        name.setStyle("-fx-font-weight: bold");
        String path = new File(dish.getPathImage()).toURI().toString();
        Image image = new Image(path);
        ImageView imageView = new ImageView(image);

        ContextMenu contextMenu = new ContextMenu();

        MenuItem view = new MenuItem("Ver");
//        MenuItem edit = new MenuItem("Editar");
//        MenuItem delete = new MenuItem("Eliminar");

        view.setOnAction(event -> createReadingWindow());
        /*edit.setOnAction(event -> createEditingWindow());
        delete.setOnAction(event -> deleteItem());*/

//        contextMenu.getItems().addAll(view, edit, delete);
        contextMenu.getItems().addAll(view);
        this.setOnContextMenuRequested(event -> contextMenu.show(this, event.getScreenX(), event.getScreenY()));

        imageView.setFitHeight(280);
        imageView.setFitWidth(280);
        imageView.setCursor(Cursor.HAND);
        this.getChildren().addAll(name, imageView);
        this.setAlignment(Pos.CENTER);
        this.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                contextMenu.hide();
                clickWindow();
            }
        });
    }

    private void clickWindow() {
        System.out.println("Click");
        isInOrdenDetail = !isInOrdenDetail;
        if (isInOrdenDetail && !((OrderDetail) orderDetail).getTableOrders().getItems().contains(dish)) {
            ((OrderDetail) orderDetail).addDish(dish);
        } else {
            ((OrderDetail) orderDetail).removeDish(dish);
        }
    }

    private void createReadingWindow() {
        Stage stage = new Stage();
        View viewAdd = new ReadView(stage, this.dish);
        createWindow(stage, viewAdd);
    }

    /*private void createEditingWindow() {
        Stage stage = new Stage();
        View viewAdd = new EditView(stage, this.dish, menu, menuView);
        createWindow(stage, viewAdd);
    }*/

    private void createWindow(Stage stage, View viewAdd) {
        Scene scene = new Scene(viewAdd, 400, 400);
        stage.setX(800);
        stage.setY(220);

        stage.setScene(scene);
        stage.setTitle("A??adir nuevo item");
        stage.show();
    }

    /*private void deleteItem() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar");
        alert.setContentText("??Desea eliminar " + (dish.isDish() ? "este plato de Comida" : "esta bebida"));
        Optional<ButtonType> option = alert.showAndWait();
        option.ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    menu.delete(dish);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                ((MenuView) menuView).refresh();
                TableView<ItemDTO> tableOrders = ((OrderDetail) orderDetail).getTableOrders();
                tableOrders.getItems().remove(dish);
            }
        });
    }*/
}
