package com.everywhere.trip.ui.main.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.everywhere.trip.R;
import com.everywhere.trip.base.BaseFragment;
import com.everywhere.trip.bean.DyData;

import com.everywhere.trip.presenter.DynamicConditionPresenter;
import com.everywhere.trip.ui.main.adapter.DyViewAdapter;
import com.everywhere.trip.view.main.DynamicConditionView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DynamicConditionFM extends BaseFragment<DynamicConditionView, DynamicConditionPresenter> implements DynamicConditionView {
    private static final String TAG = "DynamicConditionFM";
    @BindView(R.id.dy_view)
    RecyclerView mDyView;
    private int  pag=1;
    private String tokenw="142BpzvwMQ9q5Wmq0khUh6YQUKrVp0cXsDWvJoNYjv9ngTGRQB3lC6mbx2vSKpdmlOlLkBpinwNiMmoErAEfAglMaJSFEdYLAMJBA7VmVVfdvxGnT40zwQJovosM053PQ";
    private List<DyData.ResultBean.ActivitiesBean> mDylist=new ArrayList<>();
    private DyViewAdapter mDyViewAdapter;
    private String id;

    //动态 伴米
    @Override
    protected DynamicConditionPresenter initPresenter() {
        return new DynamicConditionPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dynamiccondiyionfm;
    }

    @Override
    public void toastShort(String msg) {

    }

    @Override
    protected void initView() {
        Bundle arguments = getArguments();
        id = arguments.getString("id");

        mPresenter.initDyPresneter(id,tokenw,pag);

        mDyView.setLayoutManager(new LinearLayoutManager(getContext()));
        mDyViewAdapter = new DyViewAdapter(getContext(), mDylist);
        mDyView.setAdapter(mDyViewAdapter);

    }
    @Override
    public void CorrectData(DyData dyData) {
        List<DyData.ResultBean.ActivitiesBean> activities = dyData.getResult().getActivities();
        if (activities!=null){
            mDylist.addAll(activities);
            mDyViewAdapter.setmDylist(mDylist);
        }

    }

    @Override
    public void ErrorData(String data) {
        Log.d(TAG, "ErrorData: "+data);
    }

  /*  @Override
    protected void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiHome.homeurl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiHome apiHome = retrofit.create(ApiHome.class);
        final Observable<DyData> dyBanmiData = apiHome.getDyBanmiData(id,pag, tokenw);
        dyBanmiData.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DyData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: "+d);
                    }

                    @Override
                    public void onNext(DyData dyData) {
                        List<DyData.ResultBean.ActivitiesBean> activities = dyData.getResult().getActivities();

                        Log.d(TAG, "onNext: "+activities.size());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }*/
}
