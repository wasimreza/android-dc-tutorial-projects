package com.mamlambo.livewallpaper.stars;

import android.app.WallpaperManager;
import android.os.Bundle;
import android.renderscript.Mesh;
import android.renderscript.ProgramFragmentFixedFunction;
import android.renderscript.ScriptC;

import com.android.wallpaper.RenderScriptScene;

public class StarsRS extends RenderScriptScene {
    public static final int STAR_COUNT = 2500;
    private ScriptC_stars mScript;

    public StarsRS(int width, int height) {
        super(width, height);

    }

    public void onActionDown(int x, int y) {
        mScript.set_gTouchX(x);
        mScript.set_gTouchY(y);
    }

    @Override
    protected ScriptC createScript() {
        ProgramFragmentFixedFunction.Builder pfb = new ProgramFragmentFixedFunction.Builder(
                getRS());
        pfb.setVaryingColor(true);
        getRS().bindProgramFragment(pfb.create());

        ScriptField_Star stars = new ScriptField_Star(mRS, STAR_COUNT);
        Mesh.AllocationBuilder smb = new Mesh.AllocationBuilder(mRS);
        smb.addVertexAllocation(stars.getAllocation());
        smb.addIndexSetType(Mesh.Primitive.POINT);
        Mesh sm = smb.create();

        mScript = new ScriptC_stars(mRS, getResources(), R.raw.stars);
        mScript.set_starMesh(sm);
        mScript.bind_star(stars);
        mScript.invoke_initStars();
        return mScript;
    }

    @Override
    public Bundle onCommand(String action, int x, int y, int z, Bundle extras,
            boolean resultRequested) {
        if (WallpaperManager.COMMAND_TAP.equals(action)
                || WallpaperManager.COMMAND_SECONDARY_TAP.equals(action)
                || WallpaperManager.COMMAND_DROP.equals(action)) {
            onActionDown(x, y);
        }
        return super.onCommand(action, x, y, z, extras, resultRequested);
    }

}
