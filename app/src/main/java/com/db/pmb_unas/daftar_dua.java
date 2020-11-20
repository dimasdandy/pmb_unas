package com.db.pmb_unas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import com.db.pmb_unas.Adapter.ProdiAdapter;

public class daftar_dua extends AppCompatActivity {

    AutoCompleteTextView sp_programstudi, sp_program, sp_jalur;
    TextView filefoto;
    Button btn_choose_file, btn_daftar;
    ImageView photo_profile,iv_dropdown1,iv_dropdown2,iv_dropdown3;

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
        setContentView(R.layout.activity_daftar_dua);

        getUsernameLocal();

        sp_programstudi = findViewById(R.id.sp_programstudi);
        sp_program = findViewById(R.id.sp_program);
        sp_jalur = findViewById(R.id.sp_jalur);
        photo_profile = findViewById(R.id.photo_profile);
        filefoto = findViewById(R.id.filefoto);
        btn_choose_file = findViewById(R.id.btn_choose_file);
        btn_daftar = findViewById(R.id.btn_daftar);
        iv_dropdown1 = findViewById(R.id.iv_dropdown1);
        iv_dropdown2 = findViewById(R.id.iv_dropdown2);
        iv_dropdown3 = findViewById(R.id.iv_dropdown3);

        //Array autotextview prodi
        List<String> lsProdi = new ArrayList<>();
        lsProdi.add("FISIP - Administrasi Publik S1");
        lsProdi.add("FISIP - Sosiologi S1");
        lsProdi.add("FISIP - Ilmu Komunikasi S1");
        lsProdi.add("FISIP - Ilmu Politik S1");
        lsProdi.add("FISIP - Hubungan Internasional S1");
        lsProdi.add("FE - Manajemen S1");
        lsProdi.add("FE - Akutansi S1");
        lsProdi.add("FE - Pariwisata S1");
        lsProdi.add("FBS - Sastra Inggris S1");
        lsProdi.add("FBS - Sastra Jepang S1");
        lsProdi.add("FBS - Sastra Indonesia S1");
        lsProdi.add("FBS - Bahasa Korea S1");
        lsProdi.add("FH - Hukum S1");
        lsProdi.add("FB - Agroteknologi S1");
        lsProdi.add("FB - Biologi S1");
        lsProdi.add("FTS - Teknik Fisika S1");
        lsProdi.add("FTS - Teknik Mesin S1");
        lsProdi.add("FTS - Teknik Elektro S1");
        lsProdi.add("FTS - Fisika S1");
        lsProdi.add("FTKI - Teknik Informatika S1");
        lsProdi.add("FTKI - Sistem Informasi S1");
        lsProdi.add("FIK - Keperawatan S1");
        lsProdi.add("FIK - Profesi Ners");
        lsProdi.add("FIK - Profesi Bidan");

        ArrayAdapter<String> adapter = new ProdiAdapter(this, R.layout.prodi_item, lsProdi);
        sp_programstudi.setAdapter(adapter);
        sp_programstudi.setThreshold(1);//will start working from first character
        iv_dropdown1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_programstudi.showDropDown();
            }
        });

        //Array autotextview program
        String[] program = getResources().getStringArray(R.array.program);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,program);
        sp_program.setAdapter(adapter2);
        iv_dropdown2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_program.showDropDown();
            }
        });

        //Array autotextview program
        String[] jalur = getResources().getStringArray(R.array.jalurpendidikan);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,jalur);
        sp_jalur.setAdapter(adapter3);
        iv_dropdown3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_jalur.showDropDown();
            }
        });

        btn_choose_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhoto();
                filefoto.setText("...");
            }
        });

        //-----------------------------------------------------------------------------

        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sp_programstudi.getText().toString().length()==0 || sp_program.getText().toString().length()==0 || sp_jalur.getText().toString().length()==0 ||
                filefoto.getText().toString().length() == 0){
                    btn_daftar.setEnabled(true);
                    btn_daftar.setText("Daftar");
                    btn_choose_file.setError("Input Foto");
                    btn_choose_file.setFocusable(true);
                    Toast.makeText(getApplicationContext(), "Data tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                }
                else {
                    //membuat loading
                    btn_daftar.setEnabled(false);
                    btn_daftar.setText("Loading ...");

                    //menyimpan kepada firebase
                    reference = FirebaseDatabase.getInstance().getReference().child("Users").child(uusername_key_new);
                    storage = FirebaseStorage.getInstance().getReference().child("PotoUsers").child(uusername_key_new);
                    Log.e("Log_Firebase", ""+storage);

                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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
                                                reference.getRef().child("programstudi").setValue(sp_programstudi.getText().toString());
                                                reference.getRef().child("program").setValue(sp_program.getText().toString());
                                                reference.getRef().child("jalur").setValue(sp_jalur.getText().toString());
                                            }
                                        });
                                    }
                                });
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Isi semua data terlebih dahulu", Toast.LENGTH_SHORT).show();
                                btn_daftar.setEnabled(true);
                                btn_daftar.setText("CONTINUE");
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    Intent pindah = new Intent(daftar_dua.this, masuk.class);
                    startActivity(pindah);
                    finish();
                }
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
            Picasso.with(this).load(photo_location).centerCrop().fit().into(photo_profile);
        }
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        uusername_key_new = sharedPreferences.getString(username_key, "");
    }

    @Override
    public void onBackPressed() {
        Intent gotodaftarsatu = new Intent(daftar_dua.this, daftar.class);
        startActivity(gotodaftarsatu);
        finish();
    }
}
