package com.mygdx.game.controller;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.levels.Level;

/**
 * Created by Asus123 on 16.12.2017.
 */

public class GameScreenControls {

    Level level;
    public int singlePointer;

    public GameScreenControls(Level level) {

        this.level = level;
    }

    //touch down
    public void tapControlling(Vector2 tapPosition, int pointer){
        if (singlePointer == 0){
            singlePointer = pointer;
            level.gg.isPlayerTouching = true;
            level.gg.tapPosition = tapPosition;
        }
    }

    //touch dragged
    public void tapDragging(Vector2 tapPosition, int pointer){
        if (pointer == singlePointer){
            level.gg.tapPosition = tapPosition;
        }
    }

    //touch up
    public void tapUp(Vector2 tapPosition, int pointer){
        singlePointer = 0;
        level.gg.isPlayerTouching = false;
    }


}
