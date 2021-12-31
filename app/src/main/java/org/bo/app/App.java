/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.bo.app;

import javafx.application.Application;
import javafx.scene.Scene;
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
        Scene scene = new Scene(root, 400, 400);
        stage.setScene(scene);
        stage.show();
    }
}