package org.bo.list.generator;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA, 32);
        contentStream.newLineAtOffset(20, page.getMediaBox().getHeight() - 100);
        contentStream.showText("Hello World");
        contentStream.endText();

        contentStream.close();

        document.save("app/src/main/java/org/bo/app/pdf/Hello world.pdf");
    }

}
