package com.mvvm.architecture.template.viewmodel.main;

import android.arch.lifecycle.MutableLiveData;

import com.mvvm.architecture.template.base.BaseViewModel;
import com.mvvm.architecture.template.base.Event;
import com.mvvm.architecture.template.base.EventStatus;
import com.mvvm.architecture.template.data.Repository;
import com.mvvm.architecture.template.data.network.response.BaseResponse;

/**
 * Created by FRAMGIA\mai.dai.dien on 8/22/18.
 */

public class MainViewModel extends BaseViewModel {
    private Repository mRepository;

    public MainViewModel(Repository repository) {
        mRepository = repository;
    }

    /**
     *  function for demo load data
     *  when implement in real project can remove or change name for requirement
     */
    public void loadData() {
        subcribe(
                mRepository.loadData()
                        .doOnSubscribe(__ -> loading.postValue(true))
                        .doAfterTerminate(() -> loading.postValue(false))
                        .subscribe(this::loadSuccess, this::onError)
        );
    }

    private void loadSuccess(BaseResponse response) {
        // todo load success and post value
        viewEvent.postValue(new Event<>(EventStatus.SUCCESS));
    }

    private void onError(Throwable throwable) {
        viewEvent.postValue(new Event<>(EventStatus.NETWORK_ERROR, throwable.getMessage()));
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public MutableLiveData<Event<String, String>> getViewEvent() {
        return viewEvent;
    }
}
