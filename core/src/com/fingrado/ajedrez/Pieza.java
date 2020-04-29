package com.fingrado.ajedrez;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.w3c.dom.css.Rect;

public class Pieza {
    boolean color;
    private Vector2 posicion;
    private Texture texture;
    public   Rectangle rect;
    public Pieza(boolean color, Vector2 posicion, Texture texture) {
        this.color = color;
        this.posicion = posicion;
        this.texture = texture;
        rect = new Rectangle(posicion.x, posicion.y, texture.getWidth(), texture.getHeight());
    }

    public boolean isColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public Vector2 getPosicion() {
        return posicion;
    }

    public void setPosicion(Vector2 posicion) {
        this.posicion = posicion;
    }

    public Texture getTexture() {
        return texture;
    }



    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
