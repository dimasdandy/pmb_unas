package com.db.pmb_unas.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.db.pmb_unas.R;

import org.w3c.dom.Text;

public class CallFragment extends Fragment implements View.OnClickListener {

    public CallFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_call, container, false);
    }

    //membuat onclick pada callfragment
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView tvTelepon = view.findViewById(R.id.tv_teleponUnas);
        tvTelepon.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        String phoneNumber = "(021)7806700";
        Intent dialPhoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phoneNumber));
        startActivity(dialPhoneIntent);
    }
}
