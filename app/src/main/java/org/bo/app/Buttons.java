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
import org.bo.list.Item.ItemDTO;
import org.bo.list.generator.PDFGenerator;
import org.bo.list.menu.Menu;

import java.awt.print.PrinterException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Buttons extends HBox {
    private Button btnPrintInvoice, btnPrintForKitchen;
    private Map<ItemDTO, Integer> order;
    private PDFGenerator pdfGenerator;

    public Buttons(Map<ItemDTO, Integer> order) throws IOException {
        this.order = order;
        this.pdfGenerator = new PDFGenerator(order);

        btnPrintInvoice = new Button("IMPRIMIR FACTURA");
        btnPrintForKitchen = new Button("IMPRIMIR PEDIDO");

        btnPrintInvoice.setFont(new Font("Arial", 20));
        btnPrintInvoice.setStyle("-fx-text-fill: White; -fx-background-color: Green");

        btnPrintForKitchen.setFont(new Font("Arial", 20));
        btnPrintForKitchen.setStyle("-fx-background-color: Red ; -fx-text-fill:White");

        this.setSpacing(200);
        setMargin(btnPrintInvoice, new Insets(30));
        setMargin(btnPrintForKitchen, new Insets(30));

        getChildren().addAll(btnPrintInvoice, btnPrintForKitchen);

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
    }

    /*private void createWindowAdded() {
        Stage stage = new Stage();
        VBox viewAdd = new AddView(stage, menuView);
        Scene scene = new Scene(viewAdd, 400, 400);
        stage.setX(800);
        stage.setY(220);

        stage.setScene(scene);
        stage.setTitle("Añadir nuevo item");
        stage.show();
    }*/

    private void generateWindowInvoice() throws SQLException {
        Stage stage = new Stage();
        VBox viewAdd = new InvoiceView(order, stage);
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

