package com.javid.taxi.repository;

import com.javid.taxi.domain.CarRequest;
import com.javid.taxi.domain.Customer;
import com.javid.taxi.domain.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarRequestRepository extends JpaRepository<CarRequest, Long> {

    Optional<CarRequest> findByIdAndCustomer(Long id, Customer customer);

    List<CarRequest> findAllByCustomer(Customer customer);

    Optional<CarRequest> findByIdAndDriver(Long id, Driver driver);

    List<CarRequest> findAllByDriver(Driver driver);

    void deleteByIdAndCustomer(Long id, Customer customer);
}