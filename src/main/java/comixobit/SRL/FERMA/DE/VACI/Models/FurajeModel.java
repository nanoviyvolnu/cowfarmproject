package comixobit.SRL.FERMA.DE.VACI.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "furaje")
public class FurajeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int idLot;

    @NotNull(message = "Coloana nu poate fi pustie!")
    @Column(name = "Tipul_furaj")
    private String tipulFuraj;

    @NotNull(message = "Coloana nu poate fi pustie!")
    @Column(name = "Cantitatea_primita")
    private int cantitateaPrimita;

    @NotNull(message = "Coloana nu poate fi pustie!")
    @Column(name = "Calitatea")
    private String calitatea;

    @NotNull(message = "Coloana nu poate fi pustie!")
    @Column(name = "Costul_per_unitate")
    private int costulPerUnitate;

    @NotNull(message = "Coloana nu poate fi pustie!")
    @Column(name = "Costul_total")
    private int costulTotal;

    public FurajeModel() {

    }
}
