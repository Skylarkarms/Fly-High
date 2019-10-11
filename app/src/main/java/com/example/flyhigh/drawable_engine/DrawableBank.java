package com.example.flyhigh.drawable_engine;

import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

public class DrawableBank {

    @DrawableRes
    private int drawableRes;
    @NonNull
    private ImageView imageView;

    public DrawableBank(int drawableRes,
                            @NonNull ImageView imageView) {
        this.drawableRes = drawableRes;
        this.imageView = imageView;
    }

    public int getDrawableRes() {
        return drawableRes;
    }

    @NonNull
    public ImageView getImageView() {
        return imageView;
    }
}
