package org.bo.app;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.bo.app.view.AddView;
import org.bo.app.view.InvoiceView;
import org.bo.list.generator.PDFGenerator;
import org.bo.list.menu.Menu;

import java.awt.print.PrinterException;

import java.io.IOException;

public class Buttons extends HBox {
    private Button btnPrintInvoice, btnAdd, btnPrintForKitchen;
    private Menu menu;
    private GridPane menuView;
    private OrderDetail orderDetail;
    private PDFGenerator pdfGenerator;

    public Buttons(Menu menu, GridPane menuView, OrderDetail orderDetail) throws IOException {
        this.menu = menu;
        this.menuView = menuView;
        this.orderDetail = orderDetail;
        this.pdfGenerator = new PDFGenerator(menu);

        btnPrintInvoice = new Button("IMPRIMIR FACTURA");
        btnAdd = new Button("AÑADIR");
        btnPrintForKitchen = new Button("IMPRIMIR PEDIDO");

        btnPrintInvoice.setFont(new Font("Arial", 20));
        btnPrintInvoice.setStyle("-fx-text-fill: White; -fx-background-color: Green");

        btnPrintForKitchen.setFont(new Font("Arial", 20));
        btnPrintForKitchen.setStyle("-fx-background-color: Red ; -fx-text-fill:White");
        btnAdd.setFont(new Font("Arial", 20));

        btnAdd.setOnMouseClicked(event -> createWindowAdded());

        this.setSpacing(200);
        setMargin(btnPrintInvoice, new Insets(30));
        setMargin(btnAdd, new Insets(30));
        setMargin(btnPrintForKitchen, new Insets(30));

        getChildren().addAll(btnPrintInvoice, btnPrintForKitchen, btnAdd);

        menu.setOrderDishes(orderDetail.getOrder());

        btnPrintForKitchen.setOnAction(event -> {
            try {
                printTicket();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (PrinterException e) {
                e.printStackTrace();
            }
        });
        btnPrintInvoice.setOnAction(event -> generateWindowInvoice());
    }

    private void createWindowAdded() {
        Stage stage = new Stage();
        VBox viewAdd = new AddView(stage, menu, menuView);
        Scene scene = new Scene(viewAdd, 400, 400);
        stage.setX(800);
        stage.setY(220);

        stage.setScene(scene);
        stage.setTitle("Añadir nuevo item");
        stage.show();
    }

    private void generateWindowInvoice() {
        Stage stage = new Stage();
        VBox viewAdd = new InvoiceView(menu, stage);
        Scene scene = new Scene(viewAdd, 400, 400);
        stage.setX(800);
        stage.setY(220);

        stage.setScene(scene);
        stage.setTitle("Factura");
        stage.show();
    }

    private void printTicket() throws IOException, PrinterException {
        pdfGenerator.fillHeader();
    }

}

