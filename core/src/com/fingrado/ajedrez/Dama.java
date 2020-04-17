package com.fingrado.ajedrez;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Dama  extends Pieza {
        public Rectangle rect;
    public Dama(boolean color, Vector2 posicion, Texture texture, int cont) {
            super(color, posicion, texture);
            rect = new Rectangle(posicion.x, posicion.y, texture.getWidth(), texture.getHeight());
        }


        public void movimiento(){

        }
        public void mover(){

        }
}
