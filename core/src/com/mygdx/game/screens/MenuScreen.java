package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGame;
import com.mygdx.game.utilits.ColorChanger;
import com.mygdx.game.utilits.Constants;

/**
 * Created by Asus123 on 14.12.2017.
 */

public class MenuScreen extends InputAdapter implements Screen {

    MyGame game;

    Viewport viewport;
    SpriteBatch batch;
    BitmapFont font;
    private ColorChanger colorChanger;

    public MenuScreen(MyGame game){
        this.game = game;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        game.setGamePlayScreen();

        return true;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        colorChanger = new ColorChanger();
        colorChanger.setAllColorsDuration(10);
        colorChanger.addColorState(Color.RED);
        colorChanger.addColorState(Color.BROWN);
        colorChanger.addColorState(Color.MAROON);
        colorChanger.addColorState(Color.FIREBRICK);

        viewport = new ExtendViewport(3 * Constants.WORLD_SIZE, 3 * Constants.WORLD_SIZE);
        font = new BitmapFont();
        font.getData().setScale(3);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        viewport.apply();
        colorChanger.act(delta);
        Gdx.gl.glClearColor(
                colorChanger.getColor().r,
                colorChanger.getColor().g,
                colorChanger.getColor().b,
                Constants.BACKGROUND_COLOR.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(viewport.getCamera().combined);


        batch.begin();
        font.draw(batch, "This is menu, tap anywhere you want",
                viewport.getWorldWidth()/2, viewport.getWorldHeight()/2,
                0, Align.center, false);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        batch.dispose();
        font.dispose();
    }
}
