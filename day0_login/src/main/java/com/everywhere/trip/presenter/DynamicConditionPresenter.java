package com.everywhere.trip.presenter;

import com.everywhere.trip.base.BasePresenter;
import com.everywhere.trip.bean.DyData;
import com.everywhere.trip.model.DynamicConditionModel;
import com.everywhere.trip.net.ResultCallBack;
import com.everywhere.trip.view.main.DynamicConditionView;

public class DynamicConditionPresenter extends BasePresenter<DynamicConditionView> {
    DynamicConditionModel Dymodel;
    @Override
    protected void initModel() {
        Dymodel=new DynamicConditionModel();
        mModels.add(Dymodel);

    }
    public  void initDyPresneter(String id,String tooke,int pag ){

        Dymodel.onDyMode(id, pag,tooke,new ResultCallBack<DyData>() {
            @Override
            public void onSuccess(DyData bean) {
                mMvpView.CorrectData(bean);
            }

            @Override
            public void onFail(String msg) {
                mMvpView.ErrorData(msg);

            }
        });
    }

}
