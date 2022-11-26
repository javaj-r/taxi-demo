package com.javid.taxi.service;

import com.javid.taxi.domain.enumeration.model.DriverDto;

import java.util.List;

public interface DriverService {

    void deleteById(Integer id);

    void update(Integer id, DriverDto driverDto);

    void save(DriverDto driverDto);

    DriverDto findById(Integer id);


    List<DriverDto> findAll();
}
