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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

    String[] events = new String[]{
            "去小北门拿快递",
            "跟同事一起聚餐",
            "写文档",
            "和产品开会",
            "整理开会内容",
            "提交代码到git上",
            "happy ending"
    };

    String[] times = new String[10];
    List<String> times2 = new ArrayList<String>();
    SimpleDateFormat simpledateformat;
    List<String> list = new ArrayList<String>();
    String traveltoool = "bus";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline_layout);

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
        times[0]=dts;
        for (int num=0; num<events.length; num++){
            if (num==0){
                times[num+1]=dts;
            }if (num==events.length-1){
                Date dt1 = null;
                try {
                    dt1 = simpledateformat.parse(times[num]);
                    cal.setTime(dt1);
                    cal.add(Calendar.HOUR, +1);
                    Date tdt= cal.getTime();
                    dts = simpledateformat.format(tdt);
                    times[num] = dts;
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
            else{
                try {
                    Date dt1 = simpledateformat.parse(times[num]);
                    cal.setTime(dt1);
                    cal.add(Calendar.HOUR, +1);
                    Date tdt= cal.getTime();
                    dts = simpledateformat.format(tdt);
                    times[num] = dts;
                    times[num+1]=dts;

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        Log.d("input", Arrays.toString(times));
        Log.d("input", Arrays.toString(events));
        Log.d("input2", traveltoool);

        for(String s : times) {
            if(s != null && s.length() > 0) {
                times2.add(s);
            }
        }
        times = times2.toArray(new String[times2.size()]);

        if (times.length==events.length) {

            for (int i = 0; i < times.length; i++) {
                timeList.add(new Timeline(times[i], events[i], traveltoool));
            }
            Log.d("input1", Arrays.toString(times));

            Log.d("input1", Arrays.toString(events));


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
