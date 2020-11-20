package com.db.pmb_unas.Menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.db.pmb_unas.R;
import com.db.pmb_unas.webview.webview_pdf_beasiswa;

public class menu_beasiswa extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_beasiswa);

        TextView tv_klik_beasiswa = findViewById(R.id.klik_beasiswa);
        tv_klik_beasiswa.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent tv_klik_reguler = new Intent(menu_beasiswa.this, webview_pdf_beasiswa.class);
        startActivity(tv_klik_reguler);
    }
}
