package com.javid.taxi.service.impl;

import com.javid.taxi.domain.CarRequest;
import com.javid.taxi.domain.Customer;
import com.javid.taxi.domain.Driver;
import com.javid.taxi.domain.enumeration.model.CarRequestCustomerDto;
import com.javid.taxi.domain.enumeration.model.CarRequestDriverDto;
import com.javid.taxi.domain.enumeration.model.CarRequestDto;
import com.javid.taxi.exception.BadRequestException;
import com.javid.taxi.repository.CarRequestRepository;
import com.javid.taxi.service.CarRequestService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class CarRequestServiceImpl implements CarRequestService {

    private final CarRequestRepository carRequestRepository;

    @Override
    public CarRequestDto findByIdAndCustomer(Long id, Customer customer) {
        return entityToDto(
                carRequestRepository
                        .findByIdAndCustomer(id, customer)
                        .orElseThrow(() -> new BadRequestException("Car request not found!"))
        );
    }

    @Override
    public List<CarRequestDto> findAllByCustomer(Customer customer) {
        return carRequestRepository.findAllByCustomer(customer)
                .stream()
                .map(this::entityToDto)
                .toList();
    }

    @Override
    public CarRequestDto findByIdAndDriver(Long id, Driver driver) {
        return entityToDto(
                carRequestRepository
                        .findByIdAndDriver(id, driver)
                        .orElseThrow(() -> new BadRequestException("Car request not found!"))
        );
    }

    @Override
    public List<CarRequestDto> findAllByDriver(Driver driver) {
        return carRequestRepository.findAllByDriver(driver)
                .stream()
                .map(this::entityToDto)
                .toList();
    }

    @Override
    public List<CarRequestDto> findAll() {
        return carRequestRepository.findAll()
                .stream()
                .map(this::entityToDto)
                .toList();
    }

    @Override
    public CarRequestDto findById(Long id) {
        return entityToDto(
                carRequestRepository
                        .findById(id)
                        .orElseThrow(() -> new BadRequestException("Car request not found!"))
        );
    }

    @Transactional
    @Override
    public void create(CarRequestCustomerDto dto) {
        carRequestRepository.save(new CarRequest()
                .setCustomer(dto.customer())
                .setOrderTime(dto.orderTime())
                .setDestination(dto.destination())
        );
    }

    @Transactional
    @Override
    public void update(Long id, CarRequestCustomerDto dto) {
        carRequestRepository.findByIdAndCustomer(id, dto.customer())
                .ifPresent(carRequest -> {
                    carRequest
                            .setOrderTime(dto.orderTime())
                            .setDestination(dto.destination());

                    carRequestRepository.save(carRequest);
                });
    }

    @Transactional
    @Override
    public void update(Long id, CarRequestDriverDto dto) {
        carRequestRepository.findByIdAndDriver(id, dto.driver())
                .ifPresent(carRequest -> {
                    carRequest
                            .setDriver(dto.driver())
                            .setPrice(dto.price());

                    carRequestRepository.save(carRequest);
                });
    }

    @Transactional
    @Override
    public void update(Long id, Customer customer, CarRequestDriverDto dto) {
        carRequestRepository.findByIdAndCustomer(id, customer)
                .ifPresent(carRequest -> {
                    carRequest
                            .setDriver(dto.driver())
                            .setPrice(dto.price());

                    carRequestRepository.save(carRequest);
                });
    }

    @Transactional
    @Override
    public void delete(Long id, Customer customer) {
        carRequestRepository.deleteByIdAndCustomer(id, customer);
    }

    private CarRequestDto entityToDto(CarRequest carRequest) {
        return new CarRequestDto(
                carRequest.getId(),
                carRequest.getCustomer().getId(),
                Objects.isNull(carRequest.getDriver()) ? null : carRequest.getDriver().getId(),
                carRequest.getOrderTime(),
                carRequest.getDestination(),
                carRequest.getPrice()
        );
    }
}
