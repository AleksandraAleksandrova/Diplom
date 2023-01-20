package org.elsys.diplom.service.dto;

import jakarta.validation.constraints.NotEmpty;


public class UserDTO {
    private Long id;
    @NotEmpty(message = "Username must not be empty!")
    private String username;

    public UserDTO() {
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

}
