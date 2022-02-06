package org.bo.app.view.waiter;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.bo.app.view.ReadView;
import org.bo.app.view.View;
import org.bo.list.waiter.WaiterDTO;
import org.bo.list.waiter.WaiterManagement;

import java.io.File;
import java.sql.SQLException;
import java.util.Optional;

public class WaiterImage extends VBox {
    private final static String DEFAULT_IMAGE = "src/main/java/org/bo/app/img/waiter/default.png";

    private WaiterDTO waiterDTO;
    private WaiterManagement waiterManagement;
    private MenuWaiterView menuWaiterView;

    public WaiterImage(WaiterDTO waiterDTO, WaiterManagement waiterManagement, MenuWaiterView menuWaiterView) {
        this.waiterDTO = waiterDTO;
        this.waiterManagement = waiterManagement;
        this.menuWaiterView = menuWaiterView;

        Label name = new Label(waiterDTO.getName());

        String pathImage = waiterDTO.getPath().contains(".jpg") ? waiterDTO.getPath() : DEFAULT_IMAGE;
        String path = new File(pathImage).toURI().toString();
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

        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        imageView.setCursor(Cursor.HAND);

        this.getChildren().addAll(name, imageView);
    }

    private void deleteItem() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Eliminar");
        alert.setContentText("¿Desea eliminar este mesero?");
        Optional<ButtonType> option = alert.showAndWait();
        option.ifPresent(buttonType -> {
            if (buttonType == ButtonType.OK) {
                try {
                    waiterManagement.deleteWaiter(waiterDTO);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    menuWaiterView.refresh();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void createEditingWindow() {
        Stage stage = new Stage();
        ViewWaiter view = new EditViewWaiter(waiterDTO, waiterManagement, stage);
        createWindow(stage, view, waiterDTO.getName());
    }

    private void createReadingWindow() {
        Stage stage = new Stage();
        ViewWaiter view = new ReadViewWaiter(waiterDTO, waiterManagement, stage);
        createWindow(stage, view, waiterDTO.getName());
    }

    private void createWindow(Stage stage, ViewWaiter viewAdd, String title) {
        Scene scene = new Scene(viewAdd, 400, 400);
        stage.setX(800);
        stage.setY(220);

        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }

}
