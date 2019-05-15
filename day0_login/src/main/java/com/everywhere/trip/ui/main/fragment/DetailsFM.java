package com.everywhere.trip.ui.main.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.everywhere.trip.R;
import com.everywhere.trip.base.BaseFragment;
import com.everywhere.trip.bean.MainDataBean;
import com.everywhere.trip.net.ApiHome;
import com.everywhere.trip.presenter.DetailsPresenter;
import com.everywhere.trip.ui.main.adapter.DeViewAdapter;
import com.everywhere.trip.util.Logger;
import com.everywhere.trip.view.main.DetailsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsFM extends BaseFragment<DetailsView, DetailsPresenter> implements DetailsView {
    private static final String TAG = "DetailsFM";
    @BindView(R.id.details_view)
    RecyclerView mDetailsView;
    private int  pag=1;
    private String tokenw="142BpzvwMQ9q5Wmq0khUh6YQUKrVp0cXsDWvJoNYjv9ngTGRQB3lC6mbx2vSKpdmlOlLkBpinwNiMmoErAEfAglMaJSFEdYLAMJBA7VmVVfdvxGnT40zwQJovosM053PQ";
    private List<MainDataBean.ResultBean.RoutesBean> mRoutesList=new ArrayList<>();
    private DeViewAdapter mDeViewAdapter;

    //伴米 线路
    @Override
    protected DetailsPresenter initPresenter() {
        return new DetailsPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.particularsfm;
    }

    @Override
    public void toastShort(String msg) {

    }

    @Override
    protected void initView() {
        mDetailsView.setLayoutManager(new LinearLayoutManager(getContext()));
        mDeViewAdapter = new DeViewAdapter(getContext(), mRoutesList);
        mDetailsView.setAdapter(mDeViewAdapter);


    }
    @Override
    protected void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiHome.homeurl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ApiHome home = retrofit.create(ApiHome.class);
        final Observable<MainDataBean> mainData = home.getMainData(pag, tokenw);
        mainData.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<MainDataBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MainDataBean mainDataBean) {
                        List<MainDataBean.ResultBean.RoutesBean> routes = mainDataBean.getResult().getRoutes();
                        mRoutesList.addAll(routes);
                        mDeViewAdapter.setmRoutesList(mRoutesList);
                        Log.d(TAG, "onNext: "+mRoutesList.size());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.print("数据错误:"+e);
                    }

                    @Override
                    public void onComplete() {}
                });
    }
}
