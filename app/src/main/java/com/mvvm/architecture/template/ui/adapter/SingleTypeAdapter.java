/*
 * Copyright (C) 2016 MarkZhai (http://zhaiyifan.cn).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mvvm.architecture.template.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Super simple single-type adapter using data-binding.
 *
 * @author markzhai on 16/8/22
 */
public class SingleTypeAdapter<T> extends BaseViewAdapter<T> implements ItemTouchHelperAdapter {
    protected int mLayoutRes;

    public SingleTypeAdapter(Context context, int layoutRes) {
        super(context);
        mCollection = new ArrayList<>();
        mLayoutRes = layoutRes;
    }

    @SuppressWarnings("unchecked")
    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BindingViewHolder(
            DataBindingUtil.inflate(mLayoutInflater, getLayoutRes(), parent, false));
    }

    @Override
    public int getItemCount() {
        return mCollection.size();
    }

    public void add(T viewModel) {
        mCollection.add(viewModel);
        notifyDataSetChanged();
    }

    public void add(int position, T viewModel) {
        mCollection.add(position, viewModel);
        notifyDataSetChanged();
    }

    public T get(int position) {
        return mCollection.get(position);
    }

    public void set(List<T> viewModels) {
        mCollection.clear();
        addAll(viewModels);
    }

    public void addAll(List<T> viewModels) {
        if (viewModels == null) return;
        mCollection.addAll(viewModels);
        notifyDataSetChanged();
    }

    @LayoutRes
    protected int getLayoutRes() {
        return mLayoutRes;
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < mCollection.size() && toPosition < mCollection.size()) {
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(mCollection, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(mCollection, i, i - 1);
                }
            }
            notifyItemMoved(fromPosition, toPosition);
            if (mDragCallback != null) {
                mDragCallback.onItemDrag(fromPosition, toPosition);
            }
        }
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mCollection.remove(position);
        notifyItemRemoved(position);
    }

    public interface Presenter<T> extends BaseViewAdapter.Presenter {
        void onItemClick(T t);
    }
}