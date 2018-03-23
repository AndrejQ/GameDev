package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.utilits.Constants;

/**
 * Created by Asus123 on 22.03.2018.
 */

public class HudMenu {

    public final Viewport viewport;
    final BitmapFont font;

    public HudMenu() {
        viewport = new ExtendViewport(3 * Constants.WORLD_SIZE, 3 * Constants.WORLD_SIZE);
        font = new BitmapFont();
        font.getData().setScale(3);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    public void render(SpriteBatch batch, float delta){
        batch.setProjectionMatrix(viewport.getCamera().combined);
        font.draw(batch, "This is menu, tap anywhere you want",
                viewport.getWorldWidth()/2, viewport.getWorldHeight()/5,
                0, Align.center, false);
    }
}
