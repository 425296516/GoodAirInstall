package com.aigo.AigoPm25Map.goodairinstall;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.aigo.AigoPm25Map.business.location.LocationManager;
import com.aigo.AigoPm25Map.goodairinstall.business.GoodAirInstallModule;
import com.aigo.AigoPm25Map.goodairinstall.business.ResultObject;
import com.aigo.AigoPm25Map.goodairinstall.business.ShopInfoBean;
import com.aigo.AigoPm25Map.goodairinstall.business.ShopListBean;
import com.aigo.AigoPm25Map.goodairinstall.business.bean.KT03StatusList;
import com.aigo.AigoPm25Map.goodairinstall.business.bean.SelectStatus;
import com.aigo.AigoPm25Map.goodairinstall.business.db.SPManager;
import com.aigo.usermodule.ui.util.ToastUtil;
import com.amap.api.location.AMapLocation;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ShopInfoActivity extends AppCompatActivity {

    private static final String TAG = ShopInfoActivity.class.getSimpleName();
    @Bind(R.id.et_username)
    EditText etUsername;
    @Bind(R.id.et_shop_name)
    EditText etShopName;
    @Bind(R.id.et_shop_introduce)
    EditText etShopIntroduce;
    @Bind(R.id.et_shop_branch)
    EditText etShopBranch;
    @Bind(R.id.et_shop_type)
    TextView etShopType;
    @Bind(R.id.et_shop_service)
    TextView etShopService;
    @Bind(R.id.et_shop_phone)
    EditText etShopPhone;
    @Bind(R.id.et_shop_time)
    TextView etShopTime;
    @Bind(R.id.et_shop_address)
    TextView etShopAddress;
    @Bind(R.id.btn_shop_address)
    Button buttonShopAddress;
    @Bind(R.id.et_shop_detail_address)
    EditText etShopDetailAddress;
    @Bind(R.id.iv_image_1)
    ImageView ivImage1;
    @Bind(R.id.iv_image_2)
    ImageView ivImage2;
    @Bind(R.id.iv_image_3)
    ImageView ivImage3;
    @Bind(R.id.iv_image_4)
    ImageView ivImage4;
    @Bind(R.id.btn_upload)
    Button btnUpload;
    @Bind(R.id.button_type)
    Button btnType;
    @Bind(R.id.button_service)
    Button btnService;

    private float latitude;
    private float longtitude;
    private ShopInfoBean mShopInfoBean;
    private List<SelectStatus> mTypeSelect = new ArrayList<SelectStatus>();
    private List<SelectStatus> mServiceSelect = new ArrayList<SelectStatus>();
    private static final int SHOPTYPE = 1;
    private static final int SERVICETYPE = 2;

    private List<String> mTypeList;
    private List<String> mServiceList;
    private boolean mIsUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shop_info);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mShopInfoBean = (ShopInfoBean) getIntent().getSerializableExtra("SHOP_INFO_BEAN");
        if (mShopInfoBean != null && !TextUtils.isEmpty(mShopInfoBean.getUsername())) {
            etUsername.setText(mShopInfoBean.getUsername() + "");
            etShopName.setText(mShopInfoBean.getName());
            etShopIntroduce.setText(mShopInfoBean.getPresentation());
            etShopBranch.setText(mShopInfoBean.getBranch());
            etShopPhone.setText(mShopInfoBean.getPhone());
            etShopAddress.setText(mShopInfoBean.getAddress());
            if (mShopInfoBean.getShop_type() != null && mShopInfoBean.getShop_type().size() > 0) {
                etShopType.setText(mShopInfoBean.getShop_type().toString().replace("[", "").replace("]", ""));
            }

            if (mShopInfoBean.getServiceList() != null && mShopInfoBean.getServiceList().size() > 0) {
                etShopService.setText(mShopInfoBean.getServiceList().toString().replace("[", "").replace("]", ""));
            }

            etShopTime.setText(mShopInfoBean.getOpen_hours() + "--" + mShopInfoBean.getClose_hours());
            etShopDetailAddress.setText(mShopInfoBean.getAddressDetail());
            mTypeList = mShopInfoBean.getShop_type();
            mServiceList = mShopInfoBean.getServiceList();
            mIsUpload = mShopInfoBean.isUpload();
        }

        if (mIsUpload) {
            btnUpload.setVisibility(View.GONE);
        } else {
            btnUpload.setVisibility(View.VISIBLE);
        }

        for (int i = 0; i < shopTypes.length; i++) {
            SelectStatus selectStatus = new SelectStatus();
            selectStatus.setName(shopTypes[i]);
            if (mTypeList != null && mTypeList.size() > 0 && mTypeList.contains(shopTypes[i])) {
                selectStatus.setStatus(true);
            } else {
                selectStatus.setStatus(false);
            }
            selectStatus.setType(SHOPTYPE);
            mTypeSelect.add(selectStatus);
        }

        for (int i = 0; i < servers.length; i++) {
            SelectStatus selectStatus = new SelectStatus();
            selectStatus.setName(servers[i]);
            if (mServiceList != null && mServiceList.size() > 0 && mServiceList.contains(servers[i])) {
                selectStatus.setStatus(true);
            } else {
                selectStatus.setStatus(false);
            }
            selectStatus.setType(SERVICETYPE);
            mServiceSelect.add(selectStatus);
        }

        buttonShopAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(), LookRouterActivity.class), 1);
            }
        });

        btnType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showTypeDialog(ShopInfoActivity.this, etShopType, mTypeSelect);
            }
        });

        btnService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showTypeDialog(ShopInfoActivity.this, etShopService, mServiceSelect);
            }
        });

        location();
    }

    public void location() {

        new LocationManager(this, new LocationManager.OnLocationListener() {
            @Override
            public void location(AMapLocation location) {

                if (location != null) {
                    etShopAddress.setText(location.getAddress() + "");
                    latitude = (float) location.getLatitude();
                    longtitude = (float) location.getLongitude();

                    Log.d(TAG, location.getAddress());
                }
            }

            @Override
            public void locationFail(String s) {

            }
        }).startLocation();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.save_menu, menu);

        if (mIsUpload) {
            menu.getItem(0).setVisible(false);
        } else {
            menu.getItem(0).setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.action_item_save) {
            saveData(false);
        }

        return super.onOptionsItemSelected(item);
    }

    public void isHaveDevice(){
        if (!TextUtils.isEmpty(SPManager.getInstance(getApplicationContext()).getShopList())) {
            ShopListBean shopList = new Gson().fromJson(SPManager.getInstance(getApplicationContext()).getShopList(), ShopListBean.class);
            //上传的时候加
            for (int i = 0; i < shopList.getShopsList().size(); i++) {
                if (etUsername.getText().toString().equals(shopList.getShopsList().get(i).getUsername())) {
                    if (!shopList.getShopsList().get(i).isUpload()) {
                        shopList.getShopsList().remove(i);
                    } else {
                        ToastUtil.showToast(getApplicationContext(), "保存失败,已经添加过此账号");
                        return;
                    }
                }
            }
        }
    }

    public void saveData(final boolean isUpload) {

        if (TextUtils.isEmpty(etUsername.getText())) {
            ToastUtil.showToast(getApplicationContext(), "账号信息不能为空");
            return;
        }

        isHaveDevice();

        if (TextUtils.isEmpty(etShopName.getText())) {
            ToastUtil.showToast(getApplicationContext(), "商铺名称不能为空");
            return;
        }

        GoodAirInstallModule.getInstance().GetUserLinkList(etUsername.getText().toString().trim())
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Action1<KT03StatusList>() {
                    @Override
                    public void call(KT03StatusList kt03StatusList) {
                        if (kt03StatusList.getResult().isResult() && kt03StatusList.getKt03StatusList() != null && kt03StatusList.getKt03StatusList().size() > 0) {
                            String sn = kt03StatusList.getKt03StatusList().get(0).getSn();
                            shopInfo(sn);

                            if (TextUtils.isEmpty(SPManager.getInstance(getApplicationContext()).getShopList())) {
                                ShopListBean shopListBean = new ShopListBean();
                                List<ShopInfoBean> shopInfoBeen = new ArrayList<ShopInfoBean>();
                                shopListBean.setShopsList(shopInfoBeen);
                                dataDetal(shopListBean, isUpload);

                            } else {
                                ShopListBean shopList = new Gson().fromJson(SPManager.getInstance(getApplicationContext()).getShopList(), ShopListBean.class);
                                //上传的时候加
                                dataDetal(shopList, isUpload);
                            }

                        }else{
                            ToastUtil.showToast(getApplicationContext(),"此账号下无绑定的空探狗");
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.showToast(getApplicationContext(), "保存失败");
                        Log.d(TAG, "error=" + throwable.toString());
                    }
                }, new Action0() {
                    @Override
                    public void call() {

                    }
                });

    }

    public void dataDetal(ShopListBean shopList, boolean isUpload) {
        if (isUpload) {
            mShopInfoBean.setUpload(isUpload);
        }

        //判断是否添加

        for (int i = 0; i < shopList.getShopsList().size(); i++) {
            if (mShopInfoBean.getUsername().equals(shopList.getShopsList().get(i).getUsername())) {
                if (!shopList.getShopsList().get(i).isUpload()) {
                    shopList.getShopsList().remove(i);
                } else {
                    ToastUtil.showToast(getApplicationContext(), "保存失败,已经添加过此账号");
                    return;
                }
            }
        }

        List<String> typeList = new ArrayList<>();
        for (int i = 0; i < mTypeSelect.size(); i++) {
            if (mTypeSelect.get(i).isStatus()) {
                typeList.add(mTypeSelect.get(i).getName());
            }
        }

        List<String> serviceList = new ArrayList<>();
        for (int i = 0; i < mServiceSelect.size(); i++) {
            if (mServiceSelect.get(i).isStatus()) {
                serviceList.add(mServiceSelect.get(i).getName());
            }
        }

        if (TextUtils.isEmpty(etShopType.getText())) {
            mShopInfoBean.setShop_type(null);
        } else {
            if (typeList == null || typeList.size() == 0) {
                mShopInfoBean.setShop_type(mTypeList);
            } else {
                mShopInfoBean.setShop_type(typeList);
            }
        }
        if (TextUtils.isEmpty(etShopService.getText())) {
            mShopInfoBean.setServiceList(null);
        } else {
            if (serviceList == null || serviceList.size() == 0) {
                mShopInfoBean.setServiceList(mServiceList);
            } else {
                mShopInfoBean.setServiceList(serviceList);
            }
        }

        shopList.getShopsList().add(mShopInfoBean);
        SPManager.getInstance(getApplicationContext()).setShopList(new Gson().toJson(shopList));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == 2) {
            etShopAddress.setText(data.getStringExtra("MAP_LOCATION") + "");
            latitude = data.getFloatExtra("LATITUDE_LOCATION", 0);
            longtitude = data.getFloatExtra("LONGITUDE_LOACTION", 0);
        }
    }

    //选择时间
    public void selectTime(View view) {

        showTimeDialog(this, etShopTime);
    }


    public void uploadClick(View view) {

        if (TextUtils.isEmpty(etUsername.getText())) {
            ToastUtil.showToast(getApplicationContext(), "账号信息不能为空");
            return;
        }

        if (TextUtils.isEmpty(etShopName.getText())) {
            ToastUtil.showToast(getApplicationContext(), "商铺名称不能为空");
            return;
        }

        if (TextUtils.isEmpty(etShopBranch.getText())) {
            ToastUtil.showToast(getApplicationContext(), "门店信息不能为空");
            return;
        }

        if (TextUtils.isEmpty(etShopType.getText())) {
            ToastUtil.showToast(getApplicationContext(), "商家类型不能为空");
            return;
        }

        if (TextUtils.isEmpty(etShopService.getText())) {
            ToastUtil.showToast(getApplicationContext(), "商家服务不能为空");
            return;
        }

        if (TextUtils.isEmpty(etShopTime.getText())) {
            ToastUtil.showToast(getApplicationContext(), "商铺地址不能为空");
            return;
        }

        if (TextUtils.isEmpty(etShopAddress.getText())) {
            ToastUtil.showToast(getApplicationContext(), "商铺地址不能为空");
            return;
        }

        isHaveDevice();

        GoodAirInstallModule.getInstance().GetUserLinkList(etUsername.getText().toString().trim())
                .flatMap(new Func1<KT03StatusList, Observable<ResultObject>>() {
                    @Override
                    public Observable<ResultObject> call(KT03StatusList kt03StatusList) {
                        if (kt03StatusList.getResult().isResult() && kt03StatusList.getKt03StatusList() != null && kt03StatusList.getKt03StatusList().size() > 0) {
                            String sn = kt03StatusList.getKt03StatusList().get(0).getSn();
                            shopInfo(sn);
                            return GoodAirInstallModule.getInstance().uploadShopsInfo(mShopInfoBean);
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Action1<ResultObject>() {
                    @Override
                    public void call(ResultObject resultObject) {
                        if (resultObject.getResult().isResult()) {
                            ToastUtil.showToast(getApplicationContext(), "上传成功");
                            saveData(true);
                        } else {
                            ToastUtil.showToast(getApplicationContext(), "上传失败," + resultObject.getErr_info().toString());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtil.showToast(getApplicationContext(), "上传失败");
                        Log.d(TAG, "error=" + throwable.toString());
                    }
                }, new Action0() {
                    @Override
                    public void call() {

                    }
                });
    }


    //添加商铺数据
    public void shopInfo(String sn) {
        List<String> typeList = new ArrayList<>();
        for (int i = 0; i < mTypeSelect.size(); i++) {
            if (mTypeSelect.get(i).isStatus()) {
                typeList.add(mTypeSelect.get(i).getName());
            }
        }

        List<String> serviceList = new ArrayList<>();
        for (int i = 0; i < mServiceSelect.size(); i++) {
            if (mServiceSelect.get(i).isStatus()) {
                serviceList.add(mServiceSelect.get(i).getName());
            }
        }

        mShopInfoBean = new ShopInfoBean();
        mShopInfoBean.setCreator(SPManager.getInstance(this).getUserName());
        mShopInfoBean.setUsername(etUsername.getText().toString().trim());
        mShopInfoBean.setDeviceId(sn);
        mShopInfoBean.setName(etShopName.getText().toString().trim());
        mShopInfoBean.setPresentation(etShopIntroduce.getText().toString().trim());
        mShopInfoBean.setBranch(etShopBranch.getText().toString().trim());

        if (TextUtils.isEmpty(etShopType.getText())) {
            mShopInfoBean.setShop_type(null);
        } else {
            if (typeList == null || typeList.size() == 0) {
                if (mTypeList != null && mTypeList.size() > 0) {
                    mShopInfoBean.setShop_type(GoodAirInstallModule.getInstance().typeToId(mTypeList));
                }
            } else {
                mShopInfoBean.setShop_type(GoodAirInstallModule.getInstance().typeToId(typeList));
            }
        }
        if (TextUtils.isEmpty(etShopService.getText())) {
            mShopInfoBean.setServiceList(null);
        } else {
            if (serviceList == null || serviceList.size() == 0) {
                if (mServiceList != null && mServiceList.size() > 0) {
                    mShopInfoBean.setServiceList(GoodAirInstallModule.getInstance().serviceToId(mServiceList));
                }
            } else {
                mShopInfoBean.setServiceList(GoodAirInstallModule.getInstance().serviceToId(serviceList));
            }
        }

        mShopInfoBean.setPhone(etShopPhone.getText().toString().trim());
        mShopInfoBean.setOpen_hours(hour1 + ":" + minute1);
        mShopInfoBean.setClose_hours(hour2 + ":" + minute2);

        mShopInfoBean.setLatitude(latitude + "");
        mShopInfoBean.setLongitude(longtitude + "");
        mShopInfoBean.setAddress(etShopAddress.getText().toString().trim());
        mShopInfoBean.setAddressDetail(etShopDetailAddress.getText().toString().trim());

    }

    private String[] shopTypes = {"健身", "酒店", "休闲", "儿童教育", "亲子", "餐饮"};
    private String[] servers = {"提供WiFi", "支持刷卡", "可以停车", "寄存", "无烟", "淋浴"};

    private void showTypeDialog(Context context, final TextView textView, final List<SelectStatus> selectStatuses) {

        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        final Window window = dialog.getWindow();
        window.setContentView(R.layout.dialog_multi_select_picker);

        int width = getWindowManager().getDefaultDisplay().getWidth();
        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度
        lp.width = (int) width / 5 * 4;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);

        final ListView listView = (ListView) window.findViewById(R.id.list_view);
        TextView ok = (TextView) window.findViewById(R.id.btn_dlg_set_age_custom_ok);
        TextView cancel = (TextView) window.findViewById(R.id.btn_dlg_set_age_custom_cancel);

        final MyAdapter myAdapter = new MyAdapter(selectStatuses);
        listView.setAdapter(myAdapter);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean first = true;
                textView.setText("");

                for (int i = 0; i < myAdapter.getCount(); i++) {
                    if (selectStatuses != null && selectStatuses.size() > 0 && selectStatuses.get(0).getType() == SHOPTYPE) {
                        mTypeSelect.get(i).setStatus(myAdapter.getItem(i).isStatus());
                        if (myAdapter.getItem(i).isStatus()) {

                            if (first) {
                                first = false;
                                textView.setText(myAdapter.getItem(i).getName());
                            } else {
                                textView.append("," + myAdapter.getItem(i).getName());
                            }
                        }
                    } else if (selectStatuses != null && selectStatuses.size() > 0 && selectStatuses.get(0).getType() == SERVICETYPE) {
                        mServiceSelect.get(i).setStatus(myAdapter.getItem(i).isStatus());
                        if (myAdapter.getItem(i).isStatus()) {
                            if (first) {
                                first = false;
                                textView.setText(myAdapter.getItem(i).getName());
                            } else {
                                textView.append("," + myAdapter.getItem(i).getName());
                            }
                        }
                    }
                }
                dialog.dismiss();
            }
        });
    }

    class MyAdapter extends BaseAdapter {

        private List<SelectStatus> mList;

        public MyAdapter(List<SelectStatus> list) {
            mList = list;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public SelectStatus getItem(int i) {
            return mList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {

            View layout = getLayoutInflater().inflate(R.layout.item_multex_select_checked, null);
            CheckBox checkBox = (CheckBox) layout.findViewById(R.id.checkBox);

            final SelectStatus selectStatus = getItem(i);
            checkBox.setText(selectStatus.getName());
            checkBox.setChecked(selectStatus.isStatus());

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (getItem(i).getType() == SHOPTYPE) {
                        getItem(i).setStatus(b);
                    } else if (getItem(i).getType() == SERVICETYPE) {
                        getItem(i).setStatus(b);
                    }
                }
            });

            return layout;
        }
    }

    private int hour1 = 8;
    private int minute1;
    private int hour2 = 22;
    private int minute2;

    private void showTimeDialog(Context context, final TextView etShopType) {
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        final Window window = dialog.getWindow();
        window.setContentView(R.layout.dialog_date_picker);

        //获得window窗口的属性
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);

        final TimePicker timePicker1 = (TimePicker) window.findViewById(R.id.number_picker_1);
        final TimePicker timePicker2 = (TimePicker) window.findViewById(R.id.number_picker_2);

        TextView ok = (TextView) window.findViewById(R.id.btn_dlg_set_age_custom_ok);
        TextView cancel = (TextView) window.findViewById(R.id.btn_dlg_set_age_custom_cancel);

        //是否使用24小时制
        timePicker1.setIs24HourView(true);
        timePicker1.setCurrentHour(hour1);
        timePicker1.setCurrentMinute(minute1);
        timePicker2.setIs24HourView(true);
        timePicker2.setCurrentHour(hour2);
        timePicker2.setCurrentMinute(minute2);

        timePicker1.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                hour1 = timePicker.getCurrentHour();
                minute1 = timePicker.getCurrentMinute();
            }
        });

        timePicker2.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                hour2 = timePicker.getCurrentHour();
                minute2 = timePicker.getCurrentMinute();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etShopType.setText(timePicker1.getCurrentHour() + ":" + (timePicker1.getCurrentMinute() == 0
                        ? "00" : timePicker1.getCurrentMinute()) + "--" + timePicker2.getCurrentHour() + ":"
                        + (timePicker2.getCurrentMinute() == 0 ? "00" : timePicker2.getCurrentMinute()));

                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onBackPressed() {

        if(TextUtils.isEmpty(etUsername.getText()) && TextUtils.isEmpty(etShopName.getText())){
            super.onBackPressed();
            return;
        }

        if (mIsUpload) {
            super.onBackPressed();
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("当前信息尚未保存,离开后信息将丢失,是否确认离开?");
            builder.setPositiveButton("确认",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                            finish();
                        }
                    });

            builder.setNegativeButton("取消",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }
                    });
            builder.show();
        }
    }
}
