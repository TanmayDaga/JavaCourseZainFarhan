package com.example.fusion;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;


import android.webkit.WebSettings;

import android.widget.ProgressBar;

import com.example.fusion.NetworkUtilities.MyWebClient;
import com.example.fusion.NetworkUtilities.MyWebView;
import com.example.fusion.youtubeDownloader.DownloadActivity;


public class MainActivity extends AppCompatActivity {

    private MyWebView mWebView;
    private ProgressBar mProgressBar;
    private static final String YOUTUBE_MUSIC_URL = "https://music.youtube.com/";
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = (MyWebView) findViewById(R.id.web_view);
        mProgressBar = findViewById(R.id.progress_bar);

        initialiseWebView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_download){
            Log.d(TAG,"Intent initialise");
            Intent intent = new Intent(MainActivity.this, DownloadActivity.class);
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT,getLink());
            startActivity(intent);
        }

        return true;
    }

    private String getLink(){

//        https://music.youtube.com/watch?v=6rs6_l7z3Zw&list=RDAMVM6rs6_l7z3Zw
        String curUrl = mWebView.getUrl();

        String sub = curUrl.substring(26);
        Log.d(TAG,sub);
        return "https://www.youtube.com/"+sub;
    }

    @Override
    public void onBackPressed() {
        mWebView.goBack();
    }

    private void initialiseWebView() {


        mWebView.getSettings().setJavaScriptEnabled(true);

        if (Build.VERSION.SDK_INT < 18) {
            mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // chromium, enable hardware acceleration
            mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        mWebView.setWebViewClient(new MyWebClient(this,mProgressBar));
        mWebView.loadUrl(YOUTUBE_MUSIC_URL);


    }

    public void setInvisibleProgress(){
        mProgressBar.setVisibility(View.INVISIBLE);
    }


}

