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
import com.mygdx.game.entities.particles.TriangleParticle;
import com.mygdx.game.levels.Level;
import com.mygdx.game.utilits.ColorChanger;
import com.mygdx.game.utilits.Constants;
import com.mygdx.game.entities.modifications.Modification;
import com.mygdx.game.utilits.Utils;

/**
 * Created by Asus123 on 12.12.2017.
 */

public class GG extends GameObject{
    public boolean isPlayerTouching;
    public Vector2 tapPosition;
    protected ColorChanger colorChanger;
    float health;

    public GG(Vector2 position, Vector2 velocity, Level level) {
        super(position, velocity, level);
        health = Constants.GG_HP;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        health -= delta * Constants.GG_DECREASING_HP_PER_SECOND;
        health = health < 0 ? 0 : health;
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer) {
        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(colorChanger.getColor());
        renderer.circle(position.x, position.y, radius, 20);
    }


    public void init(Vector2 position){
        velocity = new Vector2();
        this.position = position;
    }

    public Vector2 missileSpawnPosition(Vector2 tapPosition){
//        return missileSpawnDirection(tapPosition).scl(radius).add(position);
        return Utils.randomRoundVector(radius).add(position);
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

    // TODO: 25.06.2018 make this like: missileSpawn(tapPosition, Missile missileType) 'missile type can be enum'
    public void missileSpawn(Vector2 tapPosition){
        Missile missile =new LightMissile(missileSpawnPosition(tapPosition),
                missileSpawnDirection(tapPosition).scl(Constants.LIGHT_MISSILE_VELOCITY), level);

        level.missilesManager.missiles.add(missile);
        velocity.add(new Vector2(missile.velocity)
                .scl(- Constants.LIGHT_MISSILE_MASS / Constants.GG_MASS));
    }

    public void addScore(float score){
        health += score;
        health = health > Constants.GG_HP ? Constants.GG_HP : health;
    }

    public float getHealth() {
        return health;
    }

    //    private void generateStarsAround(){
//        for (int i = 0; i < Constants.STAR_COUNT; i++) {
//            generateStar();
//        }
//    }
//
//    private void generateStar(){
//        level.particleManager.stars.add(new Star(Utils.randomVector(2 * Constants.WORLD_SIZE).add(position),
//                new Vector2(), level));
//    }
}
