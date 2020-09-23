package com.yiban1314.yiban.wxapi;



import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.yiban1314.yiban.MyApplication;
import com.tencent.mm.opensdk.modelpay.PayReq;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author ：congge
 * @date : 2020-09-23 10:18
 * @desc :微信支付工具类
 **/
public class WxPayUtils {


    /**
     * 加密sign的key
     */
    public static final String WX_SIGN_KEY = "19e97b29499168c9bdad27ee595ec8f8";


    /**
     * 支付成功code
     */
    public static final int PAY_SUCCESS_CODE = 0;

    /**
     * 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
     */
    public static final int PAY_ERROR_CODE = -1;

    /**
     * 无需处理。发生场景：用户不支付了，点击取消，返回APP
     */
    public static final int PAY_CANCEL_CODE = -2;

    /**
     * @param map
     * @return
     * @author:fangyunhe
     * @time:2018年11月5日 下午5:30:34
     */
    public static String createMD5Sign(SortedMap<String, String> map) {
        StringBuilder sb = new StringBuilder();
        Set es = map.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            sb.append(k + "=" + v + "&");
        }
        String params = sb.append("key=" + getWxSignKey()).substring(0);
        String sign = MD5Encode(params, "utf8");
        sign = sign.toUpperCase();
        return sign;
    }

    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString;
    }

    /**
     * MD5加密
     *
     * @param b
     * @return
     */
    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 数字与小写字母混编字符串,生成随机字符串，不长于32位
     * 暂定生成位数size:15
     */
    public static String getNumSmallLetter() {
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            if (random.nextInt(2) % 2 == 0) {//字母
                buffer.append((char) (random.nextInt(27) + 'a'));
            } else {//数字
                buffer.append(random.nextInt(10));
            }
        }
        return buffer.toString();
    }

    /**
     * @desc 微信的appid
     * @author congge
     * @time 2019/2/18  11:52
     */
    public static String getWxAppId() {
        return "wxd3ceb06fcde5b8bf";
    }

    /**
     * @desc 获取微信商户号
     * @author congge
     * @time 2019/2/26  17:19
     */
    public static String getWxMchId() {
        return "1423999402";
    }

    /**
     * @desc :微信sign，要和接口对，必须一一对应
     * @author :congge on 2019/6/13 16:28
     **/
    public static String getWxSignKey() {
        return "19e97b29499168c9bdad27ee595ec8f8";
    }

    /**
    * @desc : 微信支付
    * @author : congge on 2020-09-23 17:01
    **/
    public static void wxPay(Context context, final String prepqyId) {
        Log.i("看看预付订单是多少",prepqyId + "\t");
        if (MyApplication.getWxapi().isWXAppInstalled()) {
            Thread payThread = new Thread() {
                @Override
                public void run() {
                    PayReq request = new PayReq();
                    request.appId = WxPayUtils.getWxAppId();
                    request.partnerId = getWxMchId(); // 商户号
                    request.prepayId = prepqyId;
                    request.packageValue = "Sign=WXPay";
                    request.nonceStr = WxPayUtils.getNumSmallLetter();
                    request.timeStamp = (System.currentTimeMillis() / 1000) + "";
                    SortedMap<String, String> map = new TreeMap<>();
                    map.put("appid", request.appId);
                    map.put("partnerid", request.partnerId);
                    map.put("prepayid", request.prepayId);
                    map.put("package", request.packageValue);
                    map.put("noncestr", request.nonceStr);
                    map.put("timestamp", request.timeStamp);
                    request.sign = WxPayUtils.createMD5Sign(map);
                    MyApplication.getWxapi().sendReq(request);
                }
            };
            payThread.start();
        } else {
            Toast.makeText(context,"请安装微信后再使用",Toast.LENGTH_SHORT).show();

        }


    }

}
