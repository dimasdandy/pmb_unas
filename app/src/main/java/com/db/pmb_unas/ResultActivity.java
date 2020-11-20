package com.db.pmb_unas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.db.pmb_unas.Adapter.ResultGridAdapter;
import com.db.pmb_unas.Common.Common;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

public class ResultActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView btn_filter_total, btn_filter_right, btn_filter_wrong, btn_filter_no_answer;
    Button btn_selesai;
    RecyclerView recycler_result;

    TextView status_ujian, username, password, namalengkap, email, nohp, jeniskelamin, programstudi, program, jalur;
    ImageView photo_user;
    Dialog mDialog;

    DatabaseReference reference;
    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String uusername_key_new = "";

    BroadcastReceiver  backToQuestion = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().toString().equals(Common.KEY_BACK_FROM_RESULT)){
                int question = intent.getIntExtra(Common.KEY_BACK_FROM_RESULT, -1);
                goBackActivityWithQuestion(question);
            }
        }
    };

    private void goBackActivityWithQuestion(int question) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(Common.KEY_BACK_FROM_RESULT,question);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        getUsernameLocal();

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(backToQuestion, new IntentFilter(Common.KEY_BACK_FROM_RESULT));

        //toolbar = findViewById(R.id.toolbar);
        //toolbar.setTitle("Result");
        //setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        //final TextView txt_result = findViewById(R.id.txt_result);
        TextView txt_timer = findViewById(R.id.txt_time);
        TextView txt_right_answer = findViewById(R.id.txt_right_answer);
        status_ujian = findViewById(R.id.status_ujian);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        namalengkap = findViewById(R.id.namalengkap);
        email = findViewById(R.id.email);
        jeniskelamin = findViewById(R.id.jeniskelamin);
        nohp = findViewById(R.id.nohp);
        programstudi = findViewById(R.id.programstudi);
        program = findViewById(R.id.program);
        jalur = findViewById(R.id.jalur);
        photo_user = findViewById(R.id.photo_user);

        btn_selesai = findViewById(R.id.btn_selesai);
        btn_filter_no_answer = findViewById(R.id.btn_no_answer);
        btn_filter_right = findViewById(R.id.btn_filter_right_answer);
        btn_filter_wrong = findViewById(R.id.btn_filter_wrong_answer);
        btn_filter_total = findViewById(R.id.btn_filter_total);

        recycler_result = findViewById(R.id.recycler_result);
        recycler_result.setHasFixedSize(true);
        recycler_result.setLayoutManager(new GridLayoutManager(this, 3));

        //Set Adapter
        ResultGridAdapter adapter = new ResultGridAdapter(this,Common.answerSheetList);
        recycler_result.addItemDecoration(new SpaceDecoration(4));
        recycler_result.setAdapter(adapter);

        txt_timer.setText(String.format("%2d:%2d",
                TimeUnit.MILLISECONDS.toMinutes(Common.timer),
                TimeUnit.MILLISECONDS.toSeconds(Common.timer) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(Common.timer))));

        txt_right_answer.setText(new StringBuilder("").append(Common.right_answer_count).append("/")
                .append(Common.questionList.size()));

        btn_filter_total.setText(new StringBuilder("").append(Common.questionList.size()));
        btn_filter_right.setText(new StringBuilder("").append(Common.right_answer_count));
        btn_filter_wrong.setText(new StringBuilder("").append(Common.wrong_answer_count));
        btn_filter_no_answer.setText(new StringBuilder("").append(Common.no_answer_count));

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
                Picasso.with(ResultActivity.this).load(dataSnapshot.child("url_photo_profile").getValue().toString()).centerCrop().fit().into(photo_user);
                //Calculate result
                final int percent = (Common.right_answer_count*100/Common.questionList.size());
                if(percent > 70){
                    status_ujian.setText("Kamu Lulus");
                }
                else
                    status_ujian.setText("Kamu Tidak Lulus");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("username").setValue(username.getText().toString());
                        dataSnapshot.getRef().child("status_ujian").setValue(status_ujian.getText().toString());
                        dataSnapshot.getRef().child("password").setValue(password.getText().toString());
                        dataSnapshot.getRef().child("namalengkap").setValue(namalengkap.getText().toString());
                        dataSnapshot.getRef().child("email").setValue(email.getText().toString());
                        dataSnapshot.getRef().child("nohp").setValue(nohp.getText().toString());
                        dataSnapshot.getRef().child("jeniskelamin").setValue(jeniskelamin.getText().toString());
                        dataSnapshot.getRef().child("programstudi").setValue(programstudi.getText().toString());
                        dataSnapshot.getRef().child("program").setValue(program.getText().toString());
                        dataSnapshot.getRef().child("jalur").setValue(jalur.getText().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Intent intent = new Intent(ResultActivity.this, myprofile.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);;
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ResultActivity.this, ResultActivity.class);
        startActivity(intent);
        finish();
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        uusername_key_new = sharedPreferences.getString(username_key, "");
    }
}
