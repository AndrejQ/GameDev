package com.mygdx.game.utilits;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Asus123 on 12.12.2017.
 */

public class Constants {

    public static final float WORLD_SIZE = 200;
    public static final float FRICTION = 0.02f;
    public static final Color BACKGROUND_COLOR = Color.BLACK;
    public static final Rectangle WORLD_BOUNDS = new Rectangle(0, 0, 1.8f * WORLD_SIZE, WORLD_SIZE);

    //GG
    public static final float GG_RADIUS = 10f;
    public static final float GG_MASS = 5f;
    public static final float WEAPON_SPRAY = 20f;
    public static final float ENEMY_DISPOSE_DISTANCE = WORLD_SIZE * 2;
    public static final Color GG_COLOR = Color.YELLOW;
    public static final String YELLOW_GG_KEY = "yellow_gg";
    public static final String  GRAY_GG_KEY = "gray_gg";

    //Background
    public static final float BACKGROUND_CELL_SIZE = 60f;
    public static final int BACKGROUND_CHUNK_SIZE = 3;


    //simple enemy
    public static final float SIMPLE_ENEMY_ADD_VELOCITY = 25f;
    public static final float SIMPLE_ENEMY_RADIUS = 15f;
    public static final float SIMPLE_ENEMY_HEALTH = 20f;
    public static final float SIMPLE_ENEMY_TIME_BETWEEN_ADDING = 0.5f;
    public static final float SIMPLE_ENEMY_SPAWN_RATE_PER_SECOND = 0.2f;
    public static final float SIMPLE_ENEMY_MASS = 10f;
    public static final Color SIMPLE_ENEMY_COLOR = Color.BLACK;


    //Light missile
    public static final float LIGHT_MISSILE_MASS = 2f;
    public static final float LIGHT_MISSILE_DAMAGE = 5f;
    public static final float LIGHT_MISSILE_VELOCITY = 100f;
    public static final float LIGHT_MISSILE_LIFETIME = 1.2f;
    public static final float LIGHT_MISSILE_SPAWN_PER_SECOND = 5f;
    public static final float LIGHT_MISSILE_LENGTH = 6f;
    public static final float LIGHT_MISSILE_WIDTH = 6f;
    public static final float LIGHT_MISSILE_WOBBLE_FREQUENCY = 30f;
//    public static final float LIGHT_MISSILE_AERODYNAMICS= 0.5f; //0..1

    //Round missile
    public static final float ROUND_MISSILE_MASS = 1.f;
    public static final float ROUND_MISSILE_DAMAGE = 2f;
    public static final float ROUND_MISSILE_VELOCITY = 70f;
    public static final float ROUND_MISSILE_LIFETIME = 5f;
    public static final float ROUND_MISSILE_SPAWN_PER_SECOND = 20f;
    public static final float ROUND_MISSILE_RADIUS = 10f;
    public static final float ROUND_MISSILE_FRICTION = 0.5f;

    // Sparkle particle
    public static final float SPARKLE_PARTICLE_LIFE_TIME = 0.3f;
    public static final float SPARKLE_PARTICLE_SIZE = 4f;
    public static final float SPARKLE_PARTICLE_FRICTION = 0.1f;
    public static final int SPARKLE_PARTICLE_NUMBER = 10;
    public static final float SPARKLE_PARTICLE_START_VELOCITY = 100f;

    //Triangle particle
    public static final float TRIANGLE_PARTICLE_LIFE_TIME = 0.3f;
    public static final float TRIANGLE_PARTICLE_SIZE = 6f;
    public static final float TRIANGLE_PARTICLE_FRICTION = 0.1f;
    public static final float TRIANGLE_PARTICLE_DENSITY = 0.2f;
    public static final float TRIANGLE_PARTICLE_START_VELOCITY = 0;


    //Round particle
    public static final float ROUND_PARTICLE_LIFE_TIME = 0.5f;
    public static final float ROUND_PARTICLE_SIZE = ROUND_MISSILE_RADIUS;
    public static final float ROUND_PARTICLE_FRICTION = 0.1f;
    public static final float ROUND_PARTICLE_SPAWN_PER_SECOND = 0f;
    public static final float ROUND_PARTICLE_START_VELOCITY = 0;
    public static final Color[] ROUND_PARTICLE_STANDART_COLORS = new Color[]{Color.WHITE, Color.GRAY, Color.BLACK};


    // light
    public static final float LIGHT_RADIUS = 3 * LIGHT_MISSILE_LENGTH;
    public static final float LIGHT_LIFETIME = LIGHT_MISSILE_LIFETIME;


    // Assets
    public static final float STAR_BLINK_DURATION = 1f;
    public static final float STAR_LIFETIME = 5f;
    public static final float STAR_RADIUS = 5f;
    public static final int STAR_COUNT = 200;
    public static final float STAR_GENERATE_PER_SECOND = STAR_COUNT / STAR_LIFETIME;
    public static final String TEXTURE_ATLAS = "images/myGameTest.pack.atlas";

}