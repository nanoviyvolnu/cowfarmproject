package comixobit.SRL.FERMA.DE.VACI.Security;

import comixobit.SRL.FERMA.DE.VACI.Models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.Collections;

public class UsersDetailsSecurity implements UserDetails {


    private final UserModel userModel;

    public UsersDetailsSecurity(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(userModel.getRole()));
    }

    @Override
    public String getPassword() {
        return this.userModel.getParola();
    }

    @Override
    public String getUsername() {
        return this.userModel.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
