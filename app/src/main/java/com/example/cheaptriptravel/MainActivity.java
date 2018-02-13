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
import java.util.Arrays;

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
                        Log.d("The Shortest Path:",shortestPath(new String[]{OUHK,APM,PLAZA,GYIN,MEGA}).toString());

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


    private String[] shortestPath(String[] markerPosition) {

        int shortestDuration = 0;
        String shortest_path[] = new String[markerPosition.length];

        int[][] list = calculateDuration(markerPosition);

        String[] listforChecked = new String[markerPosition.length];

        for(int i=0;i<markerPosition.length;i++){
            listforChecked[i] = markerPosition[i];
        }

        permute(markerPosition, 0, markerPosition.length-1,list,listforChecked);

        return shortest_path;
    }



    private void permute(String[] str, int l, int r , int[][]list , String[] usedtoCheckPosition)
    {
        int totalDuration = 0;


        if (l == r) {
           Log.d("Now PATH", Arrays.toString(str));
           // Log.d("ORIGIN PATH", Arrays.toString(usedtoCheckPosition));

            int[] x = new int[str.length];

            //Checked [C,D,E,A,B] by [A,B,C,D,E] -> 2,3,4,0,1
            for(int i=0;i<str.length;i++){
                for(int j=0;j<str.length;j++) {
                    if (str[i] == usedtoCheckPosition[j]) {
                        x[i] = j;
                    }
                }
            }


            // calculate total duration in one route
            for(int i=0;i<str.length;i++){
                if(i+1>=str.length){
                    totalDuration += 0;
                }else
                    totalDuration += list[x[i]][x[i+1]];

                Log.d("totalDuration", ""+list[x[i]][x[i+1]]);
            }


        }
        else
        {
            for (int i = l; i <= r; i++)
            {
                str = swap(str,l,i);
                permute(str, l+1, r ,list,usedtoCheckPosition);
                str = swap(str,l,i);
            }
        }
    }

    public String[] swap(String[] a, int i, int j)
    {
        String temp;
        //char[] charArray = a.toCharArray();
        temp = a[i] ;
        a[i] = a[j];
        a[j] = temp;
        return a;
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
