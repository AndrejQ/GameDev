package com.mygdx.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.GG;
import com.mygdx.game.entities.YellowGG;
import com.mygdx.game.entities.background.Background;
import com.mygdx.game.entities.enemies.EnemiesManager;
import com.mygdx.game.entities.missiles.MissilesManager;
import com.mygdx.game.entities.particles.ParticleManager;

/**
 * Created by Asus123 on 15.12.2017.
 */

public class Level {

    public GG gg;
    public EnemiesManager enemiesManager;
    public MissilesManager missilesManager;
    public ParticleManager particleManager;

    private Vector2 instantCameraPosition = new Vector2();

    public Level(){
        missilesManager = new MissilesManager();
        particleManager = new ParticleManager();
        // TODO: 25.12.2017 make gg = new GG(!!without this!!) make environmentManager where use generateStars() method (remove it from gg)
        gg = new YellowGG(new Vector2(10, 10), new Vector2(), this);
        enemiesManager = new EnemiesManager(gg);
    }

    public void update(float delta){
        gg.update(delta);
        enemiesManager.update(delta);
        missilesManager.update(delta);
        particleManager.update(delta);
    }

    public void render(SpriteBatch batch, ShapeRenderer renderer){
        enemiesManager.render(batch, renderer);
        particleManager.render(batch, renderer);
        gg.render(batch, renderer);
        missilesManager.render(batch, renderer);
    }

    public void setInstantCameraPosition(float x, float y){
        instantCameraPosition.x = x;
        instantCameraPosition.y = y;
    }


    public Vector2 getInstantCameraPosition() {
        return instantCameraPosition;
    }
}
