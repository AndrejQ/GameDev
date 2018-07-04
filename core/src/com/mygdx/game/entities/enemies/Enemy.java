package com.mygdx.game.entities.enemies;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.GG;
import com.mygdx.game.entities.GameObject;
import com.mygdx.game.entities.missiles.Missile;
import com.mygdx.game.levels.Level;
import com.mygdx.game.utilits.Utils;

/**
 * Created by Asus123 on 18.12.2017.
 */

public abstract class Enemy extends GameObject {
    public float health;
    Vector2 dstForGG;

    public Enemy(Vector2 position, Vector2 velocity, Level level) {
        super(position, velocity, level);
    }

    void velocityUpdate(){}

    public void missileCatch(Missile missile){
        missile.missileExplode(this);
    }

    public void collideWithOtherEnemy(Enemy otherEnemy){}

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    public void collideWithGG(GG gg){
        collideWithObject(gg);
    }
}
