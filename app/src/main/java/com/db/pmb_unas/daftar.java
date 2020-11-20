package com.db.pmb_unas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.regex.Pattern;

import cyd.awesome.material.AwesomeText;
import cyd.awesome.material.FontCharacterMaps;

public class daftar extends AppCompatActivity{

    EditText username, password, namalengkap, email, nohp, status_ujian;
    AutoCompleteTextView sp_jeniskelamin;
    ImageView iv_dropdown;
    Button  btn_continue;
    AwesomeText ImgShowHidePassword;
    boolean pwd_status = true;

    DatabaseReference reference, reference_username;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        namalengkap = findViewById(R.id.namalengkap);
        email = findViewById(R.id.email);
        nohp = findViewById(R.id.nohp);
        status_ujian = findViewById(R.id.status_ujian);
        ImgShowHidePassword = findViewById(R.id.showPass);
        sp_jeniskelamin = findViewById(R.id.sp_jeniskelamin);
        iv_dropdown = findViewById(R.id.iv_dropdown);

        //Array autotextview
        final String[] jeniskelamin = getResources().getStringArray(R.array.jeniskelamin);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,jeniskelamin);
        sp_jeniskelamin.setThreshold(1);//will start working from first character
        sp_jeniskelamin.setAdapter(adapter);
        iv_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_jeniskelamin.showDropDown();
            }
        });

        btn_continue = findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString().trim();
                if(username.getText().toString().length()==0 || password.getText().toString().length()==0 ||
                        namalengkap.getText().toString().length()==0 || email.getText().toString().length()==0 ||
                        nohp.getText().toString().length()==0 ||
                        sp_jeniskelamin.getText().toString().length()==0 || password.length()<6) {
                    btn_continue.setEnabled(true);
                    btn_continue.setText("DAFTAR");
                    password.setError("Password kurang dari 6 karakter!");
                    password.setFocusable(true);
                    Toast.makeText(getApplicationContext(), "Data kosong atau Format penulisan salah!", Toast.LENGTH_SHORT).show();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                    email.setError("Contoh: pmb@gmail.com");
                    email.setFocusable(true);
                    Toast.makeText(getApplicationContext(), "Format email salah!", Toast.LENGTH_SHORT).show();
                }
                else {
                    //membuat loading
                    btn_continue.setEnabled(false);
                    btn_continue.setText("Loading ...");

                    //mengambil data username dari firebase
                    reference_username = FirebaseDatabase.getInstance().getReference().child("Users").child(username.getText().toString());
                    reference_username.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                Toast.makeText(getApplicationContext(), "Useraname telah terdaftar!", Toast.LENGTH_SHORT).show();
                                btn_continue.setEnabled(true);
                                btn_continue.setText("CONTINUE");
                                username.setText("");
                            }
                            else{
                                //menyimpan username pada local
                                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(username_key, username.getText().toString());
                                editor.apply();

                                //menyimpan pada firebase
                                reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username.getText().toString());
                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        dataSnapshot.getRef().child("username").setValue(username.getText().toString());
                                        dataSnapshot.getRef().child("password").setValue(password.getText().toString());
                                        dataSnapshot.getRef().child("namalengkap").setValue(namalengkap.getText().toString());
                                        dataSnapshot.getRef().child("email").setValue(email.getText().toString());
                                        dataSnapshot.getRef().child("nohp").setValue(nohp.getText().toString());
                                        dataSnapshot.getRef().child("jeniskelamin").setValue(sp_jeniskelamin.getText().toString());
                                        dataSnapshot.getRef().child("status_ujian").setValue(status_ujian.getText().toString());
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                Intent pindah = new Intent(daftar.this, daftar_dua.class);
                                startActivity(pindah);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        // lalu kita beri action agar show hide password nya bisa berfungsi
        ImgShowHidePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pwd_status) {
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    pwd_status = false;
                    ImgShowHidePassword.setMaterialDesignIcon(FontCharacterMaps.MaterialDesign.MD_VISIBILITY);
                    password.setSelection(password.length());
                } else {
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    pwd_status = true;
                    ImgShowHidePassword.setMaterialDesignIcon(FontCharacterMaps.MaterialDesign.MD_VISIBILITY_OFF);
                    password.setSelection(password.length());
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
