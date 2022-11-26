package com.javid.taxi.domain;

import com.javid.taxi.domain.base.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@NoArgsConstructor
@Getter
@Setter
@Entity
@DiscriminatorValue("1")
public class Admin extends User {
}
