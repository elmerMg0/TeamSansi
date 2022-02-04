package org.bo.list.generator;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.printing.PDFPageable;
import org.bo.list.Item.ItemDTO;
import org.vandeseer.easytable.TableDrawer;
import org.vandeseer.easytable.settings.HorizontalAlignment;
import org.vandeseer.easytable.structure.Row;
import org.vandeseer.easytable.structure.Table;
import org.vandeseer.easytable.structure.cell.TextCell;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import java.awt.*;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class PDFGenerator {

    protected Map<ItemDTO, Integer> order;
    protected PDPageContentStream contentStream;
    protected PDPage page;
    protected PDDocument document;
    protected float height;
    protected double totalPrice;
    protected float total_height;

    public PDFGenerator(Map<ItemDTO, Integer> order) throws IOException {
        this.order = order;
        this.totalPrice = 0;

        this.document = new PDDocument();
        this.page = new PDPage(PDRectangle.A4);
        this.document.addPage(page);
        this.contentStream = new PDPageContentStream(document, page);
    }

    public void createPDFKitchen() throws IOException, PrinterException {
        createItems();
        fillItems();
        createTotalPrice();
        closeContentStream();
//        printPDF();
    }

    protected void printPDF() throws PrinterException {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        if (printerJob.printDialog()) {
            printerJob.setPageable(new PDFPageable(document));
            PrintService service = PrintServiceLookup.lookupDefaultPrintService();
            printerJob.setPrintService(service);
            printerJob.print();
        }
    }

    protected void createItems() throws IOException {
        height = page.getMediaBox().getHeight();
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 9);

        contentStream.newLineAtOffset(65, height - 10);
        contentStream.showText("Dima's Restaurant");
        contentStream.endText();

        contentStream.beginText();
        contentStream.newLineAtOffset(25, height - 25);
        contentStream.showText("Cochabamba-Bolivia");
        contentStream.endText();

        contentStream.beginText();
        contentStream.newLineAtOffset(120, height - 25);
        contentStream.showText("Fecha: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 20);
        contentStream.newLineAtOffset(25, height - 50);
        contentStream.showText("Mesa: 12  ");
        contentStream.showText("Nro: 25");
        contentStream.endText();

        Table myTable = Table.builder()
                .addColumnsOfWidth(95, 35, 35, 35)
                .padding(2)
                .font(PDType1Font.HELVETICA_BOLD)
                .addRow(Row.builder().fontSize(9)
                        .add(TextCell.builder().text("Item").borderWidth(0.5F).borderColor(Color.black)
                                .backgroundColor(Color.WHITE).horizontalAlignment(HorizontalAlignment.CENTER).build())
                        .add(TextCell.builder().text("Cant.").borderWidth(0.5f).borderColor(Color.black)
                                .backgroundColor(Color.WHITE).horizontalAlignment(HorizontalAlignment.CENTER).build())
                        .add(TextCell.builder().text("P. Unit").borderWidth(0.5f).borderColor(Color.black)
                                .backgroundColor(Color.WHITE).horizontalAlignment(HorizontalAlignment.CENTER).build())
                        .add(TextCell.builder().text("Total").borderWidth(0.5f).borderColor(Color.black)
                                .backgroundColor(Color.WHITE).horizontalAlignment(HorizontalAlignment.CENTER).build())
                        .build())
                .build();

        TableDrawer tableDrawer = TableDrawer.builder()
                .contentStream(contentStream)
                .startX(2)
                .startY(height - 65)
                .table(myTable)
                .build();
        tableDrawer.draw();
    }

    protected void fillItems() {
        AtomicReference<Double> finaltotal = new AtomicReference<>((double) 0);
        var tableBuilder = Table.builder()
                .addColumnsOfWidth(95, 35, 35, 35)
                .padding(5)
                .font(PDType1Font.HELVETICA)
                .borderColor(Color.WHITE);
        order.forEach((itemDTO, quantity) -> {
            double total = quantity * itemDTO.getPrice();
            finaltotal.set(finaltotal.get() + total);
            tableBuilder.addRow(Row.builder().fontSize(9)
                    .add(TextCell.builder().text(itemDTO.getName()).borderWidth(0.5F)
                            .backgroundColor(Color.WHITE).horizontalAlignment(HorizontalAlignment.LEFT).build())
                    .add(TextCell.builder().text(quantity + "").borderWidth(0.5F)
                            .backgroundColor(Color.WHITE).horizontalAlignment(HorizontalAlignment.RIGHT).build())
                    .add(TextCell.builder().text(itemDTO.getPrice() + "").borderWidth(0.5F)
                            .backgroundColor(Color.WHITE).horizontalAlignment(HorizontalAlignment.RIGHT).build())
                    .add(TextCell.builder().text(total + "").borderWidth(0.5F)
                            .backgroundColor(Color.WHITE).horizontalAlignment(HorizontalAlignment.RIGHT).build())
                    .build());
        });
        Table myTable = tableBuilder.build();

        TableDrawer tableDrawer = TableDrawer.builder()
                .contentStream(contentStream)
                .startX(2)
                .startY(height - 75)
                .table(myTable)
                .build();
        tableDrawer.draw();
        this.totalPrice = finaltotal.get();
    }

    protected void createTotalPrice() throws IOException {
        total_height = height - 75 - (20 * order.keySet().size());

        putItemPdf(140, total_height, "Total: ", PDType1Font.HELVETICA_BOLD);
        putItemPdf(170, total_height, totalPrice + "", PDType1Font.HELVETICA);
    }

    protected void putItemPdf(float posX, float posY, String texto, PDFont font) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(posX, posY);
        contentStream.setFont(font, 9);
        contentStream.showText(texto);
        contentStream.endText();
    }

    protected void closeContentStream() throws IOException {
        contentStream.close();
        document.save("src/main/java/org/bo/app/pdf/" + LocalDate.now() + ".pdf");
    }
}
