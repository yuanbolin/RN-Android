package com.ruowei.baseandroid.common.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hanw on 2017/2/23.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static  final String DbName="firewzdz";

    public DatabaseHelper(Context context, CursorFactory factory) {
        super(context, DbName, factory, VERSION);
    }
    public DatabaseHelper(Context context) {
        this(context, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        CreateAppDB(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void CreateAppDB(SQLiteDatabase sqLiteDatabase)
    {

        sqLiteDatabase.execSQL("CREATE TABLE tb_gerenfanghu_jichu_leixing (\n" +
                "  ID         varchar(36)            NOT NULL,\n" +
                "  leixing    varchar(8)             NOT NULL,\n" +
                "  leixingzhi varchar(256)           NOT NULL,\n" +
                "  leibie     varchar(256)           NOT NULL,\n" +
                "  constraint PK_EWORGANIZE primary key (ID)\n" +
                 ")");

        sqLiteDatabase.execSQL("create table tb_zhuangbei_leixing (\n" +
                "   ID                          varchar(36)          NOT NULL,\n" +
                "   zhuangbeileixingmingcheng   varchar(20)          NOT NULL,\n" +
                "   zhuangbeileibiemingcheng   varchar(20)          NOT NULL,\n" +
                "   constraint PK_EWORGANIZE primary key (ID)\n" +
                ")");

        sqLiteDatabase.execSQL("create table tb_zhuangbei_leibie (\n" +
                "   ID                          varchar(36)          NOT NULL,\n" +
                "   zhuangbeileibiemingcheng   varchar(20)          NOT NULL,\n" +
                "   constraint PK_EWORGANIZE primary key (ID)\n" +
                ")");

        sqLiteDatabase.execSQL("create table tb_cangku (\n" +
                "   ID                          varchar(36)          NOT NULL,\n" +
                "   cangkumingcheng             varchar(20)          NOT NULL,\n" +
                "   cunfangdidianmingcheng      varchar(20)          NOT NULL,\n" +
                "   constraint PK_EWORGANIZE primary key (ID)\n" +
                ")");

        sqLiteDatabase.execSQL("create table tb_history_xh_bz (\n" +
                "   ID                          integer  primary key autoincrement NOT NULL,\n" +
                "   leixingzhi                  varchar(100)         NOT NULL,\n" +
                "   leixing                     varchar(20)          NOT NULL,\n" +
                "   shijian                     varchar(100)         NOT NULL\n" +
                ")");

    }
}
