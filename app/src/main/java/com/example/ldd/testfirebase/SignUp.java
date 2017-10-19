package com.example.ldd.testfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity implements View.OnClickListener{
    Button btnRegister,btnLogin;
    EditText edtEmail,edtPass;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPass = (EditText)findViewById(R.id.edtPass);

        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }
    private void register(){
        String email = edtEmail.getText().toString();
        String password = edtPass.getText().toString();
        if (!email.isEmpty() && !password.isEmpty()) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignUp.this, "Đăng ký thành công",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUp.this,SignIn.class));
                        } else {
                            Toast.makeText(SignUp.this, "Vui lòng nhập đúng định dạng email và mật khẩu trên 6 ký tự",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }else{
            Toast.makeText(SignUp.this, "Vui lòng nhập đầy dủ",
                    Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegister:
                register();

                break;
            case R.id.btnLogin:
                startActivity(new Intent(this,SignIn.class));
                break;

        }
    }
}
