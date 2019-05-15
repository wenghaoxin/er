package com.everywhere.trip.ui.main.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.everywhere.trip.R;
import com.everywhere.trip.base.BaseActivity;
import com.everywhere.trip.base.Constants;
import com.everywhere.trip.presenter.PIMPresenter;
import com.everywhere.trip.util.FileProviderUtils;
import com.everywhere.trip.util.PhotosUtils;
import com.everywhere.trip.util.SPutils;
import com.everywhere.trip.view.main.PIMView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PIMActivity extends BaseActivity<PIMView, PIMPresenter> implements PIMView {
    private static final String TAG = "PIMActivity";
    @BindView(R.id.tool_tv)
    TextView mToolTv;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.img)
    ImageView mImg;
    @BindView(R.id.reimg)
    RelativeLayout mReimg;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.name)
    RelativeLayout mName;
    @BindView(R.id.tv_sex)
    TextView mTvSex;
    @BindView(R.id.sex)
    RelativeLayout mSex;
    @BindView(R.id.tv_signature)
    TextView mTvSignature;
    @BindView(R.id.signature)
    RelativeLayout mSignature;
    @BindView(R.id.tv_changes)
    RelativeLayout mTvChanges;
    @BindView(R.id.phone)
    RelativeLayout mPhone;
    @BindView(R.id.bt_logout)
    Button mBtLogout;


    private String icon = "http://yun918.cn/study/public/index.php/uploadheader";
    private String uploadUrl = "http://yun918.cn/study/public/file_upload.php";
    File camerafile;
    private PopupWindow window;
    private PopupWindow windowsex;
    private String name;
    private String signature;

    @Override
    protected PIMPresenter initPresenter() {
        return new PIMPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pim;
    }

    @Override
    public void toastShort(String msg) {

    }

/*
* 头像
* */
    private void initPopo() {
        View view = View.inflate(this, R.layout.layout_headerpop, null);
        Button cemera = view.findViewById(R.id.cemera);
        Button photo = view.findViewById(R.id.photo);
        window = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.showAsDropDown(mImg);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        window.setBackgroundDrawable(new ColorDrawable());
        window.setOutsideTouchable(true);

        initpopListenter(cemera, photo);
    }

    private void initpopListenter(Button cemera, Button photo) {
        //相机
        cemera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建意图
                Intent intentc = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //启动拍照的Activity
                startActivityForResult(intentc, 14);
                window.dismiss();
            }
        });
            //相册
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.PICK");
                //设置要打开图片的类型
                intent.setType("image/*");
                //启动相册应用
                startActivityForResult(intent, 100);
                window.dismiss();
            }
        });
    }
    private void goSingature() {
        //跳转修改个性签名界面
        String sing = mTvSignature.getText().toString().trim();
        Intent intent = new Intent(this, SingatureActivity.class);
        intent.putExtra("sing", sing);
        startActivityForResult(intent, Constants.INITENTCODE);

    }

    private void gotNameActivity() {
        //跳转修改名称界面
        String name = mTvName.getText().toString().trim();
        Intent intent = new Intent(this, NameActivity.class);
        intent.putExtra("name", name);
        startActivityForResult(intent, Constants.CODE);
    }

    @Override
    protected void initView() {
     //数据从侧滑界面传过来
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String sig = intent.getStringExtra("sig");
        mTvName.setText(name);
        mTvSignature.setText(sig);

        String sex = mTvSex.getText().toString().trim();
        SPutils.put(PIMActivity.this,"sex",sex);

        //toolbar
        mToolbar.setNavigationIcon(R.drawable.back_white);

        mToolbar.setTitle("");
        /*
         * toolbar坐上角箭头点击后返回上个界面*/
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }
    /*
     * toolbar坐上角箭头点击后返回上个界面*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            name = mTvName.getText().toString().trim();
            signature = mTvSignature.getText().toString().trim();
            Intent intent = new Intent();
            intent.putExtra(Constants.NAME, name);
            intent.putExtra(Constants.SIGNATURE, signature);
            setResult(Constants.INITENTCODE,intent);
            finish();
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.CODE && resultCode == Constants.INITENT) {
            String name = data.getStringExtra(Constants.INITENTs);
            mTvName.setText(name);
            SPutils.put(this,"name",name);

        }
        if (requestCode == Constants.INITENTCODE && resultCode == Constants.CODE) {
            String sing = data.getStringExtra(Constants.INITENTs);
            mTvSignature.setText(sing);
            SPutils.put(this,"sig",sing);
        }
        // 相册
        //对暗号
        if (requestCode == 100 && resultCode == RESULT_OK) {
            //从intent对象中获取数据
            Uri uri = data.getData();
            //把获取到的图片设置给ImageView
            mImg.setImageURI(uri);
        }
        //相机
        if (requestCode == 14 && resultCode == RESULT_OK) {
            //从intent对象中获取数据
            Bitmap bitmap = data.getParcelableExtra("data");
            //把照片在imageView中显示
            mImg.setImageBitmap(bitmap);
        }
    }


    @OnClick({R.id.tool_tv, R.id.toolbar, R.id.reimg, R.id.name, R.id.sex, R.id.signature, R.id.phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tool_tv:
                break;
            case R.id.toolbar:
                break;
            case R.id.reimg:
                //头像
                initPopo();
                break;
            case R.id.name:
                //用户昵称
                gotNameActivity();
                break;
            case R.id.sex:
                //性别
                initsexpop();
                break;
            case R.id.signature:
                //个性签名
                goSingature();
                break;
            case R.id.phone:
                //绑定手机
                break;
            case R.id.tv_changes:
                //修改密码
                break;
            case R.id.bt_logout:
                //退出
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }
/*性别的pop*/
    private void initsexpop() {
        View view = View.inflate(this, R.layout.layout_sexpop, null);
        windowsex = new PopupWindow(view, 180, 220);
        windowsex.showAsDropDown(mSex);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                windowsex.dismiss();
            }
        });
        windowsex.setBackgroundDrawable(new ColorDrawable());
        windowsex.setOutsideTouchable(true);

        final TextView mTvwoman = view.findViewById(R.id.tv_woman);
        final String trim = mTvwoman.getText().toString().trim();
        final TextView mTvman = view.findViewById(R.id.tv_mann);
        final String man = mTvman.getText().toString().trim();
        final TextView mTvsecrecy = view.findViewById(R.id.tv_secrecy);
        final String secrecy = mTvsecrecy.getText().toString().trim();
        mTvwoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvSex.setText(trim);
                windowsex.dismiss();
                SPutils.put(PIMActivity.this,"sex",trim);
            }
        });

        mTvman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvSex.setText(man);
                windowsex.dismiss();
                SPutils.put(PIMActivity.this,"sex",man);
            }
        });
        mTvsecrecy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvSex.setText(secrecy);
                windowsex.dismiss();
                SPutils.put(PIMActivity.this,"sex",secrecy);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        String sex = (String) SPutils.get(this, "sex", "");
        String name = (String) SPutils.get(this, "name", "");
        String sig = (String) SPutils.get(this, "sig", "");
        mTvSex.setText(sex);
        mTvName.setText(name);
        mTvSignature.setText(sig);
    }


}