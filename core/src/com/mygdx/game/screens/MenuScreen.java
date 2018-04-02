package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGame;
import com.mygdx.game.entities.GrayGG;
import com.mygdx.game.entities.YellowGG;
import com.mygdx.game.levels.Level;
import com.mygdx.game.levels.MenuLevel;
import com.mygdx.game.utilits.ChaseCam;
import com.mygdx.game.utilits.ColorChanger;
import com.mygdx.game.utilits.Constants;
import com.mygdx.game.utilits.Utils;

/**
 * Created by Asus123 on 14.12.2017.
 */

public class MenuScreen extends InputAdapter implements Screen {

    MyGame game;
    MenuLevel menuLevel;
    Viewport viewport;
    HudMenu hudMenu;
    SpriteBatch batch;
    ShapeRenderer renderer;
    ChaseCam chaseCam;
    BitmapFont font;
    private ColorChanger colorChanger;

    public MenuScreen(MyGame game){
        this.game = game;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 tapPosition = viewport.unproject(new Vector2(screenX, screenY));
        if (new Circle(menuLevel.yellowGG.position, menuLevel.yellowGG.radius)
                .contains(tapPosition)){
            game.setGamePlayScreen(Constants.YELLOW_GG_KEY);
        } else if (new Circle(menuLevel.grayGG.position, menuLevel.grayGG.radius)
                .contains(tapPosition)){
            game.setGamePlayScreen(Constants.GRAY_GG_KEY);
        }
        return true;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        renderer = new ShapeRenderer();
        viewport = new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE);
        hudMenu = new HudMenu();
        menuLevel = new MenuLevel();
        chaseCam = new ChaseCam(viewport.getCamera(), menuLevel.gg);
        chaseCam.fixChase(true);

        colorChanger = new ColorChanger();
        colorChanger.setAllColorsDuration(10);
        colorChanger.addColorState(Color.BROWN);
        colorChanger.addColorState(Color.MAROON);
        colorChanger.addColorState(Color.FIREBRICK);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        chaseCam.update(delta);
        viewport.apply();
        colorChanger.act(delta);
        Gdx.gl.glClearColor(
                colorChanger.getColor().r,
                colorChanger.getColor().g,
                colorChanger.getColor().b,
                Constants.BACKGROUND_COLOR.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        menuLevel.update(delta);
        menuLevel.setInstantCameraPosition(chaseCam.getCamera().position.x, chaseCam.getCamera().position.y);

        batch.begin();
        hudMenu.render(batch, delta);
        batch.end();

        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.setAutoShapeType(true);
        renderer.begin();
        menuLevel.render(batch, renderer);
        renderer.end();

    }

    @Override
    public void resize(int width, int height) {
        hudMenu.viewport.update(width, height, true);
        viewport.update(width, height, true);
        Utils.aspect_ratio = width / height;
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
        hudMenu.font.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.BACK){
            Gdx.app.exit();
        }
        return false;
    }
}
