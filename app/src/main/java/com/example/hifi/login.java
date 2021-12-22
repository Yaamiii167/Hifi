package com.example.hifi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class login extends AppCompatActivity {

    EditText emailbox,passwordbox;
    Button loginbtn,createbtn;

    FirebaseAuth auth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailbox = findViewById(R.id.emailBox);
        passwordbox = findViewById(R.id.passwordBox);

        loginbtn = findViewById(R.id.loginBtn);
        createbtn = findViewById(R.id.createBtn);

        auth= FirebaseAuth.getInstance();
        dialog=new ProgressDialog(this);
        dialog.setMessage("Please wait..");


        loginbtn.setOnClickListener ((v) -> {

            dialog.show();
            String email,pass;
            email=emailbox.getText().toString();
            pass=passwordbox.getText().toString();

            auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(task -> {
                dialog.dismiss();
                if(task.isSuccessful()){
                    Toast.makeText(this, "Logged In", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(login.this, dashboard.class));

                    }
                else {
                    Toast.makeText(login.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        });
        createbtn.setOnClickListener(v -> startActivity(new Intent(login.this,signup.class)));
    }
}