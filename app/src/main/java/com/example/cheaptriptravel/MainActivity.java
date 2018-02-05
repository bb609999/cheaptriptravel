package com.example.cheaptriptravel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.cheaptriptravel.util.HttpUtil;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements
        OnMapClickListener, OnMapLongClickListener, OnCameraIdleListener,
        OnMapReadyCallback {

    private String OUHK = "22.316279,%20114.180408";

    private String APM = "22.312441,%20114.225046";

    private TextView mTapTextView;
    private TextView mCameraTextView;
    private GoogleMap mMap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculateDuration(OUHK,APM);

        mTapTextView = (TextView) findViewById(R.id.tap_text);
        mCameraTextView = (TextView) findViewById(R.id.camera_text);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnCameraIdleListener(this);
    }

    @Override
    public void onMapClick(LatLng point) {
        mTapTextView.setText("tapped, point=" + point);
    }

    @Override
    public void onMapLongClick(LatLng point) {
        mTapTextView.setText("long pressed, point=" + point);
    }

    @Override
    public void onCameraIdle() {
        mCameraTextView.setText(mMap.getCameraPosition().toString());
    }




    private void calculateDuration(String startPoint, String endPoint) {
        HttpUtil.sendOkHttpRequest("https://maps.googleapis.com/maps/api/distancematrix/json" +
                        "?origins=" + startPoint +
                        "&destinations=" + endPoint +
                        "&mode=transit" +
                        "&key=AIzaSyBGAlVrnlB0BqZxoc7K-PBo3qdwloXTa58",
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData = response.body().string();
                        try {
                            JSONObject jsonObject = new JSONObject(responseData);
                            String destination_addresses = jsonObject.getString("destination_addresses");
                            String origin_addresses = jsonObject.getString("origin_addresses");

                            JSONArray rows = jsonObject.getJSONArray("rows");

                            JSONObject elements = new JSONObject();


                            for (int i = 0; i < rows.length(); i++) {
                                elements = rows.getJSONObject(i);
                            }

                            JSONArray elementArray = elements.getJSONArray("elements");

                            JSONObject elementObj = elementArray.getJSONObject(0);

                            JSONObject durationObj = elementObj.getJSONObject("duration");

                            String duration_value = durationObj.getString("value");

                            Log.d("TEST", destination_addresses + "\n" + origin_addresses + "\n" + rows.toString() + "\n" + elements);
                            Log.d("TEST", elementArray.toString());
                            Log.d("TEST", elementObj.toString());
                            Log.d("TEST", duration_value);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
    }



}
