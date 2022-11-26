package com.javid.taxi.service;

import com.javid.taxi.domain.enumeration.model.CustomerDto;

public interface AdminService {

    void update(Integer id, CustomerDto customerDto);

    void save(CustomerDto customerDto);
}
