package comixobit.SRL.FERMA.DE.VACI.Models;

import comixobit.SRL.FERMA.DE.VACI.Security.ValidPassword;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.userdetails.UserDetails;




@Entity
@Table(name = "users")
@RequiredArgsConstructor
@Getter
@Setter
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int idUser;

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
    @Column(name = "Parola")
    private String parola;

    @Column(name = "Role")
    private String role;
}
