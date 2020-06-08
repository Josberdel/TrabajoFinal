package com.fingrado.ajedrez.Screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.fingrado.ajedrez.*;

import java.util.ArrayList;

public class BatallaPeones implements Screen, InputProcessor {
    private BitmapFont font = new BitmapFont(Gdx.files.internal("ui/defualt.fnt"));
    private BitmapFont font2 = new BitmapFont(Gdx.files.internal("ui/defualt.fnt"));
    private BitmapFont font3 = new BitmapFont(Gdx.files.internal("ui/defualt.fnt"));
    private BitmapFont font4= new BitmapFont(Gdx.files.internal("ui/defualt.fnt"));
    SpriteBatch batch;
    int i = 0;
    int j = 0;
    Pieza cont = null;
    boolean turno=true;
    int Contador_tablas;
    int ayuda = 0;
    private ArrayList<Peon> peonsBlancos;
    private ArrayList<Peon> peonsNegros;
    private ArrayList<String> posibles;
    Pieza matriz[][] = new Pieza[8][8];
    Texture tablero[][] = new Texture[8][8];
    private ArrayList<Pieza> piezas;
    private Texture texturaCasillaBlanca = new Texture("piezasStauton/BLANCAS.PNG");
    private Texture texturaTablero = new Texture("piezasStauton/tablerovacio.PNG");
    private Texture texturaCasillaNegra = new Texture("piezasStauton/NEGRAS.PNG");
    private Texture peonBlanco = new Texture("piezasStauton/peonBlanco.PNG");
    private Texture peonNegro = new Texture("piezasStauton/peonNegro.PNG");

    @Override
    public void show() {
        batch = new SpriteBatch();
        piezas = new ArrayList<>();
        posibles= new ArrayList<>();
        cargarTableroInicial();
        Gdx.input.setInputProcessor(this);
    }
    @Override
    public void render(float delta) {
        pintar();
        actualizar();
    }

    private void actualizar() {
        comprobarTeclado();
        comprobarCoronar();
    }

    private void comprobarTeclado() {
        if (Gdx.input.isKeyPressed(Input.Keys.R))
            cargarTableroInicial();
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            ((Game)Gdx.app.getApplicationListener()).setScreen(new PantallaMenuPrincipal());


    }


    private void comprobarCoronar() {
        batch.begin();
        for(int i=0;i<8;i++){
            if(matriz[i][0].getNombre()=="PeonN"){
                font2.draw(batch,"ganan las negras ",Gdx.graphics.getWidth()-150,120);
            }
        }
        for(i=0;i<8;i++){
            if(matriz[i][7].getNombre()=="PeonB"){
                font2.draw(batch,"ganan las blancas",Gdx.graphics.getWidth()-150,120);
            }
        }
        batch.end();
    }


    private void cargarTableroInicial() {
        turno=true;
        int x = 0;
        int y = Gdx.graphics.getHeight() - 60;
        int i = 0;
        int j = 7;
        FileHandle file = Gdx.files.internal("Posiciones/peon.txt");
        String levelInfo = file.readString();
        Pieza pieza = null;

        // for (String fila : filas){
        String[] casillas = levelInfo.split(",");
        for (String casilla : casillas) {
            ayuda++;
            switch (casilla) {
                case "PN":
                    pieza = new Peon("PeonN", false, new Vector2(x, y), peonNegro);
                    break;
                case "PB":
                    pieza = new Peon("PeonB", true, new Vector2(x, y), peonBlanco);
                    break;
                case "B":
                    pieza = new Casilla("Casilla" , false, new Vector2(x, y), texturaCasillaBlanca);
                    break;
                case "N":
                    pieza = new Casilla("Casilla" , true, new Vector2(x, y), texturaCasillaNegra);
                    break;
            }
            System.out.println(pieza.getNombre());
            matriz[i][j] = pieza;

            i += 1;
            x += 60;
            // }
            if (ayuda % 8 == 0) {
                y -= 60;
                j -= 1;
                x = 0;
                i = 0;
            }
        }
    }
    private void pintar() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font4.draw(batch,"para ganar lleva uno de tus peones al otro lado del tablero ",500,240);
        batch.draw(texturaTablero, 0, 0);
        for (i = 0; i < 8; i++) {
            for (j = 0; j < 8; j++) {
                matriz[i][j].pintar(batch);
            }
        }
        font3.draw(batch," r para reiniciar o Esc para salir",500,180);
        if(cont==null){
            font2.draw(batch, "No has seleccionado ninguna pieza", 500, 120);
        }else
            font2.draw(batch, "Las posiciones a la que puede ir el " + cont.getNombre() + " son " + posibles, 500, 120);
        font.setColor(Color.RED);
        if(turno==true)
            font.draw(batch,"turno de las blancas",500,60);
        else
            font.draw(batch,"turno de las negras",500,60);
        batch.end();

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
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Rectangle rect = new Rectangle(screenX, screenY, 10, 10);
        for (i = 0; i < 8; i++) {
            for (j = 7; j > -1; j--) {

                if (rect.overlaps(matriz[i][j].rect)) {
                    j=7-j;
                    if (cont == null && !matriz[i][j].getNombre().equals("Casilla")) {
                        if (turno == matriz[i][j].isColor()) {
                            cont = matriz[i][j];
                            if ((i + j) % 2 == 0){
                                matriz[i][j] = new Casilla("Casilla" , false, new Vector2(cont.getPosicion().x, cont.getPosicion().y), texturaCasillaNegra);
                            }
                            else{
                                matriz[i][j] = new Casilla("Casilla", true, new Vector2(cont.getPosicion().x, cont.getPosicion().y), texturaCasillaBlanca);
                            }
                            switch (cont.getNombre()) {
                                case "PeonN":
                                    moverPeonN(cont, i, j);
                                    break;
                                case "PeonB":
                                    moverPeonB(cont, i, j);
                                    break;

                            }
                            break;
                        } else
                            System.out.println("turno rival");
                    } else {
                        try {
                            if (posibles.contains(String.valueOf(i) + String.valueOf(j))) {
                                cont.setPosicion(new Vector2(matriz[i][j].getPosicion().x, matriz[i][j].getPosicion().y));
                                matriz[i][j] = cont;
                                matriz[i][j].rect= new Rectangle(matriz[i][j].getPosicion().x, matriz[i][j].getPosicion().y,  matriz[i][j].getTexture().getWidth(), matriz[i][j].getTexture().getWidth());
                                if (posibles.get(posibles.size() - 1).equals((String.valueOf(i) + String.valueOf(j)))){
                                    if(cont.getCont()>0){
                                        cont.setCont(cont.getCont() - 1);
                                    }
                                }
                                else
                                    turno = !turno;
                                cont = null;
                                posibles.clear();

                            } else {
                                System.out.println("movimiento invalido ");
                                System.out.println(matriz[i][j].rect);

                            }
                        } catch (NullPointerException e) {
                            System.out.println("No tienes ninguna pieza seleccionada");

                        }catch (Exception e){
                            System.out.println(e);
                        }
                    }
                    break;
                }

            }
        }
        return false;
    }

    public void  moverPeonB(Pieza peon, int i,int j){
        if(matriz[i][j+1].getNombre().equals("Casilla")) {
            posibles.add(i + String.valueOf(j + 1));
            if (peon.getCont() == 0 && matriz[i][j + 2].getNombre().equals("Casilla")) {
                posibles.add(i + String.valueOf(j + 2));
            }
        }
        try{
            if(matriz[i+1][j+1].isColor()!=peon.isColor()&&!matriz[i+1][j+1].getNombre().equals("Casilla")){
                posibles.add(String.valueOf(i+1)+String.valueOf(j+1));
            }
        }catch (IndexOutOfBoundsException e){

        }
        try{
            if(matriz[i-1][j+1].isColor()!=peon.isColor()&&!matriz[i-1][j+1].getNombre().equals("Casilla")){
                posibles.add(String.valueOf(i-1)+String.valueOf(j+1));
            }
        }catch (IndexOutOfBoundsException e){

        }
        posibles.add(String.valueOf(i)+String.valueOf(j));
        peon.setCont(peon.getCont()+1);
    }

    public void moverPeonN(Pieza peon, int i,int j){
        if(matriz[i][j-1].getNombre().equals("Casilla")) {
            posibles.add(i + String.valueOf(j - 1));
            if (peon.getCont() == 0 && matriz[i][j - 2].getNombre().equals("Casilla")) {
                posibles.add(i + String.valueOf(j - 2));
            }
        }
        try{
            if(matriz[i+1][j-1].isColor()!=peon.isColor()&&!matriz[i+1][j-1].getNombre().equals("Casilla")){
                posibles.add(String.valueOf(i+1)+String.valueOf(j-1));
            }
        }catch (IndexOutOfBoundsException e){

        }
        try {
            if (matriz[i - 1][j - 1].isColor() != peon.isColor() && !matriz[i - 1][j - 1].getNombre().equals("Casilla")) {
                posibles.add(String.valueOf(i - 1) + String.valueOf(j - 1));
            }
        }catch(IndexOutOfBoundsException e){

        }
        posibles.add(String.valueOf(i)+String.valueOf(j));
        peon.setCont(peon.getCont()+1);
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

    @Override
    public void dispose() {

    }
}
