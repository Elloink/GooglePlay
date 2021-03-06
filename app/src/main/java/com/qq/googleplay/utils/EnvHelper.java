package com.qq.googleplay.utils;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
/**
 * ============================================================
 * Copyright：Google有限公司版权所有 (c) 2017
 * Author：   陈冠杰
 * Email：    815712739@qq.com
 * GitHub：   https://github.com/JackChen1999
 * 博客：     http://blog.csdn.net/axi295309066
 * 微博：     AndroidDeveloper
 * <p>
 * Project_Name：GooglePlay
 * Package_Name：com.qq.googleplay
 * Version：1.0
 * time：2016/2/16 13:33
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/
public class EnvHelper {

    public enum NetType {
        NO_NET,
        WIFI,
        WAP,
        NET,
        MOBILE
    }

    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static int getVersionCode(Context context) {
        int i = 0;
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return i;
        }
    }

    public static boolean isNetworkConnected(Context context) {
        NetworkInfo ni = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    public static String getOSVersion() {
        return VERSION.RELEASE;
    }

    public static int getApiVersion() {
        return VERSION.SDK_INT;
    }

    public static NetType getNetworkType(Context context) {
        NetType netType = NetType.NO_NET;
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        if (networkInfo == null) {
            return NetType.NO_NET;
        }
        int nType = networkInfo.getType();
        if (nType == 0) {
            netType = NetType.MOBILE;
            if (networkInfo.getExtraInfo().toLowerCase().contains("wap")) {
                netType = NetType.WAP;
            } else {
                netType = NetType.NET;
            }
        } else if (nType == 1) {
            netType = NetType.WIFI;
        }
        return netType;
    }
}
