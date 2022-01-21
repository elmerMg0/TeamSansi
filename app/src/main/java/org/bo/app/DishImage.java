package org.bo.app;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.bo.app.view.EditView;
import org.bo.app.view.ReadView;
import org.bo.app.view.View;
import org.bo.list.Item.ItemDTO;
import org.bo.list.menu.Menu;

import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Optional;

public class DishImage extends VBox {

    private boolean isInOrdenDetail;
    private VBox orderDetail;
    private ItemDTO dish;

    private Menu menu;
    private GridPane menuView;

    public DishImage(ItemDTO dish, VBox orderDetail, Menu menu, GridPane menuView) {
        this.dish = dish;
        this.orderDetail = orderDetail;
        this.menu = menu;
        this.menuView = menuView;

        Label name = new Label(dish.getName());
        String path = new File(dish.getPathImage()).toURI().toString();
        Image image = new Image(path);
        ImageView imageView = new ImageView(image);

        ContextMenu contextMenu = new ContextMenu();

        MenuItem view = new MenuItem("Ver");
        MenuItem edit = new MenuItem("Editar");
        MenuItem delete = new MenuItem("Eliminar");

        view.setOnAction(event -> createReadingWindow());
        edit.setOnAction(event -> createEditingWindow());
        delete.setOnAction(event -> deleteItem());

        contextMenu.getItems().addAll(view, edit, delete);
        this.setOnContextMenuRequested(event -> contextMenu.show(this, event.getScreenX(), event.getScreenY()));

        imageView.setFitHeight(280);
        imageView.setFitWidth(280);
        imageView.setCursor(Cursor.HAND);
        this.getChildren().addAll(name, imageView);
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
            ((OrderDetail) orderDetail).getTableOrders().getItems().add(dish);
        } else {
            ((OrderDetail) orderDetail).getTableOrders().getItems().remove(dish);
        }
    }

    private void createReadingWindow() {
        Stage stage = new Stage();
        View viewAdd = new ReadView(stage, this.dish, menu, menuView);
        createWindow(stage, viewAdd);
    }

    private void createEditingWindow() {
        Stage stage = new Stage();
        View viewAdd = new EditView(stage, this.dish, menu, menuView);
        createWindow(stage, viewAdd);
    }

    private void createWindow(Stage stage, View viewAdd) {
        Scene scene = new Scene(viewAdd, 400, 400);
        stage.setX(800);
        stage.setY(220);

        stage.setScene(scene);
        stage.setTitle("Añadir nuevo item");
        stage.show();
    }

    private void deleteItem() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar");
        alert.setContentText("¿Desea eliminar " + (dish.isDish() ? "este plato de Comida" : "esta bebida"));
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
    }
}
