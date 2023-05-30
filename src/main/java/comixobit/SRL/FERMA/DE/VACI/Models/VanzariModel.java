package comixobit.SRL.FERMA.DE.VACI.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;

@Entity
@Table(name = "vanzari")
@Getter
@Setter
public class VanzariModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idVanzare;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_lot")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProduseZootehniceModel produseZootehniceModel;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_client")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ClientiModel clientiModel;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_vaca")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VacaModel vacaModel;

    @NotNull(message = "Coloana nu poate fi pustie!")
    @Column(name = "Cantitate")
    private int cantitate;

    @NotNull(message = "Coloana nu poate fi pustie!")
    @Column(name = "Data_vanzare")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private java.util.Date dataVanzare;

    @NotNull(message = "Coloana nu poate fi pustie!")
    @Column(name = "Pretul")
    private float pretul;
}
