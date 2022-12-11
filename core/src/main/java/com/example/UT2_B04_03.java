package com.example;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class UT2_B04_03 extends ApplicationAdapter {
	Stage stage;
	OrthographicCamera camera;
    Array<NaveEnemiga> naveEnemigas;
    private long lastDropTime;
	Nave nave;
	private static BitmapFont font;
	private Sound boomSound;
	private int score;
	Batch batch;

	@Override
	public void create() {
		// Crear el stage y el actor de la nave
		stage = new Stage();
		nave = new Nave(400, 100);
		batch = new SpriteBatch();
		boomSound = Gdx.audio.newSound(Gdx.files.internal("boom.wav"));
        //Preparar array de enemigos
        naveEnemigas = new Array<>();
        cargarNaveEnemigas();
		// Añadir el actor al stage y permitirle que gestione el teclado
		stage.addActor(nave);
		// Preparar la cámara
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		// Crear viewport para el stage asociado a la cámara
		Viewport viewport = new ScreenViewport(camera);
		stage.setViewport(viewport);
		if (font == null) {
			// Cargar fuente solamente si es la primera vez
			font = new BitmapFont();
		}
		score = 0;
	}

	@Override
	public void render() {
		// Borrar pantalla
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //Pintar naves enemigas
        for (int i = 0; i < naveEnemigas.size; i++){
            stage.addActor(naveEnemigas.get(i));
        }

		
		// Permitir que los actores hagan sus tareas

		for (int i = 0; i < naveEnemigas.size; i++) {
			if (!naveEnemigas.get(i).getFin() && Intersector.overlaps(nave.getShape(), naveEnemigas.get(i).getShape())) {
				naveEnemigas.get(i).clearActions();
				naveEnemigas.get(i).explosion();
				boomSound.play();
				score += 100;
				naveEnemigas.removeIndex(i);
			}
		}

		//Comprobamos para que aparezcan más
		if(TimeUtils.nanoTime() - lastDropTime > 1000000000 && naveEnemigas.size == 0) cargarNaveEnemigas();
		stage.act(Gdx.graphics.getDeltaTime());
		// Dibujar el stage
		stage.draw();
		batch.begin();
		font.draw(batch, "Puntos: " + score, 20f, 460f);
		batch.end();
	}

    private void cargarNaveEnemigas() {
        for (int i = 0 ; i < 5; i++) {
            naveEnemigas.add(new NaveEnemiga(MathUtils.random(10, 600), MathUtils.random(450/2, 440)));
        }
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
	public void dispose() {
		stage.dispose();
	}
}
