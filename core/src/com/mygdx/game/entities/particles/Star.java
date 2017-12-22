package com.mygdx.game.entities.particles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utilits.Assets;
import com.mygdx.game.utilits.Constants;
import com.mygdx.game.utilits.Utils;

/**
 * Created by Asus123 on 21.12.2017.
 */

public class Star extends Particle {
    public float offset = 0;

    public Star(Vector2 position, Vector2 velocity) {
        super(position, velocity);
        lifeTime = Constants.STAR_LIFETIME;
        radius = Constants.STAR_RADIUS;
    }

    @Override
    public void update(float delta) {
        //super.update(delta); // nothing to update
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer) {
        TextureRegion region = (TextureRegion) Assets.instance.starsAssets.starBlink.getKeyFrame(Utils.timeElapsed(startTime), true);
        batch.draw(region, position.x, position.y, radius, radius);
    }
}
