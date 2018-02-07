package com.mygdx.game.entities.enemies;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.GameObject;
import com.mygdx.game.entities.missiles.Missile;

/**
 * Created by Asus123 on 18.12.2017.
 */

public abstract class Enemy extends GameObject {
    public float health;
    Vector2 dstForGG;

    public Enemy() {}

    void velocityUpdate(){}

    public void missileCatch(Missile missile){}
}
