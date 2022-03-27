package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.net.URI;

public class Email extends AppCompatActivity {
    ImageView imageView;
    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        Intent intent = getIntent();
        String name = intent.getStringExtra("NAME");
        String email = intent.getStringExtra("EMAIL");

        TextView nameTxt = findViewById(R.id.textName);
        TextView emailTxt = findViewById(R.id.textEmail);
        EditText passTxt = findViewById(R.id.textPass);
        Button signBtn = findViewById(R.id.btnSignIn);
        Button signOutBtn = findViewById(R.id.btnSignOut);
        imageView = findViewById(R.id.imageView);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
            Glide.with(this).load(String.valueOf(personPhoto)).into(imageView);
            nameTxt.setText("Welcome "+personName);
            emailTxt.setText("Email : "+personEmail);
        }




        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = passTxt.getText().toString();
                if(pass.length() > 0)
                openMailArea(pass,email);

            }
        });
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signOut();
            }
        });
    }
    public void openMailArea(String pass, String email){
        Intent intent2 = new Intent(Email.this,MailArea.class);
        intent2.putExtra("EMAIL",email);
        intent2.putExtra("PASS",pass);
        Toast.makeText(this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
        startActivity(intent2);
    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        Toast.makeText(Email.this, "Logged Out", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}