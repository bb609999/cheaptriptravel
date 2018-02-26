package com.example.cheaptriptravel;

import java.util.Date;

/**
 * Created by waiwai on 26/2/2018.
 */

public class TripsComment {
    private Trips trips;

    private String content;

    private Date publishDate;


    public Trips getTrips() {
        return trips;
    }

    public void setTrips(Trips trips) {
        this.trips = trips;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getPublishDate() {
        return publishDate;
    }
}

