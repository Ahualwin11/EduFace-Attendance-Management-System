package com.one.bean;

import java.util.Date;

public class Attendence {
    private int id;
    private String stuid;
    private String name;
    private String sex;
    private String classid;
    private String banji;
    private String jieci;
    private String flag;
    private Date date;

    public Attendence(int id, String stuid, String name, String sex, String classid, String banji, String jieci, String flag, Date date) {
        this.id = id;
        this.stuid = stuid;
        this.name = name;
        this.sex = sex;
        this.classid = classid;
        this.banji = banji;
        this.jieci = jieci;
        this.flag = flag;
        this.date = date;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStuid() {
        return stuid;
    }

    public void setStuid(String stuid) {
        this.stuid = stuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getBanji() {
        return banji;
    }

    public void setBanji(String banji) {
        this.banji = banji;
    }

    public String getJieci() {
        return jieci;
    }

    public void setJieci(String jieci) {
        this.jieci = jieci;
    }





    public Attendence() {
    }

    public Attendence( String name) {

        this.name = name;

    }


    /**
     * 获取
     * @return flag
     */
    public String getFlag() {
        return flag;
    }

    /**
     * 设置
     * @param flag
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String toString() {
        return "Attendence{id = " + id + ", stuid = " + stuid + ", name = " + name + ", sex = " + sex + ", classid = " + classid + ", banji = " + banji + ", jieci = " + jieci + ", flag = " + flag + ", date = " + date + "}";
    }
}
