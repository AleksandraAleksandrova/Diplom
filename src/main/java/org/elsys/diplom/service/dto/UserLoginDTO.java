package org.elsys.diplom.service.dto;

public class UserLoginDTO {
    private String username;
    private String password;

    public UserLoginDTO(){}

    public UserLoginDTO(String email, String password) {
        this.username = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
