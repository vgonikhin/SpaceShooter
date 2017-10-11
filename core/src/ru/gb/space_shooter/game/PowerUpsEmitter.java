package ru.gb.space_shooter.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class PowerUpsEmitter{
    private PowerUp[] powerUps;

    public PowerUpsEmitter() {
        this.powerUps = new PowerUp[50];
        for (int i = 0; i < powerUps.length; i++) {
            powerUps[i] = new PowerUp();
        }
    }

    public void render(SpriteBatch batch){
        for (int i = 0; i < powerUps.length; i++) {
            if(powerUps[i].isActive())
                powerUps[i].render(batch);
        }
    }

    public void update(float dt, Vector2 v){
        for (int i = 0; i < powerUps.length; i++) {
            if(powerUps[i].isActive())
                powerUps[i].update(dt,v);
        }
    }

    public void generate(Vector2 v, float probability){
        if(MathUtils.random()<probability) {
            for (int i = 0; i < powerUps.length; i++) {
                if (!powerUps[i].isActive()) {
                    PowerUp.Type t = PowerUp.Type.values()[(int) (MathUtils.random() * 4)];
                    powerUps[i].activate(v, t);
                    break;
                }
            }
        }
    }

    public PowerUp[] getPowerUps() {
        return powerUps;
    }
}
