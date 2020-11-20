package com.db.pmb_unas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class get_started_dua extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started_dua);

        Button btn_gabung2 = findViewById(R.id.btn_gabung2);
        btn_gabung2.setOnClickListener(this);

        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_gabung2:
                Intent btn_gabung2 = new Intent(get_started_dua.this, daftar.class);
                startActivity(btn_gabung2);
                break;
            case R.id.btn_login:
                Intent btn_login = new Intent(get_started_dua.this, masuk.class);
                startActivity(btn_login);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        //Intent gotoawal = new Intent(get_started_dua.this, get_started.class);
        //startActivity(gotoawal);
        finish();
    }
}
