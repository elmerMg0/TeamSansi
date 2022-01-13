package org.bo.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
        TableColumn<ItemDTO, Integer> colQuantity = new TableColumn<>("Cantidad");

        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        tableOrders.getColumns().addAll(colName, colPrice, colQuantity);

        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
        this.setMinWidth(250);
        this.setPadding(new Insets(10, 0, 10, 10));
        VBox.setMargin(title, new Insets(10, 10, 10, 10));
        this.getChildren().addAll(title, tableOrders);
    }

    public TableView<ItemDTO> getTableOrders() {
        return tableOrders;
    }
}
