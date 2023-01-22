package org.elsys.diplom.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.elsys.diplom.service.validation.UniqueEmail;
import org.elsys.diplom.service.validation.UniqueUsername;
import org.hibernate.validator.constraints.Length;

public class UserRegisterDTO {
    private Long id;

    @UniqueUsername
    @NotBlank(message = "Username is required!")
    @Length(min = 1, max = 30, message = "Username must be between 1 and 30 characters long")
    private String username;

    @UniqueEmail
    @Email(message = "Email should be valid!")
    @NotBlank(message = "Email is required!")
    private String email;

    @NotBlank(message = "Password is required!")
    @Length(min = 8, max = 30, message = "Password must be between 8 and 30 characters long")
    //@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\\\S+$).*$\n", message = "Password must contain at least one digit, one lowercase letter, one uppercase letter and no whitespace!")
    private String password;

    public UserRegisterDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
