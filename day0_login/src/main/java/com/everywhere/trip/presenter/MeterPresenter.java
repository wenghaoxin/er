package com.everywhere.trip.presenter;

import com.everywhere.trip.base.BasePresenter;
import com.everywhere.trip.bean.BanmiBean;
import com.everywhere.trip.model.MMode;
import com.everywhere.trip.net.ResultCallBack;
import com.everywhere.trip.view.main.HomeView;
import com.everywhere.trip.view.main.MView;
import com.everywhere.trip.view.main.MeterView;

public class MeterPresenter extends BasePresenter<MeterView> {

    MMode mMode;
    @Override
    protected void initModel() {
        mMode=new MMode();
        mModels.add(mMode);
    }
    public  void  onPresenter(int pagr,String token){
       mMode.initMode(pagr, token, new ResultCallBack<BanmiBean>() {
           @Override
           public void onSuccess(BanmiBean bean) {
               mMvpView.successData(bean);
           }

           @Override
           public void onFail(String msg) {
                mMvpView.ErrorData(msg);
           }
       });
    }

    public void addLike(String token,int id){
        mMode.addLike(token, id, new ResultCallBack<String>() {
            @Override
            public void onSuccess(String bean) {
                mMvpView.toastShort(bean);
            }

            @Override
            public void onFail(String msg) {
                mMvpView.toastShort(msg);
            }
        });
    }

    public void disLike(String token,int id){
        mMode.disLike(token, id, new ResultCallBack<String>() {
            @Override
            public void onSuccess(String bean) {
                mMvpView.toastShort(bean);
            }

            @Override
            public void onFail(String msg) {
                mMvpView.toastShort(msg);
            }
        });
    }
}
