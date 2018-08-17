package com.mvvm.architecture.template.ui.adapter;

import android.content.Context;

import com.mvvm.architecture.BR;

public class SingleTypeExtendAdapter<T> extends SingleTypeAdapter<T> {
    protected int mParentPosition;

    public SingleTypeExtendAdapter(Context context, int layoutRes) {
        super(context, layoutRes);
    }

    public int getParentPosition() {
        return mParentPosition;
    }

    public void setParentPosition(int parentPosition) {
        mParentPosition = parentPosition;
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.getBinding().setVariable(BR.parentPosition, mParentPosition);
    }
}
