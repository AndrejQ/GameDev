package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mygdx.game.entities.missiles.LightMissile;
import com.mygdx.game.entities.missiles.Missile;
import com.mygdx.game.entities.particles.Star;
import com.mygdx.game.levels.Level;
import com.mygdx.game.utilits.Constants;
import com.mygdx.game.entities.modifications.Modification;
import com.mygdx.game.utilits.Utils;

/**
 * Created by Asus123 on 12.12.2017.
 */

public class GG extends GameObject{
    private long startTimeForStars;
    public boolean isPlayerTouching;
    public Vector2 tapPosition;
    Array<? extends Modification> modifications;

    public GG(Vector2 position, Vector2 velocity, Level level) {
        super(position, velocity, level);
        radius = Constants.GG_RADIUS;
    }

//    public GG(Level level) {
//        this.level = level;
//    }

    @Override
    public void update(float delta) {
        velocity.mulAdd(velocity, -Constants.FRICTION); // air friction
        super.update(delta);

        // light missile spawn
        if (isPlayerTouching){
            if (Utils.timeElapsed(startTime) > 1 / Constants.LIGHT_MISSILE_SPAWN_PER_SECOND){
                missileSpawn(tapPosition);
                startTime = TimeUtils.nanoTime();
            }
        }

        // stars spawn
        if (Utils.timeElapsed(startTimeForStars) > 1 / Constants.STAR_GENERATE_PER_SECOND){
            generateStar();
            startTimeForStars = TimeUtils.nanoTime();
        }
    }


    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer) {
        renderer.set(ShapeRenderer.ShapeType.Filled);
        super.render(batch, renderer);
    }


    public void init(Vector2 position){
        modifications = new Array<Modification>();
        velocity = new Vector2();
        this.position = position;

        //generate first stars
        generateStarsAround();
    }

    public Vector2 missileSpawnPosition(Vector2 tapPosition){
        return missileSpawnDirection(tapPosition).scl(radius).add(position);
    }

    public Vector2 missileSpawnDirection(Vector2 tapPosition){
        Vector2 connection = new Vector2(tapPosition.x - position.x,
                tapPosition.y - position.y);

        // weapon spray
        // TODO: 18.12.2017 change weapon spray, this can slow down bullet speed
        connection.add(MathUtils.random(-Constants.WEAPON_SPRAY, Constants.WEAPON_SPRAY),
                MathUtils.random(-Constants.WEAPON_SPRAY, Constants.WEAPON_SPRAY));
        return connection.nor();
    }

    public void missileSpawn(Vector2 tapPosition){
        Missile missile =new LightMissile(missileSpawnPosition(tapPosition),
                missileSpawnDirection(tapPosition).scl(Constants.LIGHT_MISSILE_VELOCITY), level);

        level.missilesManager.missiles.add(missile);
        velocity.add(new Vector2(missile.velocity)
                .scl(- Constants.LIGHT_MISSILE_MASS / Constants.GG_MASS));
    }

    private void generateStarsAround(){
        for (int i = 0; i < Constants.STAR_COUNT; i++) {
            generateStar();
        }
    }

    private void generateStar(){
        level.particleManager.stars.add(new Star(Utils.randomVector(2 * Constants.WORLD_SIZE).add(position),
                new Vector2(), level));
    }
}
