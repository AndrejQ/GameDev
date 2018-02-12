package com.mygdx.game.utilits;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Asus123 on 10.02.2018.
 */

// TODO: 10.02.2018 Why commented version working incorrect? (Because this is magic)
public class ColorChanger{
    private Array<Color> colorStatesArray;
    private int index;
    private float allColorsDuration;
    private ColorAction colorAction;

    public ColorChanger() {
        colorStatesArray = new Array<Color>();
        colorAction = new ColorAction();
    }

    public boolean act(float delta) {
        if (colorAction.act(delta)){
            colorAction.restart();
            index = nextIndex();
            Gdx.app.log("index", "" + index);
            colorAction.setColor(new Color(colorStatesArray.get(index)));
            colorAction.setDuration(allColorsDuration / colorStatesArray.size);
            colorAction.setEndColor(new Color(colorStatesArray.get(nextIndex())));
        }
        return true;
    }

    private int nextIndex(){
        return index < colorStatesArray.size - 1 ? index + 1 : 0;
    }

    public void setAllColorsDuration(float allColorsDuration) {
        this.allColorsDuration = allColorsDuration;
    }

    public void addColorState(Color color){
        colorStatesArray.add(color);
        switch (colorStatesArray.size){
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

//public class ColorChanger extends ColorAction {
//    private Array<Color> colorStatesArray;
//    private Color currentColor;
//    private int index;
//    private float allColorsDuration;
//
//    public void setAllColorsDuration(float allColorsDuration) {
//        this.allColorsDuration = allColorsDuration;
//    }
//
//    public ColorChanger() {
//        colorStatesArray = new Array<Color>();
//        currentColor = new Color();
//    }
//
//    public void addColorState(Color color){
//        colorStatesArray.add(color);
//    }
//
//    @Override
//    protected void begin() {
//        if (getColor() == null) {
//            currentColor.set(colorStatesArray.get(0));
//            setColor(currentColor);
//            setDuration(allColorsDuration / colorStatesArray.size);
//            setEndColor(new Color(colorStatesArray.get(1)));
//            Gdx.app.log("", "" + colorStatesArray.toString());
//        }
//    }
//
//    @Override
//    protected void end() {
//        restart();
//        index = nextIndex();
//        Gdx.app.log("index", "" + index);
//        currentColor.set(colorStatesArray.get(index));
//        setColor(currentColor);
//        setDuration(allColorsDuration / colorStatesArray.size);
//        setEndColor(new Color(colorStatesArray.get(nextIndex())));
//    }
//
//    private int nextIndex(){
//        return index < colorStatesArray.size - 1 ? index + 1 : 0;
//    }
//
//    @Override
//    public boolean act(float delta) {
//        Gdx.app.log("", "" + getColor());
//        return super.act(delta);
//    }
//
//    //        @Override
////    public boolean act(float delta) {
////        if (super.act(delta)){
////            restart();
////            index = nextIndex();
////            Gdx.app.log("index", "" + index);
////            setColor(new Color(colorStatesArray.get(index)));
////            setDuration(allColorsDuration / colorStatesArray.size);
////            setEndColor(new Color(colorStatesArray.get(nextIndex())));
////        } else {Gdx.app.log("color index", "not complete" + index + " " + getTime());}
////        return true;
////    }
//
//    public void refresh(Color color, Color endColor){
//        index = 0;
//        colorStatesArray = new Array<Color>();
//        colorStatesArray.add(color);
//        colorStatesArray.add(endColor);
//        setColor(color);
//        setEndColor(endColor);
//    }
//}
