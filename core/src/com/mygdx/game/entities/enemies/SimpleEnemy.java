package com.mygdx.game.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.entities.missiles.Missile;
import com.mygdx.game.utilits.Constants;
import com.mygdx.game.utilits.Utils;

/**
 * Created by Asus123 on 18.12.2017.
 */

public class SimpleEnemy extends Enemy {

    private long startTime;
    private float timeBetweenAdding = Constants.SIMPLE_ENEMY_TIME_BETWEEN_ADDING;

    @Override
    public void update(float delta) {
        velocity.mulAdd(velocity, -Constants.FRICTION); // air friction
        super.update(delta);

        if (Utils.timeElapsed(startTime) > timeBetweenAdding){
            this.velocityUpdate();
            startTime = TimeUtils.nanoTime();
        }
    }

    public SimpleEnemy(Vector2 position) {
        radius = Constants.SIMPLE_ENEMY_RADIUS;
        health = Constants.SIMPLE_ENEMY_HEALTH;
        setMass(Constants.SIMPLE_ENEMY_MASS);
        this.position = position;
        this.velocity = new Vector2();
        startTime = TimeUtils.nanoTime();
    }

    @Override
    void velocityUpdate(){
        velocity.add(MathUtils.random(-Constants.SIMPLE_ENEMY_ADD_VELOCITY, Constants.SIMPLE_ENEMY_ADD_VELOCITY),
                MathUtils.random(-Constants.SIMPLE_ENEMY_ADD_VELOCITY, Constants.SIMPLE_ENEMY_ADD_VELOCITY))
        .add(dstForGG.nor().scl(Constants.SIMPLE_ENEMY_ADD_VELOCITY));
    }

    @Override
    public void missileCatch(Missile missile) {
        health -= missile.damage;

        Vector2 direction = new Vector2(missile.position.x - position.x, missile.position.y - position.y).nor();
        //sparkling after damage
        missile.missileExplode(this, direction);
    }

    // TODO: 12.02.2018 fix rectangle collision method
    @Override
    public void collideWithOtherEnemy(Enemy otherEnemy) {
        collideWithObject(otherEnemy);
    }
}
