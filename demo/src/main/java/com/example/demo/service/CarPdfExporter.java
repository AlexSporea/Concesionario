package com.example.demo.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import com.example.demo.entity.Car;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class CarPdfExporter {

    private Document document;
    private ByteArrayOutputStream out;
    private Paragraph title;
    private PdfPTable table;
    private List<Car> cars;
    private Font headFont;
    private PdfPCell hcell;

    public CarPdfExporter(List<Car> cars) {
        this.cars = cars;
        document = new Document();
        out = new ByteArrayOutputStream();
        table = new PdfPTable(2);
        headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
    }

    public ByteArrayInputStream carsReport() {
        try {
            createTitle();

            setTableWidth();

            createHeading("Modelo");
            createHeading("Matricula");

            createBody();

            createPdf();

        } catch (DocumentException e) {
            System.out.println(e.getStackTrace());
        }

        return new ByteArrayInputStream(out.toByteArray());

    }

    private void createTitle() {
        String titulo = "Fichero " + cars.get(0).getMarca() + ".pdf";
        title = new Paragraph(titulo, headFont);
        title.setAlignment(Paragraph.ALIGN_LEFT);
    }

    private void setTableWidth() throws DocumentException{
        table.setWidthPercentage(80);
        table.setWidths(new int[] { 4, 4 });
    }

    private void createHeading(String name) {

        hcell = new PdfPCell(new Phrase(name, headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

    }

    private void createBody() {
        for (Car carObj : cars) {
            hcell = new PdfPCell(new Phrase(carObj.getModelo()));
            completeCell();
            hcell = new PdfPCell(new Phrase(carObj.getMatricula()));
            hcell.setPaddingLeft(5);
            completeCell();

        }
    }

    private void completeCell() {
            hcell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(hcell);
    }

    private void createPdf() throws DocumentException {
        PdfWriter.getInstance(document, out);
        document.open();

        document.add(title);
        document.add(Chunk.NEWLINE);
        document.add(table);

        document.close();
    }
}
