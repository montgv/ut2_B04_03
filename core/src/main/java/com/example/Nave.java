package com.example;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Nave extends Actor {
    enum VerticalMovement {UP, NONE, DOWN}
    enum HorizontalMovement {LEFT, NONE, RIGHT}

    private final TextureRegion naveReposo;
    private final TextureRegion naveIzq;
    private final TextureRegion naveDcha;
    private final TextureRegion naveArriba;
    private final TextureRegion naveArribaIzq;
    private final TextureRegion naveArribaDcha;
    private final TextureRegion naveAbajo;
    private final TextureRegion naveAbajoIzq;
    private final TextureRegion naveAbajoDcha;

    HorizontalMovement horizontalMovement;
    VerticalMovement verticalMovement;
    TextureRegion regionActual;

    public Nave(float x, float y) {
        Texture completo = new Texture(Gdx.files.internal("spacetheme.png"));
        naveArribaIzq = new TextureRegion(completo, 0, 44, 39, 43);
        naveArriba = new TextureRegion(completo, 42, 44, 39, 43);
        naveArribaDcha = new TextureRegion(completo, 84, 44, 39, 43);
        naveIzq = new TextureRegion(completo, 0, 0, 39, 43);
        naveReposo = new TextureRegion(completo, 42, 0, 39, 43);
        naveDcha = new TextureRegion(completo, 84, 0, 39, 43);
        naveAbajoIzq = new TextureRegion(completo, 0, 88, 39, 43);
        naveAbajo = new TextureRegion(completo, 42, 88, 39, 43);
        naveAbajoDcha = new TextureRegion(completo, 84, 88, 39, 43);
        regionActual = naveReposo;
        horizontalMovement = HorizontalMovement.NONE;
        verticalMovement = VerticalMovement.NONE;
        setSize(regionActual.getRegionWidth(), regionActual.getRegionHeight());
        setPosition(x - getWidth() / 2, y - getHeight() / 2);
    }

    public Nave() {
        this(400, 240);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(regionActual, getX(), getY());
    }

    @Override
    public void act(float delta) {
        processKeyboard();

        if (verticalMovement == VerticalMovement.UP) this.moveBy(0, 100 * delta);
        if (verticalMovement == VerticalMovement.DOWN) this.moveBy(0, -100 * delta);
        if (horizontalMovement == HorizontalMovement.LEFT) this.moveBy(-100 * delta, 0);
        if (horizontalMovement == HorizontalMovement.RIGHT) this.moveBy(100 * delta, 0);

        if (getX() < 0) setX(0);
        if (getY() < 0) setY(0);
        if (getX() >= 799 - getWidth()) setX(799 - getWidth());
        if (getY() >= 479 - getHeight()) setY(479 - getHeight());

        if (verticalMovement == VerticalMovement.UP && horizontalMovement == HorizontalMovement.LEFT)
            regionActual = naveArribaIzq;
        if (verticalMovement == VerticalMovement.UP && horizontalMovement == HorizontalMovement.NONE)
            regionActual = naveArriba;
        if (verticalMovement == VerticalMovement.UP && horizontalMovement == HorizontalMovement.RIGHT)
            regionActual = naveArribaDcha;
        if (verticalMovement == VerticalMovement.NONE && horizontalMovement == HorizontalMovement.LEFT)
            regionActual = naveIzq;
        if (verticalMovement == VerticalMovement.NONE && horizontalMovement == HorizontalMovement.NONE)
            regionActual = naveReposo;
        if (verticalMovement == VerticalMovement.NONE && horizontalMovement == HorizontalMovement.RIGHT)
            regionActual = naveDcha;
        if (verticalMovement == VerticalMovement.DOWN && horizontalMovement == HorizontalMovement.LEFT)
            regionActual = naveAbajoIzq;
        if (verticalMovement == VerticalMovement.DOWN && horizontalMovement == HorizontalMovement.NONE)
            regionActual = naveAbajo;
        if (verticalMovement == VerticalMovement.DOWN && horizontalMovement == HorizontalMovement.RIGHT)
            regionActual = naveAbajoDcha;
    }

    public void processKeyboard() {
        verticalMovement = VerticalMovement.NONE;
        horizontalMovement = HorizontalMovement.NONE;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            verticalMovement = VerticalMovement.DOWN;
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            verticalMovement = VerticalMovement.UP;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            horizontalMovement = HorizontalMovement.LEFT;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            horizontalMovement = HorizontalMovement.RIGHT;
    }
    Rectangle getShape() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
}