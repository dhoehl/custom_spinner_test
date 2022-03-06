package com.example.customviewtest.utils;

import android.content.Context;
import android.util.TypedValue;

public abstract class GuiUtils {
    public static int dpToPx(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
