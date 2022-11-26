package com.javid.taxi.domain.enumeration.model;

import com.javid.taxi.domain.enumeration.Gender;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

public record CustomerDto(
        @Null
        Integer id,

        @NotBlank
        @Size(max = 13, min = 7)
        String phoneNumber,

        @NotBlank
        @Size(min = 8, max = 30)
        String password,

        @NotBlank
        String firstname,

        @NotBlank
        String lastname,

        @NotNull
        Gender gender) {}
