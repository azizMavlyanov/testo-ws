package com.testows.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRequestModel {
    @NotNull(message = "First name cannot be null")
    private String firstName;
    private String lastName;
    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email address")
    private String email;
    @NotNull(message = "Password cannot be null")
    @Size(min = 4, max = 16, message = "Password length must be between 4 and 16 characters long.")
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
