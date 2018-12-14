package com.example.data.user.repository.source.network.response;

import com.example.data.user.UserEntity;

public class SignUpResponse {
    public UserEntity userEntity;

    public SignUpResponse() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public SignUpResponse(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
