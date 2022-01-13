/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.bo.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.bo.list.menu.Menu;

public class App extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Menu menu = new Menu();
        VBox root = new VBox();
        HBox frame = new HBox();

        VBox orderDetail = new OrderDetail();
        GridPane menuView = new MenuView(menu,orderDetail);
        ScrollPane scrollPane = new ScrollPane(menuView);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        frame.getChildren().addAll(orderDetail, scrollPane);

        HBox buttons = new Buttons(menu, menuView);

        root.getChildren().addAll(frame, buttons);
        Scene scene = new Scene(root, 1120, 500);
        stage.setTitle("Punto de Venta");
        stage.setScene(scene);
        stage.show();
    }
}