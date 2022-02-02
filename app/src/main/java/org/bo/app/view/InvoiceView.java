package org.bo.app.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.bo.list.Order;
import org.bo.list.menu.Menu;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InvoiceView extends VBox {

    private Label title, placeAndDate, tableAndOrderNumber, total;
    private Label labelCash, labelChange;
    private TableView<Order> tableOrders;
    private TextField textFieldCash, textFieldChange;
    private Button accept, cancel;

    private Menu menu;
    private Stage stage;

    public InvoiceView(Menu menu, Stage stage) {
        this.menu = menu;
        this.stage = stage;

        this.title = new Label("Dima's Restaurant");
        this.placeAndDate = new Label("Cochabamba - Bolivia\t\t" + "Fecha: " + LocalDate.now());
        this.total = new Label("Total: ");
        this.tableAndOrderNumber = new Label("Mesa: 12\t\t" + "Nro: 25");

        this.tableOrders = generateTable();

        this.labelCash = new Label("Cantidad recibida: ");
        this.labelChange = new Label("Cambio: ");
        this.textFieldCash = new TextField();
        this.textFieldChange = new TextField();

        this.accept = new Button("Aceptar");
        this.cancel = new Button("Cancelar");

        this.placeAndDate.setPadding(new Insets(2, 2, 2, 2));
        this.tableAndOrderNumber.setPadding(new Insets(2, 2, 2, 2));

        HBox hCash = generateHBox(new Insets(5, 0, 5, 0), labelCash, textFieldCash);
        HBox hChange = generateHBox(new Insets(5, 0, 5, 0), labelChange, textFieldChange);
        HBox buttons = generateHBox(new Insets(5, 5, 5, 5), accept, cancel);

        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(5, 5, 5, 5));

        double totalPrice = addDishes();
        this.total = new Label("Total: " + totalPrice);
        HBox hTotalPrice = generateHBox(new Insets(5, 25, 5, 5), total);
        hTotalPrice.setAlignment(Pos.BASELINE_RIGHT);
        HBox.setMargin(hTotalPrice, new Insets(0, 15, 0, 0));
        HBox.setMargin(accept, new Insets(0, 15, 0, 0));
        HBox.setMargin(cancel, new Insets(0, 0, 0, 15));
        this.getChildren().addAll(title, placeAndDate, tableAndOrderNumber, tableOrders
                , hTotalPrice, hCash, hChange, buttons);

        this.cancel.setOnAction(event -> stage.close());
    }

    private TableView<Order> generateTable() {
        TableView<Order> tableOrders = new TableView<>();
        TableColumn<Order, String> colName = new TableColumn<>("Nombre");
        TableColumn<Order, String> colQuantity = new TableColumn<>("Cantidad");
        TableColumn<Order, Double> colPrice = new TableColumn<>("Precio");
        TableColumn<Order, Double> colTotal = new TableColumn<>("Total");
        colName.setPrefWidth(190);
        colName.setSortable(false);
        colPrice.setSortable(false);
        colQuantity.setSortable(false);
        colTotal.setSortable(false);

        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        tableOrders.getColumns().addAll(colName, colQuantity, colPrice, colTotal);
        return tableOrders;
    }

    private HBox generateHBox(Insets insets, Node... elements) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(insets);
        hBox.getChildren().addAll(elements);
        return hBox;
    }

    private double addDishes() {
        List<Order> orders = new ArrayList<>();
        this.menu.getOrderDishes().forEach((itemDTO, quantity) -> {
            double total = itemDTO.getPrice() * quantity;
            Order order = new Order(itemDTO.getName(), quantity, itemDTO.getPrice(), total);
            orders.add(order);
        });
        double totalPrice = orders.stream().map(Order::getTotal)
                .reduce(0.0, Double::sum);

        this.tableOrders.getItems().addAll(orders);
        return totalPrice;
    }

}
