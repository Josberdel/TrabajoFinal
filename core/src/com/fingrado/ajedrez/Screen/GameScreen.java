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
    //private Array<Rey> reyBlanco;
    private ArrayList<Peon> peonsBlancos;
    private ArrayList<Peon>peonsNegros;
    String matriz[][] = new String[8][8];
    Texture tablero[][] = new Texture[8][8];
    private Texture blanca = new Texture("piezasStauton/BLANCAS.PNG");
    private Texture negra= new Texture("piezasStauton/NEGRAS.PNG");
    private Texture peonBlanco = new Texture("piezasStauton/peonBlanco.PNG");
    private Texture peonNegro= new Texture("piezasStauton/peonNegro.PNG");

    private Texture torreBlanca = new Texture("piezasStauton/torreBlanca.PNG");
    private Texture torreNegra= new Texture("piezasStauton/torreNegra.PNG");
    private Texture caballoBlanco = new Texture("piezasStauton/caballoBlanco.PNG");
    private Texture caballoNegro= new Texture("piezasStauton/caballoNego.PNG");
    private Texture alfilBlanco = new Texture("piezasStauton/alfinBlanco.PNG");
    private Texture alfilNegro= new Texture("piezasStauton/alfilNegro.PNG");
    private Texture damaBlanca = new Texture("piezasStauton/damaBlanca.PNG");
    private Texture damaNegra= new Texture("piezasStauton/damaNegra.PNG");
    private Texture reyBlanco = new Texture("piezasStauton/reyBlanco.PNG");
    private Texture reyNegro= new Texture("piezasStauton/reyNegro.PNG");



    float aux=0;
    @Override
    public void show() {
        batch = new SpriteBatch();
        pintarTablero();

    }

    private void pintarTablero() {
        batch.begin();
        int x=Gdx.graphics.getWidth()/2-negra.getWidth()*4;
        int y=Gdx.graphics.getHeight()/2-negra.getHeight()*4;
        for(i=0;i<8;i++){
            for(j=0;j<8;j++){
                //colocar tablero
                if((i+j)%2==0){
                    batch.draw(negra,x+j*negra.getWidth(),y+i*negra.getHeight());

                }
                else
                    batch.draw(blanca,x+j*negra.getWidth(),y+i*negra.getHeight());

                switch (i) {
                    case 0:
                        switch(j) {
                            case 0:
                                batch.draw(torreBlanca, x + j * negra.getWidth(), y + i * negra.getHeight());
                                matriz[i][j] = "TB";
                                break;
                            case 1:
                                batch.draw(caballoBlanco, x + j * negra.getWidth(), y + i * negra.getHeight());
                                matriz[i][j] = "CB";
                                break;
                            case 2:
                                batch.draw(alfilBlanco, x + j * negra.getWidth(), y + i * negra.getHeight());
                                matriz[i][j] = "AB";
                                break;
                            case 3:
                                batch.draw(damaBlanca, x + j * negra.getWidth(), y + i * negra.getHeight());
                                matriz[i][j] = "DB";
                                break;
                            case 4:
                                batch.draw(reyBlanco, x + j * negra.getWidth(), y + i * negra.getHeight());
                                matriz[i][j] = "RB";
                                break;
                            case 5:
                                batch.draw(alfilBlanco, x + j * negra.getWidth(), y + i * negra.getHeight());
                                matriz[i][j] = "AB";
                                break;
                            case 6:
                                batch.draw(caballoBlanco, x + j * negra.getWidth(), y + i * negra.getHeight());
                                matriz[i][j] = "CB";
                                break;
                            case 7:
                                batch.draw(torreBlanca, x + j * negra.getWidth(), y + i * negra.getHeight());
                                matriz[i][j] = "TB";
                                break;
                        }
                        break;
                    case 1:
                        batch.draw(peonBlanco,x+j*negra.getWidth(),y+i*negra.getHeight());
                        matriz[i][j]="B";
                        break;
                    case 6:
                        batch.draw(peonNegro,x+j*negra.getWidth(),y+i*negra.getHeight());
                        matriz[i][j]="P";
                        break;
                    case 7:
                        switch(j){
                            case 0:
                                batch.draw(torreNegra,x+j*negra.getWidth()+5,y+i*negra.getHeight());
                                matriz[i][j]="TN";
                                break;
                            case 1:
                                batch.draw(caballoNegro,x+j*negra.getWidth(),y+i*negra.getHeight());
                                matriz[i][j]="CN";
                                break;
                            case 2:
                                batch.draw(alfilNegro,x+j*negra.getWidth(),y+i*negra.getHeight());
                                matriz[i][j]="AN";
                                break;
                            case 3:
                                batch.draw(damaNegra,x+j*negra.getWidth(),y+i*negra.getHeight());
                                matriz[i][j]="DM";
                                break;
                            case 4:
                                batch.draw(reyNegro,x+j*negra.getWidth(),y+i*negra.getHeight());
                                matriz[i][j]="RN";
                                break;
                            case 5:
                                batch.draw(alfilNegro,x+j*negra.getWidth(),y+i*negra.getHeight());
                                matriz[i][j]="AN";
                                break;
                            case 6:
                                batch.draw(caballoNegro,x+j*negra.getWidth(),y+i*negra.getHeight());
                                matriz[i][j]="CN";
                                break;
                            case 7:
                                batch.draw(torreNegra,x+j*negra.getWidth(),y+i*negra.getHeight());
                                matriz[i][j]="TN";
                                break;
                        }
                    break;
                    }
                }
            }
        batch.end();
    }

    @Override
    public void render(float delta){
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

    }
}
