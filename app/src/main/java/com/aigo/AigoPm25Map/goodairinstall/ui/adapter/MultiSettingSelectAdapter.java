package com.aigo.AigoPm25Map.goodairinstall.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aigo.AigoPm25Map.goodairinstall.R;
import com.aigo.AigoPm25Map.goodairinstall.ShopInfoActivity;
import com.aigo.AigoPm25Map.goodairinstall.business.ShopInfoBean;

/**
 * Author:    ZhuWenWu
 * Version    V1.0
 * Date:      2015/8/15  17:33.
 * Description: 多选适配器
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2015/8/15        ZhuWenWu            1.0                    1.0
 * Why & What is modified:
 */
public abstract class MultiSettingSelectAdapter<T> extends BaseMultiSelectAdapter<T> {
    private OnActionModeCallBack onActionModeCallBack;
    private boolean isActionModeShow = false;
    public Context mContext;

    public void setOnActionModeCallBack(OnActionModeCallBack onActionModeCallBack) {
        this.onActionModeCallBack = onActionModeCallBack;
    }

    public void setIsActionModeShow(boolean isActionModeShow) {
        this.isActionModeShow = isActionModeShow;
        if (!isActionModeShow) {
            clearAllSelect();
        }
    }

    public MultiSettingSelectAdapter(Context context) {

        super(context);
        mContext = context;
    }

    public abstract ShopInfoBean getItem(int position);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MultiSettingSelectViewHolder(mLayoutInflater.inflate(R.layout.item_shop_recyclerview, parent, false), this, mContext);
    }

    public static ShopInfoBean mOshops;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mOshops = getItem(position);
        Log.d(TAG, "onBindViewHolder=" + position);

        if (holder instanceof MultiSettingSelectViewHolder) {
            MultiSettingSelectViewHolder multiSettingSelectViewHolder = ((MultiSettingSelectViewHolder) holder);
            multiSettingSelectViewHolder.bindViewData(mOshops, position);
        }
    }

    static class MultiSettingSelectViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout relativeLayout;
        private RadioButton checkBox;
        private TextView tvShopName;
        private MultiSettingSelectAdapter mAdapter;
        private Context mContext;

        MultiSettingSelectViewHolder(View view, MultiSettingSelectAdapter adapter, Context context) {
            super(view);

            mAdapter = adapter;
            mContext = context;

            relativeLayout = (RelativeLayout) view.findViewById(R.id.relative_layout);
            checkBox = (RadioButton) view.findViewById(R.id.checkBox);
            tvShopName = (TextView) view.findViewById(R.id.tv_shop_name);
        }

        public void bindViewData(final ShopInfoBean shopBean, int position) {
            if (mAdapter.isSelectedEnable && mAdapter.isActionModeShow) {
                checkBox.setVisibility(View.VISIBLE);
            } else {
                checkBox.setVisibility(View.GONE);
            }

            checkBox.setChecked(mAdapter.isSelected(position));
            tvShopName.setText(shopBean.getName()+((shopBean.isUpload()==true)?"(已上传)" : "(待上传)"));
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (mAdapter.isSelectedEnable && mAdapter.isActionModeShow) {
                        onSelected();
                    } else {
                        Intent intent = new Intent(mContext, ShopInfoActivity.class);
                        intent.putExtra("SHOP_INFO_BEAN", mOshops);
                        mContext.startActivity(intent);
                    }
                }
            });

            relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    onLongSelected();

                    return false;
                }
            });

        }


        public void onSelected() {
            if (mAdapter.isSelectedEnable && mAdapter.isActionModeShow) {
                if (mAdapter.isSelected(getPosition())) {//已选中

                    mAdapter.removeSelectPosition(getPosition());
                } else {//未选中

                    mAdapter.addSelectPosition(getPosition());
                }
                mAdapter.notifyItemChanged(getPosition());
            }
        }

        public boolean onLongSelected() {
            if (mAdapter.isActionModeShow) {//已显示选择模式
                onSelected();
            } else {
                if (mAdapter.onActionModeCallBack != null) {
                    mAdapter.onActionModeCallBack.showActionMode();
                }
            }
            return true;
        }
    }

    public interface OnActionModeCallBack {
        public void showActionMode();
    }
}
