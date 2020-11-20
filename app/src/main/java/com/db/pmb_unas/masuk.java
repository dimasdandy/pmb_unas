package com.db.pmb_unas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cyd.awesome.material.AwesomeText;
import cyd.awesome.material.FontCharacterMaps;

public class masuk extends AppCompatActivity{

    EditText xusername, xpassword;
    DatabaseReference reference;
    Button btn_signin;
    AwesomeText ImgShowHidePassword;
    boolean pwd_status = true;

    public ProgressBar progressBar;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk);

        xusername = findViewById(R.id.xusername);
        xpassword = findViewById(R.id.xpassword);
        ImgShowHidePassword = findViewById(R.id.showPass);
        btn_signin = findViewById(R.id.btn_signin);


        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_signin.setEnabled(false);
                btn_signin.setText("Loading ...");
                progressBar = new ProgressBar(masuk.this);
                progressBar.setVisibility(View.VISIBLE);

                final String username = xusername.getText().toString();
                final String password = xpassword.getText().toString();

                if(xusername.getText().toString().length()==0 || xpassword.getText().toString().length()==0) {
                    btn_signin.setEnabled(true);
                    btn_signin.setText("SIGN IN");
                    Toast.makeText(getApplicationContext(), "Username atau Password kosong!", Toast.LENGTH_SHORT).show();
                }
                else{
                    reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username);
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){
                                //ambil data password dari firebase
                                String passwordFromFirebase = dataSnapshot.child("password").getValue().toString();
                                //validasi input password dgn database
                                if(password.equals(passwordFromFirebase)){
                                    //menyimpan username pada local
                                    SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(username_key, xusername.getText().toString());
                                    editor.apply();

                                    Intent gotoawal = new Intent(masuk.this, myprofile.class);
                                    startActivity(gotoawal);
                                    finish();
                                }
                                else{
                                    btn_signin.setEnabled(true);
                                    btn_signin.setText("SIGN IN");
                                    Toast.makeText(getApplicationContext(), "Password salah", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                btn_signin.setEnabled(true);
                                btn_signin.setText("SIGN IN");
                                Toast.makeText(getApplicationContext(), "Username Tidak Terdaftar", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        ImgShowHidePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pwd_status) {
                    xpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    pwd_status = false;
                    ImgShowHidePassword.setMaterialDesignIcon(FontCharacterMaps.MaterialDesign.MD_VISIBILITY);
                    xpassword.setSelection(xpassword.length());
                } else {
                    xpassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    pwd_status = true;
                    ImgShowHidePassword.setMaterialDesignIcon(FontCharacterMaps.MaterialDesign.MD_VISIBILITY_OFF);
                    xpassword.setSelection(xpassword.length());
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        //Intent gotoawal = new Intent(masuk.this, get_started_dua.class);
        //startActivity(gotoawal);
        finish();
    }
}
