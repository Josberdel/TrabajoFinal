package com.fingrado.ajedrez.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import com.fingrado.ajedrez.*;

import java.io.IOException;
import java.util.ArrayList;

public class GameScreen implements Screen, InputProcessor {
    private BitmapFont font = new BitmapFont(Gdx.files.internal("ui/defualt.fnt"));
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

    @Override
    public void show() {
        batch = new SpriteBatch();
        piezas = new ArrayList<>();
       posibles= new ArrayList<>();
       cargarTableroInicial();
       Gdx.input.setInputProcessor(this);

    }

    private void cargarTableroInicial() {
        turno=true;
        int x = 0;
        int y = Gdx.graphics.getHeight() - 60;
        int i = 0;
        int j = 7;
        FileHandle file = Gdx.files.internal("Posiciones/tablero.txt");
        String levelInfo = file.readString();
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
                    pieza = new Caballo("Caballo", false, new Vector2(x, y), caballoNegro);
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
        actualizar();

    }

    private void actualizar() {
        comprobarTeclado();
        comprobarCoronar();
    }

    private void comprobarCoronar() {
        for(int i=0;i<8;i++){
            if(matriz[i][0].getNombre()=="PeonN"){
                matriz[i][0]=new Dama("Dama", false, new Vector2(i*60, 0), damaNegra);
            }
        }
        for(i=0;i<8;i++){
            if(matriz[i][7].getNombre()=="PeonB"){
                matriz[i][7]=new Dama("Dama", true, new Vector2(i*60,420), damaBlanca);
            }
        }
    }

    private void comprobarTeclado() {
        if (Gdx.input.isKeyPressed(Input.Keys.R))
            cargarTableroInicial();
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

        font.setColor(Color.RED);
        if(turno==true)
            font.draw(batch,"turno de las blancas",Gdx.graphics.getWidth()-140,60);
        else
            font.draw(batch,"turno de las negras",Gdx.graphics.getWidth()-140,60);
        batch.end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Rectangle rect = new Rectangle(screenX, screenY, 10, 10);
        for (i = 0; i < 8; i++) {
            for (j = 7; j > -1; j--) {

                if (rect.overlaps(matriz[i][j].rect)) {
                    j=7-j;
                    if (cont == null && !matriz[i][j].getNombre().equals("Casilla")) {
                        System.out.println(i+" "+j+" dentro de equals");
                        if (turno == matriz[i][j].isColor()) {
                            cont = matriz[i][j];
                            if ((i + j) % 2 == 0){
                                matriz[i][j] = new Casilla("Casilla" , false, new Vector2(cont.getPosicion().x, cont.getPosicion().y), texturaCasillaNegra);
                            }
                            else{
                                matriz[i][j] = new Casilla("Casilla", true, new Vector2(cont.getPosicion().x, cont.getPosicion().y), texturaCasillaBlanca);
                            }
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
                        } else
                            System.out.println("turno rival");
                    } else {
                        try {
                            if (posibles.contains(String.valueOf(i) + String.valueOf(j))) {
                                cont.setPosicion(new Vector2(matriz[i][j].getPosicion().x, matriz[i][j].getPosicion().y));
                                System.out.println(matriz[i][j].getPosicion()+" "+i+" "+j);
                                matriz[i][j] = cont;
                                System.out.println(i +" "+ j + matriz[i][j].getNombre());
                                matriz[i][j].rect= new Rectangle(matriz[i][j].getPosicion().x, matriz[i][j].getPosicion().y,  matriz[i][j].getTexture().getWidth(), matriz[i][j].getTexture().getWidth());
                                System.out.println(i +" "+ j + matriz[i][j].rect);
                                if(cont.getNombre()=="Rey"&&Integer.parseInt(String.valueOf(posibles.get(posibles.size()-1).charAt(0)))+2==i){
                                    System.out.println(matriz[i][j].getNombre()+" "+matriz[i][j].getPosicion()+" "+matriz[i][j].rect+" "+matriz[i][j].getTexture());
                                    if ((7 + j) % 2 != 0) {
                                        System.out.println(matriz[i][j].getPosicion().x+" "+matriz[i][j].getPosicion().y);
                                        matriz[7][j] = new Casilla("Casilla", true, new Vector2(7*60, j * 60), texturaCasillaBlanca);
                                        matriz[i-1][j] = new Torre("Torre", false, new Vector2(cont.getPosicion().x - 60, j*60),torreBlanca);
                                    }
                                    else {
                                        System.out.println(matriz[i][j].getPosicion().x+" "+matriz[i][j].getPosicion().y);
                                        matriz[7][j] = new Casilla("Casilla", false, new Vector2(7*60, j * 60), texturaCasillaNegra);
                                        matriz[i-1][j] = new Torre("Torre", true, new Vector2(cont.getPosicion().x -60, j*60),torreNegra);
                                    }
                                }
                                else{
                                    if(cont.getNombre()=="Rey"&&Integer.parseInt(String.valueOf(posibles.get(posibles.size()-1).charAt(0)))-2==i){
                                        if ((0 + j) % 2 == 0) {
                                            matriz[0][j] = new Casilla("Casilla", false, new Vector2(i*60, j * 60), texturaCasillaNegra);
                                            matriz[i+1][j] = new Torre("Torre", true, new Vector2(cont.getPosicion().x + 60, j*60),torreBlanca );
                                        }
                                        else {
                                            matriz[0][j] = new Casilla("Casilla", true, new Vector2(i*60, j * 60), texturaCasillaBlanca);
                                            matriz[i+1][j] = new Torre("Torre", false, new Vector2(cont.getPosicion().x + 60, j*60),torreNegra);
                                        }
                                    }
                                }
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
        for (i = 0; i < 8; i++) {
            for (j = 7; j > -1; j--) {
                System.out.println(i+" "+j+matriz[i][j].getNombre()+matriz[i][j].isColor()+matriz[i][j].getTexture());
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

    }
    public void  moverRey(Pieza Rey ,int  i,int  j){
        int c=0;
        for(int a=-1;a<2;a++){
            for (int b=-1;b<2;b++){
                try{
                    if(matriz[i+a][j+b].getNombre().equals("Casilla")||matriz[i+a][j+b].isColor()!=Rey.isColor())
                        posibles.add(String.valueOf(i+a) + String.valueOf(j+b));
                    else
                        System.out.println(matriz[i+a][j+b].getNombre());
                }catch (IndexOutOfBoundsException e){
                }
            }
            if(Rey.getCont()==0){
                System.out.println("El rey no se ha movido");
                if(matriz[0][j].getCont()==0){
                    for(c=1;c<4;c++){
                        System.out.println(matriz[c][j].getNombre());
                        if(matriz[c][j].getNombre()!="Casilla"){
                           System.out.println(c+" "+j);
                           break;
                        }
                    }
                    if(c>3){
                        posibles.add(String.valueOf(i-2) + String.valueOf(j));
                    }
                    else{
                        System.out.println("Hay piezas en medio"+c+matriz[c][j]);
                    }
                }
                if(matriz[7][j].getCont()==0){
                    for(c=5;c<7;c++){
                        System.out.println(matriz[c][j].getNombre());
                        if(matriz[c][j].getNombre()!="Casilla"){
                            System.out.println(c+" "+j);
                            break;
                        }
                    }
                    if(c>6){
                        posibles.add(String.valueOf(i+2) + String.valueOf(j));
                    }
                    else{
                        System.out.println("Hay piezas en medio"+c+matriz[c][j]);
                    }
                }
            }
            else
                System.out.println("El rey  se ha movido no puede realizar ningun enroque");
        }
        posibles.add(String.valueOf(i) + String.valueOf(j));
        Rey.setCont(Rey.getCont()+1);
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
        for (int a = 1; a <8; a++) {
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
        alfil.setCont(alfil.getCont()+1);
        posibles.add(String.valueOf(i) + String.valueOf(j));
    }
    public void  moverCaballo(Pieza caballo, int  i,int  j){

        try{
            if (matriz[i +1 ][j +2 ].getNombre().equals("Casilla")||matriz[i +1 ][j +2 ].isColor() != caballo.isColor()){
                posibles.add(String.valueOf(i +1) + String.valueOf(j +2));

            }
        }catch (IndexOutOfBoundsException e) {

        }
        try{
            if (matriz[i +2][j +1 ].getNombre().equals("Casilla")||matriz[i +2 ][j +1 ].isColor() != caballo.isColor()){
                posibles.add(String.valueOf(i+2) + String.valueOf(j+1));

            }
        }catch (IndexOutOfBoundsException e) {

        }
        try{
            if (matriz[i+2 ][j -1 ].getNombre().equals("Casilla")||matriz[i +2 ][j -1 ].isColor() != caballo.isColor()){
                posibles.add(String.valueOf(i + 2) + String.valueOf(j -1 ));
        }
        }catch (IndexOutOfBoundsException e) {

        }
        try{
            if (matriz[i +1 ][j -2 ].getNombre().equals("Casilla")||matriz[i +1 ][j -2 ].isColor() != caballo.isColor()) {
                posibles.add(String.valueOf(i + 1) + String.valueOf(j - 2));
            }
        }catch (IndexOutOfBoundsException e) {
        }
        try{
            if (matriz[i -1][j-2].getNombre().equals("Casilla")||matriz[i -1][j -2 ].isColor() != caballo.isColor()){
                posibles.add(String.valueOf(i -1) + String.valueOf(j -2));
            }
        }catch (IndexOutOfBoundsException e) {

        }
        try{
            if (matriz[i -2][j -1].getNombre().equals("Casilla")||matriz[i -2 ][j -1 ].isColor() != caballo.isColor()){
                posibles.add(String.valueOf(i-2) + String.valueOf(j-1));
        }
        }catch (IndexOutOfBoundsException e) {

        }
        try{
            if (matriz[i -2 ][j +1 ].getNombre().equals("Casilla")||matriz[i -2 ][j +1 ].isColor() != caballo.isColor()){
                posibles.add(String.valueOf(i -2) + String.valueOf(j +1 ));

        }
        }catch (IndexOutOfBoundsException e) {

        }
        try{
            if (matriz[i -1 ][j +2 ].getNombre().equals("Casilla")||matriz[i -1 ][j +2 ].isColor() != caballo.isColor()){
                posibles.add(String.valueOf(i-1) + String.valueOf(j+2));

        }
        }catch (IndexOutOfBoundsException e) {

        }
        caballo.setCont(caballo.getCont()+1);
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


