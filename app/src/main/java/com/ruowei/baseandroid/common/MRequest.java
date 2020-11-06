package com.ruowei.baseandroid.common;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.ruowei.baseandroid.MyApplication;
import com.ruowei.baseandroid.R;
import com.ruowei.baseandroid.utils.MLog;
import com.ruowei.baseandroid.utils.MToast;
import com.ruowei.baseandroid.utils.SPUtil;


import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pierce on 2018/7/2.
 * Project: FireWZ
 * Package: com.ruowei.xu.firewz.common;
 * ClassName: MRequest
 * contact way: 1208950702@qq.com
 * If you want to change this class, make sure you get the permission of the author, please.
 * Tips:
 */

public class MRequest {
    public static final RequestQueue mAppQueue = MyApplication.getQueue();


    /**
     * 8*2.5 = 20s
     */
    public static final int DEFAULT_TIMECOUNT = 4;

    /**
     * @param url 后半部分不同的URL
     * @return 完整的URL
     */
    private static String getUrl(Context context, String url) {
//        return "https://" + (String) SPUtil.spRead(context, Common.URL_FILE, Common.KEY_BASE_URL, "140.249.19.181:8900") + url;
        return (String) SPUtil.spRead(context, Common.URL_FILE, Common.KEY_BASE_URL, "140.249.19.181:8900") + url;
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

    /**
     * Http请求方法base
     *
     * @param context       context
     * @param mQueue        请求队列
     * @param errorListener 如果传null，使用默认的
     * @param method        GET/POST
     * @param url           不带公共前缀的url
     * @param headerMap     header的Map，不需要header请传null
     * @param params        参数的Map，没有参数请传null
     * @param mhandler      handler
     * @param what          handler 里message的what的值
     * @param time_count    timeout的时间  2.5秒的多少倍
     */
    public static void doRequest(final Context context, final RequestQueue mQueue, Response.ErrorListener errorListener, final int method,
                                 final String url, final Map<String, String> headerMap, final Map<String, String> params,
                                 final Handler mhandler, final int what, int time_count, final int failed_what, final boolean isToast) {

        if (errorListener == null) {
            errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    ResponseBean bean = null;
                    Message message = new Message();
                    message.what = failed_what;
                    if (isToast) {
                        if (volleyError.networkResponse == null) {
                            MToast.show(context, context.getResources().getString(R.string.network_connect_error));
                        } else if (volleyError.networkResponse.statusCode == 408) {
                            MToast.show(context, context.getResources().getString(R.string.network_connect_overtime));
                        } else {
                            MToast.show(context, context.getResources().getString(R.string.server_error));
                        }
                    }
                    if (null != mQueue.getCache().get(getUrl(context, url))) {
                        String responseStr = new String(mQueue.getCache().get(getUrl(context, url)).data);
                        String expireStr = new String(mQueue.getCache().get(getUrl(context, url)).ttl + "");
                        MLog.i("HTTP --", "CACHE: " + responseStr);
                        MLog.i("HTTP --", "cache expire" + expireStr);
                        bean = MyApplication.gson.fromJson(responseStr, ResponseBean.class);

                    }
                    message.obj = bean;
                    mhandler.sendMessage(message);
                }
            };
        }
        StringRequest request = new StringRequest(method, getUrl(context, url), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.i("http:",s);
                ResponseBean bean = null;
                bean = MyApplication.gson.fromJson(s, ResponseBean.class);

                Message message = new Message();
                message.what = what;
                if (bean.getStatus() == 0) {
                    if (bean.getData() instanceof List<?>) {
                        MLog.i("HTTP --", "result is array list." + bean.getData().toString());
                    } else {
                        if (bean.getData() != null) {
                            MLog.i("HTTP --", bean.getData().toString());
                        }
                    }
                    message.obj = bean;
                    mhandler.sendMessage(message);
                } else if (bean.getStatus() == -600) {
                    MToast.show(context, context.getResources().getString(R.string.login_first));
                    //TODO 清空缓存，跳转登录
//                    SPUtil.clear(context, Common.LOGIN_FILE);
//                    Intent intent = new Intent(context, LoginActivity.class);
//                    context.startActivity(intent);
                } else {
                    /**
                     * 这里添加返回特定error code时做的操作
                     */
                    MLog.i("HTTP: error", bean.getMessage());
                    message.what = failed_what;
                    message.obj = bean;
                    mhandler.sendMessage(message);
                }
            }
        }, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (headerMap == null) {
                    return super.getHeaders();
                }
                return headerMap;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramsTemp;
                if (method == Method.GET || params == null) {
                    paramsTemp = super.getParams();
                    MLog.i("HTTP POST Request:", super.getUrl() + " no params");
                } else {
                    paramsTemp = params;
                    MLog.i("HTTP POST Request:", super.getUrl() + "-" + params.toString());
                }

                return paramsTemp;
            }

            @Override
            public String getUrl() {
                String urlString;
                if (method == Method.GET) {
                    urlString = getUrlWithParams(super.getUrl(), params);
                    MLog.i("HTTP GET Request:", urlString);
                } else {
                    urlString = super.getUrl();
                }
                return urlString;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        /**
         * 打开缓存
         */
        request.setShouldCache(false);
        if (getUrl(context, url).equals("http://115.28.224.202/v1/api/common/get-ads.json")) {
            request.setTag("homeAdv");
        } else if (getUrl(context, url).equals("http://115.28.224.202/v1/api/common/get-new-version.json")) {
            request.setTag("appVersion");
        } else if (getUrl(context, url).equals("http://115.28.224.202/v1/api/communication/recruitments.json")) {
            request.setTag("firmRecruit");
        } else {
            request.setTag("FireWZDZ");
        }

        mQueue.add(request);
    }


    /**
     * get
     * 使用Application的mQueue、errorListener
     * 进行toast
     *
     * @param context
     * @param url
     * @param headerMap
     * @param params
     * @param mhandler
     * @param what
     * @param failed_what
     * @param time_count
     */
    public static void getRequest(Context context, String url, Map<String, String> headerMap, Map<String, String> params,
                                  Handler mhandler, int what, int failed_what, int time_count) {
        doRequest(context, mAppQueue, null, Request.Method.GET, url, headerMap, params, mhandler, what, time_count, failed_what, true);
    }

    /**
     * get
     * 使用Application的mQueue、errorListener
     * 进行toast
     * 请求时间为默认时间
     *
     * @param context
     * @param url
     * @param headerMap
     * @param params
     * @param mhandler
     * @param what
     * @param failed_what
     */
    public static void getRequest(Context context, String url, Map<String, String> headerMap, Map<String, String> params,
                                  Handler mhandler, int what, int failed_what) {
        doRequest(context, mAppQueue, null, Request.Method.GET, url, headerMap, params, mhandler, what, DEFAULT_TIMECOUNT, failed_what, true);
    }

    /**
     * post
     * 用Application的mQueue、errorListener
     * 进行toast
     *
     * @param context
     * @param url
     * @param headerMap
     * @param params
     * @param mhandler
     * @param what
     * @param failed_what
     * @param time_count
     */
    public static void postRequest(Context context, String url, Map<String, String> headerMap, Map<String, String> params,
                                   Handler mhandler, int what, int failed_what, int time_count) {
        doRequest(context, mAppQueue, null, Request.Method.POST, url, headerMap, params, mhandler, what, time_count, failed_what, true);
    }

    /**
     * post
     * 使用Application的mQueue、errorListener
     * 进行toast
     * 请求时间为默认时间
     *
     * @param context
     * @param url
     * @param headerMap
     * @param params
     * @param mhandler
     * @param what
     * @param failed_what
     */
    public static void postRequest(Context context, String url, Map<String, String> headerMap, JSONObject params,
                                   Handler mhandler, int what, int failed_what) {
        doRequest1(context, mAppQueue, null, url, headerMap, params, mhandler, what, failed_what, true);
    }

    /**
     * 登出专用请求post
     * 使用Application的mQueue、errorListener
     * 不进行toast
     * 请求时间为默认时间
     *
     * @param context
     * @param url
     * @param headerMap
     * @param params
     * @param mhandler
     * @param what
     * @param failed_what
     */
    public static void logoutRequest(Context context, String url, Map<String, String> headerMap, Map<String, String> params,
                                     Handler mhandler, int what, int failed_what) {
        doRequest(context, mAppQueue, null, Request.Method.POST, url, headerMap, params, mhandler, what, DEFAULT_TIMECOUNT, failed_what, false);
    }

//    /**
//     * 上传文件
//     *
//     * @param context
//     * @param url
//     * @param params
//     * @param mHandler
//     * @param what
//     * @param what_failed
//     * @param isToast
//     */
//    public static void uploadFile(final Context context, String url, MultipartParams params, final Handler mHandler, final int what, final int what_failed, final boolean isToast) {
//        final Message message = new Message();
//        MultiPartRequest request = new MultiPartRequest(getUrl(url), new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                ResponseBean bean = null;
//                bean = JsonUtil.jsonToObj(s, ResponseBean.class);
//                message.what = what;
//                message.obj = bean;
//                mHandler.sendMessage(message);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                if (isToast) {
//                    if (volleyError.networkResponse == null) {
//                        MToast.show(context, context.getResources().getString(R.string.network_connect_error));
//                    } else if (volleyError.networkResponse.statusCode == 408) {
//                        MToast.show(context, context.getResources().getString(R.string.network_connect_overtime));
//                    } else {
//                        MToast.show(context, context.getResources().getString(R.string.server_error));
//                    }
//                }
//                message.what = what_failed;
//                mHandler.sendMessage(message);
//            }
//        }, params);
//        request.setShouldCache(false);
//        mAppQueue.add(request);
//    }
//
//    /**
//     * @param url
//     * @param params
//     * @param mHandler
//     * @param what
//     * @param what_failed
//     */
//    public static void uploadFile(String url, MultipartParams params, Handler mHandler, int what, int what_failed) {
//        uploadFile(null, url, params, mHandler, what, what_failed, false);
//    }

    public static void doRequest1(final Context context, final RequestQueue mQueue, Response.ErrorListener errorListener,
                                  final String url, final Map<String, String> headerMap, final JSONObject params,
                                  final Handler mhandler, final int what, final int failed_what, final boolean isToast) {

        if (errorListener == null) {
            errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    ResponseBean bean = null;
                    Message message = new Message();
                    message.what = failed_what;
                    if (isToast) {
                        if (volleyError.networkResponse == null) {
                            MToast.show(context, context.getResources().getString(R.string.network_connect_error));
                        } else if (volleyError.networkResponse.statusCode == 408) {
                            MToast.show(context, context.getResources().getString(R.string.network_connect_overtime));
                            //TODO 清空缓存，跳转登录
//                            SPUtil.clear(context, Common.LOGIN_FILE);
//                            Intent intent = new Intent(context, LoginActivity.class);
//                            context.startActivity(intent);
                        } else {
                            MToast.show(context, context.getResources().getString(R.string.server_error));
                        }
                    }
                    if (null != mQueue.getCache().get(getUrl(context, url))) {
                        String responseStr = new String(mQueue.getCache().get(getUrl(context, url)).data);
                        String expireStr = new String(mQueue.getCache().get(getUrl(context, url)).ttl + "");
                        MLog.i("HTTP --", "CACHE: " + responseStr);
                        MLog.i("HTTP --", "cache expire" + expireStr);
                        bean = MyApplication.gson.fromJson(responseStr, ResponseBean.class);
                    }
                    message.obj = bean;
                    mhandler.sendMessage(message);
                }
            };
        }
        JsonRequest<JSONObject> request = new JsonObjectRequest(Request.Method.POST, getUrl(context, url), params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject s) {
                Log.i("http:", String.valueOf(s));
                ResponseBean bean = null;
                bean = MyApplication.gson.fromJson(s.toString(), ResponseBean.class);


                Message message = new Message();
                message.what = what;
                if (bean.getStatus() == 0) {
                    if (bean.getData() instanceof List<?>) {
                        MLog.i("HTTP --", "result is array list." + bean.getData().toString());
                    } else {
                        if (bean.getData() != null) {
                            MLog.i("HTTP --", bean.getData().toString());
                        }
                    }
                    message.obj = bean;
                    mhandler.sendMessage(message);
                } else if (bean.getStatus() == -600) {
                    MToast.show(context, context.getResources().getString(R.string.login_first));
                    //TODO 清空缓存，跳转登录
//                    SPUtil.clear(context, Common.LOGIN_FILE);
//                    Intent intent = new Intent(context, LoginActivity.class);
//                    context.startActivity(intent);
                } else {
                    /**
                     * 这里添加返回特定error code时做的操作
                     */
                    MLog.i("HTTP: error", bean.getMessage());
                    message.what = failed_what;
                    message.obj = bean;
                    mhandler.sendMessage(message);
                }
            }
        }, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (headerMap == null) {
                    Map<String, String> header = new HashMap<String, String>();
                    header.put("Accept", "application/json");
                    header.put("Content-Type", "application/json; charset=UTF-8");
                    MLog.i("HTTP POST Request:", super.getUrl() + "-" + params.toString());
                    return header;
                }
                if(params != null){
                    MLog.i("HTTP POST Request:", super.getUrl() + "-" + params.toString());
                }
//                MLog.i("HTTP POST Request:", super.getUrl());
                return headerMap;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(/*DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * time_count*/10 * 1000,
                /*DefaultRetryPolicy.DEFAULT_MAX_RETRIES*/0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        /**
         * 打开缓存
         */
        request.setShouldCache(true);
        if (getUrl(context, url).equals("http://115.28.224.202/v1/api/common/get-ads.json")) {
            request.setTag("homeAdv");
        } else if (getUrl(context, url).equals("http://115.28.224.202/v1/api/common/get-new-version.json")) {
            request.setTag("appVersion");
        } else if (getUrl(context, url).equals("http://115.28.224.202/v1/api/communication/recruitments.json")) {
            request.setTag("firmRecruit");
        } else {
            request.setTag("FireWZDZ");
        }

        mQueue.add(request);
    }
}
