package comixobit.SRL.FERMA.DE.VACI.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "grupuri_vaci")
@Getter
@Setter
public class GrupuriVaciModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH })
    @JoinColumn(name = "Id_grup", referencedColumnName = "idGrup")
    private GroupsModel groupsModel;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE })
    @JoinColumn(name = "Id_vaca", referencedColumnName = "idVaca")
    private VacaModel vacaModel;
}