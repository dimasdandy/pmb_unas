package com.db.pmb_unas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

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

public class editprofile extends AppCompatActivity {
    TextView username, password, namalengkap, email, nohp;
    AutoCompleteTextView sp_jeniskelamin, sp_programstudi, sp_program, sp_jalur;
    ImageView photo_user, iv_dropdown,iv_dropdown1,iv_dropdown2,iv_dropdown3;
    Button btn_choose_file, btn_back, btn_simpan;
    ProgressDialog progressDialog;

    Uri photo_location;
    Integer photo_max = 1;

    DatabaseReference reference;
    StorageReference storage;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String uusername_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        getUsernameLocal();

        progressDialog = new ProgressDialog(this);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        namalengkap = findViewById(R.id.namalengkap);
        email = findViewById(R.id.email);
        sp_jeniskelamin = findViewById(R.id.sp_jeniskelamin);
        nohp = findViewById(R.id.nohp);
        sp_programstudi = findViewById(R.id.sp_programstudi);
        sp_program = findViewById(R.id.sp_program);
        sp_jalur = findViewById(R.id.sp_jalur);
        photo_user = findViewById(R.id.photo_user);

        btn_choose_file = findViewById(R.id.btn_choose_file);
        btn_back = findViewById(R.id.btn_back);
        btn_simpan = findViewById(R.id.btn_simpan);

        iv_dropdown = findViewById(R.id.iv_dropdown);
        iv_dropdown1 = findViewById(R.id.iv_dropdown1);
        iv_dropdown2 = findViewById(R.id.iv_dropdown2);
        iv_dropdown3 = findViewById(R.id.iv_dropdown3);

        //----------------------------------------------------------------------------------------------------

        //Array autotextview
        final String[] jeniskelamin = getResources().getStringArray(R.array.jeniskelamin);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,jeniskelamin);
        sp_jeniskelamin.setAdapter(adapter);
        iv_dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_jeniskelamin.getText().clear();
                sp_jeniskelamin.showDropDown();
            }
        });

        //Array autotextview prodi
        final String[] prodi = getResources().getStringArray(R.array.prodi);
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,prodi);
        sp_programstudi.setAdapter(adapter1);
        iv_dropdown1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_programstudi.getText().clear();
                sp_programstudi.showDropDown();
            }
        });

        //Array autotextview program
        final String[] program = getResources().getStringArray(R.array.program);
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,program);
        sp_program.setAdapter(adapter2);
        iv_dropdown2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_program.getText().clear();
                sp_program.showDropDown();
            }
        });

        //Array autotextview program
        final String[] jalur = getResources().getStringArray(R.array.jalurpendidikan);
        final ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,jalur);
        sp_jalur.setAdapter(adapter3);
        iv_dropdown3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_jalur.getText().clear();
                sp_jalur.showDropDown();
            }
        });

        //-==========================--------------------------------------------------------------------------------

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(uusername_key_new);
        storage = FirebaseStorage.getInstance().getReference().child("PotoUsers").child(uusername_key_new);
        Log.e("Log_Firebase", ""+storage);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                username.setText(dataSnapshot.child("username").getValue().toString());
                password.setText(dataSnapshot.child("password").getValue().toString());
                namalengkap.setText(dataSnapshot.child("namalengkap").getValue().toString());
                email.setText(dataSnapshot.child("email").getValue().toString());
                nohp.setText(dataSnapshot.child("nohp").getValue().toString());
                sp_jeniskelamin.setText(dataSnapshot.child("jeniskelamin").getValue().toString());
                sp_programstudi.setText(dataSnapshot.child("programstudi").getValue().toString());
                sp_program.setText(dataSnapshot.child("program").getValue().toString());
                sp_jalur.setText(dataSnapshot.child("jalur").getValue().toString());
                Picasso.with(editprofile.this).load(dataSnapshot.child("url_photo_profile").getValue().toString()).centerCrop().fit().into(photo_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("username").setValue(username.getText().toString());
                        dataSnapshot.getRef().child("password").setValue(password.getText().toString());
                        dataSnapshot.getRef().child("namalengkap").setValue(namalengkap.getText().toString());
                        dataSnapshot.getRef().child("email").setValue(email.getText().toString());
                        dataSnapshot.getRef().child("nohp").setValue(nohp.getText().toString());
                        dataSnapshot.getRef().child("jeniskelamin").setValue(sp_jeniskelamin.getText().toString());
                        dataSnapshot.getRef().child("programstudi").setValue(sp_programstudi.getText().toString());
                        dataSnapshot.getRef().child("program").setValue(sp_program.getText().toString());
                        dataSnapshot.getRef().child("jalur").setValue(sp_jalur.getText().toString());

                        //validasi file
                        if (photo_location != null){
                            final StorageReference storageReference1 = storage.child(System.currentTimeMillis() + "." + getFileExtension(photo_location));
                            storageReference1.putFile(photo_location).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String uri_photo = uri.toString();
                                            reference.getRef().child("url_photo_profile").setValue(uri_photo);
                                        }
                                    });
                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Intent pindahact = new Intent(editprofile.this, myprofile.class);
                pindahact.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(pindahact);
                progressDialog.setMessage("Mengubah Profil");
                progressDialog.show();
                finish();
            }
        });

        btn_choose_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhoto();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(editprofile.this, myprofile.class);
                startActivity(back);
                finish();
            }
        });
    }

    String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void findPhoto(){
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, photo_max);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == photo_max && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            photo_location = data.getData();
            Picasso.with(this).load(photo_location).centerCrop().fit().into(photo_user);
        }
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        uusername_key_new = sharedPreferences.getString(username_key, "");
    }

    @Override
    public void onBackPressed() {
        Intent keprofil = new Intent(editprofile.this, myprofile.class);
        startActivity(keprofil);
        finish();
    }
}
