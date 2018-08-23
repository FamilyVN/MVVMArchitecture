package com.mvvm.architecture.template.base;

import android.app.ProgressDialog;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.mvvm.architecture.BR;
import com.mvvm.architecture.template.utils.CommonUtils;

public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel>
    extends AppCompatActivity implements BaseNavigator {
    protected T mViewDataBinding;
    protected V mViewModel;
    private ProgressDialog mProgressDialog;

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();
    /**
     * Override for set view model
     *
     * @return view model instance
     */
    public abstract V getViewModel();

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        performDataBinding();
        initViews();
        initBannerAds();
    }

    protected void showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = CommonUtils.showLoadingDialog(this);
            mProgressDialog.show();
        }
    }

    protected void hideLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    protected void showAlertMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setPositiveButton(getString(android.R.string.ok),
            (dialog, which) -> {
                dialog.dismiss();
            });
        builder.show();
    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        mViewDataBinding.setLifecycleOwner(this);
        mViewModel = getViewModel();
        mViewDataBinding.setVariable(BR.viewModel, mViewModel);
        mViewDataBinding.executePendingBindings();
    }

    @Override
    protected void onDestroy() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        super.onDestroy();
    }

    @Override
    public void initBannerAds() {
    }

    @Override
    public void initViews() {
    }
}
