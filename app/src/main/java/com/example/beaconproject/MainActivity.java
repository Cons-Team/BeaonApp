package com.example.beaconproject;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity {

    ImageView metro_map;

    float x, y;
    float dx, dy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        metro_map =findViewById(R.id.imageView);

        Button buttonOpen = (Button) findViewById(R.id.open) ;
        buttonOpen.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer) ;
                if (!drawer.isDrawerOpen(Gravity.LEFT)) {
                    drawer.openDrawer(Gravity.LEFT) ;
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
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
}