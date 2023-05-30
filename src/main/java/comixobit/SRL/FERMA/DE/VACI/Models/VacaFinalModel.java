package comixobit.SRL.FERMA.DE.VACI.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "vacafinal")
public class VacaFinalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Id_vaca", referencedColumnName = "idVaca")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VacaModel vacaModel;

    @Column(name = "data")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataMortii;

    @PrePersist
    public void prePersist() {
        dataMortii = new Date();
    }
}
