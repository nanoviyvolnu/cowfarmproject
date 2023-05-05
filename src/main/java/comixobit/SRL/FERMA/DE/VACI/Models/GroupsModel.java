package comixobit.SRL.FERMA.DE.VACI.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "grupuri")
@RequiredArgsConstructor
@Getter
@Setter
public class GroupsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idGrup;

    @NotNull(message = "Coloana nu poate fi goala!")
    @Column(name = "Denumire_grup")
    private String denumireGrup;

    @Column(name = "Data_creare")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @CreationTimestamp
    private Date dataCreare;

    @NotNull(message = "Coloana nu poate fi goala!")
    @Column(name = "Descriere")
    private String descriere;

    @Column(name = "Numarul_de_animale")
    private int numarulDeAnimale;

    @OneToMany(mappedBy = "groupsModel", cascade = CascadeType.ALL)
    private List<GrupuriVaciModel> grupuriVaciModels;
}
