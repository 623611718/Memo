package com.example.lz.DB;

/**
 * Created by Administrator on 2019/7/25.
 */

public class BtDbConfiguration {
    public static final int DB_VERSION = 2; // 版本号
    public static final String DB_NAME = "autoblue.db";//数据库名
//    public static final String TABLE_NAME = "AutoBluetooth";
    public static final String TABLE_CUSTOMER = "contactsentity"; //表名
    /**
     * contactsEntity数据表的字段
     */
    public static class ContactsEntityfig {
        //contactsEntity
        public static final String Contacts_ID="id";
        public static final String TiTle="title";
        public static final String Content="content";
        public static final String Date="date";
        public static final String Time="time";
        public static final String Number="number";
    }

}
