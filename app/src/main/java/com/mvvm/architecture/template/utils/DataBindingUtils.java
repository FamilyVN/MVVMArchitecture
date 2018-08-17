package com.mvvm.architecture.template.utils;

import android.databinding.BindingAdapter;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DataBindingUtils {
    @BindingAdapter("layoutManager")
    public static void setLayoutManager(RecyclerView view, RecyclerView.LayoutManager manager) {
        view.setLayoutManager(manager);
    }

    @BindingAdapter("adapter")
    public static void setAdapter(RecyclerView view, RecyclerView.Adapter adapter) {
        view.setAdapter(adapter);
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
