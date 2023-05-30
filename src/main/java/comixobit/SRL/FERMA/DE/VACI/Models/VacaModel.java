package comixobit.SRL.FERMA.DE.VACI.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "vaca")
@RequiredArgsConstructor
@Getter
@Setter
public class VacaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idVaca;

    @Column(name = "Codul")
    private String codul;

    @Column(name = "Photo", length = 65)
    private String photo;

    @NotNull(message = "Coloana nu poate fi pustie!")
    @Column(name = "Rasa")
    private String rasa;

    @NotNull(message = "Coloana nu poate fi pustie!")
    @Column(name = "Masa_kg_initiala")
    private int masaKgInitiala;

    @NotNull(message = "Coloana nu poate fi pustie!")
    @Column(name = "Masa_kg_moment")
    private int masaKgMoment;

    @NotNull(message = "Coloana nu poate fi pustie!")
    @Column(name = "Statutul")
    private String statutul;

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

    @NotNull(message = "Coloana nu poate fi pustie!")
    @Column(name = "Starea_sanatatii")
    private String stareaSanatatii;

    @OneToMany(mappedBy = "vacaModel", cascade = CascadeType.ALL)
    private List<GrupuriVaciModel> grupuriVaciModels;

    @Column(name = "Pretul")
    private int pretul;
    
    @Column(name = "Data_nasterii")
    @NotNull(message = "Data nu poate fi pustie!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataNasterii;

    @Column(name = "Data_insarcinarii")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataInsarcinare;



    public void calculatePregnancyDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataNasterii);

        // Add 3.5 years to the "dataLuariiEvidenta" date
        calendar.add(Calendar.YEAR, 3);
//        calendar.add(Calendar.MONTH, 6);

        dataInsarcinare = calendar.getTime();
    }

    @Transient
    public String getPhotosImagePathCow() {
        if (photo.equals(null))
            return null;
        return "/images/cows/" + idVaca + "/" + photo;
    }
}
