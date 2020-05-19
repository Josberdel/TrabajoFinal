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

    int Contador_tablas;
    //private Array<Rey> reyBlanco;
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
        /*batch.begin();
        batch.draw(texturaTablero, 0,  Gdx.graphics.getHeight()-texturaTablero.getHeight());
        batch.end();*/
        cargarTableroInicial();
        Gdx.input.setInputProcessor(this);
      /* for(i=0;i<8;i++) {
            matriz[1][i] = new Peon(true,new Vector2(x + i* texturaCasillaNegra.getWidth(),y+1* texturaCasillaNegra.getHeight()),peonBlanco,0);
            matriz[6][i] = new Peon(false,new Vector2(x+i* texturaCasillaNegra.getWidth(),y+6* texturaCasillaNegra.getHeight()),peonNegro,0);
            piezas.add(matriz[1][i]);
            piezas.add(matriz[6][i]);

            for(j=2;j<6;j++){
                matriz[j][i]=new Pieza();
            }
        }

        for(i=0;i<8;i++){
           piezas.add(matriz[0][i]);
           piezas.add(matriz[7][i]);
       }
       System.out.println(piezas.size());*/
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
                            pieza = new Casilla("CasillaB"+i+j,false, new Vector2(x, y),texturaCasillaBlanca);
                            break;
                        case "N":
                            pieza = new Casilla("CasillaN"+i+j,true, new Vector2(x, y),texturaCasillaNegra);
                            break;
                    }
                    matriz[i][j] = pieza;
                  //  System.out.println(pieza);
                    i += 1;
                    x += 60;
                }
                y -= 60;
                j -= 1;

                x = 0;
                i = 0;
                // Todos los casos
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
               // System.out.println(matriz[i][j].getTexture().toString());
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
                    if(rect.overlaps(matriz[i][j].rect));
                    {

                        System.out.println(i+j+matriz[i][j].getNombre());
                        break;
                    }
                }
            }
            return false;
        }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Rectangle rect = new Rectangle(screenX,screenY ,5,5 );
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Rectangle rect = new Rectangle(screenX,screenY ,5,5 );
        for (Pieza pieza : piezas) {
            if(rect.overlaps(pieza.rect));{


            }
        }

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
   /* private void pintarTablero() {
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
    }*/
    /*  public void MovRey(int i ,int j){

        if(matriz[i+1][j-1]==""){}
        if(matriz[i+1][j]==""){}
        if(matriz[i+1][j+1]==""){}
        if(matriz[i][j-1]==""){}
        if(matriz[i][j+1]==""){}
        if(matriz[i-1][j-1]==""){}
        if(matriz[i-1][j]==""){}
        if(matriz[i-1][j+1]==""){}

        }

    public void MovCaballo(int i ,int j){
        if(blanco==true){
            if(matriz[i+1][j-1]==""){}
            if(matriz[i+1][j]==""){}
            if(matriz[i+1][j+1]==""){}
            if(matriz[i][j-1]==""){}
            if(matriz[i][j+1]==""){}
            if(matriz[i-1][j-1]==""){}
            if(matriz[i-1][j]==""){}
            if(matriz[i-1][j+1]==""){}
        }
    }
    public void MovPeonBlanco(int i ,int j){
        if(matriz[i+1][j]==""){
           /* peoncontador ==0
            poner dos
           else

        }


        if(matriz[i+1][j-1].charAt(1)=='N'){

        }
        if(matriz[i+1][j+1].charAt(1)=='N'){

        }
    }
    public void MovPeonNegro(int i,int j){
        if(matriz[i-1][j]==""){
           /* peoncontador ==0
            poner dos
           else

        }


        if(matriz[i-1][j-1].charAt(1)=='B'){

        }
        if(matriz[i-1][j+1].charAt(1)=='B'){
        }
    }
    public void MovTorre(int i,int j){
        while(matriz[i][j+1]==""){
            j++;
        }
        while(matriz[i][j-1]==""){
            j--;
        }
        while(matriz[i+1][j]==""){
            i++;
        }
        while(matriz[i-1][j+1]==""){
            i--;
        }
    }
    public void MovAlfil(int i,int j){

        while(matriz[i+1][j+1]==""){
            j++;
            i++;
        }
        while(matriz[i+1][j-1]==""){
            j--;
            i++;

        }
        while(matriz[i-1][j+1]==""){
            i--;
            j++;
        }
        while(matriz[i-1][j-1]==""){
            i--;
            j--;
        }
    }
    public void MovDama(int i,int j){
     MovAlfil( i,j);
     MovTorre( i, j);
    }
*/