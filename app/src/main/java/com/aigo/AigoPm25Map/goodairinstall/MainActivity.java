package com.aigo.AigoPm25Map.goodairinstall;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.aigo.AigoPm25Map.goodairinstall.business.ShopInfoBean;
import com.aigo.AigoPm25Map.goodairinstall.business.ShopListBean;
import com.aigo.AigoPm25Map.goodairinstall.business.db.SPManager;
import com.aigo.AigoPm25Map.goodairinstall.business.db.SPreferences;
import com.aigo.AigoPm25Map.goodairinstall.ui.adapter.DefaultMultipleAdapter;
import com.aigo.AigoPm25Map.goodairinstall.ui.adapter.MultiSettingSelectAdapter;
import com.aigo.usermodule.ui.util.ToastUtil;
import com.google.gson.Gson;
import com.goyourfly.ln.Ln;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private List<ShopInfoBean> data;
    private String mCreator;
    private static final int ACCESS_COARSE_LOCATION_REQUEST_CODE = 1;
    private TextView mTvShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTvShop = (TextView) findViewById(R.id.tv_shop);

        if (!TextUtils.isEmpty(SPManager.getInstance(getApplicationContext()).getUserName())) {
            mCreator = SPManager.getInstance(getApplicationContext()).getUserName();
        }

        initPermissions();

    }

    @Override
    protected void onResume() {
        super.onResume();

        initData();
    }

    public void initData() {

        ShopListBean shopListBean = new Gson().fromJson(SPManager.getInstance(this).getShopList(), ShopListBean.class);

        if (shopListBean != null && shopListBean.getShopsList()!=null && shopListBean.getShopsList().size()!=0) {
            data = shopListBean.getShopsList();
            if(data!=null || data.size()>0){
                mTvShop.setVisibility(View.GONE);
            }else {
                mTvShop.setVisibility(View.VISIBLE);
            }

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
            mRecyclerView.setLayoutManager(linearLayoutManager);

            mDefaultMultipleAdapter = new DefaultMultipleAdapter(MainActivity.this);
            mDefaultMultipleAdapter.addItems(data);
            mRecyclerView.setAdapter(mDefaultMultipleAdapter);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());

            mDefaultMultipleAdapter.setOnActionModeCallBack(new MultiSettingSelectAdapter.OnActionModeCallBack() {
                @Override
                public void showActionMode() {
                    mDefaultMultipleAdapter.setIsActionModeShow(true);
                    mDefaultMultipleAdapter.notifyDataSetChanged();
                    startSupportActionMode(mDeleteMode);
                }
            });
        }else{
            mTvShop.setVisibility(View.VISIBLE);
        }
    }


    private void initPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    ACCESS_COARSE_LOCATION_REQUEST_CODE);
            Ln.d("未有位置信息权限");
        } else {
            Ln.d("已有位置信息权限");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length == 0) {
            return;
        }

        if (requestCode == ACCESS_COARSE_LOCATION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Ln.d("允许位置信息权限");
            } else {
                Ln.d("拒绝位置信息权限");
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.add_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.action_logout) {
            //提示对话框
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示").setMessage("确定要退出当前账号？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(MainActivity.this, InstallLoginActivity.class));
                            finish();
                            SPreferences.clear(getApplicationContext());

                            dialog.dismiss();
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            }).show();

            return true;
        } else if (id == R.id.action_item_delete) {

            boolean isUpload = true;
            ShopListBean shopListBean = new Gson().fromJson(SPManager.getInstance(this).getShopList(),ShopListBean.class);
            if(shopListBean!=null && shopListBean.getShopsList()!=null){
                for(int i=0;i<shopListBean.getShopsList().size();i++){
                    if(!shopListBean.getShopsList().get(i).isUpload()){
                        isUpload = false;
                        ToastUtil.showToast(getApplicationContext(),""+shopListBean.getShopsList().get(i).getName()+"未上传");
                    }
                }
            }

            if(isUpload){
                startActivity(new Intent(getApplicationContext(),ShopInfoActivity.class));
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private DefaultMultipleAdapter mDefaultMultipleAdapter;

    private ActionMode.Callback mDeleteMode = new ActionMode.Callback() {


        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            mDefaultMultipleAdapter.setIsActionModeShow(false);
        }

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            getMenuInflater().inflate(R.menu.delete_menu, menu);
            actionMode.setTitle("商户列表");
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            int i = menuItem.getItemId();
            if (i == R.id.action_item_delete) {
                onDeleteItems();
                actionMode.finish();
                return true;
            } else {
            }
            return false;
        }
    };

    private void onDeleteItems() {
        List<ShopInfoBean> deleteItems = new ArrayList<>();
        for (Integer integer : mDefaultMultipleAdapter.getMultiSelectPositions()) {
            deleteItems.add(mDefaultMultipleAdapter.getItemData(integer));
            mDefaultMultipleAdapter.notifyItemRemoved(integer);
        }
        if (deleteItems.size() > 0) {
            mDefaultMultipleAdapter.getDataList().removeAll(deleteItems);

            ShopListBean shopListBean =new ShopListBean();
            shopListBean.setShopsList(mDefaultMultipleAdapter.getDataList());
            SPManager.getInstance(this).setShopList(new Gson().toJson(shopListBean));
        }

        initData();
    }
}
