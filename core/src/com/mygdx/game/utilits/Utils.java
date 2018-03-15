package com.mygdx.game.utilits;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by Asus123 on 16.12.2017.
 */

public class Utils {

    public static float timeElapsed(long startTime) {
        return MathUtils.nanoToSec * (TimeUtils.nanoTime() - startTime);
    }

    public static Vector2 randomVector(float bounds){
        return Utils.randomVector(-bounds, bounds);
    }

    public static Vector2 randomVector(float leftBound, float rightBound){
        Vector2 vector = new Vector2(MathUtils.random(leftBound, rightBound),
                MathUtils.random(leftBound, rightBound));
        return vector;
    }

    public static float pow2(float num){
        return pow(num, 2);
    }

    public static float pow(float num, int power){
        if (power == 0) return 1;
        float number = num;
        for (int i = 0; i < power - 1; i++) {
            number *= num;
        }
        return number;
    }

    public static Color randomColor(){
        switch (MathUtils.random(0, 5)){
            case 0:
                return Color.OLIVE;
            case 1:
                return Color.DARK_GRAY;
            case 2:
                return Color.FOREST;
            case 3:
                return Color.TEAL;
            case 4:
                return Color.ROYAL;
            case 5:
                return Color.BROWN;
        }
        return null;
    }

    // example: shadesOfColor(white, 1, 50) will return 1/50 of white (almost black)
    public static Color shadesOfColor(Color colorTheme, int shadeOrder, int numberOfShades){
        float r = colorTheme.r * shadeOrder / numberOfShades;
        float g = colorTheme.g * shadeOrder / numberOfShades;
        float b = colorTheme.b * shadeOrder / numberOfShades;
        float a = colorTheme.a;
        return new Color(r, g, b, a);
    }

    public static boolean randomBoolean(float truePercentage){
        return truePercentage < MathUtils.random();
    }
}
