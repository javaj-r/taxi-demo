package com.javid.taxi.controller.api.v1;

import com.javid.taxi.domain.Driver;
import com.javid.taxi.domain.base.Role;
import com.javid.taxi.domain.enumeration.model.DriverDto;
import com.javid.taxi.service.DriverService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.javid.taxi.Application.SECURITY_NAME;

@RestController
@RequestMapping("api/v1/driver")
public record DriverController(DriverService driverService) {

    @SecurityRequirement(name = SECURITY_NAME)
    @PreAuthorize("hasAnyRole(" + Role.DRIVER + ")")
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public DriverDto getCurrent(Authentication authentication) {
        if (authentication.getPrincipal() instanceof Driver driver)
            return driverService.findById(driver.getId());

        throw new AuthorizationServiceException("Not Driver User!");
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @ModelAttribute DriverDto driverDto) {
        driverService.save(driverDto);
    }

    @SecurityRequirement(name = SECURITY_NAME)
    @PreAuthorize("hasAnyRole(" + Role.DRIVER + ")")
    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(Authentication authentication, @Valid @ModelAttribute DriverDto driverDto) {
        if (authentication.getPrincipal() instanceof Driver driver)
            driverService.update(driver.getId(), driverDto);
    }

    @SecurityRequirement(name = SECURITY_NAME)
    @PreAuthorize("hasAnyRole(" + Role.DRIVER + ")")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(HttpSession session, Authentication authentication) {
        if (authentication.getPrincipal() instanceof Driver driver) {
            driverService.deleteById(driver.getId());
            session.invalidate();
        }
    }
}