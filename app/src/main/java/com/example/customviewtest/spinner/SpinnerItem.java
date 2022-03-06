package com.example.customviewtest.spinner;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.material.imageview.ShapeableImageView;

public interface SpinnerItem {

    @NonNull
    default String getText(Context context) {
        return getText();
    }

    @NonNull
    String getText();

    boolean loadPicture(Context context, ShapeableImageView image);
}
