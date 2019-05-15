package com.example.day_login.presenter;


import com.example.day_login.base.BasePresenter;
import com.example.day_login.bean.VerifyCodeBean;
import com.example.day_login.model.LoginModel;
import com.example.day_login.net.ResultCallBack;
import com.example.day_login.view.main.LoginView;

/**
 * @author xts
 *         Created by asus on 2019/4/29.
 */

public class LoginPresenter extends BasePresenter<LoginView> {
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

            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
