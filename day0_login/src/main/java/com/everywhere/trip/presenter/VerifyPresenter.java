package com.everywhere.trip.presenter;

import com.everywhere.trip.R;
import com.everywhere.trip.base.BaseApp;
import com.everywhere.trip.base.BaseModel;
import com.everywhere.trip.base.BaseMvpView;
import com.everywhere.trip.base.BasePresenter;
import com.everywhere.trip.bean.VerifyCodeBean;
import com.everywhere.trip.model.LoginModel;
import com.everywhere.trip.net.ApiService;
import com.everywhere.trip.net.ResultCallBack;
import com.everywhere.trip.view.main.EmptyView;
import com.everywhere.trip.view.main.VerifyView;

/**
 * @author xts
 *         Created by asus on 2019/5/4.
 */

public class VerifyPresenter extends BasePresenter<VerifyView> {

    private LoginModel mLoginModel;

    @Override
    protected void initModel() {
        mLoginModel = new LoginModel();
        mModels.add(mLoginModel);

    }

    public void getVerifyCode() {
    mLoginModel.getVerifyCode(new ResultCallBack<VerifyCodeBean>() {
        @Override
        public void onSuccess(VerifyCodeBean bean) {
            if (bean!=null&&bean.getCode()==ApiService.SUCCESS_CODE){
                    if (mMvpView!=null){
                        mMvpView.setData(bean.getData());
                    }
            }else {
              if (mMvpView!=null) {
                  mMvpView.toastShort(BaseApp.getRes().getString(R.string.get_verify_fail));
              }
            }
        }

        @Override
        public void onFail(String msg) {

        }
    });
    }
}