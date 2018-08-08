package com.mvvm.architecture.utils;

import android.databinding.BindingAdapter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DataBindingUtils {
    @BindingAdapter("layoutManager")
    public static void setLayoutManager(RecyclerView view, RecyclerView.LayoutManager manager) {
        view.setLayoutManager(manager);
    }

    @BindingAdapter("adapter")
    public static void setAdapter(RecyclerView view, RecyclerView.Adapter adapter) {
        view.setAdapter(adapter);
    }

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

    @BindingAdapter("android:layout_marginStart")
    public static void setMarginStart(View view, float margin) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams margins = (ViewGroup.MarginLayoutParams) params;
            margins.leftMargin = (int) margin;
            view.requestLayout();
        }
    }

    @BindingAdapter("android:layout_marginBottom")
    public static void setMarginBottom(View view, float margin) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams margins = (ViewGroup.MarginLayoutParams) params;
            margins.bottomMargin = (int) margin;
            view.requestLayout();
        }
    }

    @BindingAdapter("android:text")
    public static void setText(TextView textView, String text) {
        if (TextUtils.isEmpty(text)) {
            textView.setVisibility(View.GONE);
            return;
        }
        textView.setVisibility(View.VISIBLE);
        textView.setText(text);
    }

    @BindingAdapter("font")
    public static void setFont(TextView textView, String font) {
        textView.setTypeface(Typeface.createFromAsset(textView.getContext().getAssets(), font));
    }
}
