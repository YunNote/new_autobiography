package com.example.study.vo;

public class SilverTownVO {

    private long idx;
    private String title;
    private String number;
    private String address;
    private String description;
    private String thumbnailUrl;

    public SilverTownVO(long idx, String title, String number, String address, String description, String thumbnailUrl) {
        this.idx = idx;
        this.title = title;
        this.number = number;
        this.address = address;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
    }

    public long getIdx() {
        return idx;
    }

    public String getTitle() {
        return title;
    }

    public String getNumber() {
        return number;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setIdx(long idx) {
        this.idx = idx;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public String toString() {
        return "SilverTownVO{" +
                "idx=" + idx +
                ", title='" + title + '\'' +
                ", number='" + number + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                '}';
    }
}
