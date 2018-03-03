package com.example.cheaptriptravel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends AppCompatActivity {
    List<String> places = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        final ListView listview = (ListView) findViewById(R.id.edit_list_view);

        List<Trips> trips = DataSupport.findAll(Trips.class);
        for (Trips trip:trips){
            Log.d("Edit", "place is "+trip.getPlaces());
            //Log.d("localId", String.valueOf(trip.getId()));
            places.add(trip.getPlaces());
        }
        ListAdapter adapter = new ArrayAdapter<>(this , android.R.layout.simple_list_item_1 ,places);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(onClickListView);


    }
    private AdapterView.OnItemClickListener onClickListView = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(EditActivity.this,"點選第 "+(position +1) +" 個 \n內容："+ places.get(position), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EditActivity.this, ShowtextActivity.class);
            intent.putExtra("editdata",places.get(position));
            startActivity(intent);

        }
    };
    }
