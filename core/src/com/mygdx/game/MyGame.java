package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.screens.GamePlayScreen;
import com.mygdx.game.screens.MenuScreen;

public class MyGame extends Game {
	@Override
	public void create() {
		setMenuScreen();
	}

	public void setMenuScreen(){
		setScreen(new MenuScreen(this));
	}

	public void setGamePlayScreen(){ setScreen(new GamePlayScreen(this)); }
}
