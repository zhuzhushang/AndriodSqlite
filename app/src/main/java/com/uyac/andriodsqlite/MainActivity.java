package com.uyac.andriodsqlite;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "MainActivity";
    private Context context;
    private RecyclerView recyclerview;
    private List<PersonModel> mList;
    private RecyclerAdapter mAdapter;
    private Button add, delete, modify, query,update;
    private MySqliteHelper mySqliteHelper;

    private Random mRandom;
    //名字数组，用于添加数据时随机
    private String nameArray[] = {"大一", "小二", "张三", "李四", "王五", "马六", "胡七", "王八", "金九", "银十", "天地", "玄黄", "宇宙", "洪荒", "嘻嘻", "欣欣向荣", "小明", "小红帽", "五天", "陈奕迅"};
    private String addressArray[] = {"北京市东花市北里20号楼6单元501室",
            "虹口区西康南路125弄34号201室 ",
            "湖北省荆州市红苑大酒店 李有财 ",
            "河南南阳市八一路272号特钢公司",
            "广东中山市东区亨达花园7栋702",
            "福建省厦门市莲花五村龙昌里34号601室",
            "山东省青岛市开平路53号国棉四厂二宿舍1号楼2单元204户甲",
            "河南省南阳市中州路42号",
            "中国四川省江油市川西北矿区采气一队 ",
            "北京市朝阳区霄云路50号",
            "北京市西城区槐柏树街22号",
            "广东省广州市越秀区中山六路",
            "上海市浦东新区x606"};
    private int ageArray[] = {464, 654, 564, 56, 456, 456, 4, 56131, 8, 1, 3, 556, 464, 611, 66, 5, 79, 9, 8, 79, 1, 100, 12, 15, 10000};
    private int isBoyArray[] = {1, 1, 0, 1, 0, 10, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0,};
    private int imgArray[] = {R.mipmap.ic_launcher, R.mipmap.pic_1, R.mipmap.pic_2, R.mipmap.pic_3, R.mipmap.pic_4, R.mipmap.pic_5, R.mipmap.pic_6, R.mipmap.pic_7, R.mipmap.pic_8, R.mipmap.pic_9, R.mipmap.pic_10, R.mipmap.pic_11, R.mipmap.pic_12, R.mipmap.pic_spc};
    private byte picArray[][];
    //缺点
    private String bads[] = {"懒","宅","笨","脾气暴躁"};
    //优点
    private String goods[] = {"爱运动","爱笑","专业能力强","无敌","善良"};

    //用于更新的版本号
    private int currentVersion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        viewInit();
        dataInit();
        eventInit();

        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(null);
        imageView.getDrawable();

    }


    private void viewInit() {
        // TODO Auto-generated method stub

        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        add = (Button) findViewById(R.id.add);
        delete = (Button) findViewById(R.id.delete);
        modify = (Button) findViewById(R.id.modify);
        query = (Button) findViewById(R.id.query);
        update = (Button) findViewById(R.id.update);
    }

    private void dataInit() {
        // TODO Auto-generated method stub

        mRandom = new Random();
        mySqliteHelper = new MySqliteHelper(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        currentVersion = Constants.DB_VERSION;
        mList = new ArrayList<>();
        mAdapter = new RecyclerAdapter(context, mList);
        recyclerview.setAdapter(mAdapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        //加载图片
        loadImg();

    }

    private void eventInit() {
        // TODO Auto-generated method stub
        add.setOnClickListener(this);
        delete.setOnClickListener(this);
        modify.setOnClickListener(this);
        query.setOnClickListener(this);
        update.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        mList.clear();
        mList.addAll(mySqliteHelper.queryAllPersonData());
        Log.e(TAG, "" + mList.size());
        mAdapter.notifyDataSetChanged();

    }

    private void loadImg() {
        picArray = new byte[imgArray.length][];

        for (int i = 0; i < imgArray.length; i++) {

            picArray[i] = picTobyte(imgArray[i]);
        }
    }


    /**
     * @param resourceID  图片资源id
     * @return   将图片转化成byte
     */
    private byte[] picTobyte(int resourceID)
    {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream is = context.getResources().openRawResource(resourceID);
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        //压缩图片，100代表不压缩（0～100）
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

        return baos.toByteArray();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.add:

                addDataReturnID();
//                addData();
//                addDataSql();

                break;
            case R.id.delete:

                if (mList == null || mList.size() == 0) {
                    ToastUtils.show(context, "请先添加数据");
                    return;
                }

                mySqliteHelper.deletePersonData(mList.get(0));
//                mySqliteHelper.deletePersonDataSql(new PersonModel());
                notifyData();


                ToastUtils.show(context, "删除第一条数据成功");


                break;
            case R.id.modify:

                if (mList == null || mList.size() == 0) {
                    ToastUtils.show(context, "请先添加数据");
                    return;
                }

                PersonModel model = getPersonModel();
                model.setId(mList.get(0).getId());
                mySqliteHelper.updatePersonData(model);
//                mySqliteHelper.updatePersonDataSql(getPersonModel());

                ToastUtils.show(context, "修改第一条数据成功");

                notifyData();

                break;
            case R.id.query:

                if (mList == null || mList.size() == 0) {
                    ToastUtils.show(context, "请先添加数据");
                    return;
                }

                mList.clear();
                mList.addAll(mySqliteHelper.queryAllPersonDataOrderBy());
//                mList.addAll(mySqliteHelper.rawQueryAllPersonData());
                mAdapter.notifyDataSetChanged();

                break;
            case R.id.update:

                //增加版本号来更新数据库
//                mySqliteHelper = new MySqliteHelper(context, Constants.DB_NAME, null, ++currentVersion);
//                Log.e(TAG, " currentVersion = "+currentVersion );

                break;
        }


    }

    /**
     * 添加数据
     */
    private void addData() {

        PersonModel model = new PersonModel();
        model.setAddress(addressArray[mRandom.nextInt(addressArray.length)]);
        model.setName(nameArray[mRandom.nextInt(nameArray.length)]);
        model.setAge(mRandom.nextInt(101));
        model.setIsBoy(isBoyArray[mRandom.nextInt(isBoyArray.length)]);
        model.setPic(picArray[mRandom.nextInt(picArray.length)]);

        boolean isSucc = mySqliteHelper.addPersonData(model);

        if (isSucc) {

            ToastUtils.show(context, "添加数据成功");
            mList.add(0, model);
            mAdapter.notifyDataSetChanged();
        } else {
            ToastUtils.show(context, "添加失败");
        }
    }

    /**
     * 添加数据返回id
     */
    private void addDataReturnID() {

        PersonModel model = new PersonModel();
        model.setAddress(addressArray[mRandom.nextInt(addressArray.length)]);
        model.setName(nameArray[mRandom.nextInt(nameArray.length)]);
        model.setAge(mRandom.nextInt(101));
        model.setIsBoy(isBoyArray[mRandom.nextInt(isBoyArray.length)]);
        model.setPic(picArray[mRandom.nextInt(picArray.length)]);

        model = mySqliteHelper.addPersonDataReturnID(model);

        if (model != null) {

            ToastUtils.show(context, "添加数据成功");
            mList.add(0, model);
            mAdapter.notifyDataSetChanged();
        } else {
            ToastUtils.show(context, "添加失败");
        }
    }

    /**
     * 用sql语句添加数据
     */
    private void addDataSql() {

        PersonModel model = new PersonModel();
        model.setAddress(addressArray[mRandom.nextInt(addressArray.length)]);
        model.setName(nameArray[mRandom.nextInt(nameArray.length)]);
        model.setAge(mRandom.nextInt(101));
        model.setIsBoy(isBoyArray[mRandom.nextInt(isBoyArray.length)]);
        model.setPic(picArray[mRandom.nextInt(picArray.length)]);

        mySqliteHelper.addPersonDataSql(model);
        mList.add(0, model);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * @return 生成一个随机的PersonModel
     */
    private PersonModel getPersonModel() {
        PersonModel model = new PersonModel();
        model.setAddress(addressArray[mRandom.nextInt(addressArray.length)]);
        model.setName(nameArray[mRandom.nextInt(nameArray.length)]);
        model.setAge(mRandom.nextInt(101));
        model.setIsBoy(isBoyArray[mRandom.nextInt(isBoyArray.length)]);
        model.setPic(picArray[mRandom.nextInt(picArray.length)]);

        return model;



    }


    /**
     * @param min
     * @param max
     * @return 随机几到几
     */
    private int randomNum(int min, int max) {
        if (min > max) {
            int temp = min;
            min = max;
            max = temp;
        }

        //专为此类设计
        max = max - 1;

        int r = Math.abs(max - min) + 1;

        return mRandom.nextInt(r) + min;
    }


    private void notifyData() {
        mList.clear();
        mList.addAll(mySqliteHelper.queryAllPersonData());
        mAdapter.notifyDataSetChanged();

    }


}
