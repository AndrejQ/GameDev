package com.mygdx.game.utilits;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.GG;

/**
 * Created by Asus123 on 22.12.2017.
 */

public class ChaseCam {

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
//        camera.position.x = target.position.x;
//        camera.position.y = target.position.y;

        // reacting for accelerometer
        camera.position.x += xAccelerometer;
        camera.position.y -= yAccelerometer;
        float yAccelerometerNew = 5 * Gdx.input.getAccelerometerX();
        float xAccelerometerNew = 5 * Gdx.input.getAccelerometerY();
        // smoothing
        float eps = 0.2f;
        if (Math.abs(xAccelerometerNew - xAccelerometer) > eps ||
                Math.abs(yAccelerometerNew - yAccelerometer) > eps){
            xAccelerometer = xAccelerometerNew * eps;
            yAccelerometer = yAccelerometerNew * eps;
        } else {
            xAccelerometer = xAccelerometerNew;
            yAccelerometer = yAccelerometerNew;
        }
        camera.position.x -= xAccelerometer;
        camera.position.y += yAccelerometer;

        float distanseCamGG = target.position.dst(camera.position.x, camera.position.y);
        velocity.add(new Vector2(target.position).add(new Vector2(camera.position.x, camera.position.y).scl(-1)).scl(delta * distanseCamGG));
        velocity.mulAdd(velocity, -Constants.FRICTION * 8); // air friction
        camera.position.x += velocity.x * delta;
        camera.position.y += velocity.y * delta;
    }

    public Camera getCamera() {
        return camera;
    }
}
