package com.fingrado.ajedrez;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	int i=0;
	int Contador_tablas;
	private Array<Rey> reyBlanco;
	private ArrayList<Peon>peonsBlancos;
    private ArrayList<Peon>peonsNegros;
	float aux=0;
	@Override
	public void create () {
		batch = new SpriteBatch();

		for(i=0;i<17;i++){
			if(i<8){

				peonsBlancos.add(new Peon(true,new Vector2(aux,0), new Texture("imagenespersonajes/imagen1.PNG"),0);
				aux++;
			}else{
				peonsNegros.add(new Peon(true,new Vector2(aux,0), new Texture("imagenespersonajes/imagen1.PNG"),0);
			}
			aux++;

		}
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
