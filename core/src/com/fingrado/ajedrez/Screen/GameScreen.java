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

public class GameScreen implements Screen, InputProcessor {
    SpriteBatch batch;
    int i = 0;
    int j = 0;
    int pos = 0;
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
    private Texture torreBlanca = new Texture("piezasStauton/torreBlanca.PNG");
    private Texture torreNegra = new Texture("piezasStauton/torreNegra.PNG");
    private Texture caballoBlanco = new Texture("piezasStauton/caballoBlanco.PNG");
    private Texture caballoNegro = new Texture("piezasStauton/caballoNegro.PNG");
    private Texture alfilBlanco = new Texture("piezasStauton/alfinBlanco.PNG");
    private Texture alfilNegro = new Texture("piezasStauton/alfilNegro.PNG");
    private Texture damaBlanca = new Texture("piezasStauton/damaBlanca.PNG");
    private Texture damaNegra = new Texture("piezasStauton/damaNegra.PNG");
    private Texture reyBlanco = new Texture("piezasStauton/reyBlanco.PNG");
    private Texture reyNegro = new Texture("piezasStauton/reyNegro.PNG");
    private Texture texturaMover = new Texture("piezasStauton/mover.PNG");



    @Override
    public void show() {
        batch = new SpriteBatch();
        piezas = new ArrayList<>();
       posibles= new ArrayList<>();
       cargarTableroInicial();
        Gdx.input.setInputProcessor(this);

    }

    private void cargarTableroInicial() {

        int x = 0;
        int y = Gdx.graphics.getHeight() - 60;
        int i = 0;
        int j = 7;
        FileHandle file = Gdx.files.internal("Posiciones/Alfil.txt");
        String levelInfo = file.readString();
        // String [] filas = levelInfo.split("\n" );
        Pieza pieza = null;

        // for (String fila : filas){
        String[] casillas = levelInfo.split(",");
        for (String casilla : casillas) {
            ayuda++;
            switch (casilla) {
                case "TN":
                    pieza = new Torre("Torre", false, new Vector2(x, y), torreNegra);
                    break;
                case "CN":
                    pieza = new Caballo("Caballo" + i + j, false, new Vector2(x, y), caballoNegro);
                    break;
                case "AN":
                    pieza = new Alfil("Alfil", false, new Vector2(x, y), alfilNegro);
                    break;
                case "DN":
                    pieza = new Dama("Dama", false, new Vector2(x, y), damaNegra);
                    break;
                case "RN":
                    pieza = new Rey("Rey", false, new Vector2(x, y), reyNegro);
                    break;
                case "PN":
                    pieza = new Peon("PeonN", false, new Vector2(x, y), peonNegro);
                    break;
                case "TB":
                    pieza = new Torre("Torre" , true, new Vector2(x, y), torreBlanca);
                    break;
                case "CB":
                    pieza = new Caballo("Caballo", true, new Vector2(x, y), caballoBlanco);
                    break;
                case "AB":
                    pieza = new Alfil("Alfil" , true, new Vector2(x, y), alfilBlanco);
                    break;
                case "DB":
                    pieza = new Dama("Dama", true, new Vector2(x, y), damaBlanca);
                    break;
                case "RB":
                    pieza = new Rey("Rey", true, new Vector2(x, y), reyBlanco);
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

    @Override
    public void render(float delta) {

        pintar();
    }

    private void pintar() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(texturaTablero, 0, 0);
        for (i = 0; i < 8; i++) {
            for (j = 0; j < 8; j++) {
                matriz[i][j].pintar(batch);
            }

        }
        batch.end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Rectangle rect = new Rectangle(screenX, screenY, 10, 10);
        for (i = 0; i < 8; i++) {
            for (j = 0; j < 8; j++) {
                if (rect.overlaps(matriz[i][j].rect)) {
                    j = 7 - j;
                    if (cont == null && !matriz[i][j].getNombre().equals("Casilla")) {
                        if(turno==matriz[i][j].isColor()){
                            cont = matriz[i][j];
                            if ((i + j) % 2 == 0)
                                matriz[i][j] = new Casilla("Casilla" + i + j, false, new Vector2(cont.getPosicion().x, cont.getPosicion().y), texturaCasillaNegra);
                            else
                                matriz[i][j] = new Casilla("Casilla" + i + j, false, new Vector2(cont.getPosicion().x, cont.getPosicion().y), texturaCasillaBlanca);
                            System.out.println("Pieza seleccionada " + cont.getNombre());
                            switch (cont.getNombre()) {
                                case "Rey":
                                    moverRey(cont, i, j);
                                    break;
                                case "Dama":
                                    moverTorre(cont, i, j);
                                    moverAlfil(cont, i, j);
                                    break;
                                case "Caballo":
                                    moverCaballo(cont, i, j);
                                    break;
                                case "Alfil":
                                    moverAlfil(cont, i, j);
                                    break;
                                case "Torre":
                                    moverTorre(cont, i, j);
                                    break;
                                case "PeonN":
                                    moverPeonN(cont, i, j);
                                    break;
                                case "PeonB":
                                    moverPeonB(cont, i, j);
                                    break;

                            }
                            break;
                        }
                        else
                            System.out.println("turno rival");
                    } else {
                        System.out.println(cont);
                        System.out.println(posibles);
                        try {
                            if (posibles.contains(String.valueOf(i) + String.valueOf(j))) {
                                cont.setPosicion(new Vector2(matriz[i][j].getPosicion().x, matriz[i][j].getPosicion().y));
                                matriz[i][j] = cont;
                                System.out.println(i + j + matriz[i][j].getNombre());

                                if(!posibles.get(posibles.size()-1).equals((String.valueOf(i)+String.valueOf(j)))){
                                    turno= !turno;
                                    cont.setCont(cont.getCont()-1);
                                    System.out.println(posibles.get(posibles.size()-1)+" "+String.valueOf(i)+String.valueOf(j));
                                }
                                cont = null;
                                posibles.clear();

                            } else {
                                System.out.println("movimiento invalido ");
                            }
                        } catch (NullPointerException e) {
                            System.out.println("No tienes ninguna pieza seleccionada");
                        }

                    }
                    break;
                }
            }
        }
        return false;
    }
    public void  moverPeonB(Pieza peon, int i,int j){

        if(matriz[i][j+1].getNombre().startsWith("Casil")) {
            posibles.add(i + String.valueOf(j + 1));
            if (peon.getCont() == 0 && matriz[i][j + 2].getNombre().startsWith("Casilla")) {
                posibles.add(i + String.valueOf(j + 2));
            }
        }

        try{
            if(matriz[i+1][j+1].isColor()!=peon.isColor()&&matriz[i+1][j+1].getNombre()!="Casilla"){
                posibles.add(String.valueOf(i+1)+String.valueOf(j+1));
            }
        }catch (IndexOutOfBoundsException e){

        }
        try{
            if(matriz[i+1][j-1].isColor()!=peon.isColor()&&matriz[i-1][j+1].getNombre()!="Casilla"){
                posibles.add(String.valueOf(i-1)+String.valueOf(j+1));
            }
        }catch (IndexOutOfBoundsException e){

        }
        posibles.add(String.valueOf(i)+String.valueOf(j));
        peon.setCont(peon.getCont()+1);
    }
    public void moverPeonN(Pieza peon, int i,int j){
        System.out.println("peon negro"+i+""+j);
        if(matriz[i][j-1].getNombre().startsWith("Casil")) {
            posibles.add(i + String.valueOf(j - 1));
            if (peon.getCont() == 0 && matriz[i][j - 2].getNombre().startsWith("Casilla")) {
                posibles.add(i + String.valueOf(j - 2));
            }
        }
        try{
            if(matriz[i+1][j+1].isColor()!=peon.isColor()&&matriz[i+1][j-1].getNombre()!="Casilla"){
                posibles.add(String.valueOf(i+1)+String.valueOf(j-1));
            }
        }catch (IndexOutOfBoundsException e){

        }
        try {
            if (matriz[i + 1][j - 1].isColor() != peon.isColor() && matriz[i - 1][j - 1].getNombre() != "Casilla") {
                posibles.add(String.valueOf(i - 1) + String.valueOf(j - 1));
            }
        }catch(IndexOutOfBoundsException e){

        }
        posibles.add(String.valueOf(i)+String.valueOf(j));
        peon.setCont(peon.getCont()+1);
    }
    public void  moverTorre(Pieza torre, int i,int j){
        for(int a=j+1;a<8;a++){
            try{
                if(matriz[i][a].getNombre().equals("Casilla")){
                    posibles.add(String.valueOf(i)+String.valueOf(a));
                }
                else{
                    if(matriz[i][a].isColor()!=torre.isColor())
                        posibles.add(String.valueOf(i)+String.valueOf(a));
                    break;
                }
            }catch (IndexOutOfBoundsException e){
            }
        }
        for(int a=j-1;a>=0;a--){
            try{
                if(matriz[i][a].getNombre().equals("Casilla")){
                    posibles.add(String.valueOf(i)+String.valueOf(a));
                }
                else{
                    if(matriz[i][a].isColor()!=torre.isColor())
                        posibles.add(String.valueOf(i)+String.valueOf(a));
                    break;
                }
            }catch (IndexOutOfBoundsException e){
            }
        }
        for(int a=i+1;a<=8;a++){
            try {
                if (matriz[a][j].getNombre().equals("Casilla")) {
                    posibles.add(String.valueOf(a) + String.valueOf(j));
                } else {
                    if (matriz[a][j].isColor() != torre.isColor())
                    posibles.add(String.valueOf(a) + String.valueOf(j));
                    break;
                }
            }catch (IndexOutOfBoundsException e){
            }
        }
        for(int a=i-1;a>=0;a--){
            try {
                if (matriz[a][j].getNombre().equals("Casilla")) {
                    posibles.add(String.valueOf(a) + String.valueOf(j));
                } else {
                    if (matriz[a][j].isColor() != torre.isColor())
                        posibles.add(String.valueOf(a) + String.valueOf(j));
                    break;
                }
            }catch (IndexOutOfBoundsException e){
            }
            }
        posibles.add(String.valueOf(i)+String.valueOf(j));
        torre.setCont(torre.getCont()+1);
        System.out.println(posibles);
    }
    public void  moverRey(Pieza Rey ,int  i,int  j){
        for(int a=-1;a<2;a++){
            for (int b=-1;b<2;b++){
                try{
                posibles.add(String.valueOf(a) + String.valueOf(j));
                }catch (IndexOutOfBoundsException e){
                }
            }
        }
    }
    public void  moverAlfil(Pieza alfil,int i ,int j) {
        for (int a = 1; a < 8; a++) {
            try {
                if (matriz[i + a][j + a].getNombre().equals("Casilla")) {
                        posibles.add(String.valueOf(i + a) + String.valueOf(j + a));
                }
                else {
                    if (matriz[i + a][j + a].isColor() != alfil.isColor())
                        posibles.add(String.valueOf(i + a) + String.valueOf(j + a));
                    break;
                }
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }
        for (int a = 1; a < 8; a++) {
            try{
                if (matriz[i + a][j - a].getNombre().equals("Casilla")) {
                    posibles.add(String.valueOf(i + a) + String.valueOf(j - a));
                }
                else {
                    if (matriz[i + a][j - a].isColor() != alfil.isColor())
                        posibles.add(String.valueOf(i + a) + String.valueOf(j - a));
                    break;
                }
            } catch (IndexOutOfBoundsException e) {
            }
        }
        for (int a = 1; a < 8; a++) {
            try {
                if (matriz[i - a][j + a].getNombre().equals("Casilla")) {
                    posibles.add(String.valueOf(i - a) + String.valueOf(j + a));
                } else {
                    if (matriz[i - a][j + a].isColor() != alfil.isColor())
                        posibles.add(String.valueOf(i - a) + String.valueOf(j + a));
                    break;
                }
            } catch (IndexOutOfBoundsException e) {
            }
        }
        for (int a = 1; a < 8; a++) {
            try{
                if (matriz[i - a][j - a].getNombre().equals("Casilla")) {
                    posibles.add(String.valueOf(i - a) + String.valueOf(j - a));
                }
                else {
                    if (matriz[i - a][j - a].isColor() != alfil.isColor())
                        posibles.add(String.valueOf(i - a) + String.valueOf(j - a));
                    break;
                }
            }catch (IndexOutOfBoundsException e) {
            }
        }
        posibles.add(String.valueOf(i) + String.valueOf(j));
    }
    public void  moverCaballo(Pieza caballo, int  i,int  j){
        try{
            if (matriz[i +1 ][j +2 ].getNombre().equals("Casilla")||matriz[i +2 ][j +1 ].isColor() != caballo.isColor())
                posibles.add(String.valueOf(i +2) + String.valueOf(j +1 ));
        }catch (IndexOutOfBoundsException e) {
        }
        try{
            if (matriz[i +2][j +1 ].getNombre().equals("Casilla")||matriz[i +1 ][j +2 ].isColor() != caballo.isColor())
                posibles.add(String.valueOf(i+1) + String.valueOf(j+2));
        }catch (IndexOutOfBoundsException e) {
        }
        try{
            if (matriz[i+2 ][j -1 ].getNombre().equals("Casilla")||matriz[i +2 ][j +1 ].isColor() != caballo.isColor())
                posibles.add(String.valueOf(i +2) + String.valueOf(j +1 ));
        }catch (IndexOutOfBoundsException e) {
        }
        try{
            if (matriz[i +1 ][j -2 ].getNombre().equals("Casilla")||matriz[i +2 ][j +1 ].isColor() != caballo.isColor())
                posibles.add(String.valueOf(i+1) + String.valueOf(j+2));
        }catch (IndexOutOfBoundsException e) {
        }
        try{
            if (matriz[i -1][j-2].getNombre().equals("Casilla")||matriz[i +2 ][j +1 ].isColor() != caballo.isColor())
                posibles.add(String.valueOf(i +2) + String.valueOf(j +1 ));
        }catch (IndexOutOfBoundsException e) {
        }
        try{
            if (matriz[i -2][j -1].getNombre().equals("Casilla")||matriz[i +2 ][j +1 ].isColor() != caballo.isColor())
                posibles.add(String.valueOf(i+1) + String.valueOf(j+2));
        }catch (IndexOutOfBoundsException e) {
        }
        try{
            if (matriz[i -2 ][j +1 ].getNombre().equals("Casilla")||matriz[i +2 ][j +1 ].isColor() != caballo.isColor())
                posibles.add(String.valueOf(i +2) + String.valueOf(j +1 ));
        }catch (IndexOutOfBoundsException e) {
        }
        try{
            if (matriz[i -1 ][j +2 ].getNombre().equals("Casilla")||matriz[i +2 ][j +1 ].isColor() != caballo.isColor())
                posibles.add(String.valueOf(i+1) + String.valueOf(j+2));
        }catch (IndexOutOfBoundsException e) {
        }
        posibles.add(String.valueOf(i) + String.valueOf(j));
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


