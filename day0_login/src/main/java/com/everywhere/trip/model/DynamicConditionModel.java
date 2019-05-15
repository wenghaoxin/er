package com.everywhere.trip.model;

import android.util.Log;

import com.everywhere.trip.base.BaseModel;
import com.everywhere.trip.bean.DyData;
import com.everywhere.trip.net.ApiHome;
import com.everywhere.trip.net.ResultCallBack;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DynamicConditionModel extends BaseModel {
    private static final String TAG = "DynamicConditionModel ";

    public void onDyMode(String id,int pag, String tooke, final ResultCallBack<DyData> dycallback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiHome.homeurl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiHome apiHome = retrofit.create(ApiHome.class);
        final Observable<DyData> dyBanmiData = apiHome.getDyBanmiData(id, pag, tooke);
        dyBanmiData.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<DyData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: "+d);
                    }

                    @Override
                    public void onNext(DyData dyData) {
                        dycallback.onSuccess(dyData);
                        Log.d(TAG, "onNext: "+dyData.getResult().getActivities().size());

                    }

                    @Override
                    public void onError(Throwable e) {
                        dycallback.onFail(e+"");
                        Log.d(TAG, "onError: "+e);

                    }

                    @Override
                    public void onComplete() {

                    }
                });
       /* final Observable<DyData> dyBanmiData = apiHome.getDyBanmiData(id,pag,tooke);
        dyBanmiData.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DyData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: "+d);
                    }

                    @Override
                    public void onNext(DyData dyData) {
                            dycallback.onSuccess(dyData);
                        Log.d(TAG, "onNext: "+dyData.getResult().getActivities().size());
                    }

                    @Override
                    public void onError(Throwable e) {
                        dycallback.onFail(e+"");
                        Log.d(TAG, "onError: "+e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
*/
    }

}
