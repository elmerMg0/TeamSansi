package org.bo.app;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.bo.app.view.AddView;
import org.bo.list.menu.Menu;

public class Buttons extends HBox {
    private Button btnPrint, btnAdd, btnEdit;
    private Menu menu;
    private GridPane menuView;

    public Buttons(Menu menu, GridPane menuView) {
        this.menu = menu;
        this.menuView = menuView;

        btnPrint = new Button("IMPRIMIR FACTURA");
        btnAdd = new Button("AÑADIR");
        btnEdit = new Button("IMPRIMIR PEDIDO");

        btnPrint.setFont(new Font("Arial", 20));
        btnPrint.setStyle("-fx-text-fill: White; -fx-background-color: Green");

        btnEdit.setFont(new Font("Arial", 20));
        btnEdit.setStyle("-fx-background-color: Red ; -fx-text-fill:White");
        btnAdd.setFont(new Font("Arial", 20));

        btnAdd.setOnMouseClicked(event -> createWindowAdded());

        this.setSpacing(200);
        setMargin(btnPrint, new Insets(30));
        setMargin(btnAdd, new Insets(30));
        setMargin(btnEdit, new Insets(30));

        getChildren().addAll(btnPrint, btnEdit, btnAdd);

    }

    private void createWindowAdded() {
        Stage stage = new Stage();
        VBox viewAdd = new AddView(stage, menu, menuView);
        Scene scene = new Scene(viewAdd, 400, 400);
        stage.setX(800);
        stage.setY(220);

        stage.setScene(scene);
        stage.setTitle("Añadir nuevo item");
        stage.show();
    }
}
