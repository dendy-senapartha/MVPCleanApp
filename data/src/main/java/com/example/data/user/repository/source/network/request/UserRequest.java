package com.example.data.user.repository.source.network.request;

public class UserRequest {
    public String email;
    public String password;

    public UserRequest(String email, String password)
    {
        this.password = password;
        this.email = email;
    }
}
