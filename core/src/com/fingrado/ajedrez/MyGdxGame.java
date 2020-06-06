package com.fingrado.ajedrez;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.fingrado.ajedrez.Screen.GameScreen;
import com.fingrado.ajedrez.Screen.PantallaMenuPrincipal;

import java.util.ArrayList;

public class MyGdxGame extends Game {


	@Override
	public void create () {
		((Game)Gdx.app.getApplicationListener()).setScreen(new PantallaMenuPrincipal());
	}
	@Override
	public void render () {
		super.render();
	}
	@Override
	public void dispose () {super.dispose();

	}

}
