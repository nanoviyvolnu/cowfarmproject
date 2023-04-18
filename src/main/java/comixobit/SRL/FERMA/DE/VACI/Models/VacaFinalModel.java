package comixobit.SRL.FERMA.DE.VACI.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Table(name = "vacafinal")
public class VacaFinalModel {

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "Id_vaca", referencedColumnName = "Id_vaca")
    private int idVaca;

    @Column(name = "Statut")
    private String statut;
}
