package com.dataverse.authservice.services;

import com.dataverse.authservice.configs.security.BasicSecurityConfig;
import com.dataverse.authservice.dtos.UserRegistrationDto;
import com.dataverse.authservice.entities.User;
import com.dataverse.authservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BasicSecurityConfig basicSecurityConfig;

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    user.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority(role.getName()))
                            .toList()
            );
        }
        throw new UsernameNotFoundException(String.format("User %s not found", username));
    }

    public User createNewUser(UserRegistrationDto userRegistrationDto) {
        User user = new User();
        user.setUsername(userRegistrationDto.getUsername());
        user.setFirstName(userRegistrationDto.getFirstName());
        user.setSecondName(userRegistrationDto.getSecondName());
        user.setPassword(basicSecurityConfig.passwordEncoder().encode(userRegistrationDto.getPassword()));
        user.setEmail(userRegistrationDto.getEmail());
        user.setPhone(userRegistrationDto.getPhone());
        user.setAddress(userRegistrationDto.getAddress());
        return userRepository.save(user);
    }
}
