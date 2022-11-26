package com.javid.taxi.service.impl;

import com.javid.taxi.domain.enumeration.model.CustomerDto;
import com.javid.taxi.repository.AdminRepository;
import com.javid.taxi.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final UserService userService;
    private final PasswordEncoder encoder;

    @Override
    public void update(Integer id, CustomerDto customerDto) {

    }

    @Override
    public void save(CustomerDto customerDto) {

    }
}
