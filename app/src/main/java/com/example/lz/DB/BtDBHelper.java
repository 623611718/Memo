package com.example.lz.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2019/7/25.
 */

public class BtDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DB";

    public BtDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //数据库建表语句
    private static final String CREATE_SEARCH_CONTACT_TABLE = "create table "
            + BtDbConfiguration.TABLE_CUSTOMER + "("
            + BtDbConfiguration.ContactsEntityfig.Contacts_ID + " integer primary key autoincrement ,"
            + BtDbConfiguration.ContactsEntityfig.TiTle + " varchar(20),"
            + BtDbConfiguration.ContactsEntityfig.Content + " varchar(20) ,"
            + BtDbConfiguration.ContactsEntityfig.Date + " varchar(20) ,"
            + BtDbConfiguration.ContactsEntityfig.Time + " varchar(20) ,"
            + BtDbConfiguration.ContactsEntityfig.Number + " integer unique"
            + ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "DB=======onCreate()");

        db.execSQL(CREATE_SEARCH_CONTACT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        Log.i(TAG, "=======onUpgrade()");
        db.execSQL("DROP TABLE IF EXISTS " + BtDbConfiguration.TABLE_CUSTOMER); //删除表
    }

    public void dropDbtable(SQLiteDatabase db,String table){
        db.execSQL("DROP TABLE IF EXISTS " + table); //删除表
    }
    public void createDbtable(SQLiteDatabase db, String table){
        if (table.equals(BtDbConfiguration.TABLE_CUSTOMER)){
            db.execSQL(CREATE_SEARCH_CONTACT_TABLE);//创建表
        }

    }
}
