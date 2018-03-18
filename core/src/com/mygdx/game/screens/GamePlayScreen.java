package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGame;
import com.mygdx.game.controller.GameScreenControls;
import com.mygdx.game.entities.background.Background;
import com.mygdx.game.entities.particles.Light;
import com.mygdx.game.utilits.Assets;
import com.mygdx.game.utilits.ChaseCam;
import com.mygdx.game.utilits.Constants;
import com.mygdx.game.entities.missiles.LightMissile;
import com.mygdx.game.levels.Level;
import com.mygdx.game.utilits.Params;
import com.mygdx.game.utilits.Utils;

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
    private ColorAction colorAction;
    public Background background;


    private HudFPS hudFPS;

    public GamePlayScreen(MyGame game){
        this.game = game;
    }

    @Override
    public void show() {
        AssetManager am = new AssetManager();
        Assets.instance.init(am);

        batch = new SpriteBatch();
        level = new Level();
        background = new Background(3);
        batch = new SpriteBatch();
        renderer = new ShapeRenderer();
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        chaseCam = new ChaseCam(viewport.getCamera(), level.gg);
        colorAction = new ColorAction();
        Gdx.input.setInputProcessor(this);

        controls = new GameScreenControls(level);

        Gdx.input.setCatchBackKey(true);

        //fps
        hudFPS = new HudFPS();
    }

    @Override
    public void render(float delta) {
        chaseCam.update(delta);
        level.update(delta);
        background.update(new Vector2(chaseCam.getCamera().position.x, chaseCam.getCamera().position.y));

        viewport.apply();
        Gdx.gl.glClearColor(Params.background_color.r, Params.background_color.g, Params.background_color.b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(viewport.getCamera().combined);
        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.setAutoShapeType(true);
        batch.begin();
        renderer.begin();
        background.render(renderer);
        level.render(batch, renderer);
        batch.end();
        renderer.end();
        hudFPS.render(batch, delta); // fps

    }

    @Override
    public void resize(int width, int height) {
        hudFPS.viewport.update(width, height, true);
        viewport.update(width, height, true);

        Utils.aspect_ratio = width / height;

        //set camera position centered to gg when resized
        chaseCam.getCamera().position.x = level.gg.position.x;
        chaseCam.getCamera().position.y = level.gg.position.y;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
//        batch.dispose();
//        Assets.instance.dispose();
//        renderer.dispose();

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

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK){
            game.setMenueScreen();
        }
        return false;
    }
}
