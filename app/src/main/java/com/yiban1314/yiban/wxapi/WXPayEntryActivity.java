package com.yiban1314.yiban.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.yiban1314.yiban.MyApplication;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.greenrobot.eventbus.EventBus;


/**
 * @author lzPeng
 * @desc 调用微信支付后回调时触发的界面
 * @time 2019/2/18  11:43
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {


    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = MyApplication.getWxapi();
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.v("wxpayy", resp.getType() +"\t"+resp.errCode);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == WxPayUtils.PAY_SUCCESS_CODE) {
                //支付成功
                Log.v("wxpayy", "支付成功");
                EventBus.getDefault().post(new WxPayResultEvent(true));
            } else {
                //支付失败
                Log.v("wxpayy", "支付失败");
                Log.v("wxpayy", resp.errCode + "/");
                EventBus.getDefault().post(new WxPayResultEvent(false));
            }
        }
        finish();
    }
}

