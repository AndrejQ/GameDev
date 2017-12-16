package com.mygdx.game.entities.missiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.utilits.Constants;
import com.mygdx.game.utilits.Utils;

import static com.mygdx.game.utilits.Utils.timeElapsed;

/**
 * Created by Asus123 on 15.12.2017.
 */

public class LightMissile extends Missile {

    public LightMissile(Vector2 position, Vector2 velocity) {
        super(position, velocity);
        radius = Constants.LIGHT_MISSILE_RADIUS;
        startTime = TimeUtils.nanoTime();
    }


    @Override
    public void render(ShapeRenderer renderer) {
        renderer.setColor(Color.RED);
        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.circle(position.x, position.y, radius);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public boolean timeIsOver(){
        return timeElapsed(startTime) > Constants.LIGHT_MISSILE_LIFETIME;
    }
}
