package com.mygdx.game.entities.enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.entities.missiles.Missile;
import com.mygdx.game.levels.Level;
import com.mygdx.game.utilits.Constants;
import com.mygdx.game.utilits.Utils;

/**
 * Created by Asus123 on 18.12.2017.
 */

public class SimpleEnemy extends Enemy {

    private long startTime;
    private float timeBetweenAdding = Constants.SIMPLE_ENEMY_TIME_BETWEEN_ADDING_VELOCITY;
    private Color color;

    public SimpleEnemy(Vector2 position, Level level){
        this(position, new Vector2(), level);
    }

    public SimpleEnemy(Vector2 position, Vector2 velocity, Level level) {
        super(position, velocity, level);
        radius = Constants.SIMPLE_ENEMY_RADIUS;
        health = Constants.SIMPLE_ENEMY_HEALTH;
        setMass(Constants.SIMPLE_ENEMY_MASS);
        startTime = TimeUtils.nanoTime();
        color = new Color(Constants.SIMPLE_ENEMY_COLOR);
    }

    @Override
    public void update(float delta) {
//        velocity.mulAdd(velocity, -Constants.FRICTION); // air friction
        super.update(delta);

        if (Utils.timeElapsed(startTime) > timeBetweenAdding){
            this.velocityUpdate();
            startTime = TimeUtils.nanoTime();
        }
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer) {
        int segments = 20;
        for (int i = segments; i > 10; i = i - 5) {
            renderer.setColor(
                    color.r + (float)(segments - i) / segments,
                    color.g,
                    color.b,
                    color.a
            );
            renderer.circle(position.x, position.y, radius * (float)i / segments, i);
        }
    }

    @Override
    void velocityUpdate(){
        velocity.add(MathUtils.random(-Constants.SIMPLE_ENEMY_ADD_VELOCITY, Constants.SIMPLE_ENEMY_ADD_VELOCITY),
                MathUtils.random(-Constants.SIMPLE_ENEMY_ADD_VELOCITY, Constants.SIMPLE_ENEMY_ADD_VELOCITY))
        .add(dstForGG.nor().scl(Constants.SIMPLE_ENEMY_ADD_VELOCITY));
    }

    @Override
    public void missileCatch(Missile missile) {
        super.missileCatch(missile);
        health -= missile.damage;

        // reaction of enemy
        color.r = 0.6f - 0.5f * health / Constants.SIMPLE_ENEMY_HEALTH;
        timeBetweenAdding = Constants.SIMPLE_ENEMY_TIME_BETWEEN_ADDING_VELOCITY * 1 /
                (0.5f * (Constants.SIMPLE_ENEMY_HEALTH - health) + 1);

        Vector2 direction = new Vector2(missile.position.x - position.x, missile.position.y - position.y).nor();
        //sparkling after damage
        missile.missileExplode(this, direction);
    }

    @Override
    public boolean contains(Vector2 pointPosition) {
        return new Circle(position, radius).contains(pointPosition);
    }

    // TODO: 12.02.2018 fix rectangle collision method
    @Override
    public void collideWithOtherEnemy(Enemy otherEnemy) {
        collideWithObject(otherEnemy);
    }
}
