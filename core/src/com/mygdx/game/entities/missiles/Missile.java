package com.mygdx.game.entities.missiles;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.commonGameObjects.GameObject;
import com.mygdx.game.utilits.Constants;

import static com.mygdx.game.utilits.Utils.timeElapsed;

/**
 * Created by Asus123 on 15.12.2017.
 */

public abstract class Missile extends GameObject {

    public long startTime;


    public Missile(Vector2 position, Vector2 velocity){
        this.position = position;
        this.velocity = velocity;
    }

    public boolean timeIsOver(){
        return false;
    }

    @Override
    public boolean collideWithWalls() {
        return !Constants.WORLD_BOUNDS.contains(position);
    }
}
