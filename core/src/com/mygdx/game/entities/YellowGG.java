package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.entities.particles.TriangleParticle;
import com.mygdx.game.levels.Level;
import com.mygdx.game.utilits.ColorChanger;
import com.mygdx.game.utilits.Constants;
import com.mygdx.game.utilits.Utils;

/**
 * Created by Asus123 on 22.03.2018.
 */

public class YellowGG extends GG {
    public YellowGG(Vector2 position, Vector2 velocity, Level level) {
        super(position, velocity, level);
        setMass(Constants.GG_MASS);
        radius = Constants.GG_RADIUS;
        colorChanger = new ColorChanger();
        colorChanger.addColorState(Color.GOLD);
        colorChanger.addColorState(Color.YELLOW);
        colorChanger.addColorState(new Color(0.9f, 0.9f, 1, 1));
        colorChanger.addColorState(Color.YELLOW);
        colorChanger.addColorState(Color.ORANGE);
        colorChanger.setAllColorsDuration(10);
    }

    @Override
    public void update(float delta) {
        velocity.mulAdd(velocity, -Constants.FRICTION * 60 * delta); // air friction
        super.update(delta);

        colorChanger.act(delta);

        for (int i = 0; i < 2; i++) {
            this.level.particleManager.particles.
                    add(new TriangleParticle(position,
                            Utils.randomRoundVector(radius * 5),
                            new Color[]{colorChanger.getColor(), colorChanger.getColor(), Color.ORANGE},
                            radius,
                            level));
        }
        // light missile spawn
        if (isPlayerTouching){
            if (Utils.timeElapsed(startTime) > 1 / Constants.LIGHT_MISSILE_SPAWN_PER_SECOND){
                missileSpawn(tapPosition);
                startTime = TimeUtils.nanoTime();
            }
        }

        // stars spawn
//        if (Utils.timeElapsed(startTimeForStars) > 1 / Constants.STAR_GENERATE_PER_SECOND){
//            generateStar();
//            startTimeForStars = TimeUtils.nanoTime();
//        }
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer) {
        super.render(batch, renderer);
    }

    @Override
    public Vector2 missileSpawnPosition(Vector2 tapPosition) {
        return missileSpawnDirection(tapPosition).scl(radius).add(position);
    }

    @Override
    public Vector2 missileSpawnDirection(Vector2 tapPosition) {
        return super.missileSpawnDirection(tapPosition);
    }

    @Override
    public void missileSpawn(Vector2 tapPosition) {
        super.missileSpawn(tapPosition);
    }
}
