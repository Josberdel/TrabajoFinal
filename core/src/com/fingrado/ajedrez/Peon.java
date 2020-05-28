package com.fingrado.ajedrez;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Peon extends Pieza {
    int cont;

    public Peon(String nombre, boolean color, Vector2 posicion, Texture texture) {
        super(nombre,color, posicion, texture);
        this.cont = 0;
        }

}
