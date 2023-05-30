package comixobit.SRL.FERMA.DE.VACI.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Table(name  = "jurnal")
@Data
@Getter
@Setter
public class JurnalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEvent;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "Id_vaca", referencedColumnName = "idVaca")
    private VacaModel vacaModel;

    @Column(name =  "Status")
    private String status;
}
