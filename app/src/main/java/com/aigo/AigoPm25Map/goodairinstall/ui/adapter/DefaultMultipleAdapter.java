package com.aigo.AigoPm25Map.goodairinstall.ui.adapter;

import android.content.Context;

import com.aigo.AigoPm25Map.goodairinstall.business.ShopInfoBean;

/**
 * Author:    ZhuWenWu
 * Version    V1.0
 * Date:      2015/8/26  13:21.
 * Description: 学校选择适配器
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2015/8/26        ZhuWenWu            1.0                    1.0
 * Why & What is modified:
 */
public class DefaultMultipleAdapter extends MultiSettingSelectAdapter<ShopInfoBean> {

    public DefaultMultipleAdapter(Context context) {
        super(context);
    }

    @Override
    public ShopInfoBean getItem(int position) {
        return getItemData(position);
    }

}
