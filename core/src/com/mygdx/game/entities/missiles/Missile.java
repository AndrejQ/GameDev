package com.mygdx.game.entities.missiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.DelayedRemovalArray;
import com.mygdx.game.entities.GameObject;
import com.mygdx.game.entities.enemies.Enemy;
import com.mygdx.game.levels.Level;
import com.mygdx.game.utilits.Constants;

/**
 * Created by Asus123 on 15.12.2017.
 */

public abstract class Missile extends GameObject {

    public float damage;


    public Missile(Vector2 position, Vector2 velocity, Level level){
        this.position = position;
        this.velocity = velocity;
        this.level = level;
    }

    public boolean timeIsOver(){
        return false;
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
