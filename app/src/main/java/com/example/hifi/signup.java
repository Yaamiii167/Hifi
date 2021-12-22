package com.example.hifi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class signup extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore database;





    EditText emailbox,passwordbox,usernamebox;
    Button signupbtn,login2btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();



        emailbox = findViewById(R.id.emailBox2);
        passwordbox = findViewById(R.id.passwordBox2);
        usernamebox= findViewById(R.id.usernameBox);

        signupbtn = findViewById(R.id.signupBtn);
        login2btn = findViewById(R.id.login2Btn);

                //creating user a/c by giving email & pass via signup btn to "auth" firebase variable
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,pass,name;
                email = emailbox.getText().toString();
                pass  = passwordbox.getText().toString();
                name  = usernamebox.getText().toString();

                User user = new User();
                user.setEmail(email);
                user.setPass(pass);
                user.setName(name);



                auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            //success continue your desired task
                        database.collection("users").document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                startActivity(new Intent(signup.this,login.class));
                            }
                        });

                            Toast.makeText(signup.this, "Account is created!", Toast.LENGTH_SHORT).show();


                        }
                        else{
                            Toast.makeText(signup.this, Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                    }
                });
            }
        });

            //redirecting from AlreadyHaveAnAccount? btn to login screen
        login2btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                startActivity(new Intent (signup.this,login.class));
            }
        });



    }
}