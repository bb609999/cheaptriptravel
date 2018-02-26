package com.example.cheaptriptravel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by waiwai on 26/2/2018.
 */

public class TripsCategory {
    private int id;
    private String categoryName;
    private int categoryCode;
    private List<Trips> TripsList = new ArrayList<Trips>();

    public List<Trips> getTripsList() {
        return TripsList;
    }

    public void setTripsList(List<Trips> tripsList) {
        TripsList = tripsList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}

