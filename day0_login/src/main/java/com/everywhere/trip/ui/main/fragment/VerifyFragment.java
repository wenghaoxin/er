package com.everywhere.trip.ui.main.fragment;

import android.content.Intent;
import android.media.DrmInitData;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.everywhere.trip.R;
import com.everywhere.trip.base.BaseApp;
import com.everywhere.trip.base.BaseFragment;
import com.everywhere.trip.base.Constants;
import com.everywhere.trip.presenter.VerifyPresenter;
import com.everywhere.trip.ui.main.activity.LoginActivity;
import com.everywhere.trip.ui.main.activity.MainActivity;
import com.everywhere.trip.view.main.VerifyView;
import com.everywhere.trip.widget.IdentifyingCodeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author xts
 * Created by asus on 2019/5/4.
 */

public class VerifyFragment extends BaseFragment<VerifyView, VerifyPresenter> implements VerifyView {
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_send_again)
    TextView mTvSendAgain;
    @BindView(R.id.icv)
    IdentifyingCodeView mIcv;
    @BindView(R.id.tv_wait)
    TextView mTvWait;
    private int countDownTimer;
    private int mTime;

    public  static  VerifyFragment newINtance(String code){
        VerifyFragment verifyFragment = new VerifyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.VERIFY_CODE,code);
        verifyFragment.setArguments(bundle);
        return  verifyFragment;
    }

    @Override
    protected VerifyPresenter initPresenter() {
        return new VerifyPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_verify;
    }

    @Override
    public void toastShort(String msg) {

    }

    @Override
    protected void initView() {
        String code = getArguments().getString(Constants.VERIFY_CODE);
        if (TextUtils.isEmpty(code)) {
            //mTvWait.setText(code);
            setData(code);
        }

    }



    @OnClick({R.id.iv_back, R.id.tv_send_again})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                pop();
                break;
            case R.id.tv_send_again:
                //调用它是有条件的
               if (mTime==0){
                   mPresenter.getVerifyCode();
                   //重新发起倒计时
                   LoginOrBindFragment fragmentByTag = (LoginOrBindFragment) getActivity().getSupportFragmentManager().findFragmentByTag(LoginActivity.TAG);
                    fragmentByTag.countDown();
               }
                break;
        }
    }


    /*
     * 碎片手动弹栈 返回上一个碎片
     * */
    private void pop() {
        //碎片管理器
        FragmentManager manager = getActivity().getSupportFragmentManager();
        //返回
        manager.popBackStack();
    }

    @Override
    protected void initData() {
        //正常请求发送短信的接口在前frgament调用，在倒计时结束之后不允许再次调用，这里因为假的所以每次都调用了
        //mPresenter.getVerifyCode();

       /* String textContent = mIcv.getTextContent();

        if (TextUtils.isEmpty(textContent)){

        }*/
    }

    @Override
    public void setData(String data) {
        if (!TextUtils.isEmpty(data)&& mTvWait!=null){
            mTvWait.setText(BaseApp.getRes().getString(R.string.verify_code)+data);
        }
    }


    @Override
    protected void initListener() {
        mIcv.setOnEditorActionListener(new IdentifyingCodeView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }

            @Override
            public void onTextChanged(String s) {
                autLogin( mIcv.getTextContent());
            }
        });
    }

    private void autLogin(String s) {
        if (mIcv.getTextContent().length()>=4){
            //自动登录
            toastShort("自动登录");
            mIcv.setBackgroundEnter(false);
            mTvWait.setText(BaseApp.getRes().getString(R.string.timer));
            showLoading();
            startActivity(new Intent(getActivity(),MainActivity.class));
        }
    }

    public void setCountDownTimer(int time) {
   if (mTvSendAgain!=null){
       mTime=time;
       if (time!=0){
           //当time不为0的时候
           String format = String.format(getResources().getString(R.string.send_again) + "(%s)", time);
           mTvSendAgain.setText(format);
           mTvSendAgain.setTextColor(getResources().getColor(R.color.c_999));
       }else {
           //当time为0的时候
           mTvSendAgain.setText(getResources().getString(R.string.send_again));
           mTvSendAgain.setTextColor(getResources().getColor(R.color.c_FA6A13));
       }

   }
    }

    public int getCountDownTimer() {
        return countDownTimer;
    }
}
