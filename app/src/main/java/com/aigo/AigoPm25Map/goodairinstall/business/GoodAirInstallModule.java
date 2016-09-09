package com.aigo.AigoPm25Map.goodairinstall.business;

import android.util.Log;

import com.aigo.AigoPm25Map.goodairinstall.business.bean.AllService;
import com.aigo.AigoPm25Map.goodairinstall.business.bean.KT03StatusList;
import com.aigo.AigoPm25Map.goodairinstall.business.bean.TypeList;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func0;
import rx.functions.Func1;

/**
 * Created by zhangcirui on 16/9/1.
 */
public class GoodAirInstallModule {


    private static final String TAG = GoodAirInstallModule.class.getSimpleName();
    private static GoodAirInstallModule module;

    public static GoodAirInstallModule getInstance() {
        if (module == null) {
            module = new GoodAirInstallModule();
        }
        return module;
    }


    //上传商铺信息(并验证)：uploadShopsInfo.json
    public Observable<ResultObject> uploadShopsInfo(final ShopInfoBean shopInfoBean) {
        return Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                //URL生成
                String url = "http://api.aigolife.com/pm25/uploadShopsInfo.json";

                String urlStr = new StringBuffer(url)
                        .append("?creator=").append(shopInfoBean.getCreator())
                        .append("&username=").append(shopInfoBean.getUsername())
                        .append("&deviceId=").append(shopInfoBean.getDeviceId())
                        .append("&name=").append(shopInfoBean.getName())
                        .append("&presentation=").append(shopInfoBean.getPresentation())
                        .append("&branch=").append(shopInfoBean.getBranch())
                        .append("&shop_type=").append(shopInfoBean.getShop_type())
                        .append("&serviceList=").append(shopInfoBean.getServiceList())
                        .append("&phone=").append(shopInfoBean.getPhone())
                        .append("&open_hours=").append(shopInfoBean.getOpen_hours())
                        .append("&close_hours=").append(shopInfoBean.getClose_hours())
                        .append("&latitude=").append(shopInfoBean.getLatitude())
                        .append("&longitude=").append(shopInfoBean.getLongitude())
                        .append("&address=").append(shopInfoBean.getAddress())
                        .append(shopInfoBean.getAddressDetail()).toString();

                      /*  .append("&banner_photos=").append(shopInfoBean.getBanner_photos())
                        .append("&certificate=").append(shopInfoBean.getCertificate())
                        .append("&around_photos=").append(shopInfoBean.getAround_photos())
                        .append("&other_photos=").append(shopInfoBean.getOther_photos())
                        .toString();*/

                Log.d(TAG, urlStr);


                return NetHelper.getData(urlStr);
            }
        }).flatMap(new Func1<String, Observable<ResultObject>>() {
            @Override
            public Observable<ResultObject> call(String s) {
                Log.d(TAG, s);
                ResultObject result = new Gson().fromJson(s, ResultObject.class);

                return Observable.just(result);
            }
        });
    }

    //获取后台用户创建的商铺列表：getUPloadShopsList.json
    public Observable<ShopListBean> getUploadShopsList(final String creator) {
        return Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                //URL生成
                String url = "http://api.aigolife.com/pm25/getUPloadShopsList.json";

                String urlStr = new StringBuffer(url)
                        .append("?creator=").append(creator)
                        .toString();

                Log.d(TAG, urlStr);

                return NetHelper.getData(urlStr);
            }
        }).flatMap(new Func1<String, Observable<ShopListBean>>() {
            @Override
            public Observable<ShopListBean> call(String s) {
                Log.d(TAG, s);
                ShopListBean result = new Gson().fromJson(s, ShopListBean.class);
                return Observable.just(result);
            }
        });
    }

    //删除商铺：deleteShops.json
    public Observable<ShopListBean> deleteShops(final String creator, final List<String> shopIdList) {
        return Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                //URL生成
                String url = "http://api.aigolife.com/pm25/deleteShops.json";

                String urlStr = new StringBuffer(url)
                        .append("?creator=").append(creator)
                        .append("&shopIdList=").append(new Gson().toJson(shopIdList))
                        .toString();

                Log.d(TAG, urlStr);

                return NetHelper.getData(urlStr);
            }
        }).flatMap(new Func1<String, Observable<ShopListBean>>() {
            @Override
            public Observable<ShopListBean> call(String s) {
                Log.d(TAG, s);
                ShopListBean result = new Gson().fromJson(s, ShopListBean.class);

                return Observable.just(result);
            }
        });
    }

    //是否登录：
    public Observable<ResultObject> getLogin(final String creator, final String password) {
        return Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                //URL生成
                String url = "http://api.aigolife.com/pm25/workerLogin.json";

                String urlStr = new StringBuffer(url)
                        .append("?creator=").append(creator)
                        .append("&password=").append(password)
                        .toString();

                Log.d(TAG, urlStr);

                return NetHelper.getData(urlStr);
            }
        }).flatMap(new Func1<String, Observable<ResultObject>>() {
            @Override
            public Observable<ResultObject> call(String s) {
                Log.d(TAG, s);
                ResultObject result = new Gson().fromJson(s, ResultObject.class);

                return Observable.just(result);
            }
        });
    }


    public Observable<KT03StatusList> GetUserLinkList(final String username) {
        return Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                //URL生成
                String url = "http://api.aigolife.com/kt03/GetUserLinkList.json";

                String urlStr = new StringBuffer(url)
                        .append("?token=").append("test")
                        .append("&username=").append(username)
                        .toString();

                Log.d(TAG, urlStr);

                return NetHelper.getData(urlStr);
            }
        }).flatMap(new Func1<String, Observable<KT03StatusList>>() {
            @Override
            public Observable<KT03StatusList> call(String s) {
                Log.d(TAG, s);
                KT03StatusList result = new Gson().fromJson(s, KT03StatusList.class);

                return Observable.just(result);
            }
        });
    }

    //获取商铺类型：getAllShopsType.json
    public Observable<TypeList> getAllShopsType() {
        return Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                //URL生成
                String url = "http://api.aigolife.com/pm25/getAllShopsType.json";

                Log.d(TAG, url);

                return NetHelper.getData(url);
            }
        }).flatMap(new Func1<String, Observable<TypeList>>() {
            @Override
            public Observable<TypeList> call(String s) {
                Log.d(TAG, s);
                TypeList result = new Gson().fromJson(s, TypeList.class);

                return Observable.just(result);
            }
        });
    }

    //获取服务类型：getAllService.json
    public Observable<AllService> getAllService() {
        return Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                //URL生成
                String url = "http://api.aigolife.com/pm25/getAllService.json";

                Log.d(TAG, url);

                return NetHelper.getData(url);
            }
        }).flatMap(new Func1<String, Observable<AllService>>() {
            @Override
            public Observable<AllService> call(String s) {
                Log.d(TAG, s);
                AllService result = new Gson().fromJson(s, AllService.class);

                return Observable.just(result);
            }
        });
    }
    public List<String> typeToId(List<String> types){
        List<String> list = new ArrayList<String>();

        for(int i=0;i<types.size();i++){
            if(types.get(i).equals("健身")){
                list.add("1");
            }else if(types.get(i).equals("酒店")){
                list.add("2");
            }else if(types.get(i).equals("休闲")){
                list.add("3");
            }else if(types.get(i).equals("儿童教育")){
                list.add("4");
            }else if(types.get(i).equals("亲子")){
                list.add("5");
            }else if(types.get(i).equals("餐饮")){
                list.add("6");
            }
        }

        return list;
    }

    public List<String> serviceToId(List<String> types){
        List<String> list = new ArrayList<String>();
        for(int i=0;i<types.size();i++){
            if(types.get(i).equals("提供WiFi")){
                list.add("1");
            }else if(types.get(i).equals("支持刷卡")){
                list.add("2");
            }else if(types.get(i).equals("可以停车")){
                list.add("3");
            }else if(types.get(i).equals("寄存")){
                list.add("4");
            }else if(types.get(i).equals("无烟")){
                list.add("5");
            }else if(types.get(i).equals("淋浴")){
                list.add("6");
            }
        }
        return list;
    }


}
