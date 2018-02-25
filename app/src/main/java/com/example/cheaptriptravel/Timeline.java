package com.example.cheaptriptravel;

/**
 * Created by waiwai on 25/2/2018.
 */

public class Timeline {
    private String acceptTime;
    private String acceptStation;
    private String acceptTool;

    public Timeline() {
    }

    public Timeline(String acceptTime, String acceptStation, String acceptTool) {
        this.acceptTime = acceptTime;
        this.acceptStation = acceptStation;
        this.acceptTool = acceptTool;
    }

    public String getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getAcceptStation() {
        return acceptStation;
    }

    public void setAcceptStation(String acceptStation) {
        this.acceptStation = acceptStation;
    }

    public void setTravelTool(String acceptTool){
        this.acceptTool = acceptTool;
    }

    public String getTravelTool(){
        return acceptTool;
    }

}


