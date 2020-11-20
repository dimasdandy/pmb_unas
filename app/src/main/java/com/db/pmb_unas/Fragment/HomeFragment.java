package com.db.pmb_unas.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.db.pmb_unas.R;
import com.db.pmb_unas.biaya;
import com.db.pmb_unas.Menu.menu_beasiswa;
import com.db.pmb_unas.Menu.menu_jadwal;
import com.db.pmb_unas.Menu.menu_jenjang;
import com.db.pmb_unas.Menu.menu_karyawan;
import com.db.pmb_unas.Menu.menu_lokasi;
import com.db.pmb_unas.Menu.menu_materiujian;
import com.db.pmb_unas.Menu.menu_prosedur;
import com.db.pmb_unas.Menu.menu_tartib;
import com.db.pmb_unas.webview.webview_profilunas;
import com.db.pmb_unas.webview.webview_wfh;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class HomeFragment extends Fragment implements View.OnClickListener {



    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    //membuat onclick pada icon menu jadwal
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {



        ImageView ivJadwal = view.findViewById(R.id.iv_menu_jadwal);
        ivJadwal.setOnClickListener(this);

        ImageView ivLokasi = view.findViewById(R.id.iv_menu_lokasi);
        ivLokasi.setOnClickListener(this);

        ImageView ivProsedur = view.findViewById(R.id.iv_menu_prosedur);
        ivProsedur.setOnClickListener(this);

        ImageView ivMateri = view.findViewById(R.id.iv_menu_materi);
        ivMateri.setOnClickListener(this);

        ImageView ivTartib = view.findViewById(R.id.iv_menu_tartib);
        ivTartib.setOnClickListener(this);

        ImageView ivBiaya = view.findViewById(R.id.iv_menu_biaya);
        ivBiaya.setOnClickListener(this);

        ImageView ivBeasiswa = view.findViewById(R.id.iv_menu_beasiswa);
        ivBeasiswa.setOnClickListener(this);

        ImageView ivJenjang = view.findViewById(R.id.iv_menu_jenjang);
        ivJenjang.setOnClickListener(this);

        ImageView ivKaryawan = view.findViewById(R.id.iv_menu_karyawan);
        ivKaryawan.setOnClickListener(this);

        ImageView wv_profilunas = view.findViewById(R.id.wv_profilunas);
        wv_profilunas.setOnClickListener(this);

        ImageView wv_wfh = view.findViewById(R.id.wv_wfhunas);
        wv_wfh.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_menu_jadwal:
                Intent ivJadwal = new Intent(getActivity(), menu_jadwal.class);
                startActivity(ivJadwal);
                break;
            case R.id.iv_menu_lokasi:
                Intent ivLokasi = new Intent(getActivity(), menu_lokasi.class);
                startActivity(ivLokasi);
                break;
            case R.id.iv_menu_prosedur:
                Intent ivProsedur = new Intent(getActivity(), menu_prosedur.class);
                startActivity(ivProsedur);
                break;
            case R.id.iv_menu_materi:
                Intent ivMateri = new Intent(getActivity(), menu_materiujian.class);
                startActivity(ivMateri);
                break;
            case R.id.iv_menu_tartib:
                Intent ivTartib = new Intent(getActivity(), menu_tartib.class);
                startActivity(ivTartib);
                break;
            case R.id.iv_menu_biaya:
                Intent ivBiaya = new Intent(getActivity(), biaya.class);
                startActivity(ivBiaya);
                break;
            case R.id.iv_menu_beasiswa:
                Intent ivBeasiswa = new Intent(getActivity(), menu_beasiswa.class);
                startActivity(ivBeasiswa);
                break;
            case R.id.iv_menu_jenjang:
                Intent ivJenjang = new Intent(getActivity(), menu_jenjang.class);
                startActivity(ivJenjang);
                break;
            case R.id.iv_menu_karyawan:
                Intent ivKaryawan = new Intent(getActivity(), menu_karyawan.class);
                startActivity(ivKaryawan);
                break;
            case R.id.wv_profilunas:
                Intent wv_profilunas = new Intent(getActivity(), webview_profilunas.class);
                startActivity(wv_profilunas);
                break;
            case R.id.wv_wfhunas:
                Intent wv_wfhunas = new Intent(getActivity(), webview_wfh.class);
                startActivity(wv_wfhunas);
                break;
        }
    }
}
