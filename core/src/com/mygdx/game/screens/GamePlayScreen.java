package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGame;
import com.mygdx.game.Constants;
import com.mygdx.game.levels.Level;

/**
 * Created by Asus123 on 12.12.2017.
 */

public class GamePlayScreen extends InputAdapter implements Screen {

    Level level;
    SpriteBatch batch;
    Viewport viewport;
    MyGame game;
    ShapeRenderer renderer;

    public GamePlayScreen(MyGame game){
        this.game = game;
    }

    @Override
    public void show() {
        level = new Level();
        batch = new SpriteBatch();
        renderer = new ShapeRenderer();
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        level.update(delta);

        viewport.apply();
        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r, Constants.BACKGROUND_COLOR.g, Constants.BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(viewport.getCamera().combined);
        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.setAutoShapeType(true);
        renderer.setColor(Color.BLACK);

        batch.begin();
        batch.end();
        renderer.begin();
        level.render(renderer);
        renderer.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
    }

    @Override
    public void dispose() {
        batch.dispose();
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 tapPosition = viewport.unproject(new Vector2(screenX, screenY));
        level.gg.velocity.add(level.gg.velocity.x - tapPosition.x,
                level.gg.velocity.y - tapPosition.y);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return super.touchUp(screenX, screenY, pointer, button);
    }
}
