package ru.gb.space_shooter.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameClass extends Game {
	public static final int SCREEN_HEIGHT = 720;
	public static final int SCREEN_WIDTH = 1280;

	protected SpriteBatch batch;
	GameScreen gameScreen;
	MenuScreen menuScreen;
	HiScoreScreen hiScoreScreen;
	HiScores hiScores;

	private Viewport viewport;
	private Camera camera;

	public Camera getCamera() {
		return camera;
	}

	public Viewport getViewport() {
		return viewport;
	}

	@Override
	public void create () {
		Gdx.input.setCatchBackKey(true);
		batch = new SpriteBatch();
		camera = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
		viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, camera);
		viewport.update(SCREEN_WIDTH, SCREEN_HEIGHT, true);
		viewport.apply();
		MyInputProcessor mip = new MyInputProcessor(this);
		Gdx.input.setInputProcessor(mip);
		hiScores = new HiScores();
		hiScores.loadScores();
		gameScreen = new GameScreen(this, batch);
		menuScreen = new MenuScreen(this, batch);
		hiScoreScreen = new HiScoreScreen(this, batch);
		setScreen(menuScreen);
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		viewport.apply();
		camera.update();
	}

	@Override
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();
		if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
			GameAssets.getInstance().assetManager.clear();
			Gdx.app.exit();
		}
		getScreen().render(dt);
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
