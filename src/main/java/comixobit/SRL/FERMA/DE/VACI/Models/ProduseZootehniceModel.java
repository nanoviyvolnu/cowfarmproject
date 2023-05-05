package comixobit.SRL.FERMA.DE.VACI.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "produsezootehnice")
public class ProduseZootehniceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int idLot;

    @NotNull(message = "Coloana nu poate fi pustie!")
    @Column(name = "Cantitate")
    private int cantitate;

    @NotNull(message = "Data nu poate fi pustie!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "Data_recoltarii")
    private Date dataRecoltarii;

    @NotNull(message = "Coloana nu poate fi pustie!")
    @Column(name = "Proveninta")
    private String proveninta;

    @NotNull(message = "Coloana nu poate fi pustie!")
    @Column(name = "Tip_produs")
    private String tipProdus;

    @NotNull(message = "Coloana nu poate fi pustie!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "Data_expirarii")
    private Date dataExpirarii;

}
