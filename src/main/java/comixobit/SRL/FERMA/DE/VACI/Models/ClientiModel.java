package comixobit.SRL.FERMA.DE.VACI.Models;

import comixobit.SRL.FERMA.DE.VACI.Security.ValidPassword;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Table(name ="clienti")
@Getter
@Setter
public class ClientiModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int idClient;

    @NotNull(message = "Coloana nu poate fi goala!")
    @Column(name = "Nume")
    private String nume;

    @NotNull(message = "Coloana nu poate fi goala!")
    @Column(name = "Prenume")
    private String prenume;

    @NotNull(message = "Coloana nu poate fi goala!")
    @Column(name = "Email", unique = true)
    private String email;

    @ValidPassword
    @NotNull(message = "Coloana nu poate fi goala!")
    @Column(name = "Nr_tel")
    private String nrTel;

    @Column(name = "Organizatia")
    private String organizatia;
}
