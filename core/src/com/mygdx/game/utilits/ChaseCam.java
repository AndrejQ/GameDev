package com.mygdx.game.utilits;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.GG;

/**
 * Created by Asus123 on 22.12.2017.
 */

public class ChaseCam {

    private boolean fixedChasing;
    private Vector2 velocity;
    private Camera camera;
    private GG target;
    float xAccelerometer;
    float yAccelerometer;

    public ChaseCam(Camera camera, GG gg) {
        this.camera = camera;
        this.target = gg;
        velocity = new Vector2();
    }

    public void update(float delta){
        if (fixedChasing) {
            camera.position.x = target.position.x;
            camera.position.y = target.position.y;
            return;
        }
        // reacting for accelerometer
        camera.position.x += xAccelerometer;
        camera.position.y -= yAccelerometer;
        float yAccelerometerNew = 2 * Gdx.input.getAccelerometerX();
        float xAccelerometerNew = 2 * Gdx.input.getAccelerometerY();
        // smoothing
        float eps = 0.1f;
        if (Math.abs(xAccelerometerNew - xAccelerometer) > eps ||
                Math.abs(yAccelerometerNew - yAccelerometer) > eps){
            xAccelerometer += xAccelerometerNew * eps / 3;
            yAccelerometer += yAccelerometerNew * eps / 3;
        } else {
            xAccelerometer = xAccelerometerNew;
            yAccelerometer = yAccelerometerNew;
        }
        camera.position.x -= xAccelerometer;
        camera.position.y += yAccelerometer;

        float distanceCamGG = target.position.dst(camera.position.x, camera.position.y);
        velocity.add(new Vector2(target.position).add(new Vector2(camera.position.x, camera.position.y).scl(-1)).scl(delta * distanceCamGG));
        velocity.mulAdd(velocity, -Constants.FRICTION * 8); // air friction
        camera.position.x += velocity.x * delta;
        camera.position.y += velocity.y * delta;
    }

    public void fixChase(boolean fix){
        fixedChasing = fix;
    }

    public Camera getCamera() {
        return camera;
    }
}
