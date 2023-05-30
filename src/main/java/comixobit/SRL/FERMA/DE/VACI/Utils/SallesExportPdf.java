package comixobit.SRL.FERMA.DE.VACI.Utils;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import comixobit.SRL.FERMA.DE.VACI.Models.ClientiModel;
import comixobit.SRL.FERMA.DE.VACI.Models.VanzariModel;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class SallesExportPdf {

    private List<VanzariModel> vanzariModelList;

    public SallesExportPdf(List<VanzariModel> vanzariModelList) {
        this.vanzariModelList = vanzariModelList;
    }

    private void writeTableHeader(PdfPTable table){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Nume", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Prenume", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Email", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Nr_tel", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Organizatia", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Cantitatea", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Tip produs", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Rasa", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Categorie", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Data expirarii", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Data vanzare", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Pretul", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (VanzariModel vanzariModel : vanzariModelList) {
            table.addCell(vanzariModel.getClientiModel().getNume());
            table.addCell(vanzariModel.getClientiModel().getPrenume());
            table.addCell(vanzariModel.getClientiModel().getEmail());
            table.addCell(vanzariModel.getClientiModel().getNrTel());
            table.addCell(vanzariModel.getClientiModel().getOrganizatia());
            if(vanzariModel.getCantitate() != 0) {
                table.addCell(String.valueOf(vanzariModel.getCantitate()));
            }
            else {
                table.addCell("NULL");
            }
            if(vanzariModel.getProduseZootehniceModel() != null && vanzariModel.getProduseZootehniceModel().getTipProdus() != null) {
                table.addCell(vanzariModel.getProduseZootehniceModel().getTipProdus());
            }
            else{
                table.addCell("NULL");
            }
            if(vanzariModel.getVacaModel() != null && vanzariModel.getVacaModel().getRasa() != null) {
                table.addCell(vanzariModel.getVacaModel().getRasa());
            }
            else{
                table.addCell("NULL");
            }
            if(vanzariModel.getVacaModel() != null && vanzariModel.getVacaModel().getCategorie() != null){
                table.addCell(vanzariModel.getVacaModel().getCategorie());
            }
            else{
                table.addCell("NULL");
            }
            if(vanzariModel.getProduseZootehniceModel() != null){
                table.addCell(String.valueOf(vanzariModel.getProduseZootehniceModel().getDataExpirarii()));
            }
            else {
                table.addCell("NULL");
            }
            table.addCell(String.valueOf(vanzariModel.getDataVanzare()));
            table.addCell(String.valueOf(vanzariModel.getPretul()));
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setSize(15);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("Lista vanzarilor", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(12);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f});
        table.setSpacingBefore(5);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }

}
