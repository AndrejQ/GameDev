package com.mygdx.game.entities.missiles;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.commonGameObjects.GameObject;

/**
 * Created by Asus123 on 15.12.2017.
 */

public abstract class Missile extends GameObject {
    public Missile(Vector2 position, Vector2 velocity){
        this.position = position;
        this.velocity = velocity;
    }
}
