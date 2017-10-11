package ru.gb.space_shooter.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

class GameAssets {
    private static final GameAssets ourInstance = new GameAssets();

    static GameAssets getInstance() {
        return ourInstance;
    }

    AssetManager assetManager;

    private GameAssets() {
        this.assetManager = new AssetManager();
    }

    void loadAssets(ScreenTypes type){
        switch (type){
            case MENU:
                assetManager.load("menufont.fnt", BitmapFont.class);
                assetManager.load("bg720.png", Texture.class);
                assetManager.finishLoading();
                break;
            case GAME:
                assetManager.load("font.fnt", BitmapFont.class);
                assetManager.load("bg720.png", Texture.class);
                assetManager.load("atlas.pack",TextureAtlas.class);
                assetManager.load("CollapseNorm.wav",Sound.class);
                assetManager.load("laser.wav",Sound.class);
                assetManager.finishLoading();
                break;
            case HISCORE:
                assetManager.load("menufont.fnt", BitmapFont.class);
                assetManager.load("bg720.png", Texture.class);
                assetManager.finishLoading();
                break;
        }
    }
}
