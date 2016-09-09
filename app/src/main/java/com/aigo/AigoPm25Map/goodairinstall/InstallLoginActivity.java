package com.aigo.AigoPm25Map.goodairinstall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.aigo.AigoPm25Map.goodairinstall.business.GoodAirInstallModule;
import com.aigo.AigoPm25Map.goodairinstall.business.ResultObject;
import com.aigo.AigoPm25Map.goodairinstall.business.db.SPManager;
import com.aigo.usermodule.ui.util.ToastUtil;
import com.aigo.usermodule.ui.view.ClearEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class InstallLoginActivity extends AppCompatActivity {

    @Bind(R.id.et_username)
    ClearEditText etUsername;
    @Bind(R.id.et_password)
    ClearEditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install_login);
        ButterKnife.bind(this);

        if(!TextUtils.isEmpty(SPManager.getInstance(getApplicationContext()).getUserName())){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

    }

    public void onLogin(View view){

        GoodAirInstallModule.getInstance().getLogin(etUsername.getText().toString(),etPassword.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResultObject>() {
                               @Override
                               public void call(ResultObject resultObject) {

                                   if(resultObject.getResult().isResult()){
                                       SPManager.getInstance(getApplicationContext()).setUserName(etUsername.getText().toString().trim());
                                       startActivity(new Intent(InstallLoginActivity.this,MainActivity.class));
                                       finish();
                                   }else {
                                       ToastUtil.showToast(getApplicationContext(),"登录失败");
                                   }

                               }
                           }, new Action1<Throwable>() {
                               @Override
                               public void call(Throwable throwable) {
                                   ToastUtil.showToast(getApplicationContext(),"登录失败");
                               }
                           }, new Action0() {
                               @Override
                               public void call() {

                               }
                           }
                );
    }

}
