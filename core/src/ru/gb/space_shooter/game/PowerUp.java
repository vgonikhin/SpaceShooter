package ru.gb.space_shooter.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class PowerUp implements Poolable {
    enum Type{
        MONEY10(0),MONEY25(1),MONEY50(2),MEDKIT(3);

        private int number;

        Type(int number){
            this.number = number;
        }
    }

    private Vector2 coord;
    private Vector2 speed;
    private boolean active;
    private float time;
    private float maxTime;
    private TextureRegion[][] textureRegion;
    private Type type;

    public PowerUp() {
        this.coord = new Vector2(0,0);
        this.speed = new Vector2(0,0);
        this.active = false;
        this.time = 0f;
        this.maxTime = 3f;
        TextureAtlas ta = GameAssets.getInstance().assetManager.get("atlas.pack",TextureAtlas.class);
        TextureRegion powerUpTexture = ta.findRegion("powerUps");
        this.textureRegion = powerUpTexture.split(32,32);
        this.type = Type.MONEY10;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void update(float dt, Vector2 v){
        time+=dt;
        coord.mulAdd(new Vector2(v.x+16.0f*speed.x,0.5f*v.y+16.0f*speed.y),-0.06f*dt);
        if(time>maxTime)
            this.setActive(false);
    }

    public void render(SpriteBatch batch){
        batch.draw(textureRegion[0][type.number],coord.x-16,coord.y-16);
    }

    public void activate(Vector2 coord, Type type){
        this.coord.set(coord);
        this.speed.x = MathUtils.random(40.0f,60.0f);
        this.speed.y = MathUtils.random(-30.0f,30.0f);
        this.time = 0f;
        this.type = type;
        this.setActive(true);
    }

    public Vector2 getCoord() {
        return coord;
    }

    public void use(Player player) {
        switch (type){
            case MONEY10:
                player.addMoney(10);
                break;
            case MONEY25:
                player.addMoney(25);
                break;
            case MONEY50:
                player.addMoney(50);
                break;
            case MEDKIT:
                player.takeDamage(-50);
                break;
        }
        setActive(false);
    }


}
