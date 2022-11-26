package com.javid.taxi.domain;

import com.javid.taxi.domain.base.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Accessors(chain = true)
@NoArgsConstructor
@Getter
@Setter
@Entity
@DiscriminatorValue("3")
public class Driver extends User {

    @Column(name = "CAR_TYPE")
    private String carType;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<CarRequest> carRequests = new HashSet<>();
}
