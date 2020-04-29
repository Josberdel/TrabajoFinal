package com.fingrado.ajedrez.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.fingrado.ajedrez.Peon;
import com.fingrado.ajedrez.Rey;

import java.util.ArrayList;

public class GameScreen implements Screen {
    SpriteBatch batch;

    int i=0;
    int j=0;
    int Contador_tablas;
    private Array<Rey> reyBlanco;
    private ArrayList<Peon> peonsBlancos;
    private ArrayList<Peon>peonsNegros;
    String matriz[][] = new String[8][8];
    Texture tablero[][] = new Texture[8][8];
    private Texture blanca = new Texture("piezasStauton/BLANCAS.PNG");
    private Texture negra= new Texture("piezasStauton/NEGRAS.PNG");

    float aux=0;
    @Override
    public void show() {
        batch = new SpriteBatch();

    }

    private void pintarTablero() {
        batch.begin();
        int x=Gdx.graphics.getWidth()/2-negra.getWidth()*4;
        int y=Gdx.graphics.getHeight()/2-negra.getHeight()*4;
        for(i=0;i<8;i++){
            for(j=0;j<8;j++){

                if((i+j)%2==0){
                    batch.draw(negra,x+j*negra.getWidth(),y+i*negra.getHeight());
                }
                else
                    batch.draw(blanca,x+j*negra.getWidth(),y+i*negra.getHeight());
            }
        }
        batch.end();
    }

    @Override
    public void render(float delta) {
        pintarTablero();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
    private void pintar() {

    }
}
