package com.example.first_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor mEditor = myPrefs.edit();
        if(myPrefs.getString("theme_mode",null) == null)
        {
            mEditor.putString("theme_mode", "default");
            mEditor.apply();
        }
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
        setContentView(R.layout.activity_main);

        EditText address = findViewById(R.id.url_address);
        webView = findViewById(R.id.webview);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if(!Global.from_arrow) {
                    Global.next_url.clear();
                    Global.prev_url.push(Global.actual);
                    Global.actual = webView.getUrl();
                }
                Global.from_arrow = false;
                if(!Global.actual.equals(Global.home)) {
                    address.setText(Global.actual);
                }
                else address.setText("");
                Log.d("ACT", url);
            }
        });
        webView.loadUrl(Global.home);

        address.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String addr = address.getText().toString();
                    if(!addr.isEmpty()) {
                        if(addr.indexOf(' ') == -1 && addr.indexOf('.') != -1)
                            webView.loadUrl(address.getText().toString());
                        else {
                            addr = addr.replace(' ', '+');
                            webView.loadUrl("https://duckduckgo.com/?q="+addr);
                        }
                    }
                    else{
                        address.setError("Please enter url address first!");
                    }
                    return true;
                }
                return false;
            }
        });


        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            if(!Global.prev_url.isEmpty() && webView != null && !Global.prev_url.peek().equals(Global.actual)) {
                Global.from_arrow = true;
                String temp = Global.actual;
                Global.next_url.push(temp);
                webView.loadUrl(Global.prev_url.pop());
                Global.actual = webView.getUrl();
            }
        });

        Button btnForward = findViewById(R.id.btnForward);
        btnForward.setOnClickListener(v -> {
            if(!Global.next_url.isEmpty() && webView != null && !Global.next_url.peek().equals(Global.actual)) {
                Global.from_arrow = true;
                String temp = Global.actual;
                Global.prev_url.push(temp);
                webView.loadUrl(Global.next_url.pop());
                Global.actual = webView.getUrl();

            }
        });

        Button btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(v -> {
            if(!webView.getUrl().equals(Global.home) && webView != null) {
                webView.loadUrl(Global.home);
                Global.actual = webView.getUrl();
                Global.prev_url.clear();
                Global.next_url.clear();
            }
        });


        ImageButton btnSettings = findViewById(R.id.btn_settings);
        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        });

    }
}


