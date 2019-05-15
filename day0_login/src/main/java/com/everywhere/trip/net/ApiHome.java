package com.everywhere.trip.net;

import com.everywhere.trip.bean.BanmiBean;
import com.everywhere.trip.bean.DeData;
import com.everywhere.trip.bean.DyData;
import com.everywhere.trip.bean.FollowedBanmi;
import com.everywhere.trip.bean.LoginInfo;
import com.everywhere.trip.bean.MainDataBean;
import com.everywhere.trip.bean.MainDataUnFOld;
import com.everywhere.trip.bean.WebData;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiHome  {
  // 访问地址：http://api.banmi.com/  api/3.0/account/login/oauth

    String homeurl = "http://api.banmi.com/";

  @FormUrlEncoded
  @POST("api/3.0/account/login/oauth")
  Observable<LoginInfo> postWeiboLogin(@Field("oAuthToken") String oAuthToken);

    @GET("api/3.0/content/routesbundles")
    Observable<MainDataBean> getMainData(@Query("page") int page, @Header("banmi-app-token")String token);

    //伴米
    @GET("api/3.0/banmi")
    Observable<BanmiBean> getBanmiData(@Query("page") int page, @Header("banmi-app-token")String token);

    //我的关注
    @GET("api/3.0/account/followedBanmi")
    Observable<FollowedBanmi> getFollowedBanmiData(@Query("page") int page, @Header("banmi-app-token")String token);

    // api/3.0/banmi/{banmiId}/follow 关注伴米
//线路详情
  //api/3.0/content/routes/{routeId}
  /*@POST("api/3.0/content/routes/{routeId}")*/
//伴米动态
   //路径:api/3.0/banmi/{banmiId}
  @GET("api/3.0/banmi/{banmiId}")
  Observable<DyData> getDyBanmiData(@Path("banmiId") String id, @Query("page") int page, @Header("banmi-app-token")String token);

 @GET("api/3.0/content/routes/{banmiId}/reviews")
 Observable<DeData> getDyBanmiDataa(@Path("banmiId") String id, @Header("banmi-app-token")String token);

//首页详情
 @GET("api/3.0/content/routes/{routeId}")
  Observable<MainDataUnFOld> getunfoldData(@Path("routeId") String id, @Header("banmi-app-token")String token);

// 获取专题列表                  api/3.0/content/bundles
    @GET("api/3.0/content/bundles")
    Observable<WebData> getwebjsData();
}
