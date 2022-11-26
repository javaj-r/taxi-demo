package com.javid.taxi.service.impl;

import com.javid.taxi.domain.Driver;
import com.javid.taxi.domain.base.Role;
import com.javid.taxi.domain.enumeration.model.DriverDto;
import com.javid.taxi.exception.InternalServerException;
import com.javid.taxi.repository.DriverRepository;
import com.javid.taxi.service.DriverService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final UserService userService;
    private final PasswordEncoder encoder;

    private final String passwordHint;

    public DriverServiceImpl(DriverRepository driverRepository, UserService userService,
                             PasswordEncoder encoder, @Value("${password.hint:###}") String passwordHint) {
        this.driverRepository = driverRepository;
        this.userService = userService;
        this.encoder = encoder;
        this.passwordHint = passwordHint;
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        driverRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Integer id, DriverDto driverDto) {
        driverRepository.findById(id)
                .ifPresent(driver -> {
                    if (!driver.getPhoneNumber().equals(driverDto.phoneNumber()))
                        userService.existsPhoneNumber(driverDto.phoneNumber());

                    driver
                            .setCarType(driverDto.carType())
                            .setPassword(driverDto.password())
                            .setFirstname(driverDto.firstname())
                            .setLastname(driverDto.lastname())
                            .setPhoneNumber(driverDto.phoneNumber())
                            .setGender(driverDto.gender());

                    driverRepository.save(driver);
                });
    }

    @Override
    @Transactional
    public void save(DriverDto driverDto) {
        userService.existsPhoneNumber(driverDto.phoneNumber());
        driverRepository.save((Driver) new Driver()
                .setCarType(driverDto.carType())
                .setPhoneNumber(driverDto.phoneNumber())
                .setPassword(encoder.encode(driverDto.password()))
                .setFirstname(driverDto.firstname())
                .setLastname(driverDto.lastname())
                .setGender(driverDto.gender())
                .addRole(Role.DRIVER)
                .setEnabled(true)
        );
    }

    @Override
    public DriverDto findById(Integer id) {
        var driver = findEntityById(id);

        return entityToDto(driver);
    }

    @Override
    public List<DriverDto> findAll() {
        return findAllEntity()
                .stream().map(this::entityToDto)
                .toList();
    }

    private List<Driver> findAllEntity() {
        return driverRepository.findAll();
    }

    private DriverDto entityToDto(Driver driver) {
        return new DriverDto(
                driver.getId(),
                driver.getPhoneNumber(),
                passwordHint,
                driver.getFirstname(),
                driver.getLastname(),
                driver.getGender(),
                driver.getCarType()
        );
    }

    private Driver findEntityById(Integer id) {
        return driverRepository.findById(id)
                .orElseThrow(InternalServerException::new);
    }
}
