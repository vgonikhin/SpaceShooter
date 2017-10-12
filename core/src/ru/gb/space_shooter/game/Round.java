package ru.gb.space_shooter.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import static ru.gb.space_shooter.game.GameScreen.*;

class Round implements Poolable{
    private Vector2 coord;
    private Vector2 speed;
    private TextureRegion textureRound;
    private boolean active;
    private int type;

    Round() {
        this.coord = new Vector2(0,0);
        this.speed = new Vector2(0,0);
        this.active = false;
        this.type = 0;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive(){
        return this.active;
    }

    int getType() {
        return type;
    }

    public void render(SpriteBatch batch){ batch.draw(textureRound, coord.x, coord.y,ROUND_WIDTH/2,ROUND_HEIGHT/2,ROUND_WIDTH,ROUND_HEIGHT,1,1,0); }

    public Vector2 update(float dt){
        return coord.mulAdd(speed,dt);
    }

    void setCoordAndSpeed(Vector2 coord, Vector2 speed, int type, TextureRegion textureRound) {
        this.coord = coord;
        this.speed = speed;
        this.type = type;
        this.textureRound = textureRound;
    }

    Vector2 getCoord() {
        return coord;
    }
}