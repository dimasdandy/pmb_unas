package com.db.pmb_unas.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.db.pmb_unas.Fragment.QuestionFragment;

import java.util.ArrayList;
import java.util.List;

public class QuestionFragmentAdapter extends FragmentPagerAdapter {

    Context context;
    List<QuestionFragment> fragmentList;

    public QuestionFragmentAdapter(@NonNull FragmentManager fm, Context context, ArrayList<QuestionFragment> fragmentList) {
        super(fm);
        this.context = context;
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return new StringBuilder("Question ").append(position+1).toString();
    }
}
