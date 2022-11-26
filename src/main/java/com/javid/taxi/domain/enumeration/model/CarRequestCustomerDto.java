package com.javid.taxi.domain.enumeration.model;

import com.javid.taxi.domain.Customer;

import javax.validation.constraints.NotBlank;
import java.time.LocalTime;

public record CarRequestCustomerDto(
        LocalTime orderTime,

        Customer customer,

        @NotBlank
        String destination) {
}
