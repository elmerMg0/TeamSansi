package org.bo.app;

import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.bo.list.Item.ItemDTO;

import java.util.HashMap;
import java.util.Map;

public class OrderDetail extends VBox {

    private Label title;
    private TableView<ItemDTO> tableOrders;
    private Map<ItemDTO, Integer> order;

    public OrderDetail() {
        this.title = new Label("Detalle del pedido");
        this.title.setStyle("-fx-font-weight: bold;");
        this.order = new HashMap<>();

        this.tableOrders = new TableView<>();
        tableOrders.setEditable(true);

        TableColumn<ItemDTO, String> colName = new TableColumn<>("Nombre");
        TableColumn<ItemDTO, Double> colPrice = new TableColumn<>("Precio");
        TableColumn<ItemDTO, Spinner<Integer>> colQuantity = new TableColumn<>("Cantidad");
        colName.setSortable(false);
        colPrice.setSortable(false);
        colQuantity.setSortable(false);


        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colQuantity.setCellValueFactory(param -> {
            ItemDTO itemDTO = param.getValue();
            int value = order.get(itemDTO);
            Spinner<Integer> spinner = new Spinner<Integer>(1, 10, value);
            spinner.valueProperty().addListener((obs, oldValue, newValue) -> {
                        order.put(itemDTO, newValue);
                    }
            );

            return new SimpleObjectProperty<>(spinner);
        });

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

    public void addDish(ItemDTO dish) {
        order.put(dish, 1);
        tableOrders.getItems().add(dish);
    }

    public void removeDish(ItemDTO dish) {
        order.remove(dish);
        tableOrders.getItems().remove(dish);
    }

    public Map<ItemDTO, Integer> getOrder() {
        return order;
    }

}
