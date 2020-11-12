package com.example.study.vo;

public class UserVO {

    private int idx;
    private String id;
    private String password;
    private String name;
    private String email;
    private String address;
    private String birth;
    private String grade;
    private int invite;
    private int cash;

    public UserVO() { }

    public UserVO(String id, String password, String name, String email, String address, String birth, String grade, int invite, int cash) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.address = address;
        this.birth = birth;
        this.grade = grade;
        this.invite = invite;
        this.cash = cash;
    }

    public UserVO(int idx, String id, String password, String name, String email, String address, String birth, String grade, int invite, int cash) {
        this.idx = idx;
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.address = address;
        this.birth = birth;
        this.grade = grade;
        this.invite = invite;
        this.cash = cash;
    }

    public int getIdx() {
        return idx;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getBirth() {
        return birth;
    }

    public String getGrade() {
        return grade;
    }

    public int getInvite() {
        return invite;
    }

    public int getCash() {
        return cash;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "idx=" + idx +
                ", id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", birth='" + birth + '\'' +
                ", grade='" + grade + '\'' +
                ", invite=" + invite +
                ", cash=" + cash +
                '}';
    }
}
