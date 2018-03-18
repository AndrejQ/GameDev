package com.mygdx.game.utilits;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by Asus123 on 16.12.2017.
 */

public class Utils {

    public static float aspect_ratio;

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

    public static Vector2[] pointsOnLine(Vector2 begin, Vector2 end, int numberOfPoints){
        Vector2[] points = new Vector2[numberOfPoints];
        for (int i = 0; i < numberOfPoints; i++) {
            points[i] = new Vector2(
                    begin.x + i * (end.x - begin.x) / numberOfPoints,
                    begin.y + i * (end.y - begin.y) / numberOfPoints
            );
        }
        return points;
    }

    public static Vector2[] pointsOnLineWithDensity(Vector2 begin, Vector2 end, float density) {
        float lineLength2 = begin.dst2(end) * (density * density); // density ** 2 for normalization condition

        if (lineLength2 < 1){

            // check if we have short line
            return Utils.pow2(MathUtils.random()) < lineLength2 - (int) lineLength2 ?
                    pointsOnLine(begin, end, 1) :
                    pointsOnLine(begin, end, 0);
        } else {
//            Gdx.app.log("", "dsvds" + (int) (lineLength2 * density * density));
            return pointsOnLine(begin, end, (int) Math.sqrt(lineLength2));
        }
    }

    public static boolean outOfScreen(Vector2 targetPosition, Vector2 centerPosition){
        return Math.abs(targetPosition.x - centerPosition.x) > aspect_ratio * Constants.WORLD_SIZE ||
        Math.abs(targetPosition.y - centerPosition.y) > Constants.WORLD_SIZE;
    }
}
