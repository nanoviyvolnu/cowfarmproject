package comixobit.SRL.FERMA.DE.VACI.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.util.Date;

@Data
@Getter
@Setter
@Entity
@Table(name = "task")
public class TaskModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idTask;

    @JsonProperty("denumire")
    @Column(name = "denumire")
    private String denumire;

    @Column(name = "data_creare")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataCreare;

    @Column(name = "data_limita")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataLimita;

    @Column(name = "status_task")
    private String statusTask;

    @JsonProperty("descriere")
    @Column(name = "descriere")
    private String descriere;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_lucrator")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private LucratorModel lucratorModel;
}
