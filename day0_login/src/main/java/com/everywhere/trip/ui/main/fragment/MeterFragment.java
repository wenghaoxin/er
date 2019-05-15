package com.everywhere.trip.ui.main.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.everywhere.trip.R;
import com.everywhere.trip.base.BaseFragment;
import com.everywhere.trip.base.Constants;
import com.everywhere.trip.bean.BanmiBean;
import com.everywhere.trip.bean.BanmiEntity;
import com.everywhere.trip.presenter.MeterPresenter;
import com.everywhere.trip.ui.main.activity.ParticularsActivity;
import com.everywhere.trip.ui.main.adapter.MenterAdapter;
import com.everywhere.trip.ui.main.adapter.MeterFmAdapter;
import com.everywhere.trip.util.SpUtil;
import com.everywhere.trip.util.ToastUtil;
import com.everywhere.trip.view.main.MeterView;
import com.umeng.commonsdk.framework.UMLogDataProtocol;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

//extends BaseFragment<LoginOrBindView, LoginOrBindPresenter> implements LoginOrBindView
public class MeterFragment extends BaseFragment<MeterView, MeterPresenter> implements MeterView {
    private static final String TAG = "MeterFragment";
    @BindView(R.id.meterview)
    RecyclerView mMeterview;
    private List<BanmiEntity> mMist=new ArrayList<>();
    private MenterAdapter mMenterAdapter;
    private String token="JVy0IvZamK7f7FBZLKFtoniiixKMlnnJ6dWZ6NlsY4HGsxcAA9qvFo8yacHCKHE8YAcd0UF9L59nEm7zk9AUixee0Hl8EeWA880c0ikZBW0KEYuxQy5Z9NP3BNoBi3o3Q0g";
    private String tokenw="142BpzvwMQ9q5Wmq0khUh6YQUKrVp0cXsDWvJoNYjv9ngTGRQB3lC6mbx2vSKpdmlOlLkBpinwNiMmoErAEfAglMaJSFEdYLAMJBA7VmVVfdvxGnT40zwQJovosM053PQ";
    private  int pager=1;
    String param = (String) SpUtil.getParam(Constants.TOKEN, "");



    @Override
    protected MeterPresenter initPresenter() {
        return new MeterPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.meterfragment;
    }

    @Override
    public void toastShort(String msg) {
    }

    @Override
    protected void initView() {
        mPresenter.onPresenter(pager,tokenw);
        mMeterview.setLayoutManager(new LinearLayoutManager(getContext()));
        mMenterAdapter = new MenterAdapter(getContext(), mMist);
        mMeterview.setAdapter(mMenterAdapter);
        //接口回调
        mMenterAdapter.setOnBanmiClick(new MenterAdapter.onBanmiClick() {
            @Override
            public void setonBanminClick(View view, int i) {
                //名字
                String name = mMist.get(i).getName().trim();
                //图片
                String photo = mMist.get(i).getPhoto().trim();
                //关注人数
                int following = mMist.get(i).getFollowing();
                //地址
                String location = mMist.get(i).getLocation();
                //作者
                String occupation = mMist.get(i).getOccupation();
                //简介
                String introduction = mMist.get(i).getIntroduction();
                //心
                boolean isFollowed = mMist.get(i).getIsFollowed();
                //id
                int id = mMist.get(i).getId();


                Intent intent = new Intent(getContext(), ParticularsActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("photo",photo);
                intent.putExtra("following",following+"");
                intent.putExtra("location",location);
                intent.putExtra("occupation",occupation);
                intent.putExtra("introduction",introduction);
                intent.putExtra("isFollowed",isFollowed);
                intent.putExtra("id",id+"");
                startActivity(intent);
            }
        });
        //心形关注
        mMenterAdapter.setLike(new MenterAdapter.OnItenClickListener() {
            @Override
            public void like(int id) {
                mPresenter.addLike(tokenw,id);
                Log.d(TAG, "like: "+id);
            }

            @Override
            public void remove(int id) {
                mPresenter.disLike(tokenw,id);

            }
        });

    }

    @Override
    public void successData(BanmiBean banmiBean) {
        //List<BanmiBean.ResultEntity.BanmiEntity> banmi = banmiBean.getResult().getBanmi();
        BanmiBean.ResultEntity result = banmiBean.getResult();
        List<BanmiEntity> banmi = result.getBanmi();
        mMist.addAll(banmi);
        mMenterAdapter.setmMist(mMist);
    }

    @Override
    public void ErrorData(String e) {

    }
}
