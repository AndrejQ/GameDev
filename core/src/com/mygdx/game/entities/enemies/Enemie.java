package com.mygdx.game.entities.enemies;

import com.mygdx.game.entities.GameObject;
import com.mygdx.game.entities.missiles.Missile;

/**
 * Created by Asus123 on 18.12.2017.
 */

public abstract class Enemie extends GameObject {
    public float health;

    public void missileCatch(Missile missile){}
}
