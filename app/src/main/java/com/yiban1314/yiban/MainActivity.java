package com.yiban1314.yiban;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.yiban1314.yiban.wxapi.WxPayResultEvent;
import com.yiban1314.yiban.wxapi.WxPayUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);

        findViewById(R.id.tv_wx_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prepayId = "wx23170435224626bfa11f6714fc76b20000";
                WxPayUtils.wxPay(MainActivity.this,prepayId);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWxPayResultEvent(WxPayResultEvent wxPayResultEvent){
        if (wxPayResultEvent.isPaySuccess()){
            Toast.makeText(MainActivity.this,"支付成功666",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this,"支付失败233",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
