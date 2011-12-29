package com.mamlambo.livewallpaper.stars;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import com.android.wallpaper.galaxy.*;

public class StarsLiveWallpaperActivity extends Activity {
    private StarsTestView mPreview;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        FrameLayout frame = (FrameLayout) findViewById(R.id.frame);
        mPreview = new StarsTestView(this);
        frame.addView(mPreview);
        //setContentView(mPreview);
    }
    
    public void launchSettings(View view) {
        Intent liveWallpaperSettings = new Intent(Intent.ACTION_SET_WALLPAPER);
        startActivity(liveWallpaperSettings);
    }
    
    
    @Override
    protected void onResume() {
        super.onResume();
        mPreview.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPreview.pause();
    }
}