package com.db.pmb_unas.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.db.pmb_unas.R;
import com.db.pmb_unas.daftar;
import com.db.pmb_unas.daftar_dua;
import com.db.pmb_unas.masuk;

public class GabungFragment extends Fragment implements View.OnClickListener {
    Button btn_gabung2;
    Button btn_login;
    public GabungFragment() {

    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_get_started_dua, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        btn_gabung2 = view.findViewById(R.id.btn_gabung2);
        btn_gabung2.setOnClickListener(this);

        btn_login = view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_gabung2:
                Intent btn_gabung2 = new Intent(getActivity(), daftar.class);
                startActivity(btn_gabung2);
                break;
            case R.id.btn_login:
                Intent btn_login = new Intent(getActivity(), masuk.class);
                startActivity(btn_login);
                break;
        }
    }
}
