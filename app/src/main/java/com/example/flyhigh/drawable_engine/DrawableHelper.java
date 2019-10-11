package com.example.flyhigh.drawable_engine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;


public class DrawableHelper {
    @NonNull
    public Context getContext() {
        return mContext;
    }

    @NonNull
    Context mContext;
    @ColorRes
    private int mColor;
    private Drawable mDrawable;
    private Drawable mWrappedDrawable;

    public DrawableHelper(@NonNull Context context) {
        mContext = context;
    }

    public static DrawableHelper withContext(@NonNull Context context) {
        return new DrawableHelper(context);
    }

    public DrawableHelper recolorRes (@ColorRes int newColor, DrawableBank drawableRes) {
        if (drawableRes == null) {
            throw new NullPointerException("Must initialize DrawableBank");
        }
        withColor(newColor);
        recolor(drawableRes);
        return this;
    }

    private void recolor (DrawableBank drawableRes) {
        withDrawable(drawableRes.getDrawableRes());
        tint();
        applyTo(drawableRes.getImageView());
    }

    public DrawableHelper withDrawable(@DrawableRes int drawableRes) {
        mDrawable = ContextCompat.getDrawable(mContext, drawableRes);
        return this;
    }

    public DrawableHelper withDrawable(@NonNull Drawable drawable) {
        mDrawable = drawable;
        return this;
    }

    @SuppressLint("ResourceAsColor")
    public DrawableHelper withColor(@ColorRes int colorRes) {
        mColor = ContextCompat.getColor(mContext, colorRes);
        return this;
    }

    @SuppressLint("ResourceAsColor")
    public DrawableHelper tint() {
        if (mDrawable == null) {
            throw new NullPointerException("Must implement withDrawable()");
        }

        if (mColor == 0) {
            throw new IllegalStateException("Must implement withColor()");
        }

        mWrappedDrawable = mDrawable.mutate();
        mWrappedDrawable = DrawableCompat.wrap(mWrappedDrawable);
        DrawableCompat.setTint(mWrappedDrawable, mColor);
        DrawableCompat.setTintMode(mWrappedDrawable, PorterDuff.Mode.SRC_IN);

        return this;
    }

    @SuppressWarnings("deprecation")
    public void applyToBackground(@NonNull View view) {
        if (mWrappedDrawable == null) {
            throw new NullPointerException("Must implement tint()");
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(mWrappedDrawable);
        } else {
            view.setBackgroundDrawable(mWrappedDrawable);
        }
    }

    public void applyTo(@NonNull ImageView imageView) {
        if (mWrappedDrawable == null) {
            throw new NullPointerException("Must implement tint()");
        }

        imageView.setImageDrawable(mWrappedDrawable);
    }

    public void applyTo(@NonNull MenuItem menuItem) {
        if (mWrappedDrawable == null) {
            throw new NullPointerException("Must implement tint()");
        }

        menuItem.setIcon(mWrappedDrawable);
    }

    public Drawable get() {
        if (mWrappedDrawable == null) {
            throw new NullPointerException("Must implement tint()");
        }

        return mWrappedDrawable;
    }
}
