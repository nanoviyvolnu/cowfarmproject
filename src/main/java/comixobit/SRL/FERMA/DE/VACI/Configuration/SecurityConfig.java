package comixobit.SRL.FERMA.DE.VACI.Configuration;

import comixobit.SRL.FERMA.DE.VACI.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final UsersService usersService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler, UsersService usersService) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.usersService = usersService;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/adminPanel", "/adminPanel/**").hasRole("ADMIN")
                        .requestMatchers("/index/users", "/grupuri", "/listaGrupuri", "/adaugaGrup").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/auth/login", "/auth/register", "/index")
                        .permitAll()
                        .anyRequest()
                        .hasAnyRole("USER", "ADMIN"))
                .formLogin((formLogin) -> formLogin
                        .loginPage("/auth/login")
                        .successHandler(authenticationSuccessHandler)
                        .loginProcessingUrl("/perform_login")
                        .failureUrl("/auth/login?error=true"))
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/index")
                        .permitAll())
                .httpBasic(withDefaults())
                .build();
    }
}
