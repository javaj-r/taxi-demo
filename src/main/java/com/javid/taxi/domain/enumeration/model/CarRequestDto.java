package com.javid.taxi.domain.enumeration.model;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalTime;

public record CarRequestDto(
        Long id,
        Integer customerId,
        Integer driverId,

        LocalTime orderTime,

        @NotBlank
        String destination,

        @Digits(integer = 15, fraction = 2)
        @DecimalMin(value = "0.0")
        BigDecimal price) {
}
