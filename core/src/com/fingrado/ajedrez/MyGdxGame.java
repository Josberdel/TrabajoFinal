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

	int i=0;
	int j=0;
	int Contador_tablas;
	private Array<Rey> reyBlanco;
	private ArrayList<Peon>peonsBlancos;
    private ArrayList<Peon>peonsNegros;
	String matriz[][] = new String[8][8];
	Texture tablero[][] = new Texture[8][8];
	private Texture blanca= new Texture("piezasStauton/blanca 1.png");
	private Texture negra= new Texture("piezasStauton/negra.PNG");

	float aux=0;

	@Override
	public void create () {
		batch = new SpriteBatch();
		pintarTablero();
		/*for(i=0;i<17;i++){
			if(i<8){

				peonsBlancos.add(new Peon(true,new Vector2(aux,0), new Texture("imagenespersonajes/imagen1.PNG"),0);
				aux++;
			}else{
				peonsNegros.add(new Peon(true,new Vector2(aux,0), new Texture("imagenespersonajes/imagen1.PNG"),0);
			}
			aux++;

		}*/
	}

	@Override
	public void render () {
		pintar();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		//img.dispose();
	}
	private void pintarTablero(){
		int x=40;
		int y=40;
		for(i=0;i<8;i++){
			for(j=0;j<8;j++){
				System.out.println(i +" "+j);
				if((i+j)%2==0){
					batch.draw(negra,x+j*negra.getWidth(),y+i*negra.getHeight());
				}
				else
					batch.draw(blanca,x+j*negra.getWidth(),y+i*negra.getHeight());
			}
		}
	}
	private void pintar() {

	}
}
