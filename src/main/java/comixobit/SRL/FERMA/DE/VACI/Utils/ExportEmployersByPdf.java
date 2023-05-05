package comixobit.SRL.FERMA.DE.VACI.Utils;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import comixobit.SRL.FERMA.DE.VACI.Models.LucratorModel;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ExportEmployersByPdf {
    private List<LucratorModel> lucratorModelList;

    public ExportEmployersByPdf(List<LucratorModel> lucratorModelList) {
        this.lucratorModelList = lucratorModelList;
    }

    private void writeTableHeader(PdfPTable table){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Idnp", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Nume", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Prenume", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Poza", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Norma de munca", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Remunerare pe ora", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Data angajarii", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (LucratorModel lucratorModel : lucratorModelList) {
            table.addCell(lucratorModel.getIdnp());
            table.addCell(lucratorModel.getNume());
            table.addCell(lucratorModel.getPrenume());
            table.addCell(String.valueOf(lucratorModel.getNormaDeMunca()));
            table.addCell(String.valueOf(lucratorModel.getRemunerarePeOra()));
            table.addCell(String.valueOf(lucratorModel.getDataAngajarii()));
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setSize(15);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("Lista angajatilor", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f});
        table.setSpacingBefore(5);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}
