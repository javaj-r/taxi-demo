package com.javid.taxi.controller.api.v1;

import com.javid.taxi.domain.Customer;
import com.javid.taxi.domain.base.Role;
import com.javid.taxi.domain.enumeration.model.CustomerDto;
import com.javid.taxi.service.CustomerService;
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
@RequestMapping("api/v1/customer")
public record CustomerController(CustomerService customerService) {

    @SecurityRequirement(name = SECURITY_NAME)
    @PreAuthorize("hasAnyRole(" + Role.CUSTOMER + ")")
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public CustomerDto getCurrent(Authentication authentication) {
        if (authentication.getPrincipal() instanceof Customer customer)
            return customerService.findById(customer.getId());

        throw new AuthorizationServiceException("Not Customer User!");
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @ModelAttribute CustomerDto customerDto) {
        customerService.save(customerDto);
    }

    @SecurityRequirement(name = SECURITY_NAME)
    @PreAuthorize("hasAnyRole(" + Role.CUSTOMER + ")")
    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(Authentication authentication, @Valid @ModelAttribute CustomerDto customerDto) {
        if (authentication.getPrincipal() instanceof Customer customer)
            customerService.update(customer.getId(), customerDto);
    }

    @SecurityRequirement(name = SECURITY_NAME)
    @PreAuthorize("hasAnyRole(" + Role.CUSTOMER + ")")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(HttpSession session, Authentication authentication) {
        if (authentication.getPrincipal() instanceof Customer customer) {
            customerService.deleteById(customer.getId());
            session.invalidate();
        }
    }
}