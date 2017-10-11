package ru.gb.space_shooter.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.StringBuilder;

import static ru.gb.space_shooter.game.GameClass.SCREEN_HEIGHT;

public class MenuScreen implements Screen{
    private GameClass game;

    protected SpriteBatch batch;
    private BitmapFont fnt;
    private Background bg;
    private Rectangle rectangleStart;
    private Rectangle rectangleHiScore;
    private Rectangle rectangleExit;
    private StringBuilder menuString;


    public MenuScreen(GameClass game, SpriteBatch batch){
        this.game = game;
        this.batch = batch;
        this.rectangleStart = new Rectangle(500,520,200,64);
        this.rectangleHiScore = new Rectangle(390,420,420,64);
        this.rectangleExit = new Rectangle(530,320,140,64);
        this.menuString = new StringBuilder(50);
    }

    @Override
    public void show() {
        GameAssets.getInstance().loadAssets(ScreenTypes.MENU);
        fnt = GameAssets.getInstance().assetManager.get("menufont.fnt",BitmapFont.class);
        bg = new Background(ScreenTypes.MENU);
    }

    @Override
    public void render(float delta) {
        update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(game.getCamera().combined);
        batch.begin();
        bg.render(ScreenTypes.MENU,batch);
        menuString.setLength(0);
        menuString.append("START");
        fnt.draw(batch,menuString,rectangleStart.x,rectangleStart.y+50);
        menuString.setLength(0);
        menuString.append("HIGH SCORES");
        fnt.draw(batch,menuString,rectangleHiScore.x,rectangleHiScore.y+50);
        menuString.setLength(0);
        menuString.append("EXIT");
        fnt.draw(batch,menuString,rectangleExit.x,rectangleExit.y+50);
        batch.end();
    }

    public void update (){
        MyInputProcessor mip = (MyInputProcessor) Gdx.input.getInputProcessor();
        if (mip.isTouchedInArea(rectangleStart)>-1){
            game.setScreen(game.gameScreen);
        }
        if (mip.isTouchedInArea(rectangleHiScore)>-1){
            if(game.gameScreen.player!=null)
                game.gameScreen.player.addScore(-50000);
            game.setScreen(game.hiScoreScreen);
        }
        if (mip.isTouchedInArea(rectangleExit)>-1){
            GameAssets.getInstance().assetManager.clear();
            Gdx.app.exit();
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
