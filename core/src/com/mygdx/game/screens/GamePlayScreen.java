package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGame;
import com.mygdx.game.controller.GameScreenControls;
import com.mygdx.game.entities.particles.Light;
import com.mygdx.game.utilits.Assets;
import com.mygdx.game.utilits.ChaseCam;
import com.mygdx.game.utilits.Constants;
import com.mygdx.game.entities.missiles.LightMissile;
import com.mygdx.game.levels.Level;

/**
 * Created by Asus123 on 12.12.2017.
 */

public class GamePlayScreen extends InputAdapter implements Screen {

    private Level level;
    private SpriteBatch batch;
    private Viewport viewport;
    private MyGame game;
    private ShapeRenderer renderer;
    private GameScreenControls controls;
    private ChaseCam chaseCam;

    public GamePlayScreen(MyGame game){
        this.game = game;
    }

    @Override
    public void show() {
        AssetManager am = new AssetManager();
        Assets.instance.init(am);

        batch = new SpriteBatch();
        level = new Level();
        batch = new SpriteBatch();
        renderer = new ShapeRenderer();
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        chaseCam = new ChaseCam(viewport.getCamera(), level.gg);
        Gdx.input.setInputProcessor(this);

        controls = new GameScreenControls(level);
    }

    @Override
    public void render(float delta) {
        chaseCam.update(delta);
        level.update(delta);

        viewport.apply();
        Gdx.gl.glClearColor(Constants.BACKGROUND_COLOR.r, Constants.BACKGROUND_COLOR.g, Constants.BACKGROUND_COLOR.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(viewport.getCamera().combined);
        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.setAutoShapeType(true);
        batch.begin();
        renderer.begin();
        level.render(batch, renderer);
        batch.end();
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

    // TODO: 21.12.2017 What does hide() method make?
    @Override
    public void hide() {
        batch.dispose();
        Assets.instance.dispose();
        renderer.dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        Assets.instance.dispose();
        renderer.dispose();
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 tapPosition = viewport.unproject(new Vector2(screenX, screenY));
        controls.tapControlling(tapPosition, pointer);

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector2 tapPosition = viewport.unproject(new Vector2(screenX, screenY));
        controls.tapDragging(tapPosition, pointer);

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector2 tapPosition = viewport.unproject(new Vector2(screenX, screenY));
        controls.tapUp(tapPosition, pointer);
        return true;
    }
}
