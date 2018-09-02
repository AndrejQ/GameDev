package com.mygdx.game.entities.missiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.DelayedRemovalArray;

/**
 * Created by Asus123 on 18.12.2017.
 */

public class MissilesManager {
    public DelayedRemovalArray<Missile> missiles;

    public MissilesManager() {
        missiles = new DelayedRemovalArray<Missile>();
    }

    public void update(float delta){
        for (Missile missile : missiles){
            missile.update(delta);
            if (missile.timeIsOver()){
                missiles.removeValue(missile, false);
            }
            // TODO: 18.12.2017 there is checking for colliding bullet and enemy. Maybe its not efficient.
            if (missile.collideWithEnemy(missile.level.enemiesManager.enemies)){
                missiles.removeValue(missile, false);
            }
        }


    }

    public void render(SpriteBatch batch, ShapeRenderer renderer){
        for (Missile missile : missiles){
            missile.render(batch, renderer);
        }
    }

}
