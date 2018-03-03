package com.example.cheaptriptravel;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.cheaptriptravel.TimelineAdapter.OnItemPressedListener;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by waiwai on 25/2/2018.
 */

public class TimelineActivity extends AppCompatActivity {

    private RecyclerView rvTrace;
    private List<Timeline> timeList = new ArrayList<>(10);
    private TimelineAdapter adapter;

    List<String> events = new ArrayList<String>();
    List<String> times = new ArrayList<String>();

    List<String> times2 = new ArrayList<String>();
    SimpleDateFormat simpledateformat;
    List<String> list = new ArrayList<String>();
    String traveltoool = "bus";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline_layout);
        //Intent intent = getIntent();
        //Location location = (Location)getIntent().getSerializableExtra("loc0");
        //String name = intent.getStringExtra("loc0");
        //events = intent.getStringArrayListExtra("loc2");
        //Log.d("TestMessage", name);
        /*for (int n=0;n<events.size();n++) {
            Log.d("TestMessage", events.get(n));
        }*/
        List<Trips> trips = DataSupport.findAll(Trips.class);
        for (Trips trip:trips){
            Log.d("localPlace", "place is "+trip.getPlaces());
            //Log.d("localId", String.valueOf(trip.getId()));

            events.add(trip.getPlaces());
        }
        findView();
        initData();
    }

    private void findView() {

        rvTrace = (RecyclerView) findViewById(R.id.rvTrace);

    }

    @TargetApi(Build.VERSION_CODES.N)
    private void initData() {
        Calendar cal = Calendar.getInstance();
        simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt=new Date();
        String dts=simpledateformat.format(dt);
        times.add(dts);
        Log.d("start", times.get(0));
        for (int num=0; num<events.size(); num++){
            if (num==0) {
                times.add(num + 1, dts);
            }else if (num==events.size()-1){
                try {
                    Date dt1 = simpledateformat.parse(times.get(num));
                    cal.setTime(dt1);
                    cal.add(Calendar.HOUR, +1);
                    Date tdt= cal.getTime();
                    dts = simpledateformat.format(tdt);
                    times.set(num, dts);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                try {
                    Date dt1 = simpledateformat.parse(times.get(num));
                    cal.setTime(dt1);
                    cal.add(Calendar.HOUR, +1);
                    Date tdt= cal.getTime();
                    dts = simpledateformat.format(tdt);
                    times.set(num, dts);
                    times.add(num + 1, dts);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        for (int s=0;s<times.size();s++){
            Log.d("times", times.get(s));

        }
        //Log.d("input", Arrays.toString(events));
        Log.d("input2", traveltoool);

        /*
        for(String s : times) {

            if(s != null && s.length() > 0) {
                times2.add(s);
            }
        }
        times = times2.toArray(new String[times2.size()]);
*/
        if (times.size()==events.size()) {

            for (int i = 0; i < times.size(); i++) {
                timeList.add(new Timeline(times.get(i), events.get(i), traveltoool));
            }
            for (int r=0;r<times.size();r++){
                Log.d("input1", times.get(r));

            }

            //Log.d("input1", Arrays.toString(events));


            adapter = new TimelineAdapter(this, timeList, traveltoool);
            rvTrace.setLayoutManager(new LinearLayoutManager(this));
            rvTrace.setAdapter(adapter);
            adapter.setOnItemPressedListener(new OnItemPressedListener(){

                @Override
                public void onItemClick(int position) {
                    Toast.makeText(TimelineActivity.this, "onItemClick", Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(TimelineActivity.this, DisplayActivity.class);
                    //startActivity(intent);

                }

                @Override
                public boolean OnItemLongClick(int position) {
                    Toast.makeText(TimelineActivity.this, "OnItemLongClick", Toast.LENGTH_SHORT).show();

                    return true;
                }
            });
            rvTrace.setRecyclerListener(new RecyclerView.RecyclerListener() {
                @Override
                public void onViewRecycled(RecyclerView.ViewHolder holder) {
                    TimelineAdapter.ViewHolder vh = (com.example.cheaptriptravel.TimelineAdapter.ViewHolder) holder;
                }
            });
        }
    }


}
