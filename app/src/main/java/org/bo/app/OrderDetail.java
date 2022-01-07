package org.bo.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import org.bo.list.Item.ItemDTO;

public class OrderDetail extends VBox {

    private Label title;
    private TableView<ItemDTO> tableOrders;

    public OrderDetail() {
        this.title = new Label("Detalle del pedido");
        this.title.setStyle("-fx-font-weight: bold;");

        this.tableOrders = new TableView();
        TableColumn<ItemDTO, String> colName = new TableColumn<>("Nombre");
        TableColumn<ItemDTO, Double> colPrice = new TableColumn<>("Precio");
        tableOrders.getColumns().addAll(colName, colPrice);

        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
        VBox.setMargin(title, new Insets(10, 10, 10, 10));
        this.getChildren().addAll(title, tableOrders);
    }

}
