package com.ruowei.baseandroid.common;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import com.ruowei.baseandroid.R;
import com.ruowei.baseandroid.utils.MToast;
import com.ruowei.baseandroid.utils.SPUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Pierce on 2018/7/2.
 * Project: FireWZ
 * Package: com.ruowei.xu.firewz.common;
 * ClassName: Common
 * contact way: 1208950702@qq.com
 * If you want to change this class, make sure you get the permission of the author, please.
 * Tips:
 */

public class Common {

    public static final String USER_SETTING = "setting";
    public static final String KEY_USER_PUSH_CHECK = "check";
    public static final String LOGIN_FILE = "login";
    public static final String URL_FILE = "url";
    public static final String KEY_LOGIN_SP = "isLogin";
    public static final String KEY_LOGIN_ID = "userId";
    public static final String KEY_USER_NAME = "userName";
    public static final String KEY_USER_AUTH = "authority";
    public static final String KEY_JG_NAME = "jgName";
    public static final String KEY_JG_CODE = "jgCode";
    public static final String KEY_JG_KIND = "jgKind";
    public static final String KEY_BUMEN_CODE = "bmCode";
    public static final String KEY_RENYUANLEIXING = "renyuanleixing";
    public static final String KEY_JUESE = "juese";
    public static final String KEY_JUESE_ID = "jueseId";
    public static final String KEY_LIUCHENG_JUESE = "liuchengjuese";
    public static final String KEY_GB = "ganbu";
    public static final String KEY_LEIBIE = "个人防护装备";
    public static final String KEY_YINGQU = "XXXX营区";
    public static final String KEY_BASE_URL = "baseUrl";
    public static final String LOGIN_ID = "loginId";
    public static final String LOGIN_P = "loginP";
    public static final String COUNT = "count";
    public static final String RENYUANBIANHAO = "renyuanbianhao";

    public static final String AllFunctionInfo = "AllFunctionInfo";
    public static final String SelFunctionInfo = "SelFunctionInfo";
    // 用于App更新类型的修改 值为leixingId
    public static final String LeixingId = "1";


    /**
     * 生产环境默认写的ip为
     * http://114.115.218.68:8083
     */
    public static final String BaseUrl = "https://xunte.vip:443";
    /**
     * 开发环境的ip
     * http://58.59.70.10:8083
     */
//    public static final String BaseUrl = "http://192.168.1.160:8083";
    /**
     * 测试环境的ip
     */
//    public static final String BaseUrl = "http://58.59.70.10:8088";

    public static final int loginSuccess = 11000;
    public static final int loginFailed = 11001;
    public static final int getAppQuanxianSuccess = 10000;
    public static final int getAppQuanxianFailed = 10001;

    public static final int getVersionSuccess = 11002;
    public static final int getVersionFailed = 11003;
    public static final int modifyPwdSuccess = 11004;
    public static final int modifyPwdFailed = 11005;


    public static final int uploadFileSuccess = 11220;
    public static final int uploadFileFailed = 11221;

    public static final int getAppQuanxianNewSuccess = 20000;
    public static final int getAppQuanxianNewFailed = 20001;


    /**
     * 修改密码
     */
    public static String modifyPwdUrl = "/api/xiugaiMima";
    /**
     * 登录
     */
//    public static String loginUrl = "/api/authenticate";
    public static String loginUrl = "/api/appLoginMenu";
    /**
     * 获取登录人员的功能权限
     */
    public static String getAppQuanxianUrl = "/api/chaxunquanxianApp";



    /**
     * 获取登录人员的功能权限new
     */
    public static String getAppQuanxiannewUrl = "/api/caidan/current-login-user";
    /**
     * 密码
     */
    public static String USER_PWD = "mima";
    /**
     * 账号
     */
    public static String USER_ACCOUNT = "dengluming";
    /**
     * token
     */
    public static String TOKEN = "Authorization";
    /**
     * 旧密码
     */
    public static String pwOld = "yuanMima";
    /**
     * 新密码
     */
    public static String pwNew = "xinMima";
    public static String commonPage = "page";
    public static String commonRows = "size";


    /**
     * 登录
     *
     * @param mHandler
     * @param pwd      密码
     * @param name     用户名
     */
    public static void login(Context mContext, Handler mHandler, String pwd, String name) {
        Map<String, Object> params1 = new HashMap<String, Object>();
        params1.put(USER_PWD, pwd);
        params1.put(USER_ACCOUNT, name);
        params1.put("rememberMe", true);
        JSONObject params = new JSONObject(params1);
        MRequest.postRequest(mContext, loginUrl, null, params, mHandler,
                loginSuccess, loginFailed);
    }


    /**
     * 登录到主界面的时候获取登录人的功能权限
     * @param mContext
     * @param mHandler
     */
    public static void getAppQuanxian(Context mContext, Handler mHandler) {

        if (!"".equals(getHeader(mContext))) {
            MRequest.getRequest(mContext, getAppQuanxianUrl, getHeader(mContext), null, mHandler,
                    getAppQuanxianSuccess, getAppQuanxianFailed);
        } else {
            MToast.show(mContext, mContext.getResources().getString(R.string.login_first));
        }
    }

    /**
     * 登录到主界面的时候获取登录人的功能权限(new)
     * @param mContext
     * @param mHandler
     */
    public static void getAppQuanxianNew(Context mContext, Handler mHandler) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("fenlei", "APP");
        if (!"".equals(getHeader(mContext))) {
            MRequest.getRequest(mContext, getAppQuanxiannewUrl, getHeader(mContext), params, mHandler,
                    getAppQuanxianNewSuccess, getAppQuanxianNewFailed);
        } else {
            MToast.show(mContext, mContext.getResources().getString(R.string.login_first));
        }
    }


    /**
     * 修改密码
     *
     * @param mHandler
     * @param pwd      旧密码
     * @param pwdNew   新密码
     */
    public static void modifyPwd(Context mContext, Handler mHandler, String pwd, String pwdNew) {
        Map<String, String> params1 = new HashMap<String, String>();
        params1.put(pwOld, pwd);
        params1.put(pwNew, pwdNew);
        JSONObject params = new JSONObject(params1);
        if (!"".equals(getHeader(mContext))) {
            MRequest.postRequest(mContext, modifyPwdUrl, getHeader(mContext), params, mHandler,
                    modifyPwdSuccess, modifyPwdFailed);
        } else {
            MToast.show(mContext, mContext.getResources().getString(R.string.login_first));
        }
    }






    /**
     * @return 获取header
     */
    public static Map<String, String> getHeader(Context context) {
        Map<String, String> header = new HashMap<String, String>();
        header.put("Accept", "application/json");
        header.put("Content-Type", "application/json; charset=UTF-8");
        header.put(TOKEN, (String) SPUtil.spRead(context, Common.LOGIN_FILE, Common.KEY_LOGIN_SP, ""));
        return header;
    }

    /**
     * 判断是否登录
     *
     * @param context
     * @return
     */
    public static boolean isLogin(Context context) {
        if (SPUtil.containContent(context, Common.LOGIN_FILE, Common.KEY_LOGIN_SP)) {
            return true;
        }
        return false;
    }

    /**
     * @param context
     * @param str              字符串
     * @param length           长度  0：没有限制
     * @param status           0:=    -1:<=   1:>=
     * @param nullText         为空时的提示文字
     * @param notificationText 不合格是的提示文字
     * @return 是否合格
     */
    public static boolean strQualified(Context context, String str, int length, int status, String nullText, String notificationText) {
        boolean flag = false;
        if (notificationText == null) {
            notificationText = "输入不能为空！";
        }
        if (str == null || str.equals("")) {
            Toast.makeText(context, nullText, Toast.LENGTH_SHORT).show();
        } else {
            if (length == 0) {
                flag = true;
            } else {
                switch (status) {
                    case -1:
                        if (str.length() <= length)
                            flag = true;
                        break;
                    case 0:
                        if (str.length() == length)
                            flag = true;
                        break;
                    case 1:
                        if (str.length() >= length)
                            flag = true;
                        break;
                    default:
                        break;
                }
                if (!flag) {
                    Toast.makeText(context, notificationText, Toast.LENGTH_SHORT).show();
                }
            }
        }
        return flag;
    }

    /**
     * 判断是否是电话号码
     *
     * @param tel
     * @return
     */
    public static boolean isMobileNum(String tel) {
        Pattern p = Pattern.compile("^((13[0-9])|" +
                "(17[0-9])|" +
                "(14[0-9])|" +
                "(15[^4,\\D])|" +
                "(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(tel);
        return m.matches();
    }

    /**
     * @return 时间日期
     */
    public static String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }

    /**
     * 得到压缩的图片url
     */
    public static String getImgCompress(String imgUrl) {
        if (!TextUtils.isEmpty(imgUrl)) {
            String newUrl = imgUrl.replace("/storage/upload", "/storage/upload/compressImg");
            return newUrl;
        }
        return null;
    }

    /**
     * 得到简化数量
     */
    public static String getCount(String count) {
        String str;
        int num = Integer.parseInt(count);
        if (num < 1000) {
            str = count;
        } else if (num < 10000) {
            BigDecimal bg = new BigDecimal(num / 1000);
            double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            str = f1 + "千";
        } else {
            BigDecimal bg = new BigDecimal(num / 10000);
            double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            str = f1 + "万";
        }
        return str;
    }

    /**
     * 拼接get请求的url
     *
     * @param url    请求url
     * @param params 参数的map
     * @return 拼接好的url
     */
    private static String getUrlWithParams(String url, Map<String, String> params) {
        if (params == null || params.size() == 0)
            return url;
        StringBuilder sb = new StringBuilder();
        for (String paramKey : params.keySet()) {
            try {
                sb.append("&")
                        .append(paramKey)
                        .append("=")
                        .append(URLEncoder.encode(params.get(paramKey), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                return url;
            } catch (Exception e) {
                return url;
            }
        }
        return url + sb.replace(0, 1, "?").toString();
    }

}
