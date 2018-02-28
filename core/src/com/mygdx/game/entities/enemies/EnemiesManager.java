package com.mygdx.game.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.entities.GG;
import com.mygdx.game.utilits.Constants;
import com.mygdx.game.utilits.Utils;

/**
 * Created by Asus123 on 18.12.2017.
 */

public class EnemiesManager {
    long startTime;
    public DelayedRemovalArray<Enemy> enemies;
    GG gg;


    public EnemiesManager(GG gg) {
        enemies = new DelayedRemovalArray<Enemy>();
        startTime = TimeUtils.nanoTime();
        this.gg = gg;
    }

    public void update(float delta){

        // spawn (around gg)
        if (Utils.timeElapsed(startTime) > 1 / Constants.SIMPLE_ENEMY_SPAWN_RATE_PER_SECOND){
            enemies.add(new SimpleEnemy(Utils.randomVector(Constants.WORLD_SIZE).add(gg.position), gg.level));

            startTime = TimeUtils.nanoTime();
        }
        // update
        for (Enemy enemy : enemies){
            enemy.collideWithObject(gg);
            for (int i = 0; i < enemies.size; i++) {
                enemy.collideWithOtherEnemy(enemies.get(i));
            }
            enemy.update(delta);
            // cont distance for gg
            enemy.dstForGG = new Vector2(gg.position).add(new Vector2(enemy.position).scl(-1));

            if (enemy.dstForGG.len2() > Utils.pow2(Constants.ENEMY_DISPOSE_DISTANCE)){
                enemies.removeValue(enemy, false);
            }
        }
    }

    public void render(SpriteBatch batch, ShapeRenderer renderer){

        // render
        for (Enemy enemy : enemies){
            enemy.render(batch, renderer);
        }
    }
}
