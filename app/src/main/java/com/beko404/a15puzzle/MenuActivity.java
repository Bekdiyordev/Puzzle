package com.beko404.a15puzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {
    private EditText playerName;
    private long pressTime = 0;
    private Intent intent;
    private boolean soundState = true;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);
        playerName = findViewById(R.id.player_name);
        preferences = getSharedPreferences("name_preference", MODE_PRIVATE);
        editor = preferences.edit();
        String previousPlayer = preferences.getString("player_name","");
        playerName.setText(previousPlayer);
        soundState = getIntent().getBooleanExtra("sound_state",true);
    }

    public void playGame(View view) {
        MediaPlayer mp;
        if (!playerName.getText().toString().isEmpty()){
            if (soundState){
                mp = MediaPlayer.create(this, R.raw.menu_item_click);
                mp.start();
                mp.stop();
            }
            intent = new Intent(getApplicationContext(), GameActivity.class);
            intent.putExtra("player_name", playerName.getText().toString());
            intent.putExtra("time", getIntent().getLongExtra("time",5));
            intent.putExtra("sound_state", getIntent().getBooleanExtra("sound_state",false));
            startActivity(intent);
        } else {
            if (soundState){
                mp = MediaPlayer.create(this, R.raw.error_button_click);
                mp.start();
                mp.stop();
            }
            Toast.makeText(this, "Please, enter Player name", Toast.LENGTH_SHORT).show();
            playerName.getText().clear();
            playerName.requestFocus();
        }
    }

    public void ratings(View view) {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.menu_item_click);
        if (soundState)
        mp.start();
        mp.stop();
        intent = new Intent(getApplicationContext(), RatingsActivity.class);
        startActivity(intent);
    }

    public void settings(View view) {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.menu_item_click);
        if (soundState)
        mp.start();
        mp.stop();
        intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
    }

    public void instruction(View view) {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.menu_item_click);
        if (soundState)
        mp.start();
        mp.stop();
        intent = new Intent(getApplicationContext(), InstructionActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        if (!playerName.getText().toString().isEmpty()){
            String name = playerName.getText().toString();
            editor.putString("player_name", name);
            editor.apply();
        }
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        if (pressTime + 2000 > System.currentTimeMillis())
            finish();
        else
            Toast.makeText(this, "Please click again to exit", Toast.LENGTH_SHORT).show();
        pressTime = System.currentTimeMillis();
    }
}