package org.bo.app.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.bo.list.Item.ItemDTO;
import org.bo.list.Order;
import org.bo.list.generator.PDFGeneratorInvoice;
import org.bo.list.menu.Menu;
import org.bo.list.waiter.WaiterDTO;
import org.bo.list.waiter.WaiterManagement;

import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class InvoiceView extends VBox {

    private final static String DEFAULT_IMAGE = "src/main/java/org/bo/app/img/waiter/default.png";

    private Label title, placeAndDate, tableAndOrderNumber, total;
    private Label labelCash, labelChange, textFieldWaiter;
    private TableView<Order> tableOrders;
    private TextField textFieldCash, textFieldChange;
    private Button accept, cancel;
    private ComboBox<String> waiters;
    private ImageView waiterPhoto;

    private Map<ItemDTO, Integer> order;
    private Stage stage;
    private WaiterManagement waiterManagement;
    private List<WaiterDTO> listOfWaiters;
    private double totalPrice;

    public InvoiceView(Map<ItemDTO, Integer> order, Stage stage) throws SQLException {
        this.order = order;
        this.stage = stage;
        this.waiterManagement = new WaiterManagement();
        this.listOfWaiters = waiterManagement.selectWaiters();
        this.setStyle("-fx-background-color:Gris ");

        this.title = new Label("Dima's Restaurant");
        this.placeAndDate = new Label("Cochabamba - Bolivia\t\t" + "Fecha: " + LocalDate.now());
        this.total = new Label("Total: ");
        this.tableAndOrderNumber = new Label("Mesa: 12\t\t" + "Nro: 25");

        this.tableOrders = generateTable();
        totalPrice = addDishes();

        this.labelCash = new Label("Cantidad recibida: ");
        this.labelChange = new Label("Cambio: ");
        this.textFieldCash = new TextField();
        this.textFieldChange = new TextField();
        this.textFieldChange.setEditable(false);

        this.textFieldCash.textProperty().addListener(event -> {
            double cash = (!textFieldCash.getText().isEmpty()) ? Double.parseDouble(textFieldCash.getText()) : 0;
            if (cash < totalPrice) {
                textFieldChange.setText("");
            } else {
                textFieldChange.setText((cash - totalPrice) + "");
            }
        });

        this.accept = new Button("Aceptar");
        this.cancel = new Button("Cancelar");

        this.placeAndDate.setPadding(new Insets(2, 2, 2, 2));
        this.tableAndOrderNumber.setPadding(new Insets(2, 2, 2, 2));

        HBox hCash = generateHBox(new Insets(5, 0, 5, 0), labelCash, textFieldCash);
        hCash.setAlignment(Pos.TOP_RIGHT);
        HBox hChange = generateHBox(new Insets(5, 0, 5, 0), labelChange, textFieldChange);
        hChange.setAlignment(Pos.TOP_RIGHT);
        HBox buttons = generateHBox(new Insets(5, 5, 5, 5), accept, cancel);

        String path = new File(DEFAULT_IMAGE).toURI().toString();

        Image image = new Image(path);
        this.waiterPhoto = new ImageView(image);
        this.waiterPhoto.setFitWidth(40);
        this.waiterPhoto.setFitHeight(40);

        this.textFieldWaiter = new Label("Mesero: ");
        ObservableList<String> waitersName = FXCollections.observableArrayList();
        List<String> names = listOfWaiters.stream().map(WaiterDTO::getName).toList();
        waitersName.addAll(names);
        this.waiters = new ComboBox<>(waitersName);
        this.waiters.setOnAction(event -> {
            changeImage(waiters);
        });

        HBox hWaiter = generateHBox(new Insets(5, 5, 5, 5), textFieldWaiter, waiters, waiterPhoto);

        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(5, 5, 5, 5));

        this.total = new Label("Total: " + totalPrice);
        HBox hTotalPrice = generateHBox(new Insets(5, 25, 5, 5), total);
        hTotalPrice.setAlignment(Pos.BASELINE_RIGHT);
        HBox.setMargin(hTotalPrice, new Insets(0, 15, 0, 0));
        HBox.setMargin(accept, new Insets(0, 15, 0, 0));
        HBox.setMargin(cancel, new Insets(0, 0, 0, 15));
        HBox.setMargin(waiters, new Insets(0, 10, 0, 0));
        this.getChildren().addAll(title, placeAndDate, tableAndOrderNumber, tableOrders
                , hTotalPrice, hCash, hChange, hWaiter, buttons);

        this.cancel.setOnAction(event -> stage.close());
        this.accept.setOnAction(event -> {
            try {
                generateInvoice();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (PrinterException e) {
                e.printStackTrace();
            }
        });
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
        order.forEach((itemDTO, quantity) -> {
            double total = itemDTO.getPrice() * quantity;
            Order order = new Order(itemDTO.getName(), quantity, itemDTO.getPrice(), total);
            orders.add(order);
        });
        double totalPrice = orders.stream().map(Order::getTotal)
                .reduce(0.0, Double::sum);

        this.tableOrders.getItems().addAll(orders);
        return totalPrice;
    }

    private void changeImage(ComboBox<String> waiters) {
        String waiterName = waiters.getValue();
        Optional<WaiterDTO> waiter = listOfWaiters.stream()
                .filter(waiterDTO -> waiterDTO.getName().equals(waiterName)).findFirst();

        waiter.ifPresent(waiterDTO -> {
            if (waiterDTO.getPath().contains(".jpg") || waiterDTO.getPath().contains(".png")) {
                String path = new File(waiterDTO.getPath()).toURI().toString();
                Image image = new Image(path);
                this.waiterPhoto.setImage(image);
            }
        });
    }

    private void generateInvoice() throws IOException, PrinterException {
        Map<String, String> requirements = new HashMap<>();
        requirements.put("waiter", waiters.getValue());
        requirements.put("cash", textFieldCash.getText());
        requirements.put("change", textFieldChange.getText());
        requirements.put("totalPrice", totalPrice + "");

        PDFGeneratorInvoice pdfGeneratorInvoice = new PDFGeneratorInvoice(order, requirements);
        pdfGeneratorInvoice.generateInvoice();
        stage.close();
    }

}