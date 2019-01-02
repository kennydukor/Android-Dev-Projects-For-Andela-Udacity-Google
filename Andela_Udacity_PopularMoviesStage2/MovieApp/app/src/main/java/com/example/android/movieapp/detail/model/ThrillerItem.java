package com.example.android.movieapp.detail.model;

public class ThrillerItem {

    private String id;
    private String name;
    private String site;
    private String size;

    public ThrillerItem() {
    }

    public ThrillerItem(String id, String name, String site, String size) {
        this.id = id;
        this.name = name;
        this.site = site;
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
