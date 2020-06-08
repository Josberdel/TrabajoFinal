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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
public class GameScreen implements Screen, InputProcessor {
    private BitmapFont font = new BitmapFont(Gdx.files.internal("ui/defualt.fnt"));
    private BitmapFont font2 = new BitmapFont(Gdx.files.internal("ui/defualt.fnt"));
    private BitmapFont font3 = new BitmapFont(Gdx.files.internal("ui/defualt.fnt"));
    private BitmapFont font4 = new BitmapFont(Gdx.files.internal("ui/defualt.fnt"));
    SpriteBatch batch;
    int i = 0;
    int j = 0;
    Pieza cont = null;
    String posReyBlanco="";
    String posReyNegro="";
    boolean turno=true;
    int Contador_tablas;
    int ayuda = 0;
    private ArrayList<String> amenazasBlancas;
    private ArrayList<String> amenazasNegras;
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
        amenazasBlancas = new ArrayList<>();
        amenazasNegras = new ArrayList<>();
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
        buscaPosReyBlanco();
        buscaPosReyNegro();
    }
    private void buscaPosReyBlanco() {
        for (i = 0; i < 8; i++) {
            for (j = 7; j > -1; j--) {
                if(matriz[i][j].getNombre()=="Rey"&&matriz[i][j].isColor()==true){
                    posReyBlanco = String.valueOf(i)+String.valueOf(j);
                }
            }
        }
    }
    private void buscaPosReyNegro() {
        for (i = 0; i < 8; i++) {
            for (j = 7; j > -1; j--) {
                if(matriz[i][j].getNombre()=="Rey"&&matriz[i][j].isColor()==false){
                    posReyNegro= String.valueOf(i)+String.valueOf(j);
                }
            }
        }
    }
    private ArrayList<String> comprobarJaqueBlanco() {
        ArrayList<String> amenazasNegras=new ArrayList<>();
        for (i = 0; i < 8; i++) {
            for (j = 7; j > -1; j--) {
                if(matriz[i][j].isColor()!=true) {
                    switch (matriz[i][j].getNombre()) {
                        case "Rey":
                            //System.out.println("ReyN"+atacarRey(matriz[i][j], i, j));
                            amenazasNegras.addAll(atacarRey(matriz[i][j], i, j));
                            break;
                        case "Dama":
                            //System.out.println("DamaN"+atacarTorre(matriz[i][j], i, j));
                            // System.out.println("DamaN"+atacarAlfil(matriz[i][j], i, j));
                            amenazasNegras.addAll(atacarTorre(matriz[i][j], i, j));
                            amenazasNegras.addAll(atacarAlfil(matriz[i][j], i, j));
                            break;
                        case "Caballo":
                            //System.out.println("CaballoN"+atacarCaballo(matriz[i][j], i, j));
                            amenazasNegras.addAll(atacarCaballo(matriz[i][j], i, j));
                            break;
                        case "Alfil":
                            // System.out.println("AlfilN"+atacarAlfil(matriz[i][j], i, j));
                            amenazasNegras.addAll(atacarAlfil(matriz[i][j],i,j));
                            break;
                        case "Torre":
                            // System.out.println("TorreN"+atacarTorre(matriz[i][j], i, j));
                            amenazasNegras.addAll(atacarTorre(matriz[i][j], i, j));
                            break;
                        case "PeonN":
                            //System.out.println("PeonN"+atacarPeonN(matriz[i][j], i, j));
                            amenazasNegras.addAll(atacarPeonN(matriz[i][j], i, j) );
                            break;
                    }
                }
            }
        }
        Set<String> hashSet = new HashSet<String>(amenazasNegras);
        amenazasNegras.clear();
        amenazasNegras.addAll(hashSet);
        Collections.sort(amenazasNegras);
        return amenazasNegras;
    }
    private ArrayList<String> comprobarJaqueNegro() {
        ArrayList<String> amenazasBlancas=new ArrayList<>();
        for (i = 0; i < 8; i++) {
            for (j = 7; j > -1; j--) {
                if(matriz[i][j].isColor()!=false) {
                    switch (matriz[i][j].getNombre()) {
                        case "Rey":
                            //System.out.println("ReyB"+atacarRey(matriz[i][j], i, j));
                            amenazasBlancas.addAll(atacarRey(matriz[i][j], i, j));
                            break;
                        case "Dama":
                            /*System.out.println("DamaB"+atacarTorre(matriz[i][j], i, j));
                            System.out.println("DamaB"+atacarAlfil(matriz[i][j], i, j));*/
                            amenazasBlancas.addAll(atacarTorre(matriz[i][j], i, j));
                            amenazasBlancas.addAll(atacarAlfil(matriz[i][j], i, j));
                            break;
                        case "Caballo":
                            //System.out.println("CaballoB"+atacarCaballo(matriz[i][j], i, j));
                            amenazasBlancas.addAll(atacarCaballo(matriz[i][j], i, j));
                            break;
                        case "Alfil":
                            //System.out.println("AlfilB"+atacarAlfil(matriz[i][j], i, j));
                            amenazasBlancas.addAll(atacarAlfil(matriz[i][j], i, j));
                            break;
                        case "Torre":
                            //System.out.println("TorreB"+atacarTorre(matriz[i][j], i, j));
                            amenazasBlancas.addAll(atacarTorre(matriz[i][j], i, j));
                            break;
                        case "PeonB":
                            //System.out.println("PeonB"+atacarPeonB(matriz[i][j], i, j));
                            amenazasBlancas.addAll(atacarPeonB(matriz[i][j], i, j));
                            break;
                    }
                }
            }
        }
        Set<String> hashSet = new HashSet<String>(amenazasBlancas);
        amenazasBlancas.clear();
        amenazasBlancas.addAll(hashSet);
        Collections.sort(amenazasBlancas);
        return amenazasBlancas;
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
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            ((Game)Gdx.app.getApplicationListener()).setScreen(new PantallaMenuPrincipal());
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
            font.draw(batch,"turno de las blancas",700,60);
        else
            font.draw(batch,"turno de las negras",700,60);
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
                                System.out.println(matriz[i][j].getPosicion() + " " + i + " " + j);
                                matriz[i][j] = cont;
                                System.out.println(i + " " + j + matriz[i][j].getNombre());
                                matriz[i][j].rect = new Rectangle(matriz[i][j].getPosicion().x, matriz[i][j].getPosicion().y, matriz[i][j].getTexture().getWidth(), matriz[i][j].getTexture().getWidth());
                                System.out.println(i + " " + j + matriz[i][j].rect);
                                if (cont.getNombre() == "Rey" && cont.getCont()==1 && posibles.contains("60")) {
                                    matriz[7][j] = new Casilla("Casilla", true, new Vector2(7 * 60, j * 60), texturaCasillaBlanca);
                                    matriz[i - 1][j] = new Torre("Torre", true, new Vector2(cont.getPosicion().x - 60, j * 60), torreBlanca);
                                    System.out.println(cont.getNombre()  + cont.getCont()==0 +" "+ posibles+" Si");
                                }
                                else
                                     System.out.println(cont.getNombre() + cont.getCont()+" "+ posibles);
                                if (cont.getNombre() == "Rey" && cont.getCont()==1 && posibles.contains("67")) {
                                        matriz[7][j] = new Casilla("Casilla", false, new Vector2(7 * 60, j * 60), texturaCasillaNegra);
                                        matriz[i - 1][j] = new Torre("Torre", false, new Vector2(cont.getPosicion().x - 60, j * 60), torreNegra);
                                }
                                if (cont.getNombre() == "Rey" && cont.getCont()==1 && posibles.contains("20")) {
                                    matriz[0][j] = new Casilla("Casilla", false, new Vector2(i * 60, j * 60), texturaCasillaNegra);
                                    matriz[i + 1][j] = new Torre("Torre", true, new Vector2(cont.getPosicion().x + 60, j * 60), torreBlanca);
                                }
                                if (cont.getNombre() == "Rey" && cont.getCont()==1 && posibles.contains("27")) {
                                            matriz[0][j] = new Casilla("Casilla", true, new Vector2(i * 60, j * 60), texturaCasillaBlanca);
                                            matriz[i + 1][j] = new Torre("Torre", false, new Vector2(cont.getPosicion().x + 60, j * 60), torreNegra);
                                }
                                if (turno == true) {
                                    System.out.println(posReyBlanco);
                                    System.out.println(comprobarJaqueBlanco());
                                    if (comprobarJaqueBlanco().contains(posReyBlanco)) {
                                        cont.setPosicion(new Vector2(matriz[Integer.parseInt(String.valueOf(posibles.get(posibles.size() - 1).charAt(0)))]
                                                [Integer.parseInt(String.valueOf(posibles.get(posibles.size() - 1).charAt(1)))].getPosicion().x,
                                                matriz[Integer.parseInt(String.valueOf(posibles.get(posibles.size() - 1).charAt(0)))]
                                                        [Integer.parseInt(String.valueOf(posibles.get(posibles.size() - 1).charAt(1)))].getPosicion().y));
                                        cont.setCont(cont.getCont() - 1);
                                        matriz[Integer.parseInt(String.valueOf(posibles.get(posibles.size() - 1).charAt(0)))][Integer.parseInt(String.valueOf(posibles.get(posibles.size() - 1).charAt(1)))] = cont;
                                        turno = !turno;
                                        System.out.println("No puedes mover el rey blanco estaria en jaque");
                                    }
                                } else {
                                    System.out.println(posReyNegro);
                                    System.out.println(comprobarJaqueNegro());
                                    if (comprobarJaqueNegro().contains(posReyNegro)) {
                                        cont.setPosicion(new Vector2(matriz[Integer.parseInt(String.valueOf(posibles.get(posibles.size() - 1).charAt(0)))]
                                                [Integer.parseInt(String.valueOf(posibles.get(posibles.size() - 1).charAt(1)))].getPosicion().x,
                                                matriz[Integer.parseInt(String.valueOf(posibles.get(posibles.size() - 1).charAt(0)))]
                                                        [Integer.parseInt(String.valueOf(posibles.get(posibles.size() - 1).charAt(1)))].getPosicion().y));
                                        matriz[Integer.parseInt(String.valueOf(posibles.get(posibles.size() - 1).charAt(0)))][Integer.parseInt(String.valueOf(posibles.get(posibles.size() - 1).charAt(1)))] = cont;
                                        System.out.println("No puedes mover el rey negro estaria en jaque");
                                        turno = !turno;
                                    }
                                }
                                if (posibles.get(posibles.size() - 1).equals((String.valueOf(i) + String.valueOf(j)))) {
                                    if (cont.getCont() > 0) {
                                        cont.setCont(cont.getCont() - 1);
                                    }
                                } else {
                                    turno = !turno;
                                }
                                cont = null;
                                posibles.clear();
                            } else {
                                font2.draw(batch, "Movimiento invalido las posiciones a la que puede ir el " + cont.getNombre() + " son " + posibles, 700, 120);
                            }
                        } catch (Exception e){
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
                        if(Rey.isColor()==true){
                            if(!comprobarJaqueBlanco().contains(String.valueOf(i+a) + String.valueOf(j+b)))
                                posibles.add(String.valueOf(i+a) + String.valueOf(j+b));
                        }
                        else{
                            if(!comprobarJaqueNegro().contains(String.valueOf(i+a) + String.valueOf(j+b)))
                                posibles.add(String.valueOf(i+a) + String.valueOf(j+b));
                        }
                }catch (IndexOutOfBoundsException e){
                }
            }
            if(Rey.getCont()==0){
                if(matriz[0][j].getCont()==0){
                    for(c=1;c<4;c++){
                        if(matriz[c][j].getNombre()!="Casilla"){
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
                        if(matriz[c][j].getNombre()!="Casilla"){
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
    public ArrayList<String> atacarPeonB(Pieza peon, int i, int j){
        ArrayList<String> posibles=new ArrayList<>();
        try{
            if(matriz[i+1][j+1].getNombre().equals("Casilla")){
                posibles.add(String.valueOf(i+1)+String.valueOf(j+1));
            }
        }catch (IndexOutOfBoundsException e){
        }
        try{
            if(matriz[i-1][j+1].getNombre().equals("Casilla")){
                posibles.add(String.valueOf(i-1)+String.valueOf(j+1));
            }
        }catch (IndexOutOfBoundsException e){
        }
        return posibles;
    }
    public ArrayList<String> atacarPeonN(Pieza peon, int i, int j){
        ArrayList<String> posibles=new ArrayList<>();
        try{
            if(matriz[i+1][j-1].getNombre().equals("Casilla")){
                posibles.add(String.valueOf(i+1)+String.valueOf(j-1));
            }
        }catch (IndexOutOfBoundsException e){
        }
        try {
            if (matriz[i - 1][j - 1].getNombre().equals("Casilla")) {
                posibles.add(String.valueOf(i - 1) + String.valueOf(j - 1));
            }
        }catch(IndexOutOfBoundsException e){
        }
        return posibles;
    }
    public ArrayList<String> atacarTorre(Pieza torre, int i, int j){
        ArrayList<String> posibles=new ArrayList<>();
        for(int a=j+1;a<8;a++){
            try{
                if(matriz[i][a].getNombre().equals("Casilla")){
                    posibles.add(String.valueOf(i)+String.valueOf(a));
                }
                else{
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
                }
                else {
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
                    posibles.add(String.valueOf(a) + String.valueOf(j));
                    break;
                }
            }catch (IndexOutOfBoundsException e){
            }
        }
        return posibles;
    }
    public ArrayList<String> atacarRey(Pieza Rey , int  i, int  j){
        ArrayList<String> posibles=new ArrayList<>();
        int c=0;
        for(int a=-1;a<2;a++){
            for (int b=-1;b<2;b++){
                try{
                    posibles.add(String.valueOf(i+a) + String.valueOf(j+b));
                }catch (IndexOutOfBoundsException e){
                }
            }
        }
        return posibles;
    }
    public ArrayList<String> atacarAlfil(Pieza alfil, int i , int j) {
        ArrayList<String> posibles=new ArrayList<>();
        for (int a = 1; a < 8; a++) {
            try {
                if (matriz[i + a][j + a].getNombre().equals("Casilla")) {
                    posibles.add(String.valueOf(i + a) + String.valueOf(j + a));
                }
                else {
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
                    posibles.add(String.valueOf(i - a) + String.valueOf(j - a));
                    break;
                }
            }catch (IndexOutOfBoundsException e) {
            }
        }
        return posibles;
    }
    public ArrayList<String> atacarCaballo(Pieza caballo, int  i, int  j){
        ArrayList<String> posibles=new ArrayList<>();
        try{
            if (!matriz[i +1 ][j +2 ].getNombre().equals("f")){
                posibles.add(String.valueOf(i +1) + String.valueOf(j +2));
            }
        }catch (IndexOutOfBoundsException e) {
        }
        try{
            if (!matriz[i +2][j +1 ].getNombre().equals("f")){
                posibles.add(String.valueOf(i+2) + String.valueOf(j+1));
            }
        }catch (IndexOutOfBoundsException e) {
        }
        try{
            if (!matriz[i+2 ][j -1 ].getNombre().equals("f")){
                posibles.add(String.valueOf(i + 2) + String.valueOf(j - 1 ));
            }
        }catch (IndexOutOfBoundsException e) {
        }
        try{
            if (!matriz[i +1 ][j -2 ].getNombre().equals("f")) {
                posibles.add(String.valueOf(i + 1) + String.valueOf(j - 2));
            }
        }catch (IndexOutOfBoundsException e) {
        }
        try{
            if (!matriz[i -1][j-2].getNombre().equals("f")){
                posibles.add(String.valueOf(i -1) + String.valueOf(j -2));
            }
        }catch (IndexOutOfBoundsException e) {
        }
        try{
            if (!matriz[i -2][j -1].getNombre().equals("f")){
                posibles.add(String.valueOf(i-2) + String.valueOf(j-1));
            }
        }catch (IndexOutOfBoundsException e) {
        }
        try{
            if (!matriz[i -2 ][j +1 ].getNombre().equals("f")){
                posibles.add(String.valueOf(i -2) + String.valueOf(j +1 ));
            }
        }catch (IndexOutOfBoundsException e) {
        }
        try{
            if (!matriz[i -1 ][j +2 ].getNombre().equals("f")){
                posibles.add(String.valueOf(i-1) + String.valueOf(j+2));
            }
        }catch (IndexOutOfBoundsException e) {
        }
        return posibles;
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