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
                       // calculateDuration(new String[]{OUHK,APM,PLAZA,GYIN,MEGA});
                      calculateDuration(new String[]{OUHK,APM,PLAZA,GYIN,MEGA});
                      Log.d("The Shortest Path:",Arrays.toString(shortestPath(new String[]{OUHK,APM,PLAZA,GYIN,MEGA})));
                       // calculateRoute(new String[]{OUHK,APM,PLAZA,GYIN,MEGA});

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


    /*private String calculateRoute(String[] endPoint) {

        String html = "";

        String address = "http://gebweb.net/optimap/index.php?";

        for (int i = 0; i < endPoint.length; i++) {
            address += "loc" + String.valueOf(i) + "=" + endPoint[i] + "&";
        }

        Intent intent = new Intent(MainActivity.this, RouteWebViewActivity.class);
        intent.putExtra("address", address);
        startActivity(intent);

        return html;
    }
    */




    private String[] shortestPath(String[] markerPosition) {

        int[][] list = calculateDuration(markerPosition);
        List<String> pathList = new ArrayList<>();
        int shortest_path = 0;
        String[] finalmarkers = new String[markerPosition.length];

        String str = "";
        int n = markerPosition.length;
        for(int i =0 ; i<markerPosition.length;i++){
            str += String.valueOf(i);
        }


       permute(str, 0, n-1, pathList);

        //Log.d("Com2 :",""+pathList.size());

        int Totalduration = 0;
        String shortestTemp = "";

        for(int i =0 ; i<pathList.size();i++){
            for(int j =0 ; j<markerPosition.length;j++){
                if(j+1>=markerPosition.length){
                    Totalduration += 0;
                }else {
                       // Log.d("Temp Path", pathList.get(i));
                        Totalduration += list[Integer.parseInt(pathList.get(i).substring(j, j + 1))][Integer.parseInt(pathList.get(i).substring(j + 1, j + 2))];
                        //Log.d("Temp duration",String.valueOf(Totalduration));


                }
            }
            if(shortest_path==0||shortest_path>Totalduration){

                shortest_path = Totalduration;
                shortestTemp = pathList.get(i);
                //Log.d("Shortest Path", pathList.get(i));
                //Log.d("Shortest duration",String.valueOf(Totalduration));
            }
            Totalduration = 0;

        }
        Log.d("Shortest Path", ""+shortest_path);
        Log.d("Shortest Path", ""+shortestTemp);

        for(int i =0;i<markerPosition.length;i++){
            int temp = Integer.parseInt(shortestTemp.substring(i,i+1));
            finalmarkers[i]=markerPosition[temp];
        }

        return  finalmarkers;
    }

    private void permute(String str, int l, int r ,List<String> pathList)
    {
        if (l == r) {
           // Log.d("Combination :",str);

            for(int i =0 ; i<str.length();i++){
                pathList.add(str);
            }

        }
        else
        {
            for (int i = l; i <= r; i++)
            {
                str = swap(str,l,i);
                permute(str, l+1, r,pathList);
                str = swap(str,l,i);
            }
        }
    }

    public String swap(String a, int i, int j)
    {
        char temp;
        char[] charArray = a.toCharArray();
        temp = charArray[i] ;
        charArray[i] = charArray[j];
        charArray[j] = temp;
        return String.valueOf(charArray);
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
                "&key=AIzaSyC-uFuq2rGcGB34hLLeHtZBPF92B5UtCOI";


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
