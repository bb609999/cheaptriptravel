package com.example.cheaptriptravel;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Created by waiwai on 26/2/2018.
 */

public class Trips extends DataSupport {
    private String name;

    private int id;

    private double lat;
    private double lon;

    private String address;

    private String opentime;

    private String content;

    private String tag;

    private int rating;

    private Date publishDate;

    private TripsIntroduction tripsIntroduction;

    private List<TripsComment> commentList = new ArrayList<TripsComment>();

    private List<TripsCategory> categoryList = new ArrayList<TripsCategory>();

    public List<TripsCategory> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<TripsCategory> categoryList) {
        this.categoryList = categoryList;
    }

    public void setCommentList(List<TripsComment> commentList) {
        this.commentList = commentList;
    }

    public List<TripsComment> getCommentList() {
        return commentList;
    }

    public void setTripsIntroduction(TripsIntroduction tripsIntroduction) {
        this.tripsIntroduction = tripsIntroduction;
    }

    public TripsIntroduction getTripsIntroduction() {
        return tripsIntroduction;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public double getLon() {
        return lon;
    }

    public int getRating() {
        return rating;
    }

    public String getAddress() {
        return address;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public String getOpentime() {
        return opentime;
    }

    public String getTag() {
        return tag;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
