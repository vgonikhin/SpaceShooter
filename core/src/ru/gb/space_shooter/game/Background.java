package ru.gb.space_shooter.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static ru.gb.space_shooter.game.GameClass.*;

class Background {

    private class Star {
        private Vector2 coord;
        private float speed;
        private float scale;

        private Star() {
            this.coord = new Vector2(MathUtils.random(SCREEN_WIDTH),MathUtils.random(SCREEN_HEIGHT));
            this.speed = MathUtils.random(20.0f,100.0f);
            this.scale = this.speed*0.0075f;
        }

        public void update(float dt, Vector2 v){
            coord.mulAdd(new Vector2(v.x+16.0f*speed,0.5f*v.y),-0.06f*dt);
            if(coord.x<-40){
                coord.x= SCREEN_WIDTH+40;
                speed = MathUtils.random(20.0f,100.0f);
                scale = speed*0.0075f;
            }
        }
    }

    private Texture textureBG;
    private TextureRegion textureStar;
    private Star[] stars;

    Background(ScreenTypes type) {
        GameAssets.getInstance().loadAssets(ScreenTypes.GAME);
        this.textureBG = GameAssets.getInstance().assetManager.get("bg720.png",Texture.class);
        if(type==ScreenTypes.GAME) {
            TextureAtlas ta = GameAssets.getInstance().assetManager.get("atlas.pack",TextureAtlas.class);
            textureStar = ta.findRegion("star8");
        }
        stars = new Star[100];
        for (int i = 0; i < stars.length; i++) stars[i] = new Star();
    }

    public void render(ScreenTypes type,SpriteBatch batch){
        batch.draw(textureBG,0,0);
        if(type==ScreenTypes.GAME) for (int i = 0; i < stars.length; i++) batch.draw(textureStar, stars[i].coord.x, stars[i].coord.y, textureStar.getRegionWidth() / 2, textureStar.getRegionHeight() / 2, textureStar.getRegionWidth(), textureStar.getRegionHeight(), stars[i].scale, stars[i].scale, 0);
    }

    public void update (float dt, Vector2 v){ for (int i = 0; i < stars.length; i++) stars[i].update(dt,v); }
}