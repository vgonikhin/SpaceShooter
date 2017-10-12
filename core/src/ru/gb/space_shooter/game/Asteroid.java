package ru.gb.space_shooter.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static ru.gb.space_shooter.game.GameClass.*;
import static ru.gb.space_shooter.game.GameScreen.ASTEROID_SIZE;

class Asteroid extends SpaceObject implements Poolable {

    private float scale;
    private int angle;
    private int rotation;
    private TextureRegion textureAsteroid;
    private boolean active;
    private Vector2 v;

    Asteroid() {
        this.coord = new Vector2(0,0);
        this.speed = new Vector2(0,0);
        this.scale = MathUtils.random(0.5f,2.0f);
        this.rotation = MathUtils.random(-1,1);
        this.active = false;
        this.hitArea = new Circle(this.coord,(ASTEROID_SIZE/2)*scale);
        this.maxHP = 2*Math.round(scale);
        this.currentHP = this.maxHP;
        this.v = new Vector2(0,0);
    }

    void remake(GameScreen game){
        this.game = game;
        float x = MathUtils.random(1,1.5f)* SCREEN_WIDTH;
        float y = MathUtils.random()*SCREEN_HEIGHT;
        float vx = MathUtils.random(game.levels.get(game.lh.getCurrentLevel()).getAsteroidSpeedMin(),game.levels.get(game.lh.getCurrentLevel()).getAsteroidSpeedMax());
        float vy = MathUtils.random(-game.levels.get(game.lh.getCurrentLevel()).getAsteroidSpeedMax(),game.levels.get(game.lh.getCurrentLevel()).getAsteroidSpeedMax());

        this.coord.x = x;
        this.coord.y = y;
        this.speed.x = vx;
        this.speed.y = vy;
        this.scale = MathUtils.random(0.5f,2.0f);
        this.rotation = MathUtils.random(-1,1);
        this.hitArea = new Circle(this.coord,(ASTEROID_SIZE/2)*scale);
        this.maxHP = 2*Math.round(scale);
        this.currentHP = this.maxHP;
        TextureAtlas ta = GameAssets.getInstance().assetManager.get("atlas.pack",TextureAtlas.class);
        this.textureAsteroid = ta.findRegion("asteroid64");
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean isActive(){
        return active;
    }

    @Override
    public void render(SpriteBatch batch){ batch.draw(textureAsteroid, coord.x, coord.y,ASTEROID_SIZE/2,ASTEROID_SIZE/2,ASTEROID_SIZE,ASTEROID_SIZE,scale,scale,angle); }

    @Override
    public void update(float dt){
        v.set(game.player.getSpeed().x+16.0f*speed.x,0.5f*game.player.getSpeed().y+16.0f*speed.y);
        coord.mulAdd(v,-0.06f*dt);
        angle += speed.x*dt*rotation;
        if(angle==-360||angle==360) angle = 0;
        if(coord.y>(SCREEN_HEIGHT - ASTEROID_SIZE*scale -(ASTEROID_SIZE/2 - ASTEROID_SIZE*scale/2))){
            coord.y = SCREEN_HEIGHT - ASTEROID_SIZE*scale - (ASTEROID_SIZE/2 - ASTEROID_SIZE*scale/2);
            if(speed.y<0) speed.y*=-1;
        }
        if(coord.y< (ASTEROID_SIZE*scale/2 - ASTEROID_SIZE/2)){
            coord.y = (ASTEROID_SIZE*scale/2 - ASTEROID_SIZE/2);
            if(speed.y>0) speed.y*=-1;
        }
        hitArea.setPosition(coord);
    }

    @Override
    public void takeDamage(int dmg) {
        hitArea.setRadius((ASTEROID_SIZE/2)*scale);
        currentHP -= dmg;
        if((scale -= (float)dmg/2)<0.5f) currentHP=0;
    }
}