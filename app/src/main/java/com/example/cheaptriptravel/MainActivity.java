package com.example.cheaptriptravel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cheaptriptravel.util.HttpUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
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

public class MainActivity extends AppCompatActivity implements
        OnMapClickListener, OnMapLongClickListener, OnCameraIdleListener,
        OnMapReadyCallback {

    private String OUHK = "22.316279,%20114.180408";
    private String APM = "22.312441,%20114.225046";
    private String PLAZA = "22.310602,%20114.187868";
    private String GYIN = "22.308235,%20114.185765";
    private String MEGA = "22.320165,%20114.208168";

    private LatLng P1_OUHK = new LatLng(22.316279,114.180408);
    private LatLng P2_APM = new LatLng(22.312441,114.225046);
    private LatLng P3_PLAZA = new LatLng(22.310602,114.187868);
    private LatLng P4_GYIN = new LatLng(22.308235,114.185765);
    private LatLng P5_MEGA = new LatLng(22.320165,114.208168);




    private LatLng[] POIS = {P1_OUHK,P2_APM,P3_PLAZA,P4_GYIN,P5_MEGA};


    private TextView mTapTextView;
    private TextView mCameraTextView;
    private GoogleMap mMap;

    private int result;
    private boolean returnOrNot = false;

    private Button calculate_btn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        //
       //

        calculate_btn = (Button) findViewById(R.id.calculate_btn);
        calculate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //int temp =calculateDuration(OUHK,APM);
               // Log.d("Next",""+temp);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //List<String> list = shortestPath(OUHK,APM,PLAZA,GYIN,MEGA);
                        //Log.d("List",list.toString());
                        calculateDuration(new String[]{OUHK,APM,PLAZA,GYIN,MEGA});
                        calculateDuration(new String[]{OUHK,APM,PLAZA,GYIN,MEGA});
                        Log.d("Hello",shortestPath(new String[]{OUHK,APM,PLAZA,GYIN,MEGA}).toString());

                    }
                }).start();


            }
        });



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

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(P1_OUHK, 13));

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




    private List<String> shortestPath(String[] endpoint){
        ArrayList<String> SiteVisit = new ArrayList<String>();

        int[][] list = calculateDuration(endpoint);

        for(int i =0; i< endpoint.length;i++) {
            SiteVisit.add(endpoint[i]);
        }
        ArrayList<String> CountList = new ArrayList<String>();//store temp path
        CountList.add(endpoint[0]);

        List<String> Temp0List = new ArrayList<>();
        List<String> Count0List = new ArrayList<>();
        List<String> Answer = new ArrayList<>();
        int p ;
        int shortestpathduration = 0;
        int Temppathduration = 0;//store temp path's duration

        for(int i=1;i<SiteVisit.size();i++){
            Count0List = CountList;//reset countlist
            Temp0List = SiteVisit;//templist
            Temppathduration+=list[0][i];
            Count0List.add(SiteVisit.get(i));

            for (int j=i+1;j<Temp0List.size();j++){
                if(i==j){
                    j++;
                }
                Temppathduration+=list[i][j];
                Count0List.add(Temp0List.get(j));
                for (int k=j+1;k<Temp0List.size();k++){
                    if(j==k){
                        k++;
                    }
                    Temppathduration+=list[j][k];
                    Count0List.add(Temp0List.get(k));
                    Temppathduration+=list[0][k];
                    Count0List.add(Temp0List.get(0));
                    if(shortestpathduration == 0||Temppathduration<shortestpathduration){//store the shortest path
                        shortestpathduration=Temppathduration;
                        Answer=Count0List;
                    }
                }
            }
        }
        return Answer;

    }

    private int[][] calculateDuration(String[] endPoint) {

        final int[][] durationValues = new int[endPoint.length][endPoint.length];

        String address = "https://maps.googleapis.com/maps/api/distancematrix/json" +
                "?origins=";

        for(int i=0;i<endPoint.length;i++) {
            address += endPoint[i]+"%7C";
        }

        address += "&destinations=";


        for(int i=0;i<endPoint.length;i++) {
        address += endPoint[i]+"%7C";
        }

        address += "&mode=transit" +
                "&key=AIzaSyBGAlVrnlB0BqZxoc7K-PBo3qdwloXTa58";


        HttpUtil.sendOkHttpRequest(address,
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

                            for (int i = 0; i < rows.length(); i++){
                                for (int j = 0; j < rows.length(); j++){
                                    durationValues[i][j] = rows.getJSONObject(i).getJSONArray("elements")
                                            .getJSONObject(j).getJSONObject("duration").getInt("value");
                                }
                            }

                            for(int i = 0 ;i<rows.length();i++) {
                                Log.d("DurationValues : ", Arrays.toString(durationValues[i]));
                            }

                            returnOrNot = true;


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });


        while(returnOrNot == false){
        }
        returnOrNot = false;

        return durationValues;



    }




}
