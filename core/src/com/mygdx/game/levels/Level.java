package com.mygdx.game.levels;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Constants;
import com.mygdx.game.entities.GG;

/**
 * Created by Asus123 on 15.12.2017.
 */

public class Level {
    public GG gg;

    public Level(){
        gg = new GG();
    }

    public void update(float delta){
        gg.update(delta);
    }

    public void render(ShapeRenderer renderer){
        gg.render(renderer);
    }
}
