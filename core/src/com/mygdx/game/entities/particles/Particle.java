package com.mygdx.game.entities.particles;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.entities.GameObject;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.utilits.Utils;

/**
 * Created by Asus123 on 19.12.2017.
 */

public abstract class Particle extends GameObject {
    public float lifeTime;

    // Enemy with explosion (particles)
    private Enemy mHostEnemy;

    public Particle(Enemy hostEnemy,Vector2 position, Vector2 velocity){
        this(position, velocity);
        this.mHostEnemy = hostEnemy;
    }

    public Particle(Vector2 position, Vector2 velocity) {
        this.position = position;
        this.velocity = velocity;
        this.startTime = TimeUtils.nanoTime();
    }

    public boolean isTimeElapsed(){
        return Utils.timeElapsed(startTime) > lifeTime;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        // explosions and particles move with host enemy
        if (mHostEnemy != null) {
            position.mulAdd(mHostEnemy.velocity, delta);
        }
    }
}
