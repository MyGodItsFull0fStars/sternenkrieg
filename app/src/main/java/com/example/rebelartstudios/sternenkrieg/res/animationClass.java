package com.example.rebelartstudios.sternenkrieg.res;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;

import com.example.rebelartstudios.sternenkrieg.R;

/**
 * Created by Chris on 20.06.2017.
 */

public class animationClass {
    ObjectAnimator objAnim;
    private static final int GLOW_ANIM_DURATION = 800;

    public void glowAnimation(View targetTab) {
        objAnim =
                ObjectAnimator.ofObject(targetTab,
                        "backgroundColor", // we want to modify the backgroundColor
                        new ArgbEvaluator(), // this can be used to interpolate between two color values
                        targetTab.getContext().getResources().getColor(R.color.tab_background), // start color defined in resources as #ff333333
                        targetTab.getContext().getResources().getColor(R.color.tab_glow) // end color defined in resources as #ff3355dd
                );
        objAnim.setDuration(GLOW_ANIM_DURATION);
        objAnim.setRepeatMode(ValueAnimator.REVERSE); // start reverse animationClass after the "growing" phase
        objAnim.setRepeatCount(ObjectAnimator.INFINITE);
        objAnim.start();
    }
    public void stop(){
        objAnim.end();
    }
}