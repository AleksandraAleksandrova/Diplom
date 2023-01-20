package org.elsys.diplom.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    @NotBlank(message = "Username is required!")
    @Length(min = 1, max = 50, message = "Username must be between 1 and 50 characters long")
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Email should be valid!")
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "Password is required!")
    @Length(min = 1, max = 30, message = "Password must be between 1 and 30 characters long")
    // @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character and no whitespace")
    private String password;

    public User() {
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
