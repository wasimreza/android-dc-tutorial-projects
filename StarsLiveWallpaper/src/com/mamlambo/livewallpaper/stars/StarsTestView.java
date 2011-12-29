package com.mamlambo.livewallpaper.stars;
import android.content.Context;
import android.renderscript.RSSurfaceView;
import android.renderscript.RenderScriptGL;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class StarsTestView extends RSSurfaceView {
    private StarsRS mRender;
    private RenderScriptGL mRSGL; 

    public StarsTestView(Context context) {
        super(context);
        //setFocusable(true);
        //setFocusableInTouchMode(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        super.surfaceChanged(holder, format, w, h);

        if (mRSGL == null) {
            RenderScriptGL.SurfaceConfig sc = new RenderScriptGL.SurfaceConfig();
            mRSGL = createRenderScriptGL(sc);
            mRSGL.setSurface(holder, w, h);
            mRender = new StarsRS(w, h);
            mRender.init(mRSGL, getResources(), false);
            mRender.start();
        }
    }
    
    @Override
    protected void onDetachedFromWindow() {
        if (mRSGL != null) {
            mRSGL = null;
            destroyRenderScriptGL();
        }
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // Pass touch events from the system to the rendering script
        if (ev.getAction() == MotionEvent.ACTION_DOWN || ev.getAction() == MotionEvent.ACTION_MOVE) {
            mRender.onActionDown((int)ev.getX(), (int)ev.getY());
            return true;
        }

        return false;
    }
}
