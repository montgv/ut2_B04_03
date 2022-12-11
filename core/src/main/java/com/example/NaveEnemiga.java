package com.example;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.utils.Align;

public class NaveEnemiga extends Actor {
    private static TextureRegion reposo;
    private Texture completo;
    private TextureRegion actual;
    float statetime = 0f;
    private TextureRegion explosion;
    Animation<TextureRegion> walkAnimation;
    Boolean explo = false;
    Boolean fin = false;

    public NaveEnemiga(float x, float y) {
        if (reposo == null || walkAnimation == null) {
            // Cargar textura solamente si es la primera vez
            completo = new Texture(Gdx.files.internal("spacetheme.png"));
            reposo = new TextureRegion(completo, 153, 301, 33, 33);
            TextureRegion[] walkFrames;
            TextureRegion e1 = new TextureRegion(completo, 6, 165, 12, 13);
            TextureRegion e2 = new TextureRegion(completo, 23, 162, 19, 19);
            TextureRegion e3 = new TextureRegion(completo, 45, 159, 25, 25);
            TextureRegion e4 = new TextureRegion(completo, 74, 150,46,41);
            TextureRegion e5 = new TextureRegion(completo, 0, 195, 52, 47);
            TextureRegion e6 = new TextureRegion(completo, 50,192,61,55);
            TextureRegion e7 = new TextureRegion(completo, 119, 192, 64, 60);
            TextureRegion e8 = new TextureRegion(completo, 188, 196, 61, 52);
            walkFrames = new TextureRegion[]{e1, e2, e3, e4, e5, e6, e7, e8};
            walkAnimation = new Animation<>(0.099f, walkFrames);
        }
        setSize(reposo.getRegionWidth(), reposo.getRegionHeight());
        setPosition(x - getWidth() / 2, y - getHeight() / 2);
        // Rotar desde el centro
        setOrigin(Align.center);
        // Animación de movimiento
        MoveToAction move1 = new MoveToAction();
        move1.setPosition( x - 100, y);
        move1.setDuration(1f);
        move1.setInterpolation(Interpolation.sine);
        MoveToAction move2 = new MoveToAction();
        move2.setPosition( x + 100, y);
        move2.setDuration(1f);
        move2.setInterpolation(Interpolation.sine);

        ParallelAction  position1 = new ParallelAction(move1);
        ParallelAction position2 = new ParallelAction(move2);

        SequenceAction sequence = new SequenceAction(position1, position2);

        RepeatAction forever = new RepeatAction();
        forever.setCount(RepeatAction.FOREVER);
        forever.setAction(sequence);
        actual = reposo;
        addAction(forever);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(actual, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public void act(float delta) {
        super.act(delta); // MUY IMPORTANTE
        statetime += delta;

        if (walkAnimation.isAnimationFinished(statetime) && getFin()) this.remove();

        if (explo) {
            explosion = walkAnimation.getKeyFrame(statetime);
            actual = explosion;
        }

        if (getX() >= 799 - getWidth()) setX(799 - getWidth());
        if (getX() < 0) setX(0);
    }
    Rectangle getShape() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public void explosion() {
        statetime = 0f;
        explo = true;
        fin = true;
    }

    public Boolean getFin() {
        return fin;
    }
}