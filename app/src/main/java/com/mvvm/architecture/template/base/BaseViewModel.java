package com.mvvm.architecture.template.base;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseViewModel<N> extends ViewModel {
    protected MutableLiveData<Boolean> loading = new MutableLiveData<>();
    protected MutableLiveData<Event<String, String>> viewEvent = new MutableLiveData<>();
    private WeakReference<N> mNavigator;
    private CompositeDisposable mDisposables = new CompositeDisposable();

    @Override
    protected void onCleared() {
        mDisposables.clear();
        super.onCleared();
    }

    protected N getNavigator() {
        return mNavigator.get();
    }

    public void setNavigator(N navigator) {
        mNavigator = new WeakReference<>(navigator);
    }

    public void subcribe(Disposable disposable) {
        mDisposables.add(disposable);
    }

    public CompositeDisposable getDisposables() {
        return mDisposables;
    }
}
