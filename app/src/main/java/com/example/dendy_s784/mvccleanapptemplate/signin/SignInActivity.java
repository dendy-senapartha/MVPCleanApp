package com.example.dendy_s784.mvccleanapptemplate.signin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dendy_s784.mvccleanapptemplate.R;
import com.example.dendy_s784.mvccleanapptemplate.base.BaseActivity;
import com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.component.DaggerSignInActivityComponent;
import com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.component.SignInActivityComponent;
import com.example.dendy_s784.mvccleanapptemplate.dependecyinjection.modules.SignInActivityModule;
import com.example.dendy_s784.mvccleanapptemplate.mainactivity.MainActivity;
import com.example.dendy_s784.mvccleanapptemplate.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;

import butterknife.BindView;

public class SignInActivity extends BaseActivity implements SignInContract.View{

    //private DatabaseReference mDatabase;
    //private FirebaseAuth mAuth;

    private static final String TAG = "SignInActivity";

    @BindView(R.id.fieldEmail)
    EditText mEmailField;
    @BindView(R.id.fieldPassword)
    EditText mPasswordField;
    @BindView(R.id.buttonSignIn)
    Button mSignInButton;
    @BindView(R.id.buttonSignUp)
    Button mSignUpButton;

    @Inject
    SignInContract.Presenter presenter;

    private SignInActivityComponent component;

    @Override
    public int getLayout() {
        return R.layout.activity_signin;
    }

    @Override
    public void init() {
        //mDatabase = FirebaseDatabase.getInstance().getReference();
       // mAuth = FirebaseAuth.getInstance();
        initComponent();

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        presenter.IsSignIn();
        // Check auth on Activity start
       // if (mAuth.getCurrentUser() != null) {
            //onAuthSuccess(mAuth.getCurrentUser());
       // }
    }

    private void initComponent() {
        if (component == null) {
            component = DaggerSignInActivityComponent.builder()
                    .applicationComponent(getApplicationComponent())
                    .signInActivityModule(new SignInActivityModule(this))
                    .build();
        }
        component.inject(this);

        registerPresenter(presenter);
    }

    @Override
    protected void configToolbar() {

    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(mEmailField.getText().toString())) {
            mEmailField.setError("Required");
            result = false;
        } else {
            mEmailField.setError(null);
        }

        if (TextUtils.isEmpty(mPasswordField.getText().toString())) {
            mPasswordField.setError("Required");
            result = false;
        } else {
            mPasswordField.setError(null);
        }

        return result;
    }

    private void signIn() {
        Log.d(TAG, "signIn");
        if (!validateForm()) {
            return;
        }

        //showProgressDialog();
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();
        presenter.SignIn(email, password);
    }

    private void signUp() {
        Log.d(TAG, "signUp");
        if (!validateForm()) {
            return;
        }

        //showProgressDialog();
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();
        presenter.SignUp(email, password);
    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());

        // Write new user
        writeNewUser(user.getUid(), username, user.getEmail());

        // Go to MainActivity
        startActivity(new Intent(SignInActivity.this, MainActivity.class));
        finish();
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    // [START basic_write]
    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);

        //mDatabase.child("users").child(userId).setValue(user);
    }
    // [END basic_write]

    @Override
    public void OnSignInSuccess() {
        // Go to MainActivity
        startActivity(new Intent(SignInActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void OnSignOutSuccess() {

    }

}
