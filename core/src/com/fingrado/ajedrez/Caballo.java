package com.fingrado.ajedrez;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Caballo extends Pieza {
    public Rectangle rect;
    int i=0;
    int j=0;
   // ArrayList<posicion>
    public Caballo(boolean color, Vector2 posicion, Texture texture) {
        super(color, posicion, texture);
        rect = new Rectangle(posicion.x, posicion.y, texture.getWidth(), texture.getHeight());
    }


    public void movimiento(){
        for (i=0;i<=5;i++){
            //
            for(j=0;j<=5;j++){

            }

            }
        }

    public void mover(){

    }
}


