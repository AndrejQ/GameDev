package com.mygdx.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.mygdx.game.entities.GG;
import com.mygdx.game.entities.missiles.LightMissile;
import com.mygdx.game.entities.missiles.Missile;

/**
 * Created by Asus123 on 15.12.2017.
 */

public class Level {
    public GG gg;
    public DelayedRemovalArray<Missile> missiles;

    public Level(){
        gg = new GG(this);
        missiles = new DelayedRemovalArray<Missile>();
    }

    public void update(float delta){
        gg.update(delta);

        for (Missile missile : missiles){
            missile.update(delta);
            if (missile.timeIsOver()){
                missiles.removeValue(missile, false);
            }
        }
    }

    public void render(ShapeRenderer renderer){
        for (Missile missile : missiles){
            missile.render(renderer);
        }
        gg.render(renderer);
    }
}
