package com.javid.taxi.service.impl;

import com.javid.taxi.domain.Customer;
import com.javid.taxi.domain.base.Role;
import com.javid.taxi.domain.enumeration.model.CustomerDto;
import com.javid.taxi.exception.InternalServerException;
import com.javid.taxi.repository.CustomerRepository;
import com.javid.taxi.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserService userService;
    private final PasswordEncoder encoder;

    @Override
    @Transactional
    public void deleteById(Integer id) {
        customerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Integer id, CustomerDto customerDto) {
        customerRepository.findById(id)
                .ifPresent(customer -> {
                    if (!customer.getPhoneNumber().equals(customerDto.phoneNumber()))
                        userService.existsPhoneNumber(customerDto.phoneNumber());

                    customer
                            .setPassword(customerDto.password())
                            .setFirstname(customerDto.firstname())
                            .setLastname(customerDto.lastname())
                            .setPhoneNumber(customerDto.phoneNumber())
                            .setGender(customerDto.gender());

                    customerRepository.save(customer);
                });
    }

    @Override
    @Transactional
    public void save(CustomerDto customerDto) {
        userService.existsPhoneNumber(customerDto.phoneNumber());
        customerRepository.save((Customer) new Customer()
                .setPhoneNumber(customerDto.phoneNumber())
                .setPassword(encoder.encode(customerDto.password()))
                .setFirstname(customerDto.firstname())
                .setLastname(customerDto.lastname())
                .setGender(customerDto.gender())
                .addRole(Role.CUSTOMER)
                .setEnabled(true)
        );
    }

    @Override
    public CustomerDto findById(Integer id) {
        var driver = findEntityById(id);
        return entityToDto(driver);
    }

    @Override
    public List<CustomerDto> findAll() {

        return findAllEntity()
                .stream().map(this::entityToDto)
                .toList();
    }

    private List<Customer> findAllEntity() {
        return customerRepository.findAll();
    }

    private CustomerDto entityToDto(Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getPhoneNumber(),
                "*".repeat(8),
                customer.getFirstname(),
                customer.getLastname(),
                customer.getGender()
        );
    }

    private Customer findEntityById(Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(InternalServerException::new);
    }
}
