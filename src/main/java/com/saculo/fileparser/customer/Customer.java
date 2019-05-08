package com.saculo.fileparser.customer;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Customer {

    private Long id;
    private String name;
    private String surname;
    private Integer age;
    private List<Contact> contacts;

    public Customer(String name, String surname, Integer age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }
}
