package com.example.cheaptriptravel;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by waiwai on 26/2/2018.
 */

public class Trips extends DataSupport {
    private String name;

    private int id;

    private LatLng location;

    private String opentime;

    private JSONObject description;//for the trip comments

    private List<String> times = new ArrayList<String>();//for timeline times

    private String places ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public String getOpentime() {
        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime;
    }

    public JSONObject getDescription() {
        return description;
    }

    public void setDescription(JSONObject description) {
        this.description = description;
    }

    public List<String> getTimes() {
        return times;
    }

    public void setTimes(List<String> times) {
        this.times = times;
    }

    public String getPlaces() {
        return places;
    }

    public void setPlaces(String places) {
        this.places = places;
    }
}
