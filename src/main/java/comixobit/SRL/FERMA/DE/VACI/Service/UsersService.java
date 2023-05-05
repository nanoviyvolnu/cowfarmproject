package comixobit.SRL.FERMA.DE.VACI.Service;

import comixobit.SRL.FERMA.DE.VACI.Models.UserModel;
import comixobit.SRL.FERMA.DE.VACI.Models.VacaModel;
import comixobit.SRL.FERMA.DE.VACI.Repository.UserRepository;
import comixobit.SRL.FERMA.DE.VACI.Security.UsersDetailsSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
@Service
public class UsersService implements UserDetailsService {

    private final UserRepository userRepository;


    @Autowired
    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Nu este inregistrat utilizator cu asa email " + email));

        UsersDetailsSecurity usersDetailsSecurity = new UsersDetailsSecurity(userModel);
        return usersDetailsSecurity;
    }

    public List<UserModel> selectAllUsers(){
        return userRepository.findAll();
    }

    public UserModel findById(Integer id){
        return userRepository.getOne(id);
    }

    public void deleteById(Integer id){
        userRepository.deleteById(id);
    }

}
