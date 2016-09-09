package com.aigo.AigoPm25Map.goodairinstall.business.db;

import android.content.Context;

/**
 * Created by zhangcirui on 15/8/24.
 */
public class SPManager {

    private static final String TAG = SPManager.class.getSimpleName();
    public static final String UserName = "UserName";
    private static final String SHOP_LIST = "SHOP_LIST";


    private static SPManager spMasterManager;

    private static Context mContext;

    public static SPManager getInstance(Context context) {
        if (spMasterManager == null) {
            mContext = context;
            spMasterManager = new SPManager();
        }
        return spMasterManager;
    }

    public String getUserName() {

        return SPreferences.getString(mContext, UserName, null);
    }

    public boolean setUserName(String info) {

        return SPreferences.putStr(mContext, UserName, info);
    }


    public String getShopList() {

        return SPreferences.getString(mContext, SHOP_LIST, null);
    }

    public boolean setShopList(String info) {

        return SPreferences.putStr(mContext, SHOP_LIST, info);
    }


}
