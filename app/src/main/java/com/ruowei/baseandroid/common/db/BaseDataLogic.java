package com.ruowei.baseandroid.common.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baitl on 2017/3/5.
 */

public class BaseDataLogic {

    Context context;
    SQLiteDatabase db;
    public BaseDataLogic(Context context)
    {
        this.context=context;
        db = new DatabaseHelper(this.context).getWritableDatabase();
    }

    public List<String> GetZBLeibie()
    {
        String sql="select zhuangbeileibiemingcheng from tb_zhuangbei_leibie";
        Cursor cursor =db.rawQuery(sql,null);
        List<String> vList = new ArrayList<String>();
        if(cursor.moveToFirst())
        {
            int curCount = cursor.getCount();
            for(int m=0;m<curCount;m++) {
                cursor.moveToPosition(m);
                vList.add(cursor.getString(0));
            }
        }
        cursor.close();
        return vList;
    }

    public List<String> GetZBKind(String leibie)
    {
        String sql="select zhuangbeileixingmingcheng from tb_zhuangbei_leixing where zhuangbeileibiemingcheng = '" + leibie + "'";
        Cursor cursor =db.rawQuery(sql,null);
        List<String> vList = new ArrayList<String>();
        if(cursor.moveToFirst())
        {
            int curCount = cursor.getCount();
            for(int m=0;m<curCount;m++) {
                cursor.moveToPosition(m);
                vList.add(cursor.getString(0));
            }
        }
        cursor.close();
        return vList;
    }

    public List<String> GetBaseData(String kind)
    {
        String sql="select leixingzhi from tb_gerenfanghu_jichu_leixing where leixing = '" + kind + "'";
        Cursor cursor =db.rawQuery(sql,null);
        List<String> vList = new ArrayList<String>();
        if(cursor.moveToFirst())
        {
            int curCount = cursor.getCount();
            for(int m=0;m<curCount;m++) {
                cursor.moveToPosition(m);
                vList.add(cursor.getString(0));
            }
        }
        cursor.close();
        return vList;
    }

    public List<String> GetGgxh(String leibie, String kind)
    {
        String sql="select leixingzhi from tb_gerenfanghu_jichu_leixing where leixing = '" + kind + "' and leibie = '" + leibie + "'";
        Cursor cursor =db.rawQuery(sql,null);
        List<String> vList = new ArrayList<String>();
        if(cursor.moveToFirst())
        {
            int curCount = cursor.getCount();
            for(int m=0;m<curCount;m++) {
                cursor.moveToPosition(m);
                vList.add(cursor.getString(0));
            }
        }
        cursor.close();
        return vList;
    }

    public List<String> GetCk()
    {
        String sql="select distinct cangkumingcheng from tb_cangku";
        Cursor cursor =db.rawQuery(sql,null);
        List<String> vList = new ArrayList<String>();
        if(cursor.moveToFirst())
        {
            int curCount = cursor.getCount();
            for(int m=0;m<curCount;m++) {
                cursor.moveToPosition(m);
                vList.add(cursor.getString(0));
            }
        }
        cursor.close();
        return vList;
    }

    public List<String> GetCfd(String ck)
    {
        String sql="select cunfangdidianmingcheng from tb_cangku where cangkumingcheng = '" + ck + "'";
        Cursor cursor =db.rawQuery(sql,null);
        List<String> vList = new ArrayList<String>();
        if(cursor.moveToFirst())
        {
            int curCount = cursor.getCount();
            for(int m=0;m<curCount;m++) {
                cursor.moveToPosition(m);
                vList.add(cursor.getString(0));
            }
        }
        cursor.close();
        return vList;
    }

    /**
     * 获取器材规格型号和生产厂家的历史记录
     * @return
     */
    public List<String> GetHistory(String leixing)
    {
        String sql = "";
        if("器材型号".equals(leixing)){
            sql="select leixingzhi from tb_history_xh_bz where leixing = '" + leixing + "' order by shijian desc";
        }
        if("申请备注".equals(leixing)){
            sql="select leixingzhi from tb_history_xh_bz where leixing = '" + leixing + "' order by shijian desc";
        }
        if("测试".equals(leixing)){
            sql="select leixingzhi from tb_history_xh_bz ";
        }

        Cursor cursor =db.rawQuery(sql,null);
        List<String> vList = new ArrayList<String>();
        if(cursor.moveToFirst())
        {
            int curCount = cursor.getCount();
            for(int m=0;m<curCount;m++) {
                cursor.moveToPosition(m);
                vList.add(cursor.getString(0));
            }
        }
        cursor.close();
        return vList;
    }

}
