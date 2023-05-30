package comixobit.SRL.FERMA.DE.VACI.Models;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@jakarta.persistence.Entity
@jakarta.persistence.Table(name = "inventar")
@Data
public class InventarModel {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_articol")
    private int idArticol;

    @NotNull(message = "Coloana nu poate fi pustie!")
    @Column(name = "denumire_articol")
    private String denumireArticol;

    @NotNull(message = "Coloana nu poate fi pustie!")
    @Column(name = "cantitate")
    private int cantitate;

    @NotNull(message = "Coloana nu poate fi pustie!")
    @Column(name = "pretul")
    private int pretul;

    @Column(name = "costul_total")
    private int costulTotal;

    @NotNull(message = "Data nu poate fi pustie!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "data_procurare")
    private Date dataProcurare;
}
