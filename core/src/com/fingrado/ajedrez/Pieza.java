package com.fingrado.ajedrez;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.w3c.dom.css.Rect;

public class Pieza {
    boolean color;
    private Vector2 posicion;
    private Texture texture;
    public   Rectangle rect;
    public String nombre;
    int cont;

    public Pieza(String nombre, boolean color, Vector2 posicion, Texture texture) {
        this.nombre=nombre;
        this.color = color;
        this.posicion = posicion;
        this.texture = texture;
        rect = new Rectangle(posicion.x, posicion.y, texture.getWidth(), texture.getHeight());
        this.cont = 0;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void pintar(SpriteBatch batch) { batch.draw(getTexture(), getPosicion().x, getPosicion().y);}

    public int getCont(){return cont;}

    public void setCont(int cont) {this.cont = cont;}
    }
