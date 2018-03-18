package com.mygdx.game.entities.missiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.mygdx.game.entities.GameObject;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.levels.Level;
import com.mygdx.game.utilits.Constants;

import static com.mygdx.game.utilits.Utils.timeElapsed;

/**
 * Created by Asus123 on 15.12.2017.
 */

public abstract class Missile extends GameObject {

    public float damage;
    protected float lifetime;
    Vector2 previousPosition;

    public Missile(Vector2 position, Vector2 velocity, Level level) {
        super(position, velocity, level);
        previousPosition = new Vector2(position);
    }

    @Override
    public void update(float delta) {
        previousPosition = new Vector2(position);
        super.update(delta);
    }
//    public Missile(Vector2 position, Vector2 velocity, Level level){
//        this.position = position;
//        this.velocity = velocity;
//        this.level = level;
//    }

    public boolean timeIsOver(){
        return timeElapsed(startTime) > lifetime;
    }

    public boolean collideWithEnemy(DelayedRemovalArray<Enemy> enemies){
        for (Enemy enemy : enemies){
            if (new Circle(enemy.position, enemy.radius).contains(position)){
                enemy.missileCatch(this);
                if (enemy.health < 0){
                    enemies.removeValue(enemy, false);
                    Gdx.input.vibrate(30);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean collideWithWalls() {
        return !Constants.WORLD_BOUNDS.contains(position);
    }

    public void missileExplode(Enemy hostEnemy){}

    public void missileExplode(Enemy hostEnemy, Vector2 direction){}
}
