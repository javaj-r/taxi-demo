package com.javid.taxi.service;

import com.javid.taxi.domain.enumeration.model.CustomerDto;

import java.util.List;

public interface CustomerService {

    void deleteById(Integer id);

    void update(Integer id, CustomerDto customerDto);

    void save(CustomerDto customerDto);

    CustomerDto findById(Integer id);

    List<CustomerDto> findAll();
}
