package com.mvvm.architecture.template.viewmodel.main;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.mvvm.architecture.template.data.Repository;

/**
 * Created by FRAMGIA\mai.dai.dien on 8/22/18.
 */
public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Repository mRepository;

    public MainViewModelFactory(Repository repository) {
        mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainViewModel(mRepository);
    }
}
