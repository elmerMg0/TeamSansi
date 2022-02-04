package org.bo.app.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.bo.app.MenuView;
import org.bo.list.Item.ItemDTO;
import org.bo.list.menu.Menu;

import java.io.File;

public class ReadView extends View {

    public ReadView(Stage stage, ItemDTO dish) {
        super(stage, dish);

        btnCancel.setText("EDITAR");
        btnAccept.setOnMouseClicked(event -> stage.close());
        btnCancel.setOnMouseClicked(event -> createEditingWindow());

        boolean isVisible = !dish.getPathImage().equals("");
        imageView.setVisible(isVisible);

        textName.setEditable(false);
        textPrice.setEditable(false);
        textName.setText(dish.getName());
        textPrice.setText(String.valueOf(dish.getPrice()));

        cbx.setEditable(false);
        cbx.setValue(dish.isDish() ? "Comida" : "Bebida");

        textDescription.setEditable(false);
        textDescription.setWrapText(true);
        textDescription.setText(dish.getDescription());

    }

    private void createEditingWindow() {
        super.stage.close();
        Stage stage = new Stage();
        View viewAdd = new EditView(stage, super.item);
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

}
