package com.example.beaconproject;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageView metro_map;
    View menu;
    ImageButton menuBtn;
    Button searchBtn;
    Button navigationBtn;

    EditText searchText;

    float x, y;
    float dx, dy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        menu = findViewById(R.id.menuLayout);
        menuBtn = (ImageButton) findViewById(R.id.menuBtn);

        metro_map =findViewById(R.id.metro_map);
    }

    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            x = event.getX();
            y = event.getY();

            Log.v("x", "" + x);
            Log.v("y", "" + y);

            Log.v("metro_x", "" + metro_map.getX());
            Log.v("metro_y", "" + metro_map.getY());
        }
        if(event.getAction() == MotionEvent.ACTION_MOVE){
            dx = event.getX() - x;
            dy = event.getY() - y;

            if(metro_map.getX()+dx >= 0 && metro_map.getX() <= 1000){
                metro_map.setX(metro_map.getX()+dx);
                metro_map.setY(metro_map.getY()+dy);
                x = event.getX();
                y = event.getY();
            }
            else if(metro_map.getX()+dx >= 1000){
                metro_map.setX(1000);
                metro_map.setY(metro_map.getY()+dy);
                x = event.getX();
                y = event.getY();
            }
            else{
                metro_map.setX(0);
                metro_map.setY(metro_map.getY()+dy);
                x = event.getX();
                y = event.getY();
            }
        }

        return super.onTouchEvent(event);
    }

    public void onClick(View view) {
        if(view.getId() == R.id.menuBtn){
            if(menu.getVisibility() == View.VISIBLE){
                menu.setVisibility(View.INVISIBLE);
            }
            else{
                menu.setVisibility(View.VISIBLE);
            }
        }
    }
}