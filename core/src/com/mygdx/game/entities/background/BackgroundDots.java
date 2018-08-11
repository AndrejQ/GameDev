package com.mygdx.game.entities.background;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.utilits.Constants;
import com.mygdx.game.utilits.Utils;

import javax.rmi.CORBA.Util;

public class BackgroundDots {
    int sizeX, sizeY;
    long startTime;
    float aspectRatio;
    float size, h;
    Vector2[][] positions;
    private Vector2 currentCamPosition;
    public BackgroundDots() {
        init();
    }

    private void init(){
        h = 50;
        size = 10;
        aspectRatio = Utils.getAspectRatio();
        sizeX = (int) (Constants.WORLD_SIZE / h * aspectRatio) + 2;
        sizeY = (int) (Constants.WORLD_SIZE / h) + 2;
        positions = new Vector2[sizeX][sizeY];
        for (int i = 0; i < sizeX; i++){
            for (int j = 0; j < sizeY; j++) {
                positions[i][j] = new Vector2();
            }
        }
        startTime = TimeUtils.nanoTime();
    }

    public void update(Vector2 camPosition){
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                float x = camPosition.x + (i - 0.5f) * h - Constants.WORLD_SIZE / 2 * aspectRatio;
                float y = camPosition.y + (j - 0.5f) * h - Constants.WORLD_SIZE / 2;
                x = x - (x % h);
                y = y - (y % h);
                positions[i][j].x = x;
                positions[i][j].y = y;
            }
        }
        currentCamPosition = new Vector2(camPosition);
    }

    public void render(ShapeRenderer renderer){
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                renderer.setColor(Color.LIGHT_GRAY);
                renderer.set(ShapeRenderer.ShapeType.Filled);
                float len = decreasingFromGG(positions[i][j])
                        * increasingFunction(Utils.timeElapsed(startTime)) * size;
                renderer.rect(positions[i][j].x - len / 2,
                        positions[i][j].y - len / 2,
                        len,
                        len);
            }
        }
    }

    private float increasingFunction(float elapsedTime){
        elapsedTime *= 10;
        return elapsedTime / (1 + elapsedTime);
    }

    private float decreasingFromGG(Vector2 dotPosition){
        float x = currentCamPosition.x - dotPosition.x;
        float y = currentCamPosition.y - dotPosition.y;
        float b = Constants.WORLD_SIZE / 2;
        float a = b * Utils.getAspectRatio();
        float r = 1 - Utils.pow(x / a, 4) - Utils.pow(y / b, 4);
        return r >= 0 ? r : 0;
    }
}
