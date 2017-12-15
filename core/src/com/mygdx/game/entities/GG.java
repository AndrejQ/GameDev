package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Constants;
import com.mygdx.game.entities.commonGameObjects.GameObject;
import com.mygdx.game.entities.modifications.Modification;

/**
 * Created by Asus123 on 12.12.2017.
 */

public class GG extends GameObject{
    Array<? extends Modification> modifications;

    @Override
    public void render(ShapeRenderer renderer) {
        renderer.set(ShapeRenderer.ShapeType.Filled);
        super.render(renderer);
    }

    public GG() {
        radius = Constants.GG_RADIUS;
        init(new Vector2(Constants.WORLD_SIZE/2, Constants.WORLD_SIZE/2));
    }


    public void init(Vector2 position){
        modifications = new Array<Modification>();
        velocity = new Vector2();
        this.position = position;
    }
}
