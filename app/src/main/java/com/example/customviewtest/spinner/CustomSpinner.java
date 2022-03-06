package com.example.customviewtest.spinner;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.customviewtest.R;
import com.example.customviewtest.databinding.CustomSpinnerBinding;
import com.example.customviewtest.utils.ColorUtils;
import com.example.customviewtest.utils.GuiUtils;
import com.example.customviewtest.utils.Stopwatch;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.ShapeAppearanceModel;

public class CustomSpinner extends ConstraintLayout {
    private static final String TAG = "CustomSpinner";

    private final CustomSpinnerBinding b;
    private AbstractSpinnerAdapter<? extends SpinnerItem> mAdapter;

    private String label;

    private String helperText;

    private SpinnerItem currentItem;

    private boolean useRoundStartIcon;

    private boolean isEnabled;

    public CustomSpinner(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Stopwatch sw = Stopwatch.createStarted("CustomSpinner");
        b = CustomSpinnerBinding.inflate(LayoutInflater.from(context), this);
        setClipToPadding(false);
        setClipChildren(false);
        int pxPadding = GuiUtils.dpToPx(getContext(), 8);
        setPadding(pxPadding, 0, pxPadding, 0);
        sw.logRound("Inflation");
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomSpinner);

        //Label text
        label = attributes.getString(R.styleable.CustomSpinner_label);
        refreshLabel();

        //Helper text
        helperText = attributes.getString(R.styleable.CustomSpinner_helperText);
        refreshHelperText();

        //Round icon
        useRoundStartIcon = attributes.getBoolean(R.styleable.CustomSpinner_useRoundIcon, false);
        refreshStartIconAppearance();


        attributes.recycle();
        initListeners();
    }

    private void refreshStartIconAppearance() {
        if (useRoundStartIcon) {
            MarginLayoutParams layoutParams = (LayoutParams) b.ivStartIcon.getLayoutParams();
            layoutParams.width = GuiUtils.dpToPx(getContext(), 30);
            layoutParams.height = GuiUtils.dpToPx(getContext(), 30);
            int left = GuiUtils.dpToPx(getContext(), 14);
            layoutParams.setMargins(left, 0, 0, 0);
            b.ivStartIcon.setLayoutParams(layoutParams);
            b.ivStartIcon.setAdjustViewBounds(false);
            b.ivStartIcon.setScaleType(ImageView.ScaleType.CENTER_CROP);
            int cornerSize = GuiUtils.dpToPx(getContext(), 15);
            ShapeAppearanceModel m = b.ivStartIcon.getShapeAppearanceModel().toBuilder().setAllCorners(CornerFamily.ROUNDED,cornerSize).build();
            b.ivStartIcon.setShapeAppearanceModel(m);
        } else {
            MarginLayoutParams layoutParams = (MarginLayoutParams) b.ivStartIcon.getLayoutParams();
            layoutParams.width = GuiUtils.dpToPx(getContext(), 20);
            layoutParams.height = 0;
            int left = GuiUtils.dpToPx(getContext(), 19);
            int top = GuiUtils.dpToPx(getContext(), 15);
            int bottom = GuiUtils.dpToPx(getContext(), 15);
            layoutParams.setMargins(left, top, 0, bottom);
            b.ivStartIcon.setLayoutParams(layoutParams);
            b.ivStartIcon.setAdjustViewBounds(true);
            b.ivStartIcon.setScaleType(ImageView.ScaleType.FIT_CENTER); //default
        }
    }

    private void refreshHelperText() {
        if (helperText == null) {
            b.tvHelper.setVisibility(INVISIBLE);
        } else {
            b.tvHelper.setVisibility(VISIBLE);
            b.tvHelper.setText(helperText);
        }
    }

    private void refreshLabel() {
        if (label == null) {
            b.tvLabel.setVisibility(GONE);
        } else {
            b.tvLabel.setVisibility(VISIBLE);
            b.tvLabel.setText(label);
        }
    }

    private void initListeners() {
    }

    public void setAdapter(AbstractSpinnerAdapter<?> adapter) {
        mAdapter = adapter;
        b.actvSpinner.setAdapter(adapter);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        b.actvSpinner.setOnItemClickListener((parent, view, position, id) -> {
            currentItem = mAdapter.getCorrectItem(position);
            refreshCurrentItem();
            b.actvSpinner.setText(null);
            listener.onItemClick(parent, view, position, id);
        });
    }

    private void refreshCurrentItem() {
        setText(currentItem.getText(getContext()));
        if (currentItem.loadPicture(getContext(), b.ivStartIcon)) {
            b.ivStartIcon.setVisibility(VISIBLE);
        } else {
            b.ivStartIcon.setVisibility(GONE);
        }
    }

    private void refreshEnabled() {
        if (isEnabled) {
            b.cardView.setCardBackgroundColor(ColorUtils.getAttrColor(getContext(), R.attr.cardColorOnPrimary));
            b.ivStartIcon.setAlpha(1f);
            b.tvText.setTextColor(ColorUtils.getAttrColor(getContext(), R.attr.textColorPrimary));
            b.tilSpinner.setEndIconTintList(ColorUtils.getAttrColorList(getContext(), R.attr.colorSecondary));
            b.tilSpinner.setEnabled(true);
            ColorUtils.saturatePicture(b.ivStartIcon, 1);
        } else {
            ColorUtils.saturatePicture(b.ivStartIcon, 0);
            b.tilSpinner.setEnabled(false);
            b.ivStartIcon.setAlpha(0.4f);
            b.tvText.setTextColor(ColorUtils.getAttrColor(getContext(), R.attr.colorItemDisabled));
            b.cardView.setCardBackgroundColor(ColorUtils.getAttrColor(getContext(), R.attr.colorDisabled));
            b.tilSpinner.setEndIconTintList(ColorUtils.getAttrColorList(getContext(), R.attr.colorItemDisabled));
        }

    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
        refreshEnabled();
    }

    public void setText(String text) {
        b.tvText.setText(text);
    }

    public void setLabel(String label) {
        this.label = label;
        refreshLabel();
    }

    public void setUseRoundStartIcon(boolean useRoundStartIcon) {
        this.useRoundStartIcon = useRoundStartIcon;
    }

    public void setSelected(SpinnerItem selected) {
        currentItem = selected;
        refreshCurrentItem();
    }

    public void setHelperText(String helperText) {
        this.helperText = helperText;
        refreshHelperText();
    }

}
