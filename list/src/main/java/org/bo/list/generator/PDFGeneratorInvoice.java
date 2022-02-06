package org.bo.list.generator;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.bo.list.Item.ItemDTO;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.util.Map;

public class PDFGeneratorInvoice extends PDFGenerator{

    private Map<String, String> requirements;

    public PDFGeneratorInvoice(Map<ItemDTO, Integer> order) throws IOException {
        super(order);
    }

    public PDFGeneratorInvoice(Map<ItemDTO, Integer> order, Map<String, String> requirements) throws IOException {
        this(order);
        this.requirements = requirements;
    }

    public void generateInvoice() throws IOException, PrinterException {
        createItems();
        fillItems();
        createTotalPrice();
        createChangeAndWaiter();
        closeContentStream("Invoice");
        printPDF();
    }

    private void createChangeAndWaiter() throws IOException {
        float pagewidth = page.getMediaBox().getWidth();
        PDFont font = PDType1Font.HELVETICA_BOLD;
        int size = 9;
        float paddingRight = 0;
        String txtWaiter = "Mesero: "+ requirements.get("waiter");
        float textWidthWaiter = (font.getStringWidth(txtWaiter) / 1000.0f) * size;
        float posWaiter = pagewidth - ((paddingRight * 2) + textWidthWaiter) - 398;

        String txtTotal = "Total Pagado: "+ requirements.get("cash");
        float textWidthTotal = (font.getStringWidth(txtTotal) / 1000.0f) * size;
        float posTotal = pagewidth - ((paddingRight * 2) + textWidthTotal) -398;

        String txtChange = "Cambio: "+ requirements.get("change");
        float textWidthChange = (font.getStringWidth(txtChange) / 1000.0f) * size;
        float posChange = pagewidth - ((paddingRight * 2) + textWidthChange) - 398;

        putItemPdf(posWaiter, total_height - 10, txtWaiter, PDType1Font.HELVETICA_BOLD);
        putItemPdf(posTotal, total_height - 20, txtTotal, PDType1Font.HELVETICA_BOLD);
        putItemPdf(posChange, total_height - 30, txtChange, PDType1Font.HELVETICA_BOLD);
    }
}
