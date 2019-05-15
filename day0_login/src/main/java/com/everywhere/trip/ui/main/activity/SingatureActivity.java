package com.everywhere.trip.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.everywhere.trip.R;
import com.everywhere.trip.base.Constants;
import com.everywhere.trip.util.Logger;

public class SingatureActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mIvBack;
    /**
     * 四肢不全五体不勤好吃懒做
     */
    private EditText mTvSing;
    /**
     * 27/27
     */
    private TextView mTvNumbar;
    /**
     * 完成
     */
    private TextView mTvTrue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singature);
        initView();

        Intent intent = getIntent();
        String sing = intent.getStringExtra("sing");
        mTvSing.setText(sing);
        //光标移动到最后一位
        mTvSing.setSelection(mTvSing.length());

    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);
        mTvSing = (EditText) findViewById(R.id.tv_sing);
        mTvNumbar = (TextView) findViewById(R.id.tv_numbar);

        mTvTrue = (TextView) findViewById(R.id.tv_true);
        mTvTrue.setOnClickListener(this);

        mTvSing.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                mTvNumbar.setText( "27/"+(27-length));
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_true:
                initData();
                break;
        }
    }

    private void initData() {
        String name =mTvSing.getText().toString().trim();
        Intent intent = new Intent();
        intent.putExtra(Constants.INITENTs, name);
        setResult(Constants.CODE, intent);
        finish();
    }

}
