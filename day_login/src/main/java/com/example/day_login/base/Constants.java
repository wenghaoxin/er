package com.example.day_login.base;

import android.os.Environment;

import java.io.File;

/**
 * Created by asus on 2019/3/5.
 */

public interface Constants {
    //是否为debug状态,正式上线版本需要改为false
    boolean isDebug = true;


    String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() +
            File.separator + "codeest" + File.separator + "GeekNews";

    String FILE_PROVIDER_AUTHORITY="com.baidu.geek.fileprovider";

    //网络缓存的地址
    String PATH_DATA = BaseApp.getInstance().getCacheDir().getAbsolutePath() +
            File.separator + "data";

    String PATH_CACHE = PATH_DATA + "/NetCache";
    String DATA = "data";
    //夜间模式
    String MODE = "mode";
    String NIGHT_CURRENT_FRAG_POS = "fragment_pos";
}
