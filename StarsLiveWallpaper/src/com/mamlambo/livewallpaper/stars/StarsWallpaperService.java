package com.mamlambo.livewallpaper.stars;

import com.android.wallpaper.RenderScriptWallpaper;

public class StarsWallpaperService extends RenderScriptWallpaper<StarsRS> {
    protected StarsRS createScene(int width, int height) {
        return new StarsRS(width, height);
    }
}
