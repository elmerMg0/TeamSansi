package org.bo.app;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;

public class Buttons extends HBox {
    private Button btnPrint;
    private Button btnAdd;
    private Button btnEdit;

    public Buttons() {
        btnPrint = new Button("IMPRIMIR");
        btnAdd = new Button("AÑADIR");
        btnEdit = new Button("EDITAR");

        btnPrint.setFont(new Font("Arial", 20));
        btnPrint.setStyle("-fx-text-fill: White; -fx-background-color: Green");

        btnEdit.setFont(new Font("Arial", 20));
        btnEdit.setStyle("-fx-background-color: Red ; -fx-text-fill:White");
        btnAdd.setFont(new Font("Arial", 20));

        btnAdd.setOnMouseClicked(event -> createWindowAdded());

        this.setSpacing(290);
        setMargin(btnPrint, new Insets(30));
        setMargin(btnAdd, new Insets(30));
        setMargin(btnEdit, new Insets(30));

        getChildren().addAll(btnPrint, btnEdit, btnAdd);

    }

    private void createWindowAdded() {
        Stage stage = new Stage();
        VBox viewAdd = new ViewAdd();
        Scene scene = new Scene(viewAdd, 400, 300);
        stage.setX(600);
        stage.setY(250);

        stage.setScene(scene);
        stage.setTitle("Añadir nuevo item");
        stage.show();
    }
}