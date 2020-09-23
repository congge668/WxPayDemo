package com.yiban1314.yiban.wxapi;

/**
 * @author congge
 * @desc 微信支付成功失败后的实时处理事件
 * @time 2019/2/18  11:53
 */
public class WxPayResultEvent {
    //是否支付成功
    private boolean paySuccess;

    public boolean isPaySuccess() {
        return paySuccess;
    }

    public void setPaySuccess(boolean paySuccess) {
        this.paySuccess = paySuccess;
    }

    public WxPayResultEvent(boolean paySuccess) {
        this.paySuccess = paySuccess;
    }
}
