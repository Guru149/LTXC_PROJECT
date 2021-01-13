package com.example.ltxc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Register extends AppCompatActivity {
    EditText inpemail,inppassword,inpphonenum;
    Button Registerbtn;
    String StringEmail,Stringpassword,Stringphonenum;
    FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Auth=FirebaseAuth.getInstance();

        inpemail=findViewById(R.id.inputEmail);
        inpphonenum=findViewById(R.id.phonenumber);
        inppassword=findViewById(R.id.inputPassword);
        Registerbtn=findViewById(R.id.btnRegister);


        Registerbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                StringEmail=inpemail.getText().toString().trim();
                Stringpassword=inppassword.getText().toString().trim();
                Stringphonenum=inpphonenum.getText().toString().trim();

                if (TextUtils.isEmpty(StringEmail))
                {
                    inpemail.setError("*Required");
                }
                else if(TextUtils.isEmpty(Stringpassword))
                {
                    inppassword.setError("Enter password");

                }
               else if( Stringpassword.length() < 8)
                {
                    inppassword.setError("Enter password at least 8 charectors");

                }
               else if (Stringphonenum.length()<10)
                {
                    inpphonenum.setError("Enter a valid Phone Number");
                }


                Auth.createUserWithEmailAndPassword(StringEmail,Stringpassword).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            startActivity(new Intent(Register.this,MainActivity.class));
                            FirebaseUser user=Auth.getCurrentUser();
                            Toast.makeText(Register.this, "createUserWithEmail:success", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });


    }

 /*   @Override
    protected void onStart()
    {
        if (Auth.getCurrentUser()!=null)
    {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
        super.onStart();
    }*/
}