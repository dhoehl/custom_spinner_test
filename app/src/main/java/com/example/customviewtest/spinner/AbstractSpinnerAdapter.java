package com.example.customviewtest.spinner;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

public abstract class AbstractSpinnerAdapter<T extends SpinnerItem> extends ArrayAdapter<T> {
    public final String TAG = getClass().getSimpleName();

    public AbstractSpinnerAdapter(@NonNull Context context) {
        super(context, 0);
    }

    public Context requireContext() {
        return getContext();
    }


    protected boolean getDividerVisible(int position){
        return position != getCount()-1;
    }

    /**
     * This represents the item that is returned 'onClick'.
     * On default this method return the same item as getItem but might be overriden to return
     * other items onClick (For example if a label shall not be returned)
     * @param position
     * @return
     */
    public T getCorrectItem(int position){
        return getItem(position);
    }
}
