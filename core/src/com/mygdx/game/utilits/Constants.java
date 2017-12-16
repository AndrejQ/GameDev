package com.mygdx.game.utilits;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Asus123 on 12.12.2017.
 */

public class Constants {

    public static final float WORLD_SIZE = 200;
    public static final float FRICTION = 0.02f;
    public static final Rectangle WORLD_BOUNDS = new Rectangle(0, 0, 1.5f * WORLD_SIZE, WORLD_SIZE);

    //GG
    public static final float GG_RADIUS = 10f;
    public static final float GG_MASS = 10f;

    //Light missile
    public static final float LIGHT_MISSILE_RADIUS = 3f;
    public static final float LIGHT_MISSILE_MASS = 1f;
    public static final float LIGHT_MISSILE_VELOCITY = 100f;
    public static final float LIGHT_MISSILE_LIFETIME = 5f;
    public static final float LIGHT_MISSILE_SPAWN_PER_SECOND= 15f;
//    public static final float LIGHT_MISSILE_AERODYNAMICS= 0.5f; //0..1

    public static final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;

    public static final String TEXTURE_ATLAS = "images/myGameTest.pack.atlas";

}