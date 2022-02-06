package org.bo.app.view.waiter;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.bo.app.DishImage;
import org.bo.list.Item.ItemDTO;
import org.bo.list.waiter.WaiterDTO;
import org.bo.list.waiter.WaiterManagement;

import java.sql.SQLException;
import java.util.List;

public class MenuWaiterView extends VBox {

    private WaiterManagement waiterManagement;
    private GridPane gridPane;
    private Button btnAdd;

    public MenuWaiterView(WaiterManagement waiterManagement) throws SQLException {
        this.waiterManagement = waiterManagement;

        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        gridPane.setHgap(5);
        gridPane.setVgap(5);

        fillDishImage();

        btnAdd = new Button("Añadir");
        btnAdd.setOnAction(event -> createAddViewWaiter());

        this.getChildren().addAll(gridPane, btnAdd);
    }

    private void createAddViewWaiter() {
        Stage stage = new Stage();
        ViewWaiter view = new AddViewWaiter(this, waiterManagement, stage);
        Scene scene = new Scene(view, 400, 400);
        stage.setX(800);
        stage.setY(220);

        stage.setScene(scene);
        stage.setTitle("Añadir nuevo mesero");
        stage.show();

    }

    private void fillDishImage() throws SQLException {
        List<WaiterDTO> waiters = waiterManagement.selectWaiters();
        int column = 0;
        int row = 0;
        for (WaiterDTO waiterDTO : waiters) {
            gridPane.add(new WaiterImage(waiterDTO, waiterManagement, this), column, row);
            column++;
            if (column % 4 == 0) {
                row++;
            }
            if (column == 4) {
                column = 0;
            }
        }
    }

    public void refresh() throws SQLException {
        gridPane.getChildren().clear();
        fillDishImage();
    }

}
