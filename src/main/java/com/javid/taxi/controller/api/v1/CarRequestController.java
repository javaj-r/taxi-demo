package com.javid.taxi.controller.api.v1;

import com.javid.taxi.domain.Admin;
import com.javid.taxi.domain.Customer;
import com.javid.taxi.domain.Driver;
import com.javid.taxi.domain.base.Role;
import com.javid.taxi.domain.enumeration.Hour;
import com.javid.taxi.domain.enumeration.model.CarRequestCustomerDto;
import com.javid.taxi.domain.enumeration.model.CarRequestDriverDto;
import com.javid.taxi.domain.enumeration.model.CarRequestDto;
import com.javid.taxi.service.CarRequestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

import static com.javid.taxi.Application.SECURITY_NAME;

@Slf4j
@RestController
@RequestMapping("api/v1/request")
public record CarRequestController(CarRequestService carRequestService) {

    @SecurityRequirement(name = SECURITY_NAME)
    @Operation(tags = {"Car Request"}, description = "Find all of requests depends on user role.")
    @PreAuthorize("hasAnyRole(" + Role.ADMIN + ", " + Role.CUSTOMER + ", " + Role.DRIVER + ")")
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public List<CarRequestDto> getAll(Authentication authentication) {
        var user = authentication.getPrincipal();

        if (user instanceof Customer customer)
            return carRequestService.findAllByCustomer(customer);

        if (user instanceof Driver driver)
            return carRequestService.findAllByDriver(driver);

        if (user instanceof Admin)
            return carRequestService.findAll();

        throw new AuthorizationServiceException("Unknown User!");
    }

    @SecurityRequirement(name = SECURITY_NAME)
    @Operation(tags = {"Car Request"}, description = "Find one request by id depends on user role.")
    @PreAuthorize("hasAnyRole(" + Role.ADMIN + ", " + Role.CUSTOMER + ", " + Role.DRIVER + ")")
    @GetMapping(
            value = "{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public CarRequestDto getOne(Authentication authentication, @PathVariable(name = "id") Long id) {
        var user = authentication.getPrincipal();

        if (user instanceof Customer customer)
            return carRequestService.findByIdAndCustomer(id, customer);

        if (user instanceof Driver driver)
            return carRequestService.findByIdAndDriver(id, driver);

        if (user instanceof Admin)
            return carRequestService.findById(id);

        throw new AuthorizationServiceException("Unknown User!");
    }

    @SecurityRequirement(name = SECURITY_NAME)
    @Operation(tags = {"Car Request"}, description = "Create new request for customer.")
    @PreAuthorize("hasAnyRole(" + Role.CUSTOMER + ")")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(
            Authentication authentication,
            @Valid
            @NotNull
            @RequestParam(name = "hour")
            Hour orderTime,
            @Valid
            @NotBlank
            @RequestParam(name = "destination")
            String destination) {

        if (authentication.getPrincipal() instanceof Customer customer) {
            var dto = new CarRequestCustomerDto(
                    LocalTime.of(orderTime.value(), 0),
                    customer,
                    destination
            );
            carRequestService.create(dto);
        }
    }

    @SecurityRequirement(name = SECURITY_NAME)
    @Operation(tags = {"Car Request"}, description = "Update customers request.")
    @PreAuthorize("hasAnyRole(" + Role.CUSTOMER + ")")
    @PatchMapping("customer")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(
            Authentication authentication,
            @Valid
            @NotNull
            @RequestParam(name = "requestId")
            Long id,
            @Valid
            @NotNull
            @RequestParam(name = "hour")
            Hour orderTime,
            @Valid
            @NotBlank
            @RequestParam(name = "destination")
            String destination) {
        if (authentication.getPrincipal() instanceof Customer customer) {
            var dto = new CarRequestCustomerDto(
                    LocalTime.of(orderTime.value(), 0),
                    customer,
                    destination
            );
            carRequestService.update(id, dto);
        }
    }

    @SecurityRequirement(name = SECURITY_NAME)
    @Operation(tags = {"Car Request"}, description = "Update drivers jub of car request.")
    @PreAuthorize("hasAnyRole(" + Role.DRIVER + ")")
    @PatchMapping("driver")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(
            Authentication authentication,
            @Valid
            @NotNull
            @RequestParam(name = "requestId")
            Long id,
            @Valid
            @NotNull
            @Digits(integer = 15, fraction = 2)
            @DecimalMin(value = "0.0")
            @RequestParam(name = "price")
            BigDecimal price
    ) {
        if (authentication.getPrincipal() instanceof Driver driver) {
            var dto = new CarRequestDriverDto(
                    id,
                    driver,
                    price
            );
            carRequestService.update(id, dto);
        }
    }

    @SecurityRequirement(name = SECURITY_NAME)
    @Operation(tags = {"Car Request"}, description = "Reject car offered by driver for customer's request.")
    @PatchMapping("reject")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void rejectDriver(
            Authentication authentication,
            @Valid
            @NotNull
            @RequestParam(name = "requestId")
            Long id
    ) {
        if (authentication.getPrincipal() instanceof Customer customer) {
            var dto = new CarRequestDriverDto(
                    id,
                    null,
                    new BigDecimal(0)
            );
            carRequestService.update(id, customer, dto);
        }
    }

    @SecurityRequirement(name = SECURITY_NAME)
    @Operation(tags = {"Car Request"}, description = "Delete customer's request by id.")
    @PreAuthorize("hasAnyRole(" + Role.CUSTOMER + ")")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            Authentication authentication,
            @Valid
            @NotNull
            @RequestParam(name = "requestId")
            Long id
    ) {
        if (authentication.getPrincipal() instanceof Customer customer)
            carRequestService.delete(id, customer);
    }

}