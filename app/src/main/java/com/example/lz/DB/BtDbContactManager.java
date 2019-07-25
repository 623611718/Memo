package com.example.lz.DB;

/**
 * Created by Administrator on 2019/7/25.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.lz.Bran.ContactsEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BtDbContactManager implements BtManagerInterface<ContactsEntity> {

    private static final String TAG = "DB";

    private static Context mCtx;
    public BtDBHelper mDBHelper;
    public  SQLiteDatabase mWritableDatabase;
    private String tableName = BtDbConfiguration.TABLE_CUSTOMER;
    // 列定义
    private final String[] ORDER_COLUMNS = new String[]{BtDbConfiguration.ContactsEntityfig.Contacts_ID,
            BtDbConfiguration.ContactsEntityfig.TiTle,
            BtDbConfiguration.ContactsEntityfig.Content,
            BtDbConfiguration.ContactsEntityfig.Date,
            BtDbConfiguration.ContactsEntityfig.Time,
            BtDbConfiguration.ContactsEntityfig.Number,
    };

    private  BtDbContactManager() {

    }

    public void init(Context context){
        this.mCtx = context;
        this.mDBHelper = new BtDBHelper(mCtx, BtDbConfiguration.DB_NAME, null, BtDbConfiguration.DB_VERSION);
        this.mWritableDatabase = mDBHelper.getWritableDatabase();
    }

    //判断表是否存在
    private boolean IsTableExist( ) {
        boolean isTableExist = false;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = mWritableDatabase;
            String sql = "select count(*) as c from Sqlite_master  where type ='table' and name ='"+tableName.trim()+"' ";
            cursor = db.rawQuery(sql, null);
            if(cursor.moveToNext()){
                int count = cursor.getInt(0);
                if(count>0){
                    isTableExist = true;
                }
            }
        } catch (Exception e) {

        }
        return isTableExist;
    }

    private static class Holder {
        private static BtDbContactManager INSTANCE = new BtDbContactManager();
    }

    public static BtDbContactManager getInstance() { // 静态内部类实现单例

        return Holder.INSTANCE;
    }

    private SQLiteDatabase getDB() {
        if (null == mWritableDatabase) {
            this.mWritableDatabase = mDBHelper.getWritableDatabase();
        }
        return this.mWritableDatabase;
    }


    @Override
    public long save(ContactsEntity contactenty) {
        Log.i("DB","save" );
        long result = -1;
        if (mWritableDatabase != null){
            if (!IsTableExist()){
                mDBHelper.createDbtable(mWritableDatabase,tableName);
            }
        }
        if (null == contactenty) {
            Log.e(TAG, "contactenty is Empty !!!");
            return -1;
        }
        try {
            ContentValues values = new ContentValues();
            values.put(BtDbConfiguration.ContactsEntityfig.TiTle, contactenty.getTitle());
            values.put(BtDbConfiguration.ContactsEntityfig.Content, contactenty.getContent());
            values.put(BtDbConfiguration.ContactsEntityfig.Date, contactenty.getDate());
            values.put(BtDbConfiguration.ContactsEntityfig.Time, contactenty.getTime());
            values.put(BtDbConfiguration.ContactsEntityfig.Number, contactenty.getNumber());
            result = getDB().replace(BtDbConfiguration.TABLE_CUSTOMER, null, values); //使用replace 实现没有就添加有则更新
            Log.i("DB","保存成功");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    public long saveAll(Collection<ContactsEntity> collection) {
        synchronized (mWritableDatabase) {
            long result = -1;
            if (null == collection || collection.isEmpty()) {
                Log.e(TAG, "contactentys is Empty !!!");
                return result;
            }
            try {
                getDB().beginTransaction();
                for (ContactsEntity contactenty : collection) {
                    ContentValues values = new ContentValues();
                    values.put(BtDbConfiguration.ContactsEntityfig.TiTle, contactenty.getTitle());
                    values.put(BtDbConfiguration.ContactsEntityfig.Content, contactenty.getContent());
                    values.put(BtDbConfiguration.ContactsEntityfig.Date, contactenty.getDate());
                    values.put(BtDbConfiguration.ContactsEntityfig.Time, contactenty.getTime());
                    values.put(BtDbConfiguration.ContactsEntityfig.Number, contactenty.getNumber());
                    result = getDB().insert(BtDbConfiguration.TABLE_CUSTOMER, null, values);
                }
                getDB().setTransactionSuccessful();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                getDB().endTransaction();

            }

            return result;
        }
    }

    @Override
    public List<ContactsEntity> queryAll(ContactsEntity entity) {
        Cursor cursor = null;
        try {
            List<ContactsEntity> list = new ArrayList<>();
            cursor = getDB().query(tableName, null, null, null, null, null, null);
            if(cursor.getCount() > 0)
            {
                while (cursor.moveToNext()) {
                    list.add(parseContact(cursor));
                }
            }
            return list;
        } catch (Exception e) {
            Log.e(TAG, "" + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }

        }
        return null;
    }


    @Override
    public List<ContactsEntity> queryAll( int number) { //查询单个人名
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = getDB();

            //1.使用这种query方法%号前不能加' ;
            //            Cursor c_test = mDatabase.query(tab_name, new String[]{tab_field02}, tab_field02+"  LIKE ? ",
            //                    new String[] { "%" + str[0] + "%" }, null, null, null);
            //2.使用这种query方法%号前必须加'  ; 注意空格
            //  Cursor  c_test=mDatabase.query(tab_name, new String[]{tab_field02},tab_field02+"  like '%" + str[0] + "%'", null, null, null, null);
            //多条件查询  BtDbConfiguration.ContactsEntityfig.FULLNAME + " LIKE" + " '%" + fullname + "%' "
            //                + " OR " +  BtDbConfiguration.ContactsEntityfig.NUMBER+ " LIKE" + " '%" + number + "%'";/
            cursor = db.query(tableName,
                    ORDER_COLUMNS,
                    BtDbConfiguration.ContactsEntityfig.Number+ " LIKE ? ",
                    new String[]{"%" + number + "%"},
                    null, null, null);
            if (cursor.getCount() > 0) {
                List<ContactsEntity> orderList = new ArrayList<ContactsEntity>(cursor.getCount());
                while (cursor.moveToNext()) {
                    ContactsEntity entity = parseContact(cursor);
                    orderList.add(entity);
                }
                return orderList;
            }
        } catch (Exception e) {
            Log.e(TAG, "" + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }

        }
        return null;
    }


    @Override
    public ContactsEntity queryById(Class<ContactsEntity> table, Object id) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = getDB();

            //1.使用这种query方法%号前不能加' ;
            //            Cursor c_test = mDatabase.query(tab_name, new String[]{tab_field02}, tab_field02+"  LIKE ? ",
            //                    new String[] { "%" + str[0] + "%" }, null, null, null);
            //2.使用这种query方法%号前必须加'  ; 注意空格
            //  Cursor  c_test=mDatabase.query(tab_name, new String[]{tab_field02},tab_field02+"  like '%" + str[0] + "%'", null, null, null, null);
            cursor = db.query(tableName,
                    ORDER_COLUMNS,
                    BtDbConfiguration.ContactsEntityfig.Contacts_ID + " LIKE ? ",
                    new String[]{"%" + id + "%"},
                    null, null, null);
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    return parseContact(cursor);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "" + e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }

        }
        return null;
    }

    //// 删除 id = 1的数据
//        sqliteDatabase.delete("user", "id=?", new String[]{"1"});
//    // 参数1：表名(String)
//    // 参数2：WHERE表达式（String），需删除数据的行； 若该参数为 null, 就会删除所有行；？号是占位符
//    // 参数3：WHERE选择语句的参数(String[]), 逐个替换 WHERE表达式中 的“？”占位符;
//  db.delete(tableName, fieldName + "=?", new String[]{value});
//    // 注：也可采用SQL语句修改
//    String sql = "delete from user where id="1"；
//            db.execSQL(sql);
    @Override
    public int delete(Class table) {
        SQLiteDatabase db = null;
        int result = 0;
        try {
            db = getDB();
            result = db.delete(tableName, "btorder=?", new String[]{"1"});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

//    /**
//     *  操作：修改数据 = update（）
//     */
//    // a. 创建一个ContentValues对象
//    ContentValues values = new ContentValues();
//        values.put("name", "zhangsan");
//
//    // b. 调用update方法修改数据库：将id=1 修改成 name = zhangsan
//        sqliteDatabase.update("user", values, "id=?", new String[] { "1" });
//    // 参数1：表名(String)
//    // 参数2：需修改的ContentValues对象
//    // 参数3：WHERE表达式（String），需数据更新的行； 若该参数为 null, 就会修改所有行；？号是占位符
//    // 参数4：WHERE选择语句的参数(String[]), 逐个替换 WHERE表达式中 的“？”占位符;
//
//    // 注：调用完upgrate（）后，则会回调 数据库子类的onUpgrade()
//
//    // 注：也可采用SQL语句修改
//    String sql = "update [user] set name = 'zhangsan' where id="1";
//            db.execSQL(sql);


    /**
     * 将查找到的数据转换成ContactsEntity类
     */
    private ContactsEntity parseContact(Cursor cursor) {
        ContactsEntity entity = new ContactsEntity();
        entity.setId(cursor.getInt(cursor.getColumnIndex(BtDbConfiguration.ContactsEntityfig.Contacts_ID)));
        entity.setTitle(cursor.getString(cursor.getColumnIndex(BtDbConfiguration.ContactsEntityfig.TiTle)));
        entity.setContent(cursor.getString(cursor.getColumnIndex(BtDbConfiguration.ContactsEntityfig.Content)));
        entity.setDate(cursor.getString(cursor.getColumnIndex(BtDbConfiguration.ContactsEntityfig.Date)));
        entity.setTime(cursor.getString(cursor.getColumnIndex(BtDbConfiguration.ContactsEntityfig.Time)));
        entity.setNumber(Integer.parseInt(cursor.getString(cursor.getColumnIndex(BtDbConfiguration.ContactsEntityfig.Number))));
        return entity;
    }

}
