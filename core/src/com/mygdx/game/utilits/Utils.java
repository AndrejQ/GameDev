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
    private static float aspect_ratio;

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

    public static Vector2 randomRoundVector(float radius){
        Vector2 vector = Utils.randomVector(-radius, radius);
        while (vector.x * vector.x + vector.y * vector.y > radius * radius){
            vector = Utils.randomVector(-radius, radius);
        }
        return vector;
    }

    public static Vector2 randomOnCircleVector(float radius){
        return new Vector2(1, 0).rotate(MathUtils.random(360f)).scl(radius);
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

    public static boolean outOfScreen(Vector2 targetPosition, Vector2 centerPosition, float radius){
        return Math.abs(targetPosition.x - centerPosition.x) - radius > aspect_ratio * Constants.WORLD_SIZE / 2 ||
        Math.abs(targetPosition.y - centerPosition.y) - radius > Constants.WORLD_SIZE / 2;
    }

    public static boolean outOfVerticalUp(Vector2 targetPosition, Vector2 centerPosition, float radius) {
        return (targetPosition.y - centerPosition.y) - radius > Constants.WORLD_SIZE / 2;
    }

    public static boolean outOfVerticalDown(Vector2 targetPosition, Vector2 centerPosition, float radius) {
            return (centerPosition.y - targetPosition.y) - radius > Constants.WORLD_SIZE / 2;
        }

//    public static float getAspectRatio() {
//        return aspect_ratio;
//    }

    public static void setAspectRatio(float aspect_ratio) {
        Utils.aspect_ratio = aspect_ratio;
    }


}
