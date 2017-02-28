package com.uyac.andriodsqlite;

/**
 * Created by ShaoQuanwei on 2017/2/14.
 */

public class PersonModel {

    private int id;
    private String name;
    /*1代表是男孩*/
    private int isBoy;
    private int age;
    private String address;
    private byte[] pic;


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIsBoy() {
        return isBoy;
    }

    public void setIsBoy(int isBoy) {
        this.isBoy = isBoy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }

}
