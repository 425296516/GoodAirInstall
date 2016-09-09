package com.aigo.AigoPm25Map.goodairinstall;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aigo.AigoPm25Map.business.location.LocationManager;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;

/**
 * Created by zhangcirui on 16/8/31.
 */
public class LookRouterActivity extends AppCompatActivity implements LocationSource,
        AMapLocationListener, AMap.OnMarkerClickListener,AMap.OnInfoWindowClickListener, AMap.OnCameraChangeListener, GeocodeSearch.OnGeocodeSearchListener {

    private static final String TAG = LookRouterActivity.class.getSimpleName();
    private static final String LOOK_ROUTER = "LOOK_ROUTER";
    private MapView mapView;
    private AMap aMap;
    private float mLatitude = 0, mLongtitude = 0;
    private LookRouteObject lookRouteObject;
    private LocationSource.OnLocationChangedListener mLocationChangedListener;
    private LocationManagerProxy mAMapLocationManager;
    private GeocodeSearch geocoderSearch;
    private String addressName;
    private LatLonPoint latLonPoint = new LatLonPoint(mLatitude, mLongtitude);
    private LinearLayout mLocationIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_router);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mapView = (MapView) findViewById(R.id.map);
        mLocationIcon = (LinearLayout) findViewById(R.id.linear_location);

        mapView.onCreate(savedInstanceState);// 此方法必须重写

        location();

        setUpMap();

        mLocationIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location();
            }
        });
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        Intent intent = new Intent();
        intent.putExtra("MAP_LOCATION",marker.getTitle());
        intent.putExtra("LATITUDE_LOCATION",(float) marker.getPosition().latitude);
        intent.putExtra("LONGITUDE_LOACTION",(float) marker.getPosition().longitude);
        setResult(2,intent);
        finish();

    }

    public void location(){

        new LocationManager(this, new LocationManager.OnLocationListener() {
            @Override
            public void location(AMapLocation location) {

                if (location != null) {
                    mLatitude = (float) location.getLatitude();
                    mLongtitude = (float) location.getLongitude();

                    if (location == null) {
                        Toast.makeText(LookRouterActivity.this, "正在获取您的位置", Toast.LENGTH_LONG).show();
                        return;
                    }
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(16));

                    Log.d(TAG, mLatitude + "====" + mLongtitude + "  " + location.getAddress());
                }
            }

            @Override
            public void locationFail(String s) {

            }
        }).startLocation();

    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {

        if (aMap == null) {
            aMap = mapView.getMap();
        }

        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.getUiSettings().setScaleControlsEnabled(true);
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.getUiSettings().setTiltGesturesEnabled(false);
        aMap.getUiSettings().setRotateGesturesEnabled(false);

        aMap.setMyLocationEnabled(true);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        aMap.setOnMarkerClickListener(this);
        aMap.setOnCameraChangeListener(this);
        aMap.setOnInfoWindowClickListener(this);

        //定位的小图标 默认是蓝点 这里自定义一团火，其实就是一张图片
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
        aMap.setMyLocationStyle(myLocationStyle);

        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);

        addMarkersToMap(addressName);// 往地图上添加marker
    }

    private void addMarkersToMap(String addressName) {

        String name = null;
        if(!TextUtils.isEmpty(addressName) && addressName.length()>20){
            name = addressName.substring(0,19)+"\n"+addressName.substring(19,addressName.length());
        }else{
            name = addressName;
        }

        Marker marker = aMap.addMarker(new MarkerOptions()
                .title(name)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .draggable(true));

        Display disPlay = getWindowManager().getDefaultDisplay();
        marker.setPositionByPixels(disPlay.getWidth() / 2, disPlay.getHeight() / 2);
        marker.showInfoWindow();// 设置默认显示一个infowinfow
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();

        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();

        mapView.onPause();
    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);

        Log.d(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.d(TAG, "onRestoreInstanceState");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();

        Log.d(TAG, "onDestroy");
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        Log.d(TAG, "" + marker.getPosition().latitude + marker.getPosition().longitude);

        return false;
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mLocationChangedListener != null && aMapLocation != null) {
            mLocationChangedListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mLocationChangedListener = onLocationChangedListener;
        if (mAMapLocationManager == null) {
            mAMapLocationManager = LocationManagerProxy.getInstance(this);
            /*
             * mAMapLocManager.setGpsEnable(false);
			 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
			 * API定位采用GPS和网络混合定位方式
			 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
			 */
            mAMapLocationManager.requestLocationData(
                    LocationProviderProxy.AMapNetwork, 2000, 13, this);
        }
    }


    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mLocationChangedListener = null;
        if (mAMapLocationManager != null) {
            mAMapLocationManager.removeUpdates(this);
            mAMapLocationManager.destroy();
        }
        mAMapLocationManager = null;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

        Log.d(TAG, "onCameraChange:" + cameraPosition.toString());

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        Log.d(TAG, "onCameraChangeFinish:" + cameraPosition.toString());

        latLonPoint.setLatitude(cameraPosition.target.latitude);
        latLonPoint.setLongitude(cameraPosition.target.longitude);

        getAddress(latLonPoint);
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {

        if (rCode == 1000) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                addressName = result.getRegeocodeAddress().getFormatAddress() + "附近";

                aMap.clear();
                addMarkersToMap(addressName);// 往地图上添加marker
            }
        }

    }


    /**
     * 响应逆地理编码
     */
    public void getAddress(final LatLonPoint latLonPoint) {
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
    }

    //根据地理位置获取经纬度
    @Override
    public void onGeocodeSearched(GeocodeResult result, int rCode) {

        if (rCode == 1000) {
            if (result != null && result.getGeocodeAddressList() != null
                    && result.getGeocodeAddressList().size() > 0) {
                GeocodeAddress address = result.getGeocodeAddressList().get(0);

                addressName = "经纬度值:" + address.getLatLonPoint() + "\n位置描述:"
                        + address.getFormatAddress();

            }
        }
    }
}
