package com.mygdx.game.entities.particles;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.entities.GameObject;
import com.mygdx.game.utilits.Constants;
import com.mygdx.game.utilits.Utils;

/**
 * Created by Asus123 on 19.12.2017.
 */

public abstract class Particle extends GameObject {
    public float lifeTime;

    public Particle(Vector2 position, Vector2 velocity) {
        this.position = position;
        this.velocity = velocity;
        this.startTime = TimeUtils.nanoTime();
    }

    public boolean isTimeElapsed(){
        return Utils.timeElapsed(startTime) > lifeTime;
    }
}
