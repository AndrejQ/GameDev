package com.mygdx.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.GrayGG;
import com.mygdx.game.entities.YellowGG;
import com.mygdx.game.entities.missiles.MissilesManager;
import com.mygdx.game.entities.particles.ParticleManager;
import com.mygdx.game.entities.particles.SparkleParticle;
import com.mygdx.game.utilits.Constants;

/**
 * Created by Asus123 on 22.03.2018.
 */

public class MenuLevel extends Level {
    YellowGG yellowGG;
    GrayGG grayGG;

    public MenuLevel() {
        particleManager = new ParticleManager();

//        Gdx.app.log("", "" + particleManager.particles.size);

        yellowGG = new YellowGG(
                new Vector2(-20, 0),
                new Vector2(),
                this);
        grayGG = new GrayGG(
                new Vector2(20, 0),
                new Vector2(),
                this);
    }

    @Override
    public void update(float delta) {

        yellowGG.update(delta);
        grayGG.update(delta);
        missilesManager.update(delta);
        particleManager.update(delta);
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer) {
        yellowGG.render(batch, renderer);
        grayGG.render(batch, renderer);
        missilesManager.render(batch, renderer);
        Gdx.app.log("", "" + particleManager.particles.size);
        particleManager.render(batch, renderer);
    }
}
