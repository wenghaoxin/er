package com.everywhere.trip.ui.main.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.everywhere.trip.R;
import com.everywhere.trip.base.BaseFragment;
import com.everywhere.trip.base.Constants;
import com.everywhere.trip.bean.BanmiBean;
import com.everywhere.trip.bean.LoginInfo;
import com.everywhere.trip.bean.MainDataBean;
import com.everywhere.trip.net.ApiHome;
import com.everywhere.trip.presenter.HomePresenter;
import com.everywhere.trip.ui.main.activity.UnFoldActivity;
import com.everywhere.trip.ui.main.activity.WebJsActivity;
import com.everywhere.trip.ui.main.adapter.RecHomeAdapter;
import com.everywhere.trip.util.Logger;
import com.everywhere.trip.util.SpUtil;
import com.everywhere.trip.view.main.HomeView;

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

//extends BaseFragment<LoginOrBindView, LoginOrBindPresenter> implements LoginOrBindView
public class HomeFragment extends BaseFragment<HomeView, HomePresenter> implements HomeView {


    private static final String TAG = "HomeFragment";
    @BindView(R.id.home_review)
    RecyclerView mHomeReview;

    private  int pager=1;

    private List<MainDataBean.ResultBean.BannersBean> mBanners=new ArrayList<>();
    private List<MainDataBean.ResultBean.RoutesBean> mRoutes=new ArrayList<>();
    private RecHomeAdapter mAdapter;
    String param = (String) SpUtil.getParam(Constants.TOKEN, "");

    @Override
    protected HomePresenter initPresenter() {
        return new HomePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.honmefrgamnet;
    }


    @Override
    public void toastShort(String msg) {

    }

    @Override
    protected void initView() {
        mHomeReview.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter = new RecHomeAdapter(getContext());
        mHomeReview.setAdapter(mAdapter);

        mAdapter.setOnHomeItemClick(new RecHomeAdapter.onHomeItemClick() {
            @Override
            public void setonHomeItemClick(View view, int i) {
                Intent intent = new Intent(getContext(), UnFoldActivity.class);
                int id = mRoutes.get(i).getId();
                intent.putExtra("url", id+"");
                Log.d(TAG, "setonHomeItemClick: "+ id);
                startActivity(intent);
            }
        });
        mAdapter.setOnBunItemClick(new RecHomeAdapter.onBunItemClick() {
            @Override
            public void setBunItemClick(View view, int newpostion) {
                Intent intent = new Intent(getContext(), WebJsActivity.class);
                String contentURL = mRoutes.get(newpostion).getContentURL();
                String title = mRoutes.get(newpostion).getTitle();
                intent.putExtra("url",contentURL);
                intent.putExtra("title",title);
               startActivity(intent);
            }
        });

    }
    @Override
    protected void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiHome.homeurl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ApiHome home = retrofit.create(ApiHome.class);
        final Observable<MainDataBean> mainData = home.getMainData(pager, param);
        mainData.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<MainDataBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MainDataBean mainDataBean) {
                        List<MainDataBean.ResultBean.BannersBean> banners = mainDataBean.getResult().getBanners();
                        List<MainDataBean.ResultBean.RoutesBean> routes = mainDataBean.getResult().getRoutes();
                        mBanners.addAll(banners);
                        mRoutes.addAll(routes);
                        mAdapter.setBanners(mBanners);
                        mAdapter.setRoutes(mRoutes);

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
