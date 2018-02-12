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
        mass = Constants.SIMPLE_ENEMY_MASS;
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
        // check if overlapping
        if (position.dst2(otherEnemy.position) >= Utils.pow2(radius + otherEnemy.radius)
                || this.equals(otherEnemy)) {return;}

        Vector2 position1 = position.cpy();
        Vector2 position2 = otherEnemy.position.cpy();
        Vector2 axisDirection = position2.cpy().mulAdd(position1, -1).nor(); // from this to enemy

        float vel1 = velocity.dot(axisDirection);
        float vel2 = otherEnemy.velocity.dot(axisDirection);
        float mass1 = mass;
        float mass2 = otherEnemy.mass;

        // overlapped area length
        float deltaL =  radius + otherEnemy.radius - position1.dst(position2);
        otherEnemy.position.mulAdd(axisDirection, deltaL * 1);
        position.mulAdd(axisDirection, deltaL * -1);

        // annul axisDirection velocity projection
        velocity.mulAdd(axisDirection, 1 * vel1);
        otherEnemy.velocity.mulAdd(axisDirection, -1 * vel2);

        // conservation of pulse
        velocity.mulAdd(axisDirection,
                ((mass1 - mass2) * vel1 + 2 * mass2 * vel2) / (mass1 + mass2));

        otherEnemy.velocity.mulAdd(axisDirection,
                ((mass2 - mass1) * vel2 + 2 * mass1 * vel1) / (mass1 + mass2));
    }
}
