package comixobit.SRL.FERMA.DE.VACI.Utils;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import comixobit.SRL.FERMA.DE.VACI.Models.VacaModel;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ExportCowsByPdf {
    private List<VacaModel> vacaModelList;

    public ExportCowsByPdf(List<VacaModel> vacaModelList) {
        this.vacaModelList = vacaModelList;
    }

    private void writeTableHeader(PdfPTable table){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Poza", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Rasa", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Masa initiala", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Masa la moment", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Starea sanatatii", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Statutul", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Forma de achizitie", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Data luarii evidentei", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Genul", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Categorie", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (VacaModel vacaModel : vacaModelList) {
            table.addCell(vacaModel.getPhoto());
            table.addCell(vacaModel.getRasa());
            table.addCell(String.valueOf(vacaModel.getMasaKgInitiala()));
            table.addCell(String.valueOf(vacaModel.getMasaKgMoment()));
            table.addCell(vacaModel.getStareaSanatatii());
            table.addCell(vacaModel.getStatutul());
            table.addCell(vacaModel.getFormaAchizitie());
            table.addCell(String.valueOf(vacaModel.getDataLuariiEvidenta()));
            table.addCell(vacaModel.getGenul());
            table.addCell(vacaModel.getCategorie());
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

        PdfPTable table = new PdfPTable(10);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f});
        table.setSpacingBefore(5);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}
