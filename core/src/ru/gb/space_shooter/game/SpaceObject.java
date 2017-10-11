package ru.gb.space_shooter.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public abstract class SpaceObject implements Killable {
    protected GameScreen game;

    protected Vector2 coord;
    protected Vector2 speed;
    protected Circle hitArea;
    protected int currentHP;
    protected int maxHP;

    public abstract void render(SpriteBatch batch);

    public abstract void update(float dt);

    public abstract void takeDamage(int dmg);

    public Vector2 getSpeed() {
        return speed;
    }

    public Vector2 getCoord() {
        return coord;
    }

    public Circle getHitArea() {
        return hitArea;
    }

    public int getCurrentHP() {
        return currentHP;
    }
}