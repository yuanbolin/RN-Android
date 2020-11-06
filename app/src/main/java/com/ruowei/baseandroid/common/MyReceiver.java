package com.ruowei.baseandroid.common;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;


import com.ruowei.baseandroid.Main2Activity;
import com.ruowei.baseandroid.R;
import com.ruowei.baseandroid.utils.EmptyUtils;
import com.ruowei.baseandroid.utils.SPUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Pierce on 2018/7/2.
 * Project: FireWZ
 * Package: com.ruowei.xu.firewz.common;
 * ClassName: MyReceiver
 * contact way: 1208950702@qq.com
 * If you want to change this class, make sure you get the permission of the author, please.
 * Tips:
 */

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";



    @Override
    public void onReceive(Context context, Intent intent) {


        Bundle bundle = intent.getExtras();

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "接受到推送下来的自定义消息");
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            String message = bundle.getString(JPushInterface.EXTRA_ALERT);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
            //Log.d(TAG, "extras----->" + extras);
            showNotification(context, notifactionId, title, message, extras);
            Log.d(TAG, "接受到推送下来的通知");
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "用户点击打开了通知");
            Intent i = null;
            String extraData = bundle.getString(JPushInterface.EXTRA_EXTRA);
            if (extraData == null || extraData.equals(""))
                return;
            try {
                JSONObject extraJson = new JSONObject(extraData);
                String action = extraJson.getString("act");
                Log.d(TAG, "action----->" + action);

                i = new Intent(context, Main2Activity.class);
                i.putExtra("page",0+"");
                if (i != null) {
                    i.putExtras(bundle);
                    //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);
                }
            } catch (Exception ee) {

            }
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
        } else {
        }
    }

    private void showNotification(Context context, int notificationId, String title, String content, String extras) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //8.0以上自己写通知
            Intent i = new Intent(context,Main2Activity.class).putExtra("page",0+"");
            JSONObject extraJson = null;
            try {
                if(EmptyUtils.isNotEmpty(extras))
                    extraJson = new JSONObject(extras);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(EmptyUtils.isNotEmpty(i)){
                i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            }
            int notifyId = (int) System.currentTimeMillis();
            PendingIntent pendingIntent = PendingIntent.getActivity(context, notificationId, i, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationChannel channel = new NotificationChannel("1",
                    "Channel1", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true); //是否在桌面icon右上角展示小红点
            channel.setLightColor(Color.GREEN); //小红点颜色
            channel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
            Notification notification = null;
            notification = new  Notification.Builder(context,"1")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setNumber(3)//久按桌面图标时允许的此条通知的数量
                    .setContentIntent(pendingIntent)//添加点击跳转事件
                    .setAutoCancel(true)
                    .build();


//            Notification.Builder builder = new Notification.Builder(context, "1"); //与channelId对应
//
//            //icon title text必须包含，不然影响桌面图标小红点的展示
//            builder.setSmallIcon(R.mipmap.ic_launcher)
//                    .setContentTitle(title)
//                    .setContentText(content)
//                    .setNumber(3)//久按桌面图标时允许的此条通知的数量
//                    .setContentIntent(pendingIntent)//添加点击跳转事件
//                    .setAutoCancel(true);

            try {
               if (extraJson!=null){
                   String c = extraJson.getString("daichuli");

               }

            } catch (Exception e) {
                e.printStackTrace();
            }
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
            notificationManager.notify(notificationId, notification);

            notificationManager.deleteNotificationChannel("Channel1");
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

}
