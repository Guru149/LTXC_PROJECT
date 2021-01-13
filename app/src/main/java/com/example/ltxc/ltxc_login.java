package com.example.ltxc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class ltxc_login extends AppCompatActivity {
    private static final int RC_SIGN_IN = 9001;
    TextView gotoregister;
    EditText inpemail, inppassword;
    Button loginbtn;
    SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth Auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ltxc_login);
        gotoregister = findViewById(R.id.gotoRegister);
        inpemail = findViewById(R.id.inputEmail);
        inppassword = findViewById(R.id.inputPassword);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
   mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
signInButton=findViewById(R.id.sign_in_button);
        Auth = FirebaseAuth.getInstance();

        signInButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        signIn();
    }
});

        loginbtn = findViewById(R.id.btnLogin);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String StringEmail = inpemail.getText().toString().trim();
                String Stringpassword = inppassword.getText().toString().trim();

                if (TextUtils.isEmpty(StringEmail)) {
                    inpemail.setError("*Required");
                }
                if (TextUtils.isEmpty(Stringpassword)) {
                    inppassword.setError("Enter password");
                }
                Auth.signInWithEmailAndPassword(StringEmail, Stringpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            startActivity(new Intent(ltxc_login.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(ltxc_login.this, "Create Account or Check your internet connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        gotoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(ltxc_login.this, Register.class);
                    startActivity(intent);
            }
        });
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, "LOGIN FAILed", Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        Auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(ltxc_login.this, "LOGIN SUCCESSFULL", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = Auth.getCurrentUser();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(ltxc_login.this, "LOGIN FAIL", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }


  /*  @Override
    protected void onStart() {
        if (Auth.getCurrentUser()!=null)
    {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        Toast.makeText(this, "YOU ALREADY SIGNED IN", Toast.LENGTH_SHORT).show();
        finish();
    }
        super.onStart();
    }*/
}