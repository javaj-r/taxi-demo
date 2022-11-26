package com.javid.taxi.domain.enumeration.model;

import com.javid.taxi.domain.Driver;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CarRequestDriverDto(
        @NotNull
        Long id,

        Driver driver,

        @Digits(integer = 15, fraction = 2)
        @DecimalMin(value = "0.0")
        BigDecimal price) {
}
