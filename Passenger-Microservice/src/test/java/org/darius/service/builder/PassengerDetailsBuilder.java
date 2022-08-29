package org.darius.service.builder;

import org.darius.entity.PassengerDetails;

public class PassengerDetailsBuilder {
    private String firstName;
    private String lastName;
    private Integer age;
    private String gender;
    private String phoneNumber;

    public PassengerDetailsBuilder random() {
        this.firstName = "firstName";
        this.lastName = "lastName";
        this.age = Math.abs((int) (Math.random() * 10));
        this.gender = "gender";
        this.phoneNumber = "phoneNumber";
        return this;
    }

    public PassengerDetailsBuilder defaultValues() {
        PassengerDetailsBuilder passengerDetailsBuilder = new PassengerDetailsBuilder();
        return passengerDetailsBuilder;
    }

    public PassengerDetailsBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public PassengerDetailsBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public PassengerDetailsBuilder age(Integer age) {
        this.age = age;
        return this;
    }

    public PassengerDetailsBuilder gender(String gender){
        this.gender = gender;
        return this;
    }

    public PassengerDetailsBuilder phoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
        return this;
    }

    public PassengerDetails build(){
        return new PassengerDetails(firstName, lastName, age, gender, phoneNumber);
    }

}