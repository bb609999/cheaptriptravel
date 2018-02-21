package com.example.cheaptriptravel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by waiwai on 20/2/2018.
 */

public class OptimizedTripActivity extends AppCompatActivity{
    private String OUHK = "22.316279,114.180408";
    private String APM = "22.312441,114.225046";
    private String PLAZA = "22.310602,114.187868";
    private String GYIN = "22.308235,114.185765";
    private String MEGA = "22.320165,114.208168";

    private String[] POIS = {OUHK,APM,PLAZA,GYIN,MEGA};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.optimize_trip_layout);
        Button button1 = (Button) findViewById(R.id.optimize);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String address ="http://gebweb.net/optimap/index.php?";
                for(int i =0 ;i<POIS.length;i++){
                    address+= "loc"+i+"="+POIS[i];
                }
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse( address));
                startActivity(intent);
            }
        });
    }
}


