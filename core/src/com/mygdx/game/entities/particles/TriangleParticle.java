package com.mygdx.game.entities.particles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.levels.Level;
import com.mygdx.game.utilits.ColorChanger;
import com.mygdx.game.utilits.Constants;
import com.mygdx.game.utilits.Utils;

/**
 * Created by Asus123 on 18.03.2018.
 */

public class TriangleParticle extends Particle {
    private ColorChanger colorChanger;

    //points of triangle (position, point2, point3)
    private Vector2 point2, newPoint2;
    private Vector2 point3, newPoint3;

    public TriangleParticle(Vector2 position, Vector2 velocity, Level level) {
        super(Utils.randomVector(Constants.TRIANGLE_PARTICLE_SIZE).add(position), velocity, level);
        setLifeTime(Constants.TRIANGLE_PARTICLE_LIFE_TIME);

        point2 = Utils.randomVector(Constants.TRIANGLE_PARTICLE_SIZE).add(position);
        point3 = Utils.randomVector(Constants.TRIANGLE_PARTICLE_SIZE).add(position);

        colorChanger = new ColorChanger();
        colorChanger.setAllColorsDuration(getLifeTime());
//        colorChanger.addColorState(Color.RED);
        colorChanger.addColorState(Color.YELLOW);
        colorChanger.addColorState(Color.WHITE);
    }

    @Override
    public void update(float delta) {
        colorChanger.act(delta);
        newPoint2 = new Vector2(point2);
        newPoint3 = new Vector2(point3);
        newPoint2.add(new Vector2(position).add(new Vector2(point2).scl(-1)).scl(Utils.timeElapsed(startTime) / getLifeTime()));
        newPoint3.add(new Vector2(position).add(new Vector2(point3).scl(-1)).scl(Utils.timeElapsed(startTime) / getLifeTime()));
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer) {
        // TODO: 20.03.2018 fix the crutch. newPoint 2 and 3 sometimes become null.
        if (newPoint3 == null || newPoint2 == null) return;
        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(colorChanger.getColor());
        renderer.triangle(position.x, position.y, newPoint2.x, newPoint2.y, newPoint3.x, newPoint3.y);
    }
}