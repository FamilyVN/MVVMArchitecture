package com.mvvm.architecture.example.ui.activity.example;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;

import com.mvvm.architecture.R;
import com.mvvm.architecture.databinding.ActivityExampleBinding;
import com.mvvm.architecture.template.base.BaseActivity;
import com.mvvm.architecture.template.lifecycle.Constant;
import com.mvvm.architecture.template.utils.AdMobUtils;

public class ExampleActivity extends BaseActivity<ActivityExampleBinding, ExampleViewModel>
    implements ExampleNavigator {
    @Override
    public int getLayoutId() {
        return R.layout.activity_example;
    }

    @Override
    public ExampleViewModel getViewModel() {
        mViewModel = ViewModelProviders.of(this).get(ExampleViewModel.class);
        return mViewModel;
    }

    @Override
    public void initViews() {
        mViewModel.setNavigator(this);
        // native ads
        AdMobUtils.initInterstitialAds(this);
        AdMobUtils.loadNativeAd(this, mBinding.frAdsShare);
    }

    @Override
    public void initBannerAds() {
        AdMobUtils.initBannerAds(mBinding.adBanner);
    }

    @Override
    public void goToFeedBack() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{Constant.FEEDBACK_URL});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_feedback_subject));
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(
                Intent.createChooser(emailIntent, getString(R.string.title_send_feedback)));
        }
    }

    @Override
    public void onShareApp() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
            getString(R.string.text_share_content)
                + " "
                + Constant.GOOGLE_PLAY_STORE
                + getPackageName());
        sendIntent.setType("text/plain");
        startActivity(
            Intent.createChooser(sendIntent, getString(R.string.text_main_title_share_app)));
    }
}
