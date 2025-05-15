package hospital.management.hospital_management.service;

import hospital.management.hospital_management.domain.UserEntity;
import hospital.management.hospital_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component("userDetailsService")
@RequiredArgsConstructor
public class UserDetailCustom implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)  {
        UserEntity userEntity=this.userRepository.findByUsername(username);
        return new User
                (userEntity.getUsername(),
                        userEntity.getPassword(),
                        Collections.singletonList((new SimpleGrantedAuthority("ROLE_PATIENT"))));

    }


}