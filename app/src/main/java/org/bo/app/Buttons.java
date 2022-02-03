package org.bo.app;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.bo.app.view.InvoiceView;
import org.bo.list.Item.ItemDTO;
import org.bo.list.Order;
import org.bo.list.generator.PDFGenerator;

import java.awt.print.PrinterException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class Buttons extends HBox {
    private Button btnPrintInvoice, btnPrintForKitchen, btnClean;
    private Map<ItemDTO, Integer> order;
    private OrderDetail orderDetail;
    private PDFGenerator pdfGenerator;

    public Buttons(OrderDetail orderDetail) throws IOException {
        this.order = orderDetail.getOrder();
        this.orderDetail = orderDetail;
        this.pdfGenerator = new PDFGenerator(order);

        btnPrintInvoice = new Button("IMPRIMIR FACTURA");
        btnPrintForKitchen = new Button("IMPRIMIR PEDIDO");
        btnClean = new Button("LIMPIAR ORDENES");

        btnPrintInvoice.setFont(new Font("Arial", 20));
        btnPrintInvoice.setStyle("-fx-text-fill: White; -fx-background-color: Green");

        btnPrintForKitchen.setFont(new Font("Arial", 20));
        btnPrintForKitchen.setStyle("-fx-background-color: Red ; -fx-text-fill:White");

        btnClean.setFont(new Font("Arial", 20));
        btnClean.setStyle("-fx-background-color: Red ; -fx-text-fill:White");

        this.setSpacing(150);
        setMargin(btnPrintInvoice, new Insets(30));
        setMargin(btnPrintForKitchen, new Insets(30));
        setMargin(btnClean, new Insets(30));

        getChildren().addAll(btnPrintInvoice, btnPrintForKitchen, btnClean);

        btnPrintForKitchen.setOnAction(event -> {
            try {
                printTicket();
            } catch (IOException | PrinterException e) {
                e.printStackTrace();
            }
        });
        btnPrintInvoice.setOnAction(event -> {
            try {
                generateWindowInvoice();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        btnClean.setOnAction(event -> cleanOrderDetail());
    }

    private void cleanOrderDetail() {
        orderDetail.clearDishes();
    }

    private void generateWindowInvoice() throws SQLException {
        Stage stage = new Stage();
        VBox invoiceView = new InvoiceView(order, stage);
        Scene scene = new Scene(invoiceView, 400, 400);
        stage.setX(800);
        stage.setY(220);

        stage.setScene(scene);
        stage.setTitle("Factura");
        stage.show();
    }

    private void printTicket() throws IOException, PrinterException {
        pdfGenerator.createPDFKitchen();
    }

}

