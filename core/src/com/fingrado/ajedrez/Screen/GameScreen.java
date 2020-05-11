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
        for(i=0;i<8;i++) {
            matriz[1][i] = "B";
            matriz[6][i] = "N";
            for(j=2;j<6;j++){
                matriz[j][i]="";
            }

        }
        matriz[0][0]="TB";
        matriz[0][1]="CB";
        matriz[0][2]="AB";
        matriz[0][3]="DB";
        matriz[0][4]="RB";
        matriz[0][5]="AB";
        matriz[0][6]="CB";
        matriz[0][7]="TB";
        matriz[7][0]="TN";
        matriz[7][1]="CN";
        matriz[7][2]="AN";
        matriz[7][3]="DN";
        matriz[7][4]="RN";
        matriz[7][5]="AN";
        matriz[7][6]="CN";
        matriz[7][7]="TN";
      /*  for(i=0;i<8;i++){
            for(j=0;j<8;j++){
                System.out.println("posicion "+i+ 1+","+j+1 +matriz[i][j]);
            }
        }*/

    }

    private void pintarTablero() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
                switch (matriz[i][j]) {
                    case "TB":
                        batch.draw(torreBlanca, x + j * negra.getWidth(), y + i * negra.getHeight());
                        break;
                    case "CB":
                        batch.draw(caballoBlanco, x + j * negra.getWidth(), y + i * negra.getHeight());
                        break;
                    case "AB":
                        batch.draw(alfilBlanco, x + j * negra.getWidth(), y + i * negra.getHeight());
                        break;
                    case "DB":
                        batch.draw(damaBlanca, x + j * negra.getWidth(), y + i * negra.getHeight());
                        break;
                    case "RB":
                        batch.draw(reyBlanco, x + j * negra.getWidth(), y + i * negra.getHeight());
                        break;
                    case "B":
                        batch.draw(peonBlanco,x+j*negra.getWidth(),y+i*negra.getHeight());
                        break;
                    case "N":
                        batch.draw(peonNegro,x+j*negra.getWidth(),y+i*negra.getHeight());
                        break;
                    case "TN":
                        batch.draw(torreNegra,x+j*negra.getWidth()+5,y+i*negra.getHeight());
                        break;
                    case "CN":
                        batch.draw(caballoNegro,x+j*negra.getWidth(),y+i*negra.getHeight());
                        break;
                    case "AN":
                        batch.draw(alfilNegro,x+j*negra.getWidth(),y+i*negra.getHeight());
                        break;
                    case "DN":
                        batch.draw(damaNegra,x+j*negra.getWidth(),y+i*negra.getHeight());
                        break;
                    case "RN":
                        batch.draw(reyNegro,x+j*negra.getWidth(),y+i*negra.getHeight());
                        break;
                    case "":
                        if((i+j)%2==0){
                            batch.draw(negra,x+j*negra.getWidth(),y+i*negra.getHeight());

                        }
                        else
                            batch.draw(blanca,x+j*negra.getWidth(),y+i*negra.getHeight());
                        break;
                    default:
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
