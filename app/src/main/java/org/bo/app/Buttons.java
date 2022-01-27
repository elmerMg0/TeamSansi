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
    private PDPageContentStream contentStream;
    private PDPage page;
    private PDDocument document;
    Map<ItemDTO, Integer> order;

    public Buttons(Menu menu, GridPane menuView, OrderDetail orderDetail) throws IOException {
        this.menu = menu;
        this.menuView = menuView;
        this.orderDetail = orderDetail;
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

        order = orderDetail.getOrder();
        document = new PDDocument();
        page = new PDPage(PDRectangle.A4);

        document.addPage(page);
        contentStream = new PDPageContentStream(document, page);

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
        fillHeader();
        float height = page.getMediaBox().getHeight();
        float pagewidth = page.getMediaBox().getWidth();
        Iterator it = order.keySet().iterator();
        int lastLine = 90;
        double totalQuantity = 0;
        PDFont fontBold = PDType1Font.HELVETICA_BOLD;
        PDFont normalFond  = PDType1Font.HELVETICA;
        int fontSize = 9;
        while (it.hasNext()) {
            ItemDTO item = (ItemDTO) it.next();
            int quanty = order.get(item);
            double total = quanty * item.getPrice();

            putItemPdf(1,height-lastLine,item.getName(),normalFond);

            float quanty_width = (normalFond.getStringWidth(String.valueOf(quanty)) / 1000.0f) * 8;
            float xq = pagewidth - quanty_width;
            putItemPdf(xq - 505 ,height - lastLine , String.valueOf(quanty),normalFond);

            float price_width = (normalFond.getStringWidth(String.valueOf(item.getPrice())) / 1000.0f) * fontSize;
            float xp = pagewidth - price_width;
            putItemPdf(xp-460,height - lastLine,String.valueOf(item.getPrice()),normalFond);

            float total_width = (normalFond.getStringWidth(String.valueOf(total)) / 1000.0f) * fontSize;
            float xt = pagewidth - total_width;
            putItemPdf(xt-410,height - lastLine,String.valueOf(total),normalFond);
            totalQuantity += total;
            lastLine += 20;
        }

        float total_width = (normalFond.getStringWidth(String.valueOf(totalQuantity)) / 1000.0f) * fontSize;
        float xt = pagewidth - total_width;
        putItemPdf(xt - 410,height - lastLine,"Total: ",fontBold);
        putItemPdf(xt-450,height - lastLine,String.valueOf(totalQuantity),normalFond);

        contentStream.close();
        document.save("src/main/java/org/bo/app/pdf/Hello world.pdf");

        /*PrinterJob printerJob = PrinterJob.getPrinterJob();
        if(printerJob.printDialog()) {
            printerJob.setPageable(new PDFPageable(document));
            PrintService service = PrintServiceLookup.lookupDefaultPrintService();
            printerJob.setPrintService(service);
            printerJob.print();
        }*/
    }

    private void putItemPdf(float posX,float posY,String texto,PDFont font) throws IOException {
        float height = page.getMediaBox().getHeight();
        contentStream.beginText();
        contentStream.newLineAtOffset(posX, posY);
        contentStream.setFont(font, 9);
        contentStream.showText(texto);
        contentStream.endText();
    }


    private void fillHeader() throws IOException {
        float height = page.getMediaBox().getHeight() / 2;
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 9);

        contentStream.newLineAtOffset(65, height * 2 - 10);
        contentStream.showText("Dima's Restaurant");
        contentStream.endText();

        contentStream.beginText();
        contentStream.newLineAtOffset(25, height * 2 - 25);
        contentStream.showText("Cochabamba-Bolivia");
        contentStream.endText();

        contentStream.beginText();
        contentStream.newLineAtOffset(120, height * 2 - 25);
        contentStream.showText("Fecha: 26/06/22");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 20);
        contentStream.newLineAtOffset(25, height * 2 - 50);
        contentStream.showText("Mesa: 12  ");
        contentStream.showText("Nro: 25");
        contentStream.endText();

        Table myTable = Table.builder()
                .addColumnsOfWidth(85, 30, 50,35)
                .padding(2)
                .addRow(Row.builder().fontSize(9)
                        .add(TextCell.builder().text("Item      ").borderWidth(0.5F).borderColor(Color.black)
                                .backgroundColor(Color.WHITE).horizontalAlignment(HorizontalAlignment.CENTER).build())
                        .add(TextCell.builder().text("Cant.").borderWidth(0.5f).borderColor(Color.black)
                                .backgroundColor(Color.WHITE).horizontalAlignment(HorizontalAlignment.CENTER).build())
                        .add(TextCell.builder().text("P. Unitario").borderWidth(0.5f).borderColor(Color.black)
                                .backgroundColor(Color.WHITE).horizontalAlignment(HorizontalAlignment.CENTER).build())
                        .add(TextCell.builder().text("SubTotal").borderWidth(0.5f).borderColor(Color.black)
                                .backgroundColor(Color.WHITE).horizontalAlignment(HorizontalAlignment.CENTER).build())
                        .build())
                .build();

        TableDrawer tableDrawer = TableDrawer.builder()
                .contentStream(contentStream)
                .startX(2)
                .startY(height * 2 - 65)
                .table(myTable)
                .build();
        tableDrawer.draw();


    }

}

