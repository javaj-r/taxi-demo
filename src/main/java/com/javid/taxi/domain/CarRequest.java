package com.javid.taxi.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Objects;

@Accessors(chain = true)
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "CAR_REQUEST")
public class CarRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "DRIVER_ID", updatable = false, insertable = false, nullable = false)
    private Driver driver;

    @Column(name = "ORDER_TIME")
    private LocalTime orderTime;

    @Column(name = "DESTINATION")
    private String destination;

    @Digits(integer = 15, fraction = 2)
    @Column(name = "PRICE")
    private BigDecimal price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarRequest that = (CarRequest) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
