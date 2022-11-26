package com.javid.taxi.service;

import com.javid.taxi.domain.Customer;
import com.javid.taxi.domain.Driver;
import com.javid.taxi.domain.enumeration.model.CarRequestCustomerDto;
import com.javid.taxi.domain.enumeration.model.CarRequestDriverDto;
import com.javid.taxi.domain.enumeration.model.CarRequestDto;

import java.util.List;

public interface CarRequestService {

    CarRequestDto findByIdAndCustomer(Long id, Customer customer);

    List<CarRequestDto> findAllByCustomer(Customer customer);

    CarRequestDto findByIdAndDriver(Long id, Driver driver);

    List<CarRequestDto> findAllByDriver(Driver driver);

    List<CarRequestDto> findAll();

    CarRequestDto findById(Long id);

    void create(CarRequestCustomerDto dto);

    void update(Long id, CarRequestCustomerDto dto);

    void update(Long id, CarRequestDriverDto dto);

    void update(Long id, Customer customer, CarRequestDriverDto dto);

    void delete(Long id, Customer customer);
}
