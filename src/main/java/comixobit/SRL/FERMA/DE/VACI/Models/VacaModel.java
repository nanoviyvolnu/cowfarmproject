package comixobit.SRL.FERMA.DE.VACI.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "vaca")
@RequiredArgsConstructor
@Getter
@Setter
public class VacaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idVaca;

    @Column(name = "Photo", length = 65)
    private String photo;

    @NotNull(message = "Coloana nu poate fi pustie!")
    @Column(name = "Rasa")
    private String rasa;

    @NotNull(message = "Coloana nu poate fi pustie!")
    @Column(name = "Masa_kg_initiala")
    private short masaKgInitiala;

    @NotNull(message = "Coloana nu poate fi pustie!")
    @Column(name = "Masa_kg_moment")
    private short masaKgMoment;

    @NotNull(message = "Coloana nu poate fi pustie!")
    @Column(name = "Starea_sanatii")
    private String stareaSanatatii;

    @NotNull(message = "Coloana nu poate fi pustie!")
    @Column(name = "Forma_achizitie")
    private String formaAchizitie;

    @NotNull(message = "Data nu poate fi pustie!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "Data_luarii_evidenta")
    private Date dataLuariiEvidenta;

    @NotNull(message = "Coloana nu poate fi pustie!")
    @Column(name = "Genul")
    private String genul;

    @NotNull(message = "Coloana nu poate fi pustie!")
    @Column(name = "Categorie")
    private String categorie;



    @Transient
    public String getPhotosImagePathCow() {
        if (photo.equals(null))
            return null;
        return "/images/cows/" + idVaca + "/" + photo;
    }
}
