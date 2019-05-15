package com.everywhere.trip.view.main;

import com.everywhere.trip.base.BaseMvpView;
import com.everywhere.trip.bean.DyData;

public interface DynamicConditionView extends BaseMvpView {
    void  CorrectData(DyData dyData);
    void ErrorData(String data);

}
