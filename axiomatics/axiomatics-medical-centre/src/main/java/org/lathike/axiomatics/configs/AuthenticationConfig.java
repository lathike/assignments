package org.lathike.axiomatics.configs;

import org.lathike.axiomatics.model.MedicalStaff;
import org.lathike.axiomatics.repositories.MedicalStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
public class AuthenticationConfig extends GlobalAuthenticationConfigurerAdapter {

    private final MedicalStaffRepository medicalStaffRepository;

    @Autowired
    public AuthenticationConfig(MedicalStaffRepository medicalStaffRepository) {
        this.medicalStaffRepository = medicalStaffRepository;
    }

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        super.init(auth);
        auth.userDetailsService(userDetailsService());
        auth.authenticationProvider(authenticationProvider(userDetailsService()));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return (String socialSecurityNumber) -> {
            Optional<MedicalStaff> possibleStaffMember = medicalStaffRepository.findBySocialSecurityNumber(socialSecurityNumber);
            MedicalStaff staffMember = possibleStaffMember.orElseThrow(
                    () -> new UsernameNotFoundException("No such medical staff with social security number:" + socialSecurityNumber));
            return new User(staffMember.getSocialSecurityNumber(), staffMember.getPassword(), staffMember.isActive(),
                    true, true, true, AuthorityUtils.createAuthorityList("STAFF"));
        };
    }
}
