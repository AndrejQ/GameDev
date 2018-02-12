package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.utilits.Constants;

/**
 * Created by Asus123 on 27.12.2017.
 */

public class HudFPS {

    public final Viewport viewport;
    final BitmapFont font;

    public HudFPS() {
        viewport = new ExtendViewport(2 * Constants.WORLD_SIZE, 2 * Constants.WORLD_SIZE);
        font = new BitmapFont();
        font.getData().setScale(1);
    }

    public void render(SpriteBatch batch, float delta){
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        String fps = String.format("%(.1f", 1/delta);
        font.draw(batch, fps, 10f, viewport.getWorldHeight() - 10f);
        batch.end();
    }
}
