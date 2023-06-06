package com.example.pbl5demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    EditText edt_mail ;
    EditText edt_pass;
    Button btn_Login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        edt_mail = findViewById(R.id.edt_email);
        edt_pass = findViewById(R.id.edt_pass);
        btn_Login = findViewById(R.id.btn_Login);

        mAuth = FirebaseAuth.getInstance();
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailValue = edt_mail.getText().toString().trim();
                String passValue = edt_pass.getText().toString().trim();
                if(!(emailValue.equals("") && passValue.equals(""))){
                    login(emailValue,passValue);
                    passValue ="";
                }
                else{
                    Toast.makeText(MainActivity.this,"Please enter your email and password!",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

    }
    private void login(String email, String pass){
        mAuth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent i = new Intent(MainActivity.this,main.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("email",email);
                            i.putExtras(bundle);
                            startActivity(i);
                        }
                        else {
                            Toast.makeText(MainActivity.this,"Login Fail",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}