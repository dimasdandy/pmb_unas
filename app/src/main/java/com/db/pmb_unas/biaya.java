package com.db.pmb_unas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.db.pmb_unas.webview.webview_pdf_karyawan;
import com.db.pmb_unas.webview.webview_pdf_pascasarjana;
import com.db.pmb_unas.webview.webview_pdf_reguler;

public class biaya extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biaya);

        TextView tv_klik_reguler = findViewById(R.id.klik_reguler);
        tv_klik_reguler.setOnClickListener(this);

        TextView tv_klik_karyawan = findViewById(R.id.klik_karyawan);
        tv_klik_karyawan.setOnClickListener(this);

        TextView tv_klik_pasca = findViewById(R.id.klik_pascasarjana);
        tv_klik_pasca.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.klik_reguler:
                Intent tv_klik_reguler = new Intent(biaya.this, webview_pdf_reguler.class);
                startActivity(tv_klik_reguler);
                break;
            case R.id.klik_karyawan:
                Intent tv_klik_karyawan = new Intent(biaya.this, webview_pdf_karyawan.class);
                startActivity(tv_klik_karyawan);
                break;
            case R.id.klik_pascasarjana:
                Intent tv_klik_pasca = new Intent(biaya.this, webview_pdf_pascasarjana.class);
                startActivity(tv_klik_pasca);
                break;
        }
    }
}
