package com.yiban1314.yiban;

import android.app.Application;

import com.yiban1314.yiban.wxapi.WxPayUtils;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class MyApplication extends Application {

    private static IWXAPI wxapi;

    public static IWXAPI getWxapi() {
        return wxapi;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initWxPay();
    }

    private void initWxPay() {
        //初始化微信api
        wxapi = WXAPIFactory.createWXAPI(this, WxPayUtils.getWxAppId(), false);
        //注册appid
        wxapi.registerApp(WxPayUtils.getWxAppId());
    }

}
