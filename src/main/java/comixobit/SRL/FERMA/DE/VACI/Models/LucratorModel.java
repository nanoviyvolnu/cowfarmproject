package comixobit.SRL.FERMA.DE.VACI.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "lucrator")
public class LucratorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int idLucrator;

    @NotNull(message = "Coloana nu poate fi pustie!")
    @Column(name = "Idnp", length = 13)
    private String idnp;

    @NotNull(message = "Coloana nu poate fi pustie!")
    @Column(name = "Nume")
    private String nume;

    @NotNull(message = "Coloana nu poate fi pustie!")
    @Column(name = "Prenume")
    private String prenume;

    @NotNull(message = "Coloana nu poate fi pustie!")
    @Column(name = "Photo", length = 64)
    private String photo;

    @NotNull(message = "Coloana nu poate fi pustie!")
    @Column(name = "email")
    private String email;

    @NotNull(message = "Coloana nu poate fi pustie!")
    @Column(name = "Norma_de_munca")
    private int normaDeMunca;

    @NotNull(message = "Coloana nu poate fi pustie!")
    @Column(name = "Remunerare_pe_ora")
    private int remunerarePeOra;

    @NotNull(message = "Data nu poate fi pustie!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "Data_angajarii")
    private Date dataAngajarii;

    @Column(name = "salariu")
    private int salariu;

    @Column(name = "status")
    private String status;

    public LucratorModel() {

    }

    @Transient
    public String getPhotosImagePathEmployee() {
        if (photo.equals(null))
            return null;
        return "/images/employers/" + idLucrator + "/" + photo;
    }
}
