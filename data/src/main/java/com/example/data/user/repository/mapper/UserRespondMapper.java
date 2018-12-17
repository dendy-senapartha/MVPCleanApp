package com.example.data.user.repository.mapper;

import com.example.data.user.repository.source.network.response.SignInResponse;
import com.example.data.user.repository.source.network.response.SignUpResponse;
import com.example.domain.signin.SignInResult;
import com.example.domain.signin.SignUpResult;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserRespondMapper {

    @Inject
    public UserRespondMapper() {

    }

    public SignInResult transform(SignInResponse response) {
        SignInResult result = null;
        if(response !=null)
        {
            //TODO : need to rework on error handling
            if(response.userEntity.uid !=null)
            {
                result = new SignInResult(response.userEntity.uid, response.userEntity.name, response.userEntity.email);
                result.exception = "";
            }
            else
            {
                result =new SignInResult();
                result.exception = response.exception;
            }
        }
        return result;
    }

    public SignUpResult transform(SignUpResponse response) {
        SignUpResult result = null;
        if(response !=null)
        {
            //TODO : need to rework on error handling
            if(response.userEntity.uid !=null)
            {
                result = new SignUpResult(response.userEntity.uid, response.userEntity.name, response.userEntity.email);
                result.exception = "";
            }
            else
            {
                result =new SignUpResult();
                result.exception = response.exception;
            }
        }
        return result;
    }

}
