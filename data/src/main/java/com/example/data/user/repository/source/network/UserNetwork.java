package com.example.data.user.repository.source.network;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.data.Serializer;
import com.example.data.user.UserEntity;
import com.example.data.user.repository.source.UserEntityData;
import com.example.data.user.repository.source.network.request.UserRequest;
import com.example.data.user.repository.source.network.response.SignInResponse;
import com.example.data.user.repository.source.network.response.SignUpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class UserNetwork {

    private final Context context;

    private final Serializer serializer;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Inject
    public UserNetwork(Context context, Serializer serializer) {
        this.context = context;
        this.serializer = serializer;
    }

    public void init()
    {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    public SignInResponse SignIn(UserRequest userRequest) throws NullPointerException
    {
        SignInResponse res = null;
        Task<AuthResult> task = mAuth.signInWithEmailAndPassword(userRequest.email, userRequest.password);
        if (task.isSuccessful()) {
            FirebaseUser firebaseUser = task.getResult().getUser();

            UserEntity user = new UserEntity(firebaseUser.getUid(),
                    usernameFromEmail(firebaseUser.getEmail()), firebaseUser.getEmail());
            res = new SignInResponse(user);
            mDatabase.child("users").child(firebaseUser.getUid()).setValue(user);
        }
        return res;
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    public boolean CheckSignIn() {
        boolean isSignIn= false;
        if (mAuth.getCurrentUser() != null) {
            isSignIn = true;
        }
        return isSignIn;
    }

    public SignUpResponse SignUp(UserRequest userRequest) {
        SignUpResponse res = null;
        Task<AuthResult> task = mAuth.createUserWithEmailAndPassword(userRequest.email, userRequest.password);
        if (task.isSuccessful()) {
            FirebaseUser firebaseUser = task.getResult().getUser();

            UserEntity user = new UserEntity(firebaseUser.getUid(),
                    usernameFromEmail(firebaseUser.getEmail()), firebaseUser.getEmail());
            res = new SignUpResponse(user);
            mDatabase.child("users").child(firebaseUser.getUid()).setValue(user);
        }
        return res;
    }
}
