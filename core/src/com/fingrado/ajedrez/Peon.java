package com.fingrado.ajedrez;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Peon extends Pieza {
    int cont=0;
    public Rectangle rect;
    public Peon(boolean color, Vector2 posicion, Texture texture, int cont) {
        super(color, posicion, texture);
        this.cont = cont;
        rect = new Rectangle(posicion.x, posicion.y, texture.getWidth(), texture.getHeight());}


    public void movimiento(){
        if(cont<=0){
            //selecciona una arriba y dos
        }
        else {

            //selecciona uno arriba
        }
    }
    public void mover(){

    }
}
