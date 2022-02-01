package org.bo.app;

import com.sun.javafx.collections.MappingChange;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.printing.PDFPageable;
import org.bo.app.view.AddView;
import org.bo.list.Item.ItemDTO;
import org.bo.list.generator.PDFGenerator;
import org.bo.list.menu.Menu;
import org.vandeseer.easytable.TableDrawer;
import org.vandeseer.easytable.settings.HorizontalAlignment;
import org.vandeseer.easytable.structure.Row;
import org.vandeseer.easytable.structure.Table;
import org.vandeseer.easytable.structure.cell.TextCell;

import javax.print.*;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import java.awt.*;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class Buttons extends HBox {
    private Button btnPrint, btnAdd, btnEdit;
    private Menu menu;
    private GridPane menuView;
    private OrderDetail orderDetail;
    private PDFGenerator pdfGenerator;

    public Buttons(Menu menu, GridPane menuView, OrderDetail orderDetail) throws IOException {
        this.menu = menu;
        this.menuView = menuView;
        this.orderDetail = orderDetail;
        this.pdfGenerator = new PDFGenerator(menu);

        btnPrint = new Button("IMPRIMIR FACTURA");
        btnAdd = new Button("AÑADIR");
        btnEdit = new Button("IMPRIMIR PEDIDO");

        btnPrint.setFont(new Font("Arial", 20));
        btnPrint.setStyle("-fx-text-fill: White; -fx-background-color: Green");

        btnEdit.setFont(new Font("Arial", 20));
        btnEdit.setStyle("-fx-background-color: Red ; -fx-text-fill:White");
        btnAdd.setFont(new Font("Arial", 20));

        btnAdd.setOnMouseClicked(event -> createWindowAdded());

        this.setSpacing(200);
        setMargin(btnPrint, new Insets(30));
        setMargin(btnAdd, new Insets(30));
        setMargin(btnEdit, new Insets(30));

        getChildren().addAll(btnPrint, btnEdit, btnAdd);

        menu.setOrderDishes(orderDetail.getOrder());

        btnPrint.setOnAction(event -> {
            try {
                printTicket();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (PrinterException e) {
                e.printStackTrace();
            }
        });
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

    private void printTicket() throws IOException, PrinterException {
        pdfGenerator.fillHeader();
    }

}

