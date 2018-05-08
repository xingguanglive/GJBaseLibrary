/*
 * NetworkUtils.java
 * business
 *
 * Created by ChenTao on 2017/5/25.
 *
 * Copyright (c) 2017年 yidiandao. All rights reserved.
 */
package tv.guojiang.core.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * 网络工具.
 */
public final class NetworkUtils {

    /**
     * 网络工具.
     */
    private NetworkUtils() {
    }

    /**
     * 获取ConnectivityManager.
     *
     * @param context 上下文信息
     * @return 上下文信息中获取的ConnectivityManager
     */
    private static ConnectivityManager getConnectivityManager(final Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(
            Context.CONNECTIVITY_SERVICE);
        return connectivityManager;
    }

    /**
     * 获取活动的NetworkInfo.
     *
     * @param context Context
     * @return Active networkInfo
     */
    public static NetworkInfo getActiveNetworkInfo(final Context context) {
        ConnectivityManager connectivityManager = getConnectivityManager(context);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return networkInfo;
    }

    /**
     * 检测网络是否可用.
     *
     * @param context Context
     * @return ture -- 网络可用, false -- 网络不可用
     */
    public static boolean isNetworkAvailable(final Context context) {
        if (context == null) {
            throw new NullPointerException("context不能为空");
        }
        NetworkInfo networkInfo = getActiveNetworkInfo(context);
        return networkInfo != null && networkInfo.isAvailable();
    }

    /**
     * 获取用户的网络状态
     */
    public static String getDeviceNetworkStatus(Context context) {
        String strNetworkType = "";
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(
            Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "WIFI";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = networkInfo.getSubtypeName();
                // TD-SCDMA   networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        strNetworkType = "2G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        strNetworkType = "3G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        strNetworkType = "4G";
                        break;
                    default:
                        // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") ||
                            _strSubTypeName.equalsIgnoreCase("WCDMA") ||
                            _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = "3G";
                        } else {
                            strNetworkType = _strSubTypeName;
                        }

                        break;
                }
            }
        }
        return strNetworkType;
    }
}
