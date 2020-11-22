package com.example.study.vo;

public class SilverTownVO {

    private long idx;
    private String title;
    private String number;
    private String address;
    private String description;
    private String thumbnailUrl;
    private String coord;

    public SilverTownVO(long idx, String title, String number, String address, String description, String thumbnailUrl, String coord) {
        this.idx = idx;
        this.title = title;
        this.number = number;
        this.address = address;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.coord = coord;
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

    public String getCoord() {
        return coord;
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
                ", coord='" + coord + '\'' +
                '}';
    }
}
