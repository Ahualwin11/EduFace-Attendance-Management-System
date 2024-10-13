package com.one.bean;

import com.one.bean.ClassBean;

public class Student {
    private int id;
    private String name;
    private String password;
    private String stuid;
    private String classid;
    private String createDate;
    private String sex;
    private ClassBean classBean;
    private String time ;


    public Student() {
    }

    public Student(int id, String name, String sex, String classid, String stuid) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.stuid = stuid;
        this.classid = classid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    /**
     * 获取
     * @return id
     */


    public int getId() {
        return id;
    }

    /**
     * 设置
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 获取
     * @return name
     */


    public String getName() {
        return name;
    }

    /**
     * 设置
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取
     * @return stuid
     */
    public String getStuid() {
        return stuid;
    }

    /**
     * 设置
     * @param stuid
     */
    public void setStuid(String stuid) {
        this.stuid = stuid;
    }

    /**
     * 获取
     * @return classid
     */
    public String getClassid() {
        return classid;
    }

    /**
     * 设置
     * @param classid
     */
    public void setClassid(String classid) {
        this.classid = classid;
    }

    /**
     * 获取
     * @return createDate
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * 设置
     * @param createDate
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public ClassBean getClassBean() {
        return classBean;
    }

    public void setClassBean(ClassBean classBean) {
        this.classBean = classBean;
    }

    public String toString() {
        return name;
    }
}
