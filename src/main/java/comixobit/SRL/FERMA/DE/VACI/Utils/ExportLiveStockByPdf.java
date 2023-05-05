package comixobit.SRL.FERMA.DE.VACI.Utils;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import comixobit.SRL.FERMA.DE.VACI.Models.FurajeModel;
import comixobit.SRL.FERMA.DE.VACI.Models.ProduseZootehniceModel;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ExportLiveStockByPdf {

    private List<ProduseZootehniceModel> produseZootehniceModelList;

    public ExportLiveStockByPdf(List<ProduseZootehniceModel> produseZootehniceModelList) {
        this.produseZootehniceModelList = produseZootehniceModelList;
    }

    private void writeTableHeader(PdfPTable table){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Cantitate", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Data recoltarii", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Proveninta", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Tip produs", font));
        table.addCell(cell);

    }

    private void writeTableData(PdfPTable table) {
        for (ProduseZootehniceModel produseZootehniceModel : produseZootehniceModelList) {
            table.addCell(String.valueOf(produseZootehniceModel.getCantitate()));
            table.addCell(String.valueOf(produseZootehniceModel.getDataRecoltarii()));
            table.addCell(produseZootehniceModel.getProveninta());
            table.addCell(produseZootehniceModel.getTipProdus());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setSize(15);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("Lista produselor zootehnice", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {2.0f, 2.0f, 2.0f, 2.0f});
        table.setSpacingBefore(5);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}
