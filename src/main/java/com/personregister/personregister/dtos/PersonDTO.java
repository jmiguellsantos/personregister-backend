package com.personregister.personregister.dtos;

import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class PersonDTO {

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @CPF
    private String cpf;

    @NotNull
    @Past
    private LocalDate birthDate;

    @NotNull
    @Email
    private String email;

    private String phoneNumber;


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
