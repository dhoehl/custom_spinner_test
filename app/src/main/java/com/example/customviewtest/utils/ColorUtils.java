package com.example.customviewtest.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.material.color.MaterialColors;


public abstract class ColorUtils {
    public static int getAttrColor(@NonNull Context context, int attr){
       return  MaterialColors.getColor(context, attr, Color.BLACK);
    }

    public static ColorStateList getAttrColorList(@NonNull Context context, int attr){
        return  ColorStateList.valueOf(MaterialColors.getColor(context, attr, Color.BLACK));
    }

    public static void saturatePicture(ImageView iv, float saturation) {
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(saturation);
        ColorMatrixColorFilter cmcf = new ColorMatrixColorFilter(cm);
        iv.setColorFilter(cmcf);
    }
}
