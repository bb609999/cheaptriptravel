package com.example.cheaptriptravel;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by waiwai on 27/2/2018.
 */

public class Location implements Serializable{
    private String name;
    private LatLng latLng;
    private JSONObject description;
    private List<String> events = new ArrayList<String>();


    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }

    public Location(String name, LatLng latLng, JSONObject description, List<String> events) {
        this.name = name;
        this.latLng = latLng;
        this.description = description;
        this.events = events;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public JSONObject getDescription() {
        return description;
    }

    public void setDescription(JSONObject description) {
        this.description = description;
    }

}
