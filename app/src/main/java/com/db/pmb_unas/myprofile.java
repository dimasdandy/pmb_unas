package com.db.pmb_unas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class myprofile extends AppCompatActivity {
    TextView username, password, namalengkap, email, nohp, jeniskelamin, programstudi, program, jalur, status_ujian;
    ImageView photo_user;
    Button btn_signout, btn_edit, btn_test;
    Dialog mDialog;

    DatabaseReference reference;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String uusername_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

        getUsernameLocal();

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        namalengkap = findViewById(R.id.namalengkap);
        email = findViewById(R.id.email);
        jeniskelamin = findViewById(R.id.jeniskelamin);
        nohp = findViewById(R.id.nohp);
        programstudi = findViewById(R.id.programstudi);
        program = findViewById(R.id.program);
        jalur = findViewById(R.id.jalur);
        status_ujian = findViewById(R.id.status_ujian);
        photo_user = findViewById(R.id.photo_user);
        mDialog = new Dialog(this);

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(uusername_key_new);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                username.setText(dataSnapshot.child("username").getValue().toString());
                password.setText(dataSnapshot.child("password").getValue().toString());
                namalengkap.setText(dataSnapshot.child("namalengkap").getValue().toString());
                email.setText(dataSnapshot.child("email").getValue().toString());
                jeniskelamin.setText(dataSnapshot.child("jeniskelamin").getValue().toString());
                nohp.setText(dataSnapshot.child("nohp").getValue().toString());
                programstudi.setText(dataSnapshot.child("programstudi").getValue().toString());
                program.setText(dataSnapshot.child("program").getValue().toString());
                jalur.setText(dataSnapshot.child("jalur").getValue().toString());
                status_ujian.setText(dataSnapshot.child("status_ujian").getValue().toString());
                Picasso.with(myprofile.this).load(dataSnapshot.child("url_photo_profile").getValue().toString()).centerCrop().fit().into(photo_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_edit = findViewById(R.id.btn_edit);
        btn_signout = findViewById(R.id.btn_signout);
        btn_test = findViewById(R.id.btn_test);

        btn_test.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(status_ujian.getText().length() != 0){
                    Intent kesoal = new Intent(myprofile.this, MainActivity2.class);
                    startActivity(kesoal);
                    finish();
                }
                else{
                    btn_test.setEnabled(false);
                    Toast.makeText(getApplicationContext(),"Kamu sudah mengerjakan ujian",Toast.LENGTH_LONG).show();
                    btn_test.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_button_red));
                }
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahedit = new Intent(myprofile.this, editprofile.class);
                startActivity(pindahedit);
                finish();
            }
        });

        btn_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(username_key, null);
                editor.apply();

                final AlertDialog.Builder builder = new AlertDialog.Builder(myprofile.this);
                builder.setMessage("Apakah kamu ingin keluar?");
                builder.setCancelable(true);
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent signout = new Intent(myprofile.this, MainActivity.class);
                        signout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(signout);
                        finish();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        uusername_key_new = sharedPreferences.getString(username_key, "");
    }

    @Override
    public void onBackPressed() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(username_key, null);
        editor.apply();

        final AlertDialog.Builder builder = new AlertDialog.Builder(myprofile.this);
        builder.setMessage("Apakah kamu ingin keluar?");
        builder.setCancelable(true);
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent signout = new Intent(myprofile.this, MainActivity.class);
                signout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(signout);
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

