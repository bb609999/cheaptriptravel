package com.example.cheaptriptravel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class ShowtextActivity extends AppCompatActivity {
    List<String> editdata = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtext);
        EditText data = (EditText) findViewById(R.id.edit_text);
        Button submit = (Button)findViewById(R.id.submit3);
        Intent intent = getIntent();
        String name = intent.getStringExtra("editdata");
        data.setText(name);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowtextActivity.this, TimelineActivity.class);
                Trips trips = new Trips();
                trips.setPlaces(data.getText().toString());
                trips.updateAll("places=?", name);
                startActivity(intent);
            }
        });



    }
}
