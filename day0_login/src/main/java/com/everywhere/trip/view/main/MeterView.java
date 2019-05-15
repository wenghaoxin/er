package com.everywhere.trip.view.main;

import com.everywhere.trip.base.BaseMvpView;
import com.everywhere.trip.bean.BanmiBean;
import com.everywhere.trip.bean.MainDataBean;

public interface MeterView extends BaseMvpView {
    void successData(BanmiBean banmiBean);
    void ErrorData(String e);
}
