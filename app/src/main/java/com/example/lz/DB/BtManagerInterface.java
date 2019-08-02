package com.example.lz.DB;

import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2019/7/25.
 */

public  interface BtManagerInterface<T>  {
    long save(T obj); //保存数据
    long saveAll(Collection<T> collection); //保存所有数据
    List<T> queryAll(T table); //根据类名（表名）查找所有的数据
    List<T> queryAll(int order); //通过查找序号的方式查找所有的数据
    T queryById(Class<T> table, Object id);//通过id查找对应的数据
    int delete(int number); //清空对应表名中的所有数据
    List<T> queryAll(); //查找表中所有数据

}
