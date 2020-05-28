package com.fingrado.ajedrez;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Rey extends Pieza {
    int cont;
    public Rey(String nombre, boolean color, Vector2 posicion, Texture texture) {
        super(nombre, color, posicion, texture);
        this.cont = 0;
    }



    public int getCont() {
        return cont;
    }

    public void setCont(int cont) {
        this.cont = cont;
    }

    public void movimiento(){

    }
    public void mover(){

    }

}
