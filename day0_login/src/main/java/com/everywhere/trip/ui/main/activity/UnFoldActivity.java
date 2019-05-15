package com.everywhere.trip.ui.main.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;

import com.everywhere.trip.R;
import com.everywhere.trip.base.BaseActivity;
import com.everywhere.trip.bean.MainDataUnFOld;
import com.everywhere.trip.net.ApiHome;
import com.everywhere.trip.presenter.UnFoldpresenter;
import com.everywhere.trip.view.main.UnFoldView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class UnFoldActivity extends BaseActivity<UnFoldView, UnFoldpresenter> implements UnFoldView {


    private static final String TAG = "UnFoldActivity";
    @BindView(R.id.un_black)
    ImageView mUnBlack;
    @BindView(R.id.un_share)
    ImageView mUnShare;
    @BindView(R.id.un_review)
    RecyclerView mUnreview;
    @BindView(R.id.bt_preview)
    Button mBtPreview;
    @BindView(R.id.bt_money)
    Button mBtMoney;
    private String url;
    private String tokenw="142BpzvwMQ9q5Wmq0khUh6YQUKrVp0cXsDWvJoNYjv9ngTGRQB3lC6mbx2vSKpdmlOlLkBpinwNiMmoErAEfAglMaJSFEdYLAMJBA7VmVVfdvxGnT40zwQJovosM053PQ";
    private ReUnfoldAdapter adapter;

    @Override
    protected UnFoldpresenter initPresenter() {
        return new UnFoldpresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_un_fold;
    }

    @Override
    public void toastShort(String msg) {

    }

    @Override
    protected void initView() {
        url = getIntent().getStringExtra("url");
        Log.d(TAG, "initView: "+url);

    }


    @OnClick({R.id.un_black, R.id.un_share, R.id.bt_preview, R.id.bt_money})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.un_black:
                finish();
                break;
            case R.id.un_share:
                break;
            case R.id.bt_preview:
                break;
            case R.id.bt_money:
                break;
        }
    }

    @Override
    protected void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiHome.homeurl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        final ApiHome apiHome = retrofit.create(ApiHome.class);
        Observable<MainDataUnFOld> data = apiHome.getunfoldData(url, tokenw);
        data.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<MainDataUnFOld>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: "+d);
                    }

                    @Override
                    public void onNext(MainDataUnFOld mainDataUnFOld) {
                        MainDataUnFOld.ResultEntity result = mainDataUnFOld.getResult();
                        adapter = new ReUnfoldAdapter(UnFoldActivity.this, result);
                        mUnreview.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        mUnreview.setLayoutManager(new LinearLayoutManager(UnFoldActivity.this));

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
