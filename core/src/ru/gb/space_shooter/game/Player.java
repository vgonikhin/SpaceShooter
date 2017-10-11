package ru.gb.space_shooter.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.StringBuilder;

import static ru.gb.space_shooter.game.GameClass.*;
import static ru.gb.space_shooter.game.GameScreen.*;

public class Player extends Ship {
    private TextureRegion shipTexture;
    private int lives;
    private int score;
    private int money;

    private Joystick joystick;
    private HUD hud;

    class HUD {
        private TextureRegion hpBarTexture;
        private TextureRegion greenBarTexture;
        private TextureRegion redBarTexture;

        private StringBuilder hudString;

        public HUD (TextureAtlas ta){
            this.hpBarTexture = ta.findRegion("healthbars3");
            this.greenBarTexture = new TextureRegion(hpBarTexture,0,0,320,35);
            this.redBarTexture = new TextureRegion(hpBarTexture,0,35,320,35);
            this.hudString = new StringBuilder(50);
        }

        public void render(SpriteBatch batch){
            batch.draw(redBarTexture,40, SCREEN_HEIGHT-75);
            batch.draw(greenBarTexture,40, SCREEN_HEIGHT-75,320*getHPRatio(),35);
            hudString.setLength(0);
            hudString.append("X").append(lives);
            game.fnt.draw(batch,hudString,50+redBarTexture.getRegionWidth(), SCREEN_HEIGHT-47);
            hudString.setLength(0);
            hudString.append("SCORE: ").append(score).append("  MONEY: ").append(money);
            game.fnt.draw(batch,hudString,45, SCREEN_HEIGHT-50-redBarTexture.getRegionHeight());
            hudString.setLength(0);
            hudString.append("LEVEL: ").append(game.lh.getCurrentLevel()+1);
            game.fnt.draw(batch,hudString,545, SCREEN_HEIGHT-47);
            joystick.render(batch);
        }
    }


    public Player(GameScreen game) {
        this.game = game;
        this.coord = new Vector2(100, SCREEN_HEIGHT/2);
        this.speed = new Vector2(0,0);
        TextureAtlas ta = GameAssets.getInstance().assetManager.get("atlas.pack",TextureAtlas.class);
        this.shipTexture = ta.findRegion("ship64");
        this.enginePower = 400;
        this.hitArea = new Circle(coord,SHIP_SIZE/2f);
        this.maxHP = 150;
        this.currentHP = this.maxHP;
        this.fireRate = 0.075f;
        this.fireReady = 0;
        this.lives = 3;
        this.score = 0;
        this.money = 0;
        this.hud = new HUD(ta);
        this.joystick = new Joystick();
    }

    @Override
    public void render(SpriteBatch batch){
        batch.draw(shipTexture,coord.x,coord.y);
        hud.render(batch);
    }

    @Override
    public void update(float dt){
        joystick.update();
        speed.x=0;
        speed.y=0;
        if(Math.abs(joystick.getPower())>0.1f){
            speed.mulAdd(joystick.getNormal(),enginePower*joystick.getPower());
        }
        if(InputHandler.isKeyPressed(Input.Keys.D)||InputHandler.isKeyPressed(Input.Keys.RIGHT)){ speed.x=enginePower;}
		if(InputHandler.isKeyPressed(Input.Keys.A)||InputHandler.isKeyPressed(Input.Keys.LEFT)){ speed.x=-enginePower;}
		if(InputHandler.isKeyPressed(Input.Keys.W)||InputHandler.isKeyPressed(Input.Keys.UP)){ speed.y=enginePower;}
		if(InputHandler.isKeyPressed(Input.Keys.S)||InputHandler.isKeyPressed(Input.Keys.DOWN)){ speed.y=-enginePower;}

        this.coord.mulAdd(speed,dt);
        if(coord.x>(SCREEN_WIDTH- SHIP_SIZE))coord.x=(SCREEN_WIDTH- SHIP_SIZE);
        if(coord.x<0)coord.x=0;
        if(coord.y>(SCREEN_HEIGHT- SHIP_SIZE))coord.y=(SCREEN_HEIGHT- SHIP_SIZE);
        if(coord.y<0)coord.y=0;

        hitArea.setPosition(coord);
        if(InputHandler.isKeyPressed(Input.Keys.F)||joystick.getFire()) tryFire();

        float size = speed.len() / 360.0f;
        if (speed.x > 0 && speed.y<0.1*enginePower&&speed.y>-0.1*enginePower) {
            game.getParticleEmitter().setup(coord.x+4, coord.y+26, -MathUtils.random(5.0f, 20.0f), MathUtils.random(-24.0f, 24.0f), 0.15f, size*1.5f, 0.6f, 1.0f, 0.25f, 0.125f, 1.0f, 1.0f, 0.75f, 0.0f, 0.3f);
            game.getParticleEmitter().setup(coord.x, coord.y+26, -MathUtils.random(5.0f, 20.0f), MathUtils.random(-24.0f, 24.0f), 0.15f, size, 0.6f, 1.0f, 0.25f, 0.125f, 1.0f, 1.0f, 0.75f, 0.0f, 0.3f);
            game.getParticleEmitter().setup(coord.x+4, coord.y+38, -MathUtils.random(5.0f, 20.0f), MathUtils.random(-24.0f, 24.0f), 0.15f, size*1.5f, 0.6f, 1.0f, 0.25f, 0.125f, 1.0f, 1.0f, 0.75f, 0.0f, 0.3f);
            game.getParticleEmitter().setup(coord.x, coord.y+38, -MathUtils.random(5.0f, 20.0f), MathUtils.random(-24.0f, 24.0f), 0.15f, size, 0.6f, 1.0f, 0.25f, 0.125f, 1.0f, 1.0f, 0.75f, 0.0f, 0.3f);
        }
        if (speed.x > 0 && speed.y>=0.1*enginePower) {
            game.getParticleEmitter().setup(coord.x+4, coord.y+24, -MathUtils.random(5.0f, 20.0f), MathUtils.random(-24.0f, 24.0f), 0.15f, size, 0.6f, 1.0f, 0.25f, 0.125f, 1.0f, 1.0f, 0.75f, 0.0f, 0.3f);
            game.getParticleEmitter().setup(coord.x, coord.y+24, -MathUtils.random(5.0f, 20.0f), MathUtils.random(-24.0f, 24.0f), 0.15f, size, 0.6f, 1.0f, 0.25f, 0.125f, 1.0f, 1.0f, 0.75f, 0.0f, 0.3f);
            game.getParticleEmitter().setup(coord.x+32, coord.y, -MathUtils.random(5.0f, 20.0f), MathUtils.random(-24.0f, 24.0f), 0.1f, size, 0.6f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.75f, 0.0f, 0.1f);
        }
        if (speed.x > 0 && speed.y<=-0.1*enginePower) {
            game.getParticleEmitter().setup(coord.x+4, coord.y+40, -MathUtils.random(5.0f, 20.0f), MathUtils.random(-24.0f, 24.0f), 0.15f, size, 0.6f, 1.0f, 0.25f, 0.125f, 1.0f, 1.0f, 0.75f, 0.0f, 0.3f);
            game.getParticleEmitter().setup(coord.x, coord.y+40, -MathUtils.random(5.0f, 20.0f), MathUtils.random(-24.0f, 24.0f), 0.15f, size, 0.6f, 1.0f, 0.25f, 0.125f, 1.0f, 1.0f, 0.75f, 0.0f, 0.3f);
            game.getParticleEmitter().setup(coord.x+32, coord.y+64, -MathUtils.random(5.0f, 20.0f), MathUtils.random(-24.0f, 24.0f), 0.1f, size, 0.6f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.75f, 0.0f, 0.1f);
        }
        if (speed.x < 0 && speed.y<0.1*enginePower&&speed.y>-0.1*enginePower) {
            game.getParticleEmitter().setup(coord.x+60, coord.y+21, -MathUtils.random(5.0f, 20.0f), MathUtils.random(-12.0f, 12.0f), 0.1f, size*1.5f, 0.6f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.1f);
            game.getParticleEmitter().setup(coord.x+60, coord.y+43, -MathUtils.random(5.0f, 20.0f), MathUtils.random(-12.0f, 12.0f), 0.1f, size*1.5f, 0.6f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.1f);
        }
        if (speed.x < 0 && speed.y>=0.1*enginePower) {
            game.getParticleEmitter().setup(coord.x+60, coord.y+19, -MathUtils.random(5.0f, 20.0f), MathUtils.random(-12.0f, 12.0f), 0.1f, size, 0.6f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.1f);
            game.getParticleEmitter().setup(coord.x+32, coord.y, -MathUtils.random(5.0f, 20.0f), MathUtils.random(-24.0f, 24.0f), 0.1f, size, 0.6f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.75f, 0.0f, 0.1f);
        }
        if (speed.x < 0 && speed.y<=-0.1*enginePower) {
            game.getParticleEmitter().setup(coord.x+60, coord.y+45, -MathUtils.random(5.0f, 20.0f), MathUtils.random(-12.0f, 12.0f), 0.1f, size, 0.6f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.1f);
            game.getParticleEmitter().setup(coord.x+32, coord.y+64, -MathUtils.random(5.0f, 20.0f), MathUtils.random(-24.0f, 24.0f), 0.1f, size, 0.6f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.75f, 0.0f, 0.1f);
        }
        if (speed.x == 0 && speed.y>0) {
            game.getParticleEmitter().setup(coord.x+32, coord.y, -MathUtils.random(5.0f, 20.0f), MathUtils.random(-24.0f, 24.0f), 0.1f, size*1.5f, 0.6f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.75f, 0.0f, 0.1f);
            game.getParticleEmitter().setup(coord.x+32, coord.y, -MathUtils.random(5.0f, 20.0f), MathUtils.random(-24.0f, 24.0f), 0.1f, size*1.5f, 0.6f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.75f, 0.0f, 0.1f);
        }
        if (speed.x == 0 && speed.y<0) {
            game.getParticleEmitter().setup(coord.x+32, coord.y+64, -MathUtils.random(5.0f, 20.0f), MathUtils.random(-24.0f, 24.0f), 0.1f, size*1.5f, 0.6f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.75f, 0.0f, 0.1f);
            game.getParticleEmitter().setup(coord.x+32, coord.y+64, -MathUtils.random(5.0f, 20.0f), MathUtils.random(-24.0f, 24.0f), 0.1f, size*1.5f, 0.6f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.75f, 0.0f, 0.1f);
        }
    }

    @Override
    public void takeDamage(int dmg) {
        if((currentHP -= dmg)<0) {
            if(lives>1)
                currentHP = maxHP;
            else
                currentHP = 0;
            lives--;
        } else if((currentHP -= dmg)>maxHP) currentHP=maxHP;
    }

    public int getLives() {
        return lives;
    }

    public void addScore(int amount){
        score += amount;
    }

    public void addMoney(int amount){
        money += amount;
    }

    private float getHPRatio(){
        return ((float)currentHP/(float)maxHP);
    }

    public int getScore(){ return score; }
}