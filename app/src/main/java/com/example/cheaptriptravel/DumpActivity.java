package com.example.cheaptriptravel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DumpActivity extends AppCompatActivity implements Serializable{

    private JSONObject description = new JSONObject();

    private List<String> events = new ArrayList<String>();

    private List<String> times = new ArrayList<String>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dump);

        try {
            description.put("comment","good");
            events.add("去小北门拿快递");
            events.add("跟同事一起聚餐");
            events.add("go hk");
            events.add("go home");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Location location = new Location("ABC Restaurant",new LatLng(22.316279,114.180408),description, events);


        Button button = (Button) findViewById(R.id.dumpbutton1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DumpActivity.this, TimelineActivity.class);

                intent.putExtra("loc0",location.getName());
                intent.putExtra("loc1",location.getLatLng());
                intent.putExtra("loc2", (Serializable) location.getEvents());


                startActivity(intent);
            }
        });
    }
}
