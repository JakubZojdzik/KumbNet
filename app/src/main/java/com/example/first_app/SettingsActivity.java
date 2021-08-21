package com.example.first_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
        switch (myPrefs.getString("theme_mode", null)) {
            case "default":
                setTheme(R.style.DefaultTheme);
                break;
            case "dark":
                setTheme(R.style.DarkTheme);
                break;
            case "light":
                setTheme(R.style.LightTheme);
                break;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }


        RadioButton radio1 = (RadioButton)findViewById(R.id.btnRadio1);
        RadioButton radio2 = (RadioButton)findViewById(R.id.btnRadio2);
        RadioButton radio3 = (RadioButton)findViewById(R.id.btnRadio3);
        switch (myPrefs.getString("theme_mode", null)) {
            case "default":
                setTheme(R.style.DefaultTheme);
                radio1.setChecked(true);
                break;
            case "dark":
                setTheme(R.style.DarkTheme);
                radio2.setChecked(true);
                break;
            case "light":
                setTheme(R.style.LightTheme);
                radio3.setChecked(true);
                break;
        }


        ImageButton btnMain = findViewById(R.id.btnToMain);
        btnMain.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        SharedPreferences.Editor mEditor = myPrefs.edit();
        if(myPrefs.getString("theme_mode",null) == null)
        {
            mEditor.putString("theme_mode", "default");
            mEditor.commit();
        }

        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);





        Button btnSave = (Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if(selectedId != -1)
            {
                RadioButton selectedRadioButton = (RadioButton)findViewById(selectedId);
                if("Default".equals(selectedRadioButton.getText().toString()) && !myPrefs.getString("theme_mode", null).equals("default"))
                {
                    mEditor.putString("theme_mode", "default");
                    setTheme(R.style.DefaultTheme);
                }
                else if("Dark".equals(selectedRadioButton.getText().toString()) && !myPrefs.getString("theme_mode", null).equals("dark"))
                {
                    mEditor.putString("theme_mode", "dark");
                    setTheme(R.style.DarkTheme);
                }
                else if("Light".equals(selectedRadioButton.getText().toString()) && !myPrefs.getString("theme_mode", null).equals("light"))
                {
                    mEditor.putString("theme_mode", "light");
                    setTheme(R.style.LightTheme);
                }
                mEditor.commit();
                recreate();
            }
        });

    }




}