package com.example.study.vo;

public class AddressVO {
    private String id;
    private String name;
    private String tel;

    public AddressVO(String id, String name, String tel) {
        this.id = id;
        this.name = name;
        this.tel = tel;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTel() {
        return tel;
    }
}
