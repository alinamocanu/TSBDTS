package com.store.bootstrap;

import com.store.domain.security.Authority;
import com.store.domain.security.User;
import com.store.repository.security.AuthorityRepository;
import com.store.repository.security.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    private AuthorityRepository authorityRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    private void loadUserData() {
        if (userRepository.count() == 0){
            Authority adminRole = authorityRepository.save(Authority.builder().role("ROLE_ADMIN").build());
            Authority guestRole = authorityRepository.save(Authority.builder().role("ROLE_GUEST").build());
            Authority customerRole = authorityRepository.save(Authority.builder().role("ROLE_CUSTOMER").build());

            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("1234"))
                    .authority(adminRole)
                    .build();

            User guest = User.builder()
                    .username("guest")
                    .password(passwordEncoder.encode("1234"))
                    .authority(guestRole)
                    .build();

            User customer = User.builder()
                    .username("bety")
                    .password(passwordEncoder.encode("1234"))
                    .authority(customerRole)
                    .build();


            userRepository.save(admin);
            userRepository.save(guest);
            userRepository.save(customer);
        }
    }


    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }
}
