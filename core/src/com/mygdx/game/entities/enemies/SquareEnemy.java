package com.mygdx.game.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.GG;
import com.mygdx.game.entities.missiles.Missile;
import com.mygdx.game.entities.particles.SparkleParticle;
import com.mygdx.game.entities.particles.TriangleParticle;
import com.mygdx.game.levels.Level;
import com.mygdx.game.utilits.ColorChanger;
import com.mygdx.game.utilits.Constants;
import com.mygdx.game.utilits.Utils;

public class SquareEnemy extends Enemy {
    private ColorChanger mColorChanger;
    private float mAngle;
    private int mRecursionLevel;
    private float mLength;
    private int direction; // -1 or 1

    public SquareEnemy(Vector2 position, Level level) {
        this(position, new Vector2(), level, Constants.SQUARE_ENEMY_RECURSION);
    }

    public SquareEnemy(Vector2 position, Vector2 velocity, Level level, int recursicn) {
        this(position, velocity, level, 0, recursicn);
    }

    public SquareEnemy(Vector2 position, Vector2 velocity, Level level, float angle, int recurcion) {
        super(position, velocity, level);
        mAngle = angle;
        mColorChanger = new ColorChanger();
        mColorChanger.setAllColorsDuration(5);
        for (Color color : Constants.SQUARE_ENEMY_COLORS){
            mColorChanger.addColorState(color);
        }
        health = Constants.SQUARE_ENEMY_HEALTH * Utils.pow(2, recurcion);
        direction = -1 + 2 * MathUtils.random(1);
        mLength = Constants.SQUARE_ENEMY_LENGTH * Utils.pow(2, recurcion);
        mRecursionLevel = recurcion;
        scoreAfterDeath = mRecursionLevel == 0 ? Constants.SQUARE_ENEMY_SCORE_AFTER_DEATH : 0;
        radius = mLength / 2;
        setMass(Constants.SQUARE_ENEMY_MASS * Utils.pow(2, recurcion));
    }

    @Override
    void velocityUpdate() {
        super.velocityUpdate();
    }

    @Override
    public void missileCatch(Missile missile) {
        velocity.add(new Vector2(missile.velocity).scl(missile.getMass() / this.getMass()));
        super.missileCatch(missile);
        health -= missile.damage;
        missile.missileExplode(this,
                new Vector2(missile.position.x - position.x, missile.position.y - position.y).nor());
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        velocity.mulAdd(velocity, -Constants.FRICTION); // air friction

        mAngle += direction * delta * Constants.SQUARE_ENEMY_ROTATION;
        mColorChanger.act(delta);

        // acceleration
        velocity.add(dstForGG.nor().scl(Constants.SQUARE_ENEMY_ACCELERATION * delta));
    }

    @Override
    public void collideWithGG(GG gg) {
        super.collideWithGG(gg);
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer) {
        renderer.setColor(mColorChanger.getColor());
        renderer.rect(position.x - 0.5f * mLength, position.y - 0.5f * mLength, 0.5f * mLength, 0.5f * mLength, mLength, mLength, 1, 1, mAngle);
    }

    @Override
    public boolean contains(Vector2 pointPosition) {
//        return
        return new Circle(position, mLength / 2).contains(pointPosition);
    }

    @Override
    public void death() {
        for (int i = 0; i < 5; i++) {
            level.particleManager.particles.add(new TriangleParticle(
                    new Vector2(position),
                    Utils.randomRoundVector(Constants.SPARKLE_PARTICLE_START_VELOCITY),
                    Constants.SQUARE_ENEMY_COLORS,
                    mLength / 1.5f,
                    level
            ));
        }
        if (mRecursionLevel > 0){
            for (int i = 0; i < 4; i++) {
                Vector2 directionVector = (new Vector2(1, 0).scl(mLength / 2.82842712f)).rotate(mAngle + 45 + 90 * i * direction);
                level.enemiesManager.enemies.add(new SquareEnemy(
                        (new Vector2(directionVector).add(position)),
                        new Vector2(directionVector).scl(2),
                        level,
                        mAngle,
                        mRecursionLevel - 1
                ));
            }
        }
        super.death();
    }

    @Override
    public void collideWithOtherEnemy(Enemy otherEnemy) {
        collideWithObject(otherEnemy);
    }
}
