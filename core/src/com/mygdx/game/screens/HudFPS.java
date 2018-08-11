package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.levels.Level;
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

    public void render(ShapeRenderer renderer, Level level){
        viewport.apply();
        float length = 300;
        float width = 4;
        renderer.begin();
        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.BLACK);
        renderer.rect(viewport.getWorldWidth() / 2 - length / 2, viewport.getWorldHeight() - 20, length, width);
        renderer.setColor(new Color(1 - level.gg.getHealth() / Constants.GG_HP, level.gg.getHealth() / Constants.GG_HP, 0, 1));
        renderer.rect(viewport.getWorldWidth() / 2 - length / 2, viewport.getWorldHeight() - 20,  length * level.gg.getHealth() / Constants.GG_HP , width);
        renderer.end();
    }
}
