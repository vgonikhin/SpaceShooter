package ru.gb.space_shooter.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.StringBuilder;

import static ru.gb.space_shooter.game.GameClass.SCREEN_HEIGHT;

public class HiScoreScreen implements Screen {
    private GameClass game;
    private HiScores hiScores;

    private MyInputProcessor mip;
    protected SpriteBatch batch;
    private BitmapFont fnt;
    private Background bg;
    private Rectangle rectangleStart;
    private Rectangle rectangleExit;
    private StringBuilder menuString;
    private StringBuilder hsName;
    private boolean getName;
    private int place;
    private boolean changed;

    public HiScoreScreen(GameClass game, SpriteBatch batch){
        this.game = game;
        this.hiScores = game.hiScores;
        this.batch = batch;
        this.rectangleStart = new Rectangle(500,520,200,64);
        this.rectangleExit = new Rectangle(530,100,140,64);
        this.menuString = new StringBuilder(50);
        this.getName = false;
        this.changed = false;
        this.mip = (MyInputProcessor) Gdx.input.getInputProcessor();
    }

    @Override
    public void show() {
        GameAssets.getInstance().loadAssets(ScreenTypes.HISCORE);
        fnt = GameAssets.getInstance().assetManager.get("menufont.fnt",BitmapFont.class);
        bg = new Background(ScreenTypes.MENU);
        changed = false;
        place=5;
        if(game.gameScreen.player!=null)
            place = hiScores.isHiscore(game.gameScreen.player.getScore());
        if(place<5) {
            getName = true;
            mip.getString().setLength(0);
        }
    }

    @Override
    public void render(float delta) {
        update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(game.getCamera().combined);
        batch.begin();
        bg.render(ScreenTypes.MENU, batch);
        if(getName) {
            menuString.setLength(0);
            menuString.append("YOUR SCORE: ").append(game.gameScreen.player.getScore());
            fnt.draw(batch, menuString, rectangleStart.x-100, rectangleStart.y + 150);
            menuString.setLength(0);
            menuString.append("NEW HIGH SCORE!");
            fnt.draw(batch, menuString, rectangleStart.x-100, rectangleStart.y + 50);
            fnt.draw(batch, hsName, 500, 360);
            Gdx.input.setOnscreenKeyboardVisible(true);
        } else {
            for (int i = 0; i < 5; i++) {
                Gdx.input.setOnscreenKeyboardVisible(false);
                menuString.setLength(0);
                menuString.append(i+1).append(". ").append(hiScores.getName(i));
                fnt.draw(batch, menuString, 200, SCREEN_HEIGHT-90*(i+1));
                menuString.setLength(0);
                menuString.append(hiScores.getScore(i));
                fnt.draw(batch, menuString, 850, SCREEN_HEIGHT-90*(i+1));
            }
            menuString.setLength(0);
            menuString.append("BACK");
            fnt.draw(batch, menuString, rectangleExit.x, rectangleExit.y + 50);
        }
        batch.end();
    }

    public void update (){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            this.getName = false;
            hiScores.setScore(place,hsName,game.gameScreen.player.getScore());
            changed = true;
            return;
        }
        if(mip.keyUp(Input.Keys.ANY_KEY)) {
            hsName = mip.getString();
        }
        if (mip.isTouchedInArea(rectangleExit)>-1){
            if(changed)
                hiScores.saveScore();
            game.setScreen(game.menuScreen);
        }
    }

    public boolean isGetName() {
        return getName;
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
