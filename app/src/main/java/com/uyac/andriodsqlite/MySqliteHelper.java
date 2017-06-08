package com.uyac.andriodsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ShaoQuanwei on 2017/2/15.
 */

public class MySqliteHelper extends SQLiteOpenHelper {

    private String TAG = "MySqliteHelper";

    /*表名*/
    private final String TABLE_NAME_PERSON = "person";
    /*id字段*/
    private final String VALUE_ID = "_id";
    private final String VALUE_NAME = "name";
    private final String VALUE_ISBOY = "isboy";
    private final String VALUE_AGE = "age";
    private final String VALUE_ADDRESS = "address";
    /*头像字段*/
    private final String VALUE_PIC = "pic";

    /*创建表语句 语句对大小写不敏感 create table 表名(字段名 类型，字段名 类型，…)*/
    private final String CREATE_PERSON = "create table " + TABLE_NAME_PERSON + "(" +
            VALUE_ID + " integer primary key," +
            VALUE_NAME + " text ," +
            VALUE_ISBOY + " integer," +
            VALUE_AGE + " ingeter," +
            VALUE_ADDRESS + " text," +
            VALUE_PIC + " blob" +
            ")";


    public MySqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

        Log.e(TAG, "-------> MySqliteHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //创建表
        db.execSQL(CREATE_PERSON);

        Log.e(TAG, "-------> onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.e(TAG, "-------> onUpgrade");

    }


    /**
     * @param model 数据模型
     * @return 返回添加数据有木有成功
     */
    public boolean addPersonData(PersonModel model) {
        //把数据添加到ContentValues
        ContentValues values = new ContentValues();
        values.put(VALUE_NAME, model.getName());
        values.put(VALUE_AGE, model.getAge());
        values.put(VALUE_ISBOY, model.getIsBoy());
        values.put(VALUE_ADDRESS, model.getAddress());
        values.put(VALUE_PIC, model.getPic());

        //添加数据到数据库
        long index = getWritableDatabase().insert(TABLE_NAME_PERSON, null, values);

        //大于0表示添加成功
        if (index > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 添加数据
     * @param model 数据模型
     * @return 返回添加数据有木有成功
     */
    public PersonModel addPersonDataReturnID(PersonModel model) {
        //把数据添加到ContentValues
        ContentValues values = new ContentValues();
        values.put(VALUE_NAME, model.getName());
        values.put(VALUE_AGE, model.getAge());
        values.put(VALUE_ISBOY, model.getIsBoy());
        values.put(VALUE_ADDRESS, model.getAddress());
        //这里存储图片，model.getPic() 是一个字节数组
        values.put(VALUE_PIC, model.getPic());

        //添加数据到数据库
        long index = getWritableDatabase().insert(TABLE_NAME_PERSON, null, values);

        //不等于-1表示添加成功(可以看insert源码)
//    public long insert(String table, String nullColumnHack, ContentValues values) {
//        try {
//            return insertWithOnConflict(table, nullColumnHack, values, CONFLICT_NONE);
//        } catch (SQLException e) {
//            Log.e(TAG, "Error inserting " + values, e);
//            return -1;
//        }
//    }
        if (index != -1) {
            model.setId(index);
            return model;
        } else {
            return null;
        }
    }

    /**
     * sql语句添加数据，比较麻烦
     */
    public void addPersonDataSql(PersonModel model) {

        //格式： insert into 表名 （字段名,字段名,…）value('字段值','字段值','…')
        //看着很多，其实就是这个 insert into person (name,age,isboy,address,pic) values('五天','3','0','上海市浦东新区x606','[B@5340395')
        String insertSql = "insert into " + TABLE_NAME_PERSON + " (" +
                VALUE_NAME + "," +
                VALUE_AGE + "," +
                VALUE_ISBOY + "," +
                VALUE_ADDRESS + "," +
                VALUE_PIC + ")" +
                " values" + "(" +
                "'" + model.getName() + "'," +
                "'" + model.getAge() + "'," +
                "'" + model.getIsBoy() + "'," +
                "'" + model.getAddress() + "'," +
                "'" + model.getPic() + "'" +
                ")";

        Log.e(TAG, "" + insertSql);

        getWritableDatabase().execSQL(insertSql);

    }


    /**
     * 方法删除数据库数据
     */
    public void deletePersonData(PersonModel model) {
        //where后跟条件表达式 =,!=,>,<,>=,<=
        //多条件  and or

        //删除数据库里的model数据 因为_id具有唯一性。
        getWritableDatabase().delete(TABLE_NAME_PERSON, VALUE_ID + "=?", new String[]{"" + model.getId()});
        /*//删除数据库里 _id = 1 的数据
        getWritableDatabase().delete(TABLE_NAME_PERSON,VALUE_ID+"=?",new String[]{"1"});
        //删除 age >= 18 的数据
        getWritableDatabase().delete(TABLE_NAME_PERSON,VALUE_AGE+">=?",new String[]{"18"});
        //删除 id > 5 && age <= 18 的数据
        getWritableDatabase().delete(TABLE_NAME_PERSON,VALUE_ID+">?"+" and "+VALUE_AGE +"<=?",new String[]{"5","18"});
        //删除 id > 5 || age <= 18 的数据
        getWritableDatabase().delete(TABLE_NAME_PERSON,VALUE_ID+">?"+" or "+VALUE_AGE +"<=?",new String[]{"5","18"});
        //删除数据库里 _id != 1 的数据
        getWritableDatabase().delete(TABLE_NAME_PERSON,VALUE_ID+"!=?",new String[]{"1"});
        //删除所有 _id >= 7 的男生
        getWritableDatabase().delete(TABLE_NAME_PERSON,VALUE_ISBOY+"=?"+" and "+VALUE_ID+">=?",new String[]{"1","7"});
        //删除所有 _id >= 7 和 _id = 3 的数据
        getWritableDatabase().delete(TABLE_NAME_PERSON,VALUE_ID+">=?"+" or "+VALUE_ID+"=?",new String[]{"7","3"});*/

    }

    /**
     * sql删除数据库数据
     */
    public void deletePersonDataSql(PersonModel model) {
        //条件表达式 =,!=,>,<,>=,<=
        //语法格式 delete from 表名 where 字段 条件表达式 '值'
        //语法示例 delete from person where _id='2'

        //多条件 delete from person where _id>'10' and age>'100'
        //多条件 delete from person where _id>'10' or _id<'5'

        //删除数据库里的model数据 因为_id具有唯一性。
        String sql1 = "delete from " + TABLE_NAME_PERSON + " where " +
                VALUE_ID + "=" + "'" + model.getId() + "'";
        //删除数据库里 _id = 1 的数据
        String sql2 = "delete from " + TABLE_NAME_PERSON + " where " +
                VALUE_ID + "=" + "'" + 1 + "'";
        //删除 age >= 18 的数据
        String sql3 = "delete from " + TABLE_NAME_PERSON + " where " +
                VALUE_AGE + ">=" + "'" + 18 + "'";
        //删除 id > 5 && age <= 18 的数据
        String sql4 = "delete from " + TABLE_NAME_PERSON + " where " +
                VALUE_ID + ">" + "'" + 5 + "'" + " and " +
                VALUE_AGE + "<=" + "'" + 18 + "'";
        ////删除 id > 5 || age <= 18 的数据
        String sql5 = "delete from " + TABLE_NAME_PERSON + " where " +
                VALUE_ID + ">" + "'" + 5 + "'" + " or " +
                VALUE_AGE + "<=" + "'" + 18 + "'";
        //删除数据库里 _id != 1 的数据
        String sql6 = "delete from " + TABLE_NAME_PERSON + " where " +
                VALUE_ID + "!=" + "'" + 1 + "'";
        //删除所有 _id >= 7 的男生
        String sql7 = "delete from " + TABLE_NAME_PERSON + " where " +
                VALUE_ID + ">=" + "'" + 7 + "'" + " and " +
                VALUE_ISBOY + "=" + "'" + 1 + "'";
        //删除所有 _id >= 7 和 _id = 3 的数据
        String sql8 = "delete from " + TABLE_NAME_PERSON + " where " +
                VALUE_ID + ">=" + "'" + 7 + "'" + " and " +
                VALUE_ID + "=" + "'" + 3 + "'";


        Log.e(TAG, "" + sql7);
        getWritableDatabase().execSQL(sql7);
    }


    /**
     * 方法修改数据库数据
     */
    public void updatePersonData(PersonModel model) {
        //条件表达式 =,!=,>,<,>=,<=
        //多条件 and or  and和or都可以无限连接
        //多条件示例 _id>=? and _id<=?
        //多条件示例 _id>=? or _id=? or _id=?

        //将数据添加至ContentValues
        ContentValues values = new ContentValues();
        values.put(VALUE_NAME, model.getName());
        values.put(VALUE_ADDRESS, model.getAddress());
        values.put(VALUE_ISBOY, model.getIsBoy());
        values.put(VALUE_AGE, model.getAge());
        values.put(VALUE_PIC, model.getPic());

        //修改model的数据
        getWritableDatabase().update(TABLE_NAME_PERSON, values, VALUE_ID + "=?", new String[]{"" + model.getId()});
        /*//将 _id>20 的数据全部修改成model  适合重置数据
        getWritableDatabase().update(TABLE_NAME_PERSON,values,VALUE_ID+">?",new String[]{"20"});
        //将 _id>=30 && _id<=40 的数据全部修改成model  适合重置数据
        getWritableDatabase().update(TABLE_NAME_PERSON,values,VALUE_ID+">=? and "+VALUE_ID+"<=?",new String[]{"30","40"});
        //将 _id>=40 || _id=30 || _id=20的 age 修改成18 (需先将model的数据修成成18) 这里and 和 or 的效果时一样的 因为_id是唯一的
        int count = getWritableDatabase().update(TABLE_NAME_PERSON,values,VALUE_ID+">=?"+" or "+VALUE_ID+"=?"+" or "+VALUE_ID+"=?",new String[]{"40","30","20"});*/

        // count 返回被修改的条数  >0 表示修改成功
        Log.e(TAG, "" + VALUE_ID + ">=? and " + VALUE_ID + "<=?");
        Log.e(TAG, "" + VALUE_ID + ">=?" + " or " + VALUE_ID + "=?" + " or " + VALUE_ID + "=?");

    }

    /**
     * sql修改数据库数据
     */
    public void updatePersonDataSql(PersonModel model) {
        //条件表达式 =,!=,>,<,>=,<=
        //多条件 and or  and和or都可以无限连接

        //修改格式 update 表名 set 字段='字段值', 字段='字段值',… where 字段='字段值'
        //多条件示例 update person set name='钢铁侠',isboy='1' where _id='2'
        //多条件示例 update person set name='天地',isboy='1',age='79',address='山东省青岛市开平路53号国棉四厂二宿舍1号楼2单元204户甲',pic='[B@266d768b' where _id>='30' and _id<='40'
        //多条件示例 update person set name='小二',isboy='1',age='18',address='河南南阳市八一路272号特钢公司',pic='[B@17560c26' where _id>='40' or _id='30' or _id='20'


        //修改model的数据
        String update1 = "update " + TABLE_NAME_PERSON + " set " +
                VALUE_NAME + "=" + "'" + model.getName() + "'," +
                VALUE_ISBOY + "=" + "'" + model.getIsBoy() + "'," +
                VALUE_AGE + "=" + "'" + model.getAge() + "'," +
                VALUE_ADDRESS + "=" + "'" + model.getAddress() + "'," +
                VALUE_PIC + "=" + "'" + model.getPic() + "'" + " where " +
                VALUE_ID + "=" + "'" + model.getId() + "'";

        //将 _id>20 的数据全部修改成model  适合重置数据
        String update2 = "update " + TABLE_NAME_PERSON + " set " +
                VALUE_NAME + "=" + "'" + model.getName() + "'," +
                VALUE_ISBOY + "=" + "'" + model.getIsBoy() + "'," +
                VALUE_AGE + "=" + "'" + model.getAge() + "'," +
                VALUE_ADDRESS + "=" + "'" + model.getAddress() + "'," +
                VALUE_PIC + "=" + "'" + model.getPic() + "'" + " where " +
                VALUE_ID + ">=" + "'" + "20" + "'";

        //将 _id>=30 && _id<=40 的数据全部修改成model  适合重置数据
        String update3 = "update " + TABLE_NAME_PERSON + " set " +
                VALUE_NAME + "=" + "'" + model.getName() + "'," +
                VALUE_ISBOY + "=" + "'" + model.getIsBoy() + "'," +
                VALUE_AGE + "=" + "'" + model.getAge() + "'," +
                VALUE_ADDRESS + "=" + "'" + model.getAddress() + "'," +
                VALUE_PIC + "=" + "'" + model.getPic() + "'" + " where " +
                VALUE_ID + ">=" + "'" + "30" + "'" + " and " +
                VALUE_ID + "<=" + "'" + "40" + "'";

        //将 _id>=40 || _id=30 || _id=20的 age 修改成18 (需先将model的数据修成成18) 这里and 和 or 的效果时一样的 因为_id是唯一的
        String update4 = "update " + TABLE_NAME_PERSON + " set " +
                VALUE_NAME + "=" + "'" + model.getName() + "'," +
                VALUE_ISBOY + "=" + "'" + model.getIsBoy() + "'," +
                VALUE_AGE + "=" + "'" + "18" + "'," +
                VALUE_ADDRESS + "=" + "'" + model.getAddress() + "'," +
                VALUE_PIC + "=" + "'" + model.getPic() + "'" + " where " +
                VALUE_ID + ">=" + "'" + "40" + "'" + " or " +
                VALUE_ID + "=" + "'" + "30" + "'" + " or " +
                VALUE_ID + "=" + "'" + "20" + "'";

        //其实前面set语句都一样，后面的where 不一样

        Log.e(TAG, "" + update1);
        Log.e(TAG, "" + update4);
        ;
        getWritableDatabase().execSQL(update4);

    }

    private boolean order_by;

    /**
     * 查询全部数据
     */
    public List<PersonModel> queryAllPersonData() {

        //查询全部数据
        Cursor cursor = getWritableDatabase().query(TABLE_NAME_PERSON, null, null, null, null, null, null, null);
        List<PersonModel> list = new ArrayList<>();
        if (cursor.getCount() > 0) {
            //移动到首位
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {

                int id = cursor.getInt(cursor.getColumnIndex(VALUE_ID));
                String name = cursor.getString(cursor.getColumnIndex(VALUE_NAME));
                int isBoy = cursor.getInt(cursor.getColumnIndex(VALUE_ISBOY));
                int age = cursor.getInt(cursor.getColumnIndex(VALUE_AGE));
                String address = cursor.getString(cursor.getColumnIndex(VALUE_ADDRESS));
                byte pic[] = cursor.getBlob(cursor.getColumnIndex(VALUE_PIC));

                PersonModel model = new PersonModel();
                model.setId(id);
                model.setName(name);
                model.setIsBoy(isBoy);
                model.setAge(age);
                model.setAddress(address);
                model.setPic(pic);

                list.add(model);
                //移动到下一位
                cursor.moveToNext();
            }
        }

        cursor.close();
        getWritableDatabase().close();

        return list;
    }

    /**
     * 查询全部数据,按id降序或者升序排列。
     */
    public List<PersonModel> queryAllPersonDataOrderBy() {

        order_by = !order_by;
        //查询全部数据
        Cursor cursor = getWritableDatabase().query(TABLE_NAME_PERSON, null, null, null, null, null, order_by ? VALUE_ID + " desc" : VALUE_ID + " asc", null);
        List<PersonModel> list = new ArrayList<>();
        if (cursor.getCount() > 0) {
            //移动到首位
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {

                int id = cursor.getInt(cursor.getColumnIndex(VALUE_ID));
                String name = cursor.getString(cursor.getColumnIndex(VALUE_NAME));
                int isBoy = cursor.getInt(cursor.getColumnIndex(VALUE_ISBOY));
                int age = cursor.getInt(cursor.getColumnIndex(VALUE_AGE));
                String address = cursor.getString(cursor.getColumnIndex(VALUE_ADDRESS));
                byte pic[] = cursor.getBlob(cursor.getColumnIndex(VALUE_PIC));

                PersonModel model = new PersonModel();
                model.setId(id);
                model.setName(name);
                model.setIsBoy(isBoy);
                model.setAge(age);
                model.setAddress(address);
                model.setPic(pic);

                list.add(model);
                //移动到下一位
                cursor.moveToNext();
            }
        }

        cursor.close();
        getWritableDatabase().close();

        return list;
    }


    /**
     * query()方法查询
     * 一些查询用法
     */
    public Cursor queryPersonData() {

        Cursor cursor = null;

        //查询全部数据
        cursor = getWritableDatabase().query(TABLE_NAME_PERSON, null, null, null, null, null, null);
        //查询 _id = 1 的数据
        cursor = getWritableDatabase().query(TABLE_NAME_PERSON, null, VALUE_ID + "=?", new String[]{"1"}, null, null, null);
        //查询 name = 张三 并且 age > 23 的数据
        cursor = getWritableDatabase().query(TABLE_NAME_PERSON, null, VALUE_NAME + "=?" + " and " + VALUE_AGE + ">?", new String[]{"张三", "23"}, null, null, null);
        //查询 name = 张三 并且 age > 23 的数据  并按照id 降序排列
        cursor = getWritableDatabase().query(TABLE_NAME_PERSON, null, VALUE_NAME + "=?" + " and " + VALUE_AGE + ">?", new String[]{"张三", "23"}, null, null, VALUE_ID + " desc");
        //查询数据按_id降序排列 并且只取前4条。
        cursor = getWritableDatabase().query(TABLE_NAME_PERSON, null, null, null, null, null, VALUE_ID + " desc", "0,4");

        return cursor;
    }


    /**
     * rawQuery()方法查询
     *
     * 一些查询用法
     *
     * 容易出错，万千注意。
     *
     * 注意空格、单引号、单词不要写错了。
     *
     */
    public Cursor rawQueryPersonData() {

        Cursor cursor = null;
        String rawQuerySql = null;

        //查询全部数据
        rawQuerySql =  "select * from "+TABLE_NAME_PERSON;
        //查询_id = 1 的数据  select * from person where _id = 1
        rawQuerySql = "select * from "+TABLE_NAME_PERSON+" where "+VALUE_ID +" = 1";

        //查询 name = 张三 并且 age > 23 的数据  通配符？ select * from person where name = ? and age > ?
        rawQuerySql = "select * from "+TABLE_NAME_PERSON+" where "+VALUE_NAME +" = ?"+" and "+ VALUE_AGE +" > ?";
//        cursor = getWritableDatabase().rawQuery(rawQuerySql,new String[]{"张三","23"});

        //查询 name = 张三 并且 age >= 23 的数据  select * from person where name = '张三' and age >= '23'
        rawQuerySql = "select * from "+TABLE_NAME_PERSON+" where "+VALUE_NAME +" = '张三'"+" and "+ VALUE_AGE +" >= '23'";

        //查询 name = 张三 并且 age >= 23 的数据  并按照id 降序排列  select * from person where name = '张三' and age >= '23' order by _id desc
        rawQuerySql = "select * from "+TABLE_NAME_PERSON+" where "+VALUE_NAME +" = '张三'"+" and "+ VALUE_AGE +" >= '23'"+" order by "+VALUE_ID +" desc";

        //查询数据按_id降序排列 并且只取前4条。(测试下标是从0开始)  select * from person order by _id desc limit 0, 4
        rawQuerySql = "select * from "+TABLE_NAME_PERSON+" order by "+VALUE_ID +" desc"+" limit 0, 4";

        //查询年龄在20岁以上或者是女生 的数据 select age,isboy from person where age > 20 or isboy != 1
        rawQuerySql = "select "+VALUE_AGE+","+VALUE_ISBOY +" from " +TABLE_NAME_PERSON+" where "+VALUE_AGE+" > 20"+" or "+VALUE_ISBOY +" != 1";

        //查询年龄小于等于20 或者 大于等于 80的数据 并且按年龄升序排列 select * from person where age <= 20 or age >=80 order by age asc
        rawQuerySql = "select * from "+TABLE_NAME_PERSON+" where "+VALUE_AGE+" <= 20"+" or "+VALUE_AGE+" >=80"+" order by "+VALUE_AGE+" asc";

        cursor = getWritableDatabase().rawQuery(rawQuerySql,null);

        Log.e(TAG, rawQuerySql );

        return cursor;

    }

    /**
     * 查询全部数据
     */
    public List<PersonModel> rawQueryAllPersonData() {

        //查询全部数据
        Cursor cursor = rawQueryPersonData();
        List<PersonModel> list = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            //移动到首位
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {

                int id = cursor.getInt(cursor.getColumnIndex(VALUE_ID));
                String name = cursor.getString(cursor.getColumnIndex(VALUE_NAME));
                int isBoy = cursor.getInt(cursor.getColumnIndex(VALUE_ISBOY));
                int age = cursor.getInt(cursor.getColumnIndex(VALUE_AGE));
                String address = cursor.getString(cursor.getColumnIndex(VALUE_ADDRESS));
                byte pic[] = cursor.getBlob(cursor.getColumnIndex(VALUE_PIC));

                PersonModel model = new PersonModel();
                model.setId(id);
                model.setName(name);
                model.setIsBoy(isBoy);
                model.setAge(age);
                model.setAddress(address);
                model.setPic(pic);

                list.add(model);
                //移动到下一位
                cursor.moveToNext();
            }
        }

        cursor.close();
        getWritableDatabase().close();

        return list;
    }

}
