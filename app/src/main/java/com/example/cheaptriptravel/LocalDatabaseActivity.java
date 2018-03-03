package com.example.cheaptriptravel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by waiwai on 26/2/2018.
 */

public class LocalDatabaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.litepal_layout);
        Button button = (Button)findViewById(R.id.litepalButton1);
        Button update = (Button)findViewById(R.id.litepalupdate);
        Button delete = (Button)findViewById(R.id.litepaldelete);
        Button find = (Button)findViewById(R.id.litepalfind);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Connector.getDatabase();
                Trips trips = new Trips();
                trips.setName("CHAN TAI MAN");

                trips.setOpentime("9:00");

                if (trips.save()) {//saveThrows
                    Toast.makeText(LocalDatabaseActivity.this, "存储成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LocalDatabaseActivity.this, "存储失败", Toast.LENGTH_SHORT).show();
                }


            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trips trips = new Trips();
                trips.setName("aaaaaaa");
                //trips.setToDefault("name"); //all name =null
                //trips.updateAll();          //update to all data


                //List<Trips> trips= DataSupport.findAll(Trips.class); //find data
                //for (Trips trips:trips){Log.d}
                trips.updateAll("name=? and address=?", "CHAN TAI MAN", "OUHK");
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DataSupport.deleteAll(Trips.class, "name=?", "CHAN TAI MAN"); //delete data
                DataSupport.deleteAll(Trips.class);
            }
        });
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Trips> trips= DataSupport.findAll(Trips.class); //find data
                //DataSupport.findFirst(Trips.class)
                //DataSupport.findLast(Trips.class)
                //DataSupport.select("name", "address").find(Trips.class)
                //           .where("rating>?", "50").find(Trips.class)
                //           .order("rating desc").find(Trips.class)
                //           .limit(10).find(Trips.class)
                //           .limit(10).offset(1).find(Trips.class)

                for (Trips trip:trips){
                    Log.d("localdata", "name is "+ trip.getPlaces());

                }
            }
        });
    }
}
