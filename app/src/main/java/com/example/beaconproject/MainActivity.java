package com.example.beaconproject;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class MainActivity extends AppCompatActivity {

    View menu;
    ImageButton menuBtn;
    Button lostThingBtn;
    Button fontBtn;
    Button handModeBtn;
    Button themeBtn;
    ImageButton searchBtn;
    ImageButton navigationBtn;

    EditText searchText;

    String mode = "left";

    Dialog fontSettingDialog;

    int fontSizeValue = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        menu = findViewById(R.id.menuLayout);
        menuBtn = (ImageButton) findViewById(R.id.menuBtn);
        lostThingBtn = (Button) findViewById(R.id.lostThingBtn);
        fontBtn = (Button) findViewById(R.id.fontBtn);
        handModeBtn = (Button) findViewById(R.id.handModeBtn);
        themeBtn = (Button) findViewById(R.id.themeBtn);
        searchBtn = (ImageButton) findViewById(R.id.searchBtn);
        navigationBtn = (ImageButton) findViewById(R.id.navigationBtn);

        fontSettingDialog = new Dialog(this);
        fontSettingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        fontSettingDialog.setContentView(R.layout.font_setting_dialog);
    }

    public void onClick(View view) {
        //menuBtn
        if(view.getId() == R.id.menuBtn){
            if(menu.getVisibility() == View.VISIBLE){
                menu.setVisibility(View.INVISIBLE);
            }
            else{
                menu.setVisibility(View.VISIBLE);
            }
        }
        
        //lost and find thing
        if(view.getId() == R.id.lostThingBtn){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.lost112.go.kr/lost/lostList.do"));
            startActivity(intent);
        }

        //change fontSize button
        if(view.getId() == R.id.fontBtn){
            showFontSettingDialog(fontSizeValue);
        }

        //change rightHand or leftHand mode
        if(view.getId() == R.id.handModeBtn){
            if(mode.equals("left")){
                handModeBtn.setText("오른손 모드");
                mode = "right";
            }
            else{
                handModeBtn.setText("왼손 모드");
                mode = "left";
            }
        }

        //change DayTheme or DarkTheme
        if(view.getId() == R.id.themeBtn){

        }
    }

    private void showFontSettingDialog(int value) {
        fontSettingDialog.show();
        TextView fontTest =(TextView) fontSettingDialog.findViewById(R.id.fontSize);
        Button setBtn = (Button) fontSettingDialog.findViewById(R.id.setBtn);
        Button cancelBtn = (Button) fontSettingDialog.findViewById(R.id.cancelBtn);
        SeekBar seekBar = (SeekBar) fontSettingDialog.findViewById(R.id.seekBar);

        seekBar.setProgress(value-10);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.v("?", "" + seekBar.getProgress());
                fontTest.setTextSize(seekBar.getProgress()+10);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fontSizeValue = seekBar.getProgress() + 10;
                fontSettingDialog.dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fontSettingDialog.dismiss();
            }
        });
    }
}