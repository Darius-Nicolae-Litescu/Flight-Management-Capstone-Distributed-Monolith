package org.darius.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PassengerDetails {
    private String firstName;
    private String lastName;
    private Integer age;
    private String gender;
    private String phoneNumber;
}
