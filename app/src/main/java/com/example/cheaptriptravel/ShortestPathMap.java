package com.example.cheaptriptravel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cheaptriptravel.util.HttpUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ShortestPathMap extends AppCompatActivity implements
        GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnCameraIdleListener,
        OnMapReadyCallback {

    private TextView mTapTextView;
    private TextView mCameraTextView;
    private GoogleMap mMap;
    private String OUHK = "22.316279,114.180408";
    private String APM = "22.312441,114.225046";
    private String PLAZA = "22.310602,114.187868";
    private String GYIN = "22.308235,114.185765";
    private String MEGA = "22.320165,114.208168";

    private LatLng P1_OUHK = new LatLng(22.316279,114.180408);
    private LatLng P2_APM = new LatLng(22.312441,114.225046);
    private LatLng P3_PLAZA = new LatLng(22.310602,114.187868);
    private LatLng P4_GYIN = new LatLng(22.308235,114.185765);
    private LatLng P5_MEGA = new LatLng(22.320165,114.208168);

    private String a = processLatLng(P1_OUHK.toString());
    private String b = processLatLng(P2_APM.toString());
    private String c = processLatLng(P3_PLAZA .toString());
    private String d = processLatLng(P4_GYIN.toString());
    private String e = processLatLng(P5_MEGA.toString());

    private String shortestPath;


    private LatLng[] POIS = {P1_OUHK,P2_APM,P3_PLAZA,P4_GYIN,P5_MEGA};

    //private LatLng[] POIS2 =  showMap(a,b,c,d,e);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortest_path_map);



        //Toast.makeText(ShortestPathMap.this,showMap(a,b,c,d,e),Toast.LENGTH_SHORT).show();

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

        mMap.addPolyline(new PolylineOptions()
               .add(POIS));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(POIS[0], 13));

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



    public String processLatLng(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == 'x') {
            str = str.substring(1, str.length() - 1);
        }
        return str;
    }

    public String showMap(String a, String b,String c,String d,String e){
        Boolean returnOrNot = false;
        String address = "https://bb609999.herokuapp.com/api?loc=";

        address+=a+'|'+b+'|'+c+'|'+d+'|'+e;

        HttpUtil.sendOkHttpRequest(address ,new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData = response.body().string();
                        shortestPath = responseData;


                    }
                }
        );
        while(returnOrNot==false){

        }
        return shortestPath;
    }


}

