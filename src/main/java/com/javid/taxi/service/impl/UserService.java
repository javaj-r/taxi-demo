package com.javid.taxi.service.impl;

import com.javid.taxi.exception.BadRequestException;
import com.javid.taxi.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public record UserService(UserRepository userRepository,
                          PasswordEncoder passwordEncoder) implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByPhoneNumber(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    public void existsPhoneNumber(String phoneNumber) {
        if (userRepository().existsByPhoneNumber(phoneNumber))
            throw new BadRequestException("Phone number already exists!");
    }

}
