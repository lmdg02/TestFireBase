package com.example.ldd.testfirebase;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignIn extends AppCompatActivity implements View.OnClickListener{

    Button btnRegister,btnLogin;
    EditText edtEmail,edtPass;

    private FirebaseAuth mAuth;
    private LoginButton btnLoginFB;
    private CallbackManager callbackManager;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPass = (EditText)findViewById(R.id.edtPass);

        mAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();
        btnLoginFB = (LoginButton)findViewById(R.id.btnLoginFB);

        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        loginFB();

    }
    // Start login with FaceBook -----------------------
    private void loginFB(){
        btnLoginFB.setReadPermissions("email","public_profile");
        btnLoginFB.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("SignIn", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }
            @Override
            public void onCancel() {
                Log.d("SignIn", "facebook:onCancel");
            }
            @Override
            public void onError(FacebookException error) {
                Log.d("SignIn", "facebook:onError", error);
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        Log.d("SignIn", "handleFacebookAccessToken:" + accessToken);
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d("Sign", "signInWithCredential:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    startActivity(new Intent(SignIn.this,MainActivity.class));
                } else {
                    Log.w("Sign", "signInWithCredential:failure", task.getException());
                    Toast.makeText(SignIn.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
    // ----------------------- End login with FaceBook
    // Start login with Email/Password -----------------------
    private void login(){
        String email = edtEmail.getText().toString();
        String password = edtPass.getText().toString();
        if (!email.isEmpty() && !password.isEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(SignIn.this,MainActivity.class));
                                Toast.makeText(SignIn.this, "Đăng nhập thành công",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignIn.this, "Sai mật khẩu hoặc tài khoản",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(SignIn.this, "Vui lòng nhập đầy dủ",
                    Toast.LENGTH_SHORT).show();
        }
    }
    // ----------------------- End login with Email/Password
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegister:
                startActivity(new Intent(this,SignUp.class));
                break;
            case R.id.btnLogin:
                login();
                break;

        }
    }
}
