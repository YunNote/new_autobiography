package com.example.study.vo;

public class TestAddressVO {


    private int id;
    private String avatar;
    private String name;
    private String tel;

    public TestAddressVO(int id, String avatar, String name, String tel) {
        this.id = id;
        this.avatar = avatar;
        this.name = name;
        this.tel = tel;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTel() {
        return tel;
    }

    @Override
    public String toString() {
        return "TestAddressVO{" +
                "id='" + id + '\'' +
                ", avatar='" + avatar + '\'' +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                '}';
    }
}
