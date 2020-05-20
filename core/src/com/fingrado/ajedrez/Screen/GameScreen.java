package com.fingrado.ajedrez.Screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.fingrado.ajedrez.*;

import java.util.ArrayList;

public class GameScreen implements Screen , InputProcessor {
    SpriteBatch batch;
    int i=0;
    int j=0;
    int pos=0;
    Pieza cont =null;
    int Contador_tablas;

    private ArrayList<Peon> peonsBlancos;
    private ArrayList<Peon>peonsNegros;
    Pieza matriz[][] = new Pieza[8][8];
    Texture tablero[][] = new Texture[8][8];
    private ArrayList<Pieza> piezas;
    private Texture texturaCasillaBlanca = new Texture("piezasStauton/BLANCAS.PNG");
    private Texture texturaTablero = new Texture("piezasStauton/tablerovacio.PNG");
    private Texture texturaCasillaNegra = new Texture("piezasStauton/NEGRAS.PNG");
    private Texture peonBlanco = new Texture("piezasStauton/peonBlanco.PNG");
    private Texture peonNegro= new Texture("piezasStauton/peonNegro.PNG");
    private Texture torreBlanca = new Texture("piezasStauton/torreBlanca.PNG");
    private Texture torreNegra= new Texture("piezasStauton/torreNegra.PNG");
    private Texture caballoBlanco = new Texture("piezasStauton/caballoBlanco.PNG");
    private Texture caballoNegro= new Texture("piezasStauton/caballoNegro.PNG");
    private Texture alfilBlanco = new Texture("piezasStauton/alfinBlanco.PNG");
    private Texture alfilNegro= new Texture("piezasStauton/alfilNegro.PNG");
    private Texture damaBlanca = new Texture("piezasStauton/damaBlanca.PNG");
    private Texture damaNegra= new Texture("piezasStauton/damaNegra.PNG");
    private Texture reyBlanco = new Texture("piezasStauton/reyBlanco.PNG");
    private Texture reyNegro= new Texture("piezasStauton/reyNegro.PNG");
    float aux=0;
    private boolean blanco = false;
    private boolean negro = false;
    int x = Gdx.graphics.getWidth()/2- texturaCasillaNegra.getWidth()*4;
    int y = Gdx.graphics.getHeight()/2- texturaCasillaNegra.getHeight()*4;

    @Override
    public void show() {
        batch = new SpriteBatch();
        piezas = new ArrayList<>();

        cargarTableroInicial();
        Gdx.input.setInputProcessor(this);

    }

    private void cargarTableroInicial() {
        int x = 0;
        int y = Gdx.graphics.getHeight()-60;
        int i = 0;
        int j = 7;
        FileHandle file = Gdx.files.internal("piezasStauton/tablero.txt");
        String levelInfo= file.readString();
        String  [] filas = levelInfo.split("\n" );
        Pieza pieza = null;
        for (String fila : filas){
            String[] casillas = fila.split(",");
            for (String casilla :casillas){
                switch (casilla){
                    case "TN":
                        pieza = new Torre("TorreN"+i+j,false, new Vector2(x, y),torreNegra);
                        break;
                    case "CN":
                        pieza = new Caballo("CaballoN"+i+j,false, new Vector2(x, y),caballoNegro);
                        break;
                    case "AN":
                        pieza = new Alfil("AlfilN"+i+j,false, new Vector2(x, y),alfilNegro);
                        break;
                    case "DN":
                        pieza = new Dama("DamaN",false, new Vector2(x, y),damaNegra);
                        break;
                    case "RN":
                        pieza = new Rey("ReyN",false, new Vector2(x, y),reyNegro);
                        break;
                    case "PN":
                        pieza = new Peon("PeonN"+i+j,false, new Vector2(x, y),peonNegro);
                        break;
                    case "TB":
                        pieza = new Torre("TorreB"+i+j,true, new Vector2(x, y),torreBlanca);
                        break;
                    case "CB":
                        pieza = new Caballo("CaballoB"+i+j,true, new Vector2(x, y),caballoBlanco);
                        break;
                    case "AB":
                        pieza = new Alfil("AlfilB"+i+j,true, new Vector2(x, y),alfilBlanco);
                        break;
                    case "DB":
                        pieza = new Dama("DamaB",true, new Vector2(x, y),damaBlanca);
                        break;
                    case "RB":
                        pieza = new Rey("ReyB",true, new Vector2(x, y),reyBlanco);
                        break;
                    case "PB":
                        pieza = new Peon("PeonB"+i+j,true, new Vector2(x, y),peonBlanco);
                        break;
                    case "B":
                        pieza = new Casilla("Casilla",false, new Vector2(x, y),texturaCasillaBlanca);
                        break;
                    case "N":
                        pieza = new Casilla("Casilla",true, new Vector2(x, y),texturaCasillaNegra);
                        break;
                    }
                matriz[i][j] = pieza;
                System.out.println("matriz "+i+" "+j+" "+matriz[i][j].getNombre());
                i += 1;
                x += 60;
            }
            y -= 60;
            j -= 1;
            x = 0;
            i = 0;
        }
    }
    @Override
    public void render(float delta){
        pintar();
    }

    private void pintar() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(texturaTablero, 0 , 0);
        for(i=0;i<8;i++){
            for(j=0;j<8;j++){
                matriz[i][j].pintar(batch);
            }
        }
        batch.end();
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Rectangle rect = new Rectangle(screenX,screenY ,5,5 );
        for(i=0;i<8;i++){
                for(j=0;j<8;j++){
                    if(rect.overlaps(matriz[i][j].rect))
                    {
                        System.out.println(i+""+j);
                        System.out.println(matriz[i][j].getNombre());
                        System.out.println(matriz[i][j].isColor());

                
                        break;
                    }
                }
            }
            return false;
        }
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
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
}