package org.bo.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.bo.app.view.ReadView;
import org.bo.app.view.View;
import org.bo.app.view.waiter.MenuWaiterView;
import org.bo.list.Item.ItemDTO;
import org.bo.list.waiter.WaiterManagement;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

public class ViewTables extends VBox {
    private final int quantityTables = 21;
    private Button btnInitialSesion;
    private Button btnEditItems;
    private Button btnEditWaiters;
    private GridPane tables;
    private HBox buttons;
    private boolean isLogIn;

    private List<ItemDTO> dishes;
    private WaiterManagement waiterManagement;

    public ViewTables(List<ItemDTO> dishes) throws SQLException, IOException {
        this.dishes = dishes;
        this.waiterManagement = new WaiterManagement();

        tables = new GridPane();
        buttons = new HBox();
        putButtons();
        fillTables();
        this.getChildren().addAll(buttons, tables);
        this.setStyle("-fx-background-color:Gris ");
        btnInitialSesion.setOnMouseClicked(event -> {
            if (isLogIn) {
                btnEditItems.setVisible(false);
                btnEditWaiters.setVisible(false);
                btnInitialSesion.setText("Iniciar Sesión");
                isLogIn = false;
            } else {
                createPopup();
            }
        });

        btnEditWaiters.setOnAction(event -> {
            try {
                createWindowMenuWaiterView();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void createWindowMenuWaiterView() throws SQLException {
        Stage stage = new Stage();
        VBox menuWaiter = new MenuWaiterView(waiterManagement);
        Scene scene = new Scene(menuWaiter, 950, 500);
        stage.setX(500);
        stage.setY(220);

        stage.setScene(scene);
        stage.setTitle("Meseros");
        stage.show();
    }

    private void createPopup() {
        String user = "user";
        String passwordEncrypted = Base64.getEncoder().encodeToString("contrasenia".getBytes());

        Stage stage = new Stage();
        VBox pop_up = new VBox();
        Label userName = new Label("Inicar Sesion");
        Label password = new Label("Contraseña");
        TextField txtUserName = new TextField();
        PasswordField txtPassword = new PasswordField();

        Button accept = new Button("Aceptar");
        Button cancel = new Button("Cancelar");
        HBox hBox = new HBox();
        hBox.getChildren().addAll(accept, cancel);

        /*.add(userName,0,0);
        pop_up.add(password,1,0);
        pop_up.add(txtUserName,0,1);
        pop_up.add(txtPassword,1,1);*/
        pop_up.getChildren().addAll(userName, txtUserName, password, txtPassword, hBox);
        pop_up.setAlignment(Pos.CENTER);
        pop_up.setPadding(new Insets(10, 10, 10, 10));
        Scene scene = new Scene(pop_up, 270, 150);

        cancel.setOnAction(event -> stage.close());
        accept.setOnAction(event -> {
            if (txtUserName.getText().equals(user) && txtPassword.getText().equals(decrypted(passwordEncrypted))) {
                btnEditItems.setVisible(true);
                btnEditWaiters.setVisible(true);
                btnInitialSesion.setText("Salir");
                isLogIn = true;
                stage.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Contraseña no valida");
                alert.showAndWait();
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    public void fillTables() throws SQLException, IOException {
        tables.setAlignment(Pos.CENTER);
        int column = 0;
        int row = 0;
        int i = 1;
        while (i <= quantityTables) {
            tables.add(new TableImage(i, dishes), column, row);
            i++;
            column++;
            if (column == 7) {
                row++;
                column = 0;
            }
        }
    }

    public void putButtons() {
        btnInitialSesion = new Button("Iniciar Sesion");
        btnInitialSesion.setFont(new Font("Arial", 20));

        btnEditItems = new Button("Editar Items");
        btnEditItems.setFont(new Font("Arial", 20));
        btnEditItems.setVisible(false);

        btnEditWaiters = new Button("Editar meseros");
        btnEditWaiters.setFont(new Font("Arial", 20));
        btnEditWaiters.setVisible(false);

        buttons.setPadding(new Insets(25, 10, 30, 50));
        buttons.getChildren().addAll(btnEditItems, btnInitialSesion, btnEditWaiters);
        buttons.setSpacing(290);
    }

    private String decrypted(String encrypted) {
        byte[] bytesDecoded = Base64.getDecoder().decode(encrypted);
        return new String(bytesDecoded);
    }
}
