package com.db.pmb_unas;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpaceDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpaceDecoration(int space){
        this.space = space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.left = outRect.right = outRect.bottom = outRect.top = space;}
}
