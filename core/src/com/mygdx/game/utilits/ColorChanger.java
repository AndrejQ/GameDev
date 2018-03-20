package com.mygdx.game.utilits;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

/**
 * Created by Asus123 on 10.02.2018.
 */

// TODO: 10.02.2018 Why commented version working incorrect? (Because this is magic)
public class ColorChanger{
    private ArrayList<Color> colorStatesArray;
    private int index;
    private float allColorsDuration;
    private ColorAction colorAction;

    public ColorChanger() {
        colorStatesArray = new ArrayList<Color>();
        colorAction = new ColorAction();
        index = -1; // crutch. For some reason indexes starts from 1 (not from 0).
    }

    public boolean act(float delta) {
        if (colorAction.act(delta)){
            colorAction.restart();
            index = nextIndex();
//            Gdx.app.log("index", "" + index);
            colorAction.setColor(new Color(colorStatesArray.get(index)));
            colorAction.setDuration(allColorsDuration / colorStatesArray.size());
            colorAction.setEndColor(new Color(colorStatesArray.get(nextIndex())));
        }
        return true;
    }

    private int nextIndex(){
        return index < colorStatesArray.size() - 1 ? index + 1 : 0;
    }

    public void setAllColorsDuration(float allColorsDuration) {
        this.allColorsDuration = allColorsDuration;
    }

    public void addColorState(Color color){
        colorStatesArray.add(color);
        switch (colorStatesArray.size()){
            case 1:
                colorAction.setColor(new Color(color));
                return;
            case 2:
                colorAction.setEndColor(color);
                return;
        }
    }

    public Color getColor(){
        return colorAction.getColor();
    }
}
