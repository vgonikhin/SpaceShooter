package ru.gb.space_shooter.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen{
    static final int SHIP_SIZE = 64;
    static final int ASTEROID_SIZE = 64;
    static final int ROUND_HEIGHT = 16;
    static final int ROUND_WIDTH = 32;

    private GameClass game;

    private float time;

    protected SpriteBatch batch;
    BitmapFont fnt;
    Background bg;
    AsteroidEmitter ae;
    Enemies e;
    Player player;
    Weapons weapons;
    PowerUpsEmitter pue;
    ParticleEmitter pe;
    BoomEmitter be;
    List<LevelInfo> levels;
    int maxLevels;
    LevelHandler lh;
    HiScores hiScores;

    public GameScreen(GameClass game, SpriteBatch batch){
        this.game = game;
        this.batch = batch;
        this.hiScores = game.hiScores;
    }

    public ParticleEmitter getParticleEmitter() {
        return pe;
    }

    public void loadFullGameInfo() {
        levels = new ArrayList<LevelInfo>();
        BufferedReader br = null;
        try {
            br = Gdx.files.internal("leveldata.csv").reader(8192);
            br.readLine();
            String str;
            while ((str = br.readLine()) != null) {
                String[] arr = str.split("\\t");
                LevelInfo levelInfo = new LevelInfo(Integer.parseInt(arr[0]), Float.parseFloat(arr[1]),Float.parseFloat(arr[2]),
                        Integer.parseInt(arr[3]), Integer.parseInt(arr[4]), Float.parseFloat(arr[5]), Float.parseFloat(arr[6]));
                levels.add(levelInfo);
            }
            maxLevels = levels.size();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show() {
        time = 0.0f;
        loadFullGameInfo();
        fnt = new BitmapFont(Gdx.files.internal("font.fnt"));
        lh = new LevelHandler(this);
        bg = new Background(ScreenTypes.GAME);
        ae = new AsteroidEmitter(this);
        e = new Enemies(this);
        player = new Player(this);
        weapons = new Weapons(20);
        pue = new PowerUpsEmitter();
        pe = new ParticleEmitter();
        be = new BoomEmitter();
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(game.getCamera().combined);
        batch.begin();
        bg.render(ScreenTypes.GAME,batch);
        pe.render(batch);
        ae.render(batch);
        e.render(batch);
        be.render(batch);
        weapons.render(batch);
        pue.render(batch);
        player.render(batch);
        batch.end();
    }

    public void update (float dt){
        if(player.getLives()>0) {
            time+=dt;
            if(lh.getCurrentLevel()!=maxLevels-1){
                lh.setCurrentLevel((int)(time/60.0f));
            }
            lh.update(dt);
            bg.update(dt, player.getSpeed());
            player.update(dt);
            pe.update(dt);
            ae.update(dt);
            e.update(dt);
            weapons.update(dt);
            be.update(dt);
            pue.update(dt, player.getSpeed());
            CollisionHandler.checkCollisions(this);
        } else {
            game.setScreen(game.hiScoreScreen);
        }
    }

    @Override
    public void resize(int width, int height) {
        game.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        GameAssets.getInstance().assetManager.clear();
    }

    @Override
    public void dispose() {
        GameAssets.getInstance().assetManager.clear();
    }
}
