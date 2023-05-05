package comixobit.SRL.FERMA.DE.VACI.Utils;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import comixobit.SRL.FERMA.DE.VACI.Models.FurajeModel;
import comixobit.SRL.FERMA.DE.VACI.Models.LucratorModel;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ExportFeedsByPdf {

    private List<FurajeModel> furajeModelList;

    public ExportFeedsByPdf(List<FurajeModel> furajeModelList) {
        this.furajeModelList = furajeModelList;
    }

    private void writeTableHeader(PdfPTable table){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Tipul furaj", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Cantitatea primita", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Calitatea", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Costul pe unitate", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Costul total", font));
        table.addCell(cell);

    }

    private void writeTableData(PdfPTable table) {
        for (FurajeModel furajeModel : furajeModelList) {
            table.addCell(furajeModel.getTipulFuraj());
            table.addCell(String.valueOf(furajeModel.getCantitateaPrimita()));
            table.addCell(furajeModel.getCalitatea());
            table.addCell(String.valueOf(furajeModel.getCostulPerUnitate()));
            table.addCell(String.valueOf(furajeModel.getCostulTotal()));
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setSize(15);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("Lista furajelor", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {2.0f, 2.0f, 2.0f, 2.0f, 2.0f});
        table.setSpacingBefore(5);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}
