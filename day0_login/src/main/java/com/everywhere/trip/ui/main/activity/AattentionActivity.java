package com.everywhere.trip.ui.main.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.everywhere.trip.R;
import com.everywhere.trip.base.Constants;
import com.everywhere.trip.bean.BanmiBean;
import com.everywhere.trip.bean.BanmiEntity;
import com.everywhere.trip.bean.FollowedBanmi;
import com.everywhere.trip.net.ApiHome;
import com.everywhere.trip.ui.main.adapter.AttentionAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AattentionActivity extends AppCompatActivity {

    private static final String TAG = "AattentionActivity";
    private RecyclerView mAttentionView;
    private AttentionAdapter mAttentionAdapter;
    private int page = 1;
    private List<FollowedBanmi.ResultBean.BanmiBean> mMist = new ArrayList<>();
    private String token="JVy0IvZamK7f7FBZLKFtoniiixKMlnnJ6dWZ6NlsY4HGsxcAA9qvFo8yacHCKHE8YAcd0UF9L59nEm7zk9AUixee0Hl8EeWA880c0ikZBW0KEYuxQy5Z9NP3BNoBi3o3Q0g";
    private String tokenw="142BpzvwMQ9q5Wmq0khUh6YQUKrVp0cXsDWvJoNYjv9ngTGRQB3lC6mbx2vSKpdmlOlLkBpinwNiMmoErAEfAglMaJSFEdYLAMJBA7VmVVfdvxGnT40zwQJovosM053PQ";
    private ImageView mToolBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aattention);
        initView();
    }

    private void initView() {
        mAttentionView = (RecyclerView) findViewById(R.id.attention_view);
        mToolBack = (ImageView) findViewById(R.id.tool_back);
        mAttentionView.setLayoutManager(new LinearLayoutManager(this));

        mAttentionAdapter = new AttentionAdapter(this, mMist);
        mAttentionView.setAdapter(mAttentionAdapter);
        initData();

        mToolBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiHome.homeurl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiHome home = retrofit.create(ApiHome.class);
        Observable<FollowedBanmi> followedBanmiData = home.getFollowedBanmiData(page,tokenw);
        followedBanmiData.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<FollowedBanmi>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(FollowedBanmi followedBanmi) {
                        List<FollowedBanmi.ResultBean.BanmiBean> banmi = followedBanmi.getResult().getBanmi();
                        Log.d(TAG, "onNext: "+banmi.size());
                        mMist.addAll(banmi);
                        mAttentionAdapter.setmMist(mMist);
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
