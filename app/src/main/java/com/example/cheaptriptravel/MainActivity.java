package com.example.cheaptriptravel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import java.lang.reflect.Array;
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

    private  List<Integer> list = new ArrayList<Integer>();





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
                        calculateDuration(OUHK,new String[]{APM,PLAZA,GYIN,MEGA});
                        calculateDuration(OUHK,new String[]{APM,PLAZA,GYIN,MEGA});
                        Log.d("result", result +"");

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
                for (int k=j+1;k<Temp1List.size();k++){
                    if(j==k){
                        k++;
                    }
                    Temppathduration+=list[j][k];
                    Count0List.add(Temp1List.get(k));
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

    private List<Integer> calculateDuration(String startPoint, String[] endPoint) {

        list.clear();

        String address = "https://maps.googleapis.com/maps/api/distancematrix/json" +
                "?origins=" + startPoint +
                "&destinations=";


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

                            JSONObject elements = new JSONObject();


                            for (int i = 0; i < rows.length(); i++) {
                                elements = rows.getJSONObject(i);
                            }

                            JSONArray elementArray = elements.getJSONArray("elements");


                            JSONObject elementObj = elementArray.getJSONObject(0);

                            int[] answer = new int[elementArray.length()];


                            for(int i = 0 ;i<elementArray.length();i++){
                                list.add(elementArray.getJSONObject(i).getJSONObject("duration").getInt("value"));
                            }

                            JSONObject durationObj = elementObj.getJSONObject("duration");

                            int duration_value = durationObj.getInt("value");

                            Log.d("API RETURN", destination_addresses + "\n" + origin_addresses + "\n" + rows.toString() + "\n" + elements);
                            Log.d("API to element Array", elementArray.toString());
                            Log.d("TEST", elementObj.toString());
                            Log.d("TEST", duration_value+"");

                             result =  duration_value;

                            Log.d("TEST return:",""+ list.toString());


                            returnOrNot = true;


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });


        while(returnOrNot == false){
        }
        returnOrNot = false;

        return list;



    }




}
