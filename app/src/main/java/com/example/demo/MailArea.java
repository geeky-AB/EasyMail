package com.example.demo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Locale;
import java.util.*;

public class MailArea extends AppCompatActivity {
    private EditText email, subject, content;
    private Button send;
    private ImageView espk,sspk,cspk;
    SpeechRecognizer speechRecognizer;

    protected  static final int RESULT_SPEECH1 = 1;
    protected  static final int RESULT_SPEECH2 = 2;
    protected  static final int RESULT_SPEECH3 = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_area);

        email = findViewById(R.id.emailEt);
        subject = findViewById(R.id.subjectEt);
        content = findViewById(R.id.contentEt);
        send = findViewById(R.id.sendBtn);
        espk = findViewById(R.id.emailSpk);
        sspk = findViewById(R.id.subjectSpk);
        cspk = findViewById(R.id.contentSpk);

        Intent intent2 = getIntent();
        String senderEmail = intent2.getStringExtra("EMAIL");
        String pass = intent2.getStringExtra("PASS");

        espk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent3.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent3.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                try{
                    startActivityForResult(intent3,RESULT_SPEECH1);
                    email.setText("");
                }
                catch (ActivityNotFoundException e){
                    Toast.makeText(getApplicationContext(), "Your Device Don't Support This Feature", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        sspk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent3.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent3.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                try{
                    startActivityForResult(intent3,RESULT_SPEECH2);
                    subject.setText("");
                }
                catch (ActivityNotFoundException e){
                    Toast.makeText(getApplicationContext(), "Your Device Don't Support This Feature", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        cspk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent3.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent3.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                try{
                    startActivityForResult(intent3,RESULT_SPEECH3);
                    content.setText("");
                }
                catch (ActivityNotFoundException e){
                    Toast.makeText(getApplicationContext(), "Your Device Don't Support This Feature", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail(senderEmail,pass);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case RESULT_SPEECH1:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> text1 = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    email.setText(text1.get(0));
                }
                break;
            case RESULT_SPEECH2:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> text2 = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    subject.setText(text2.get(0));
                }
                break;
            case RESULT_SPEECH3:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> text3 = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    content.setText(text3.get(0));
                }
            break;    
        }
    }

    public void sendEmail(String senderEmail, String pass){

        String sEmail = email.getText().toString();
        String sSubject = subject.getText().toString();
        String sContent = content.getText().toString();
        JavaMailAPI javaMailAPI = new JavaMailAPI(this, sEmail,sSubject,sContent,senderEmail,pass);
        Toast.makeText(this,"Email Sent Successfully",Toast.LENGTH_LONG).show();
//        Intent intent = new Intent(MailArea.this, Email.class);
//        startActivity(intent);
        javaMailAPI.execute();
    }
}