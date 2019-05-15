package com.everywhere.trip.ui.main.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.everywhere.trip.R;
import com.everywhere.trip.base.BaseFragment;
import com.everywhere.trip.base.Constants;
import com.everywhere.trip.presenter.LoginOrBindPresenter;
import com.everywhere.trip.ui.main.activity.LoginActivity;
import com.everywhere.trip.ui.main.activity.MainActivity;
import com.everywhere.trip.ui.main.activity.WebActivity;
import com.everywhere.trip.util.Logger;
import com.everywhere.trip.util.SpUtil;
import com.everywhere.trip.util.ToastUtil;
import com.everywhere.trip.util.Tools;
import com.everywhere.trip.view.main.LoginOrBindView;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 5ccebb8a4ca35747450006da
 */

public class LoginOrBindFragment extends BaseFragment<LoginOrBindView, LoginOrBindPresenter> implements LoginOrBindView {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_hello)
    TextView mTvHello;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.tv_coutry_code)
    TextView mTvCoutryCode;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.btn_send_verify)
    Button mBtnSendVerify;
    @BindView(R.id.ll_container)
    LinearLayout mLlContainer;
    @BindView(R.id.view)
    View mView;
    @BindView(R.id.ll_or)
    LinearLayout mLlOr;
    @BindView(R.id.iv_wechat)
    ImageView mIvWechat;
    @BindView(R.id.iv_qq)
    ImageView mIvQq;
    @BindView(R.id.iv_sina)
    ImageView mIvSina;
    @BindView(R.id.tv_protocol)
    TextView mTvProtocol;
    @BindView(R.id.tripartite_ll)
    LinearLayout mTripartiteLl;

    private int mType;
    private VerifyFragment verifyFragment;
    private int TIMER_CODE=10;
    private int mTimer=TIMER_CODE;
    private Handler mHandler;
    //验证码
    private String mVerifyCode="";

    public static LoginOrBindFragment newIntance(int type) {
        /*
         * 创建碎片不要直接new 写一个静态方法*/
        LoginOrBindFragment fragment = new LoginOrBindFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected LoginOrBindPresenter initPresenter() {
        return new LoginOrBindPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login_bind;
    }

    @OnClick({R.id.iv_back, R.id.btn_send_verify, R.id.iv_wechat, R.id.iv_qq, R.id.iv_sina})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                getActivity().finish();
                break;
            case R.id.btn_send_verify:
                String trim = mEtPhone.getText().toString().trim();
                if (TextUtils.isEmpty(trim)){
                    ToastUtil.showShort("手机号码不能为空");
                }
                getVerifyCode();
                addVerifyFragment();
                //避免多次执行
                if (mTimer>0&&mTimer<TIMER_CODE){
                    return;
                }
                countDown();
                break;
            case R.id.iv_wechat:
                mPresenter.oauthLogin(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.iv_qq:
                mPresenter.oauthLogin(SHARE_MEDIA.QQ);
                break;
            case R.id.iv_sina:
                mPresenter.oauthLogin(SHARE_MEDIA.SINA);
                break;
        }
    }
//倒计时
   public  void countDown() {
        if (mHandler==null){
            mHandler = new Handler();
        }
       mHandler.postDelayed(new Runnable() {
           @Override
           public void run() {
               //避免倒计时变成负的
               if (mTimer<=0){
                   mTimer=TIMER_CODE;
                   return;
               }
               mTimer--;
               if (verifyFragment!=null){
                    verifyFragment.setCountDownTimer(mTimer);
                }
                countDown();
           }
       },1000);

    }

    private void getVerifyCode() {
        //mTimer>0&&mTimer!=TIMER_CODE
        if (mTimer>0&&mTimer<TIMER_CODE-1){
            //倒计时中
            return;
        }
        mVerifyCode="";
        mPresenter.getVerifyCode();
    }

    private void addVerifyFragment() {
        if (TextUtils.isEmpty(getPhone())) {
            return;
        }
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        //添加到回退栈
        fragmentTransaction.addToBackStack(null);
        verifyFragment = verifyFragment.newINtance(mVerifyCode);
        fragmentTransaction.add(R.id.fl_container, verifyFragment).commit();
        //关闭软件盘

        Tools.closeKeyBoard(getActivity());
    }

    @Override
    protected void initListener() {
        //文本发生改变监听
        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                switchBtnState(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 根据输入框中是否有内容,切换发送验证码的背景
     *
     * @param s
     */
    private void switchBtnState(CharSequence s) {
        if (TextUtils.isEmpty(s)) {
            mBtnSendVerify.setBackgroundResource(R.drawable.bg_btn_ea_r15);
        } else {
            mBtnSendVerify.setBackgroundResource(R.drawable.bg_btn_fa6a13_r15);
        }

    }

    @Override
    public String getPhone() {
        return mEtPhone.getText().toString().trim();
    }

    @Override
    public Activity getAct() {
        return getActivity();
    }

    @Override
    public void go2MainActivity() {
        MainActivity.startAct(getContext());
    }

    @Override
    public void setData(String code) {
        this.mVerifyCode=code;
        if (verifyFragment!=null){
            verifyFragment.setData(code);

        }
    }


    @Override
    protected void initView() {

        if (!TextUtils.isEmpty((String) SpUtil.getParam(Constants.TOKEN,""))){
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
        /*
         * 隐藏返回键
         * */
        getArgumentsData();
        /*
         * 设置协议
         * */
        setProtocol();
        //隐藏
        showorHideView();

    }
    //应为登录和绑定手机号码是使用的一个碎片，所以需要使用type隐藏和显示某一view
    private void showorHideView() {
        if (mType == LoginActivity.TYPR_LOGIN) {
            /*
             * VISIBLE 显示
             * INVISIBLE 隐藏 有占位符的隐藏
             * GONE 隐藏 ，不带占位符
             * */
            mIvBack.setVisibility(View.INVISIBLE);
            mTripartiteLl.setVerticalGravity(View.VISIBLE);
            mLlOr.setVerticalGravity(View.VISIBLE);
        } else {
            mIvBack.setVisibility(View.VISIBLE);
            mTripartiteLl.setVerticalGravity(View.INVISIBLE);
            mLlOr.setVerticalGravity(View.INVISIBLE);

        }
    }

    private void setProtocol() {
        SpannableStringBuilder sb = new SpannableStringBuilder(getResources().getString(R.string.agree_protocol));
        //点击时间
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                getActivity().startActivity(new Intent(getContext(), WebActivity.class));
            }
        };
        sb.setSpan(clickableSpan, 13, 17, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //下划线
        UnderlineSpan underlineSpan = new UnderlineSpan();
        sb.setSpan(underlineSpan, 13, 17, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //前景色
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.c_fa6a13));
        sb.setSpan(colorSpan, 13, 17, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        //需要设置这个Clickablespan才会有效果
        mTvProtocol.setMovementMethod(LinkMovementMethod.getInstance());
        mTvProtocol.setText(sb);
    }

    private void getArgumentsData() {
        Bundle arguments = getArguments();
        mType = arguments.getInt(Constants.TYPE);
    }

    @Override
    public void toastShort(String msg) {

    }



}
