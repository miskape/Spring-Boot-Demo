package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class Person {

    private final UUID id;
    @NotBlank
    private String firstName;
    @NotBlank
    private final String lastName;
    private final int age;

    public Person(@JsonProperty("id") UUID id, @JsonProperty("firstName") String firstName,
                  @JsonProperty("lastName") String lastName, @JsonProperty("age") int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(){
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }
}
