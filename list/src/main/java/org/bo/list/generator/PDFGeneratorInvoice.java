package org.bo.list.generator;

import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.bo.list.Item.ItemDTO;

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

    public void generateInvoice() throws IOException {
        createItems();
        fillItems();
        createTotalPrice();
        createChangeAndWaiter();
        closeContentStream();
    }

    private void createChangeAndWaiter() throws IOException {
        putItemPdf(50, total_height - 10, "Mesero: " + requirements.get("waiter"), PDType1Font.HELVETICA_BOLD);
        putItemPdf(50, total_height - 20, "Total Pagado: " + requirements.get("cash"), PDType1Font.HELVETICA_BOLD);
        putItemPdf(50, total_height - 30, "Cambio: " + requirements.get("change"), PDType1Font.HELVETICA_BOLD);
    }
}
