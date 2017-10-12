package ru.gb.space_shooter.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

abstract class SpaceObject implements Killable {
    protected GameScreen game;

    Vector2 coord;
    Vector2 speed;
    Circle hitArea;
    int currentHP;
    int maxHP;

    public abstract void render(SpriteBatch batch);

    public abstract void update(float dt);

    public abstract void takeDamage(int dmg);

    Vector2 getSpeed() {
        return speed;
    }

    Vector2 getCoord() {
        return coord;
    }

    Circle getHitArea() {
        return hitArea;
    }

    public int getCurrentHP() {
        return currentHP;
    }
}