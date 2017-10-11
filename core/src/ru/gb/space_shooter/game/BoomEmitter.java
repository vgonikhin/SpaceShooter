package ru.gb.space_shooter.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class BoomEmitter {
    private Boom[] booms;
    private Sound soundBoom;
    private Vector2 distToPlayer = new Vector2();

    public BoomEmitter() {
        TextureAtlas ta = GameAssets.getInstance().assetManager.get("atlas.pack",TextureAtlas.class);
        TextureRegion textureBoom = ta.findRegion("explosion64");
        TextureRegion textureBoombw = ta.findRegion("explosion64bw");
        this.booms = new Boom[50];
        TextureRegion[][] regions = textureBoom.split(64, 64);
        TextureRegion[] result = new TextureRegion[regions[0].length * regions.length];
        TextureRegion[][] regionsbw = textureBoombw.split(64, 64);
        TextureRegion[] resultbw = new TextureRegion[regionsbw[0].length * regions.length];
        this.soundBoom = GameAssets.getInstance().assetManager.get("CollapseNorm.wav",Sound.class);
        for (int i = 0, n = 0; i < regions.length; i++) {
            for (int j = 0; j < regions[0].length; j++, n++) {
                result[n] = regions[i][j];
                resultbw[n] = regionsbw[i][j];
            }
        }
        for (int i = 0; i < booms.length; i++) {
            booms[i] = new Boom(result,resultbw);
        }
    }

    public void update(float dt) {
        for (int i = 0; i < booms.length; i++) {
            if (booms[i].isActive()) {
                booms[i].update(dt);
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < booms.length; i++) {
            if (booms[i].isActive()) {
                booms[i].render(batch);
            }
        }
    }

    public void setup(Vector2 position, Vector2 playerPosition, int type) {
        for (int i = 0; i < booms.length; i++) {
            if (!booms[i].isActive()) {
                booms[i].activate(position,type);
                distToPlayer.set(position).sub(playerPosition);
                soundBoom.play((1280-distToPlayer.len())/1920);
                break;
            }
        }
    }
}
