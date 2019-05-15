package com.everywhere.trip.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.everywhere.trip.R;
import com.everywhere.trip.base.Constants;

public class NameActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mIvBack;
    /**
     * 拌小米
     */
    private EditText mTvName;
    /**
     * 27/27
     */
    private TextView mTvFigure;
    /**
     * 完成
     */
    private TextView mTvTrue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        initView();

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        mTvName.setText(name);
        Log.d("dgf", "onCreate: "+name);
        //光标移到到最后一位
        mTvName.setSelection(mTvName.length());
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvName = (EditText) findViewById(R.id.tv_name);


        mIvBack.setOnClickListener(this);
        mTvName.setOnClickListener(this);
        mTvFigure = (TextView) findViewById(R.id.tv_figure);

        mTvName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                mTvFigure.setText((27-length) + "/27");
            }
        });


        mTvTrue = (TextView) findViewById(R.id.tv_true);
        mTvTrue.setOnClickListener(this);
        private String aa="wsec";
        
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_name:
                break;
            case R.id.tv_true:
                initData();
                break;
        }
    }

    private void initData() {
        String name = mTvName.getText().toString().trim();
        Intent intent = new Intent();
        intent.putExtra(Constants.INITENTs, name);
        setResult(Constants.INITENT, intent);
        finish();
    }
}
