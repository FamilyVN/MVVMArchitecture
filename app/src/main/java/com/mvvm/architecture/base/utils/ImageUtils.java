package com.mvvm.architecture.base.utils;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageUtils {
    @BindingAdapter("app:srcCompat")
    public static void bindSrcCompat(ImageView imageView, Drawable drawable) {
        Glide.with(imageView.getContext())
            .load(drawable)
            .into(imageView);
    }

    @BindingAdapter("app:icon")
    public static void setIcon(ImageView imageView, int drawable) {
        if (drawable == 0) return;
        imageView.setImageResource(drawable);
    }

    @BindingAdapter("app:icon")
    public static void setIcon(ImageView imageView, Drawable drawable) {
        if (drawable == null) return;
        imageView.setImageDrawable(drawable);
    }
}
