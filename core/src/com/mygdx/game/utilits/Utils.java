package com.mygdx.game.utilits;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by Asus123 on 16.12.2017.
 */

public class Utils {
    public static float timeElapsed(long startTime){
        return MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTime);
    }
}
