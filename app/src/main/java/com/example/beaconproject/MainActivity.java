package com.example.beaconproject;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

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

    Dialog fontSettingDialog;

    SharedPreferences preferces;

    int fontSizeValue;
    String handValue;
    String themeValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        preferces = getSharedPreferences("Setting", 0);

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

    public void onStart() {
        super.onStart();

        fontSizeValue = preferces.getInt("fontSize", 14);
        handValue = preferces.getString("handMode", "right");
        themeValue = preferces.getString("theme", "Day");

        if(handValue.equals("right")){
            handModeBtn.setText("왼손모드");
        }
        else {
            handModeBtn.setText("오른손모드");
        }

        if(themeValue.equals("Day")){
            menuBtn.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));

            lostThingBtn.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            lostThingBtn.setTextColor(Color.parseColor("#000000"));

            fontBtn.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            fontBtn.setTextColor(Color.parseColor("#000000"));

            handModeBtn.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            handModeBtn.setTextColor(Color.parseColor("#000000"));

            themeBtn.setText("야간모드");
            themeBtn.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            themeBtn.setTextColor(Color.parseColor("#000000"));
        }
        else{
            menuBtn.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));

            lostThingBtn.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
            lostThingBtn.setTextColor(Color.parseColor("#ffffff"));

            fontBtn.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
            fontBtn.setTextColor(Color.parseColor("#ffffff"));

            handModeBtn.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
            handModeBtn.setTextColor(Color.parseColor("#ffffff"));

            themeBtn.setText("주간모드");
            themeBtn.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
            themeBtn.setTextColor(Color.parseColor("#ffffff"));
        }


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
            SharedPreferences.Editor editor = preferces.edit();

            if(handValue.equals("right")){
                handModeBtn.setText("오른손 모드");
                handValue = "left";
//                handModeBtn.setCompoundDrawables(img, null, null, null);
            }
            else{
                handModeBtn.setText("왼손 모드");
                handValue = "right";
//                handModeBtn.setCompoundDrawables(img, null, null, null);
            }
            editor.putString("handMode", handValue);
            editor.commit();

        }

        //change DayTheme or DarkTheme
        if(view.getId() == R.id.themeBtn){
            SharedPreferences.Editor editor = preferces.edit();
            if(themeValue.equals("Day")){
                themeValue = "Night";
            }
            else {
                themeValue = "Day";
            }
            editor.putString("theme", themeValue);
            editor.commit();

            PackageManager packageManager = this.getPackageManager();
            Intent intent = packageManager.getLaunchIntentForPackage(this.getPackageName());
            ComponentName componentName = intent.getComponent();
            Intent mainIntent = Intent.makeRestartActivityTask(componentName);
            this.startActivity(mainIntent);
            System.exit(0);
        }
    }

    private void showFontSettingDialog(int value) {
        fontSettingDialog.show();
        TextView fontTest =(TextView) fontSettingDialog.findViewById(R.id.fontSize);
        Button setBtn = (Button) fontSettingDialog.findViewById(R.id.cancelBtn);
        Button cancelBtn = (Button) fontSettingDialog.findViewById(R.id.setBtn);
        SeekBar seekBar = (SeekBar) fontSettingDialog.findViewById(R.id.seekBar);

        seekBar.setProgress(value);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Log.v("?", "" + seekBar.getProgress());
                fontTest.setTextSize(seekBar.getProgress());
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
                fontSizeValue = seekBar.getProgress();
                fontSettingDialog.dismiss();

                SharedPreferences.Editor editor = preferces.edit();
                editor.putInt("fontSize", fontSizeValue);
                editor.commit();
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