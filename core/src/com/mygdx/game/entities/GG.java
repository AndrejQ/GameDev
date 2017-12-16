package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.entities.missiles.LightMissile;
import com.mygdx.game.entities.missiles.Missile;
import com.mygdx.game.levels.Level;
import com.mygdx.game.utilits.Constants;
import com.mygdx.game.entities.commonGameObjects.GameObject;
import com.mygdx.game.entities.modifications.Modification;
import com.mygdx.game.utilits.Utils;

/**
 * Created by Asus123 on 12.12.2017.
 */

public class GG extends GameObject{

    private long startTime;

    public boolean isTousched;
    public Vector2 tapPosition;
    Level level;
    Array<? extends Modification> modifications;

    @Override
    public void update(float delta) {
        velocity.mulAdd(velocity, -Constants.FRICTION); // air friction
        super.update(delta);

        // light missile spawn
        if (isTousched){
            if (startTime== 0){startTime = TimeUtils.nanoTime();}
            if (Utils.timeElapsed(startTime) > 1 / Constants.LIGHT_MISSILE_SPAWN_PER_SECOND){
                missileSpawn(tapPosition);
                startTime = 0;
            }
        }
    }


    @Override
    public void render(ShapeRenderer renderer) {
        renderer.set(ShapeRenderer.ShapeType.Filled);
        super.render(renderer);
    }

    public GG(Level level) {
        this.level = level;
        radius = Constants.GG_RADIUS;
        init(new Vector2(Constants.WORLD_SIZE/2, Constants.WORLD_SIZE/2));
    }


    public void init(Vector2 position){
        modifications = new Array<Modification>();
        velocity = new Vector2();
        this.position = position;
    }

    public Vector2 missileSpawnPosition(Vector2 tapPosition){
        return missileSpawnDirection(tapPosition).scl(radius).add(position);
    }

    public Vector2 missileSpawnDirection(Vector2 tapPosition){
        Vector2 connection = new Vector2(tapPosition.x - position.x,
                tapPosition.y - position.y);
        return connection.nor();
    }

    public void missileSpawn(Vector2 tapPosition){
        Missile missile =new LightMissile(missileSpawnPosition(tapPosition),
                missileSpawnDirection(tapPosition).scl(Constants.LIGHT_MISSILE_VELOCITY));

        level.missiles.add(missile);
        velocity.add(new Vector2(missile.velocity)
                .scl(- Constants.LIGHT_MISSILE_MASS / Constants.GG_MASS));
    }
}
