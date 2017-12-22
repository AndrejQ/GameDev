package com.mygdx.game.entities.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.utilits.Constants;
import com.mygdx.game.utilits.Utils;

/**
 * Created by Asus123 on 18.12.2017.
 */

public class EnemiesManager {
    long startTime;
    public DelayedRemovalArray<SimpleEnemie> simpleEnemies;


    public EnemiesManager() {
        simpleEnemies = new DelayedRemovalArray<SimpleEnemie>();
        startTime = TimeUtils.nanoTime();
    }

    public void update(float delta){
        if (Utils.timeElapsed(startTime) > 1 / Constants.SIMPLE_ENEMY_SPAWN_RATE_PER_SECOND){
            simpleEnemies.add(new SimpleEnemie(new Vector2(MathUtils.random(Constants.WORLD_BOUNDS.width),
                    MathUtils.random(Constants.WORLD_BOUNDS.height))));

            startTime = TimeUtils.nanoTime();
        }
        for (SimpleEnemie simpleEnemie : simpleEnemies){
            simpleEnemie.update(delta);
        }
    }

    public void render(SpriteBatch batch, ShapeRenderer renderer){
        for (SimpleEnemie simpleEnemie : simpleEnemies){
            simpleEnemie.render(batch, renderer);
        }
    }
}
