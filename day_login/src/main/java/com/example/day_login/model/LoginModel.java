package com.example.day_login.model;


import com.example.day_login.base.BaseModel;
import com.example.day_login.bean.VerifyCodeBean;
import com.example.day_login.net.ApiService;
import com.example.day_login.net.BaseObserver;
import com.example.day_login.net.HttpUtils;
import com.example.day_login.net.ResultCallBack;
import com.example.day_login.net.RxUtils;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import retrofit2.http.HTTP;

/**
 * @author xts
 *         Created by asus on 2019/4/29.
 *         1.打log,交给日志拦截器
 *         2.无网络的情况下,没有提示
 *         3.加载数据需要loading界面
 */

public class LoginModel extends BaseModel {
    private static final String TAG = "LoginModel";

    public void getVerifyCode(final ResultCallBack<VerifyCodeBean> callBack) {
        ApiService apiserver = HttpUtils.getInstance().getApiserver(ApiService.sBaseUrl, ApiService.class);
        final Observable<VerifyCodeBean> verifyCode = apiserver.getVerifyCode();

        /*verifyCode.compose(RxUtils.<VerifyCodeBean>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<VerifyCodeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(VerifyCodeBean verifyCodeBean) {
                        //Logger.logD(TAG,verifyCodeBean.toString());
                        callBack.onSuccess(verifyCodeBean);
                    }
                });*/
        verifyCode.compose(RxUtils.<VerifyCodeBean>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<VerifyCodeBean>() {
                    @Override
                    public void error(String msg) {

                    }

                    @Override
                    protected void subscribe(Disposable d) {
                       addDisposable(d);
                    }

                    @Override
                    public void onNext(VerifyCodeBean verifyCodeBean) {
                        callBack.onSuccess(verifyCodeBean);
                    }
                });
    }
}
