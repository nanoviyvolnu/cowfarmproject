package comixobit.SRL.FERMA.DE.VACI.Utils;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import comixobit.SRL.FERMA.DE.VACI.Models.VacaFinalModel;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ExportSoldedDiedCowsByPdf {

    private List<VacaFinalModel> vacaFinalModel;

    public ExportSoldedDiedCowsByPdf(List<VacaFinalModel> vacaFinalModels) {
        this.vacaFinalModel = vacaFinalModels;
    }


    private void writeTableHeader(PdfPTable table){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Poza", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Rasa", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Masa initiala", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Masa la moment", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Statutul", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Genul", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Categorie", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Data vanzare", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (VacaFinalModel vaca : vacaFinalModel) {
            table.addCell(vaca.getVacaModel().getPhoto());
            table.addCell(vaca.getVacaModel().getRasa());
            table.addCell(String.valueOf(vaca.getVacaModel().getMasaKgInitiala()));
            table.addCell(String.valueOf(vaca.getVacaModel().getMasaKgMoment()));
            table.addCell(vaca.getVacaModel().getStatutul());
            table.addCell(vaca.getVacaModel().getGenul());
            table.addCell(vaca.getVacaModel().getCategorie());
            table.addCell(String.valueOf(vaca.getDataMortii()));
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setSize(15);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("Lista văcilor", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f});
        table.setSpacingBefore(5);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}
