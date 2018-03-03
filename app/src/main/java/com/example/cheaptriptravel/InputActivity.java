package com.example.cheaptriptravel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        EditText location = (EditText) findViewById(R.id.edit_place);
        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputActivity.this, TimelineActivity.class);
                //intent.putExtra("PlaceName", location.getText());
                Trips trips= new Trips();
                trips.setPlaces(location.getText().toString());
                if (trips.save()) {//saveThrows
                    Toast.makeText(InputActivity.this, "存储成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(InputActivity.this, "存储失败", Toast.LENGTH_SHORT).show();
                }
                Log.d("check", trips.getPlaces());
                startActivity(intent);
            }
        });
    }
}
