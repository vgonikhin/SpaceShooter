package ru.gb.space_shooter.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Joystick {
    private TextureRegion back;
    private TextureRegion stick;
    private Rectangle rectangleDirection;
    private Rectangle rectangleFire;
    private float joyCenterX, joyCenterY;
    private int lastIdDir;
    private int lastIdFire;
    private Vector2 vs;
    private Vector2 normal;

    public float getPower() {
        return vs.len() / 75.0f;
    }

    public Vector2 getNormal() {
        return normal;
    }

    public boolean getFire(){return lastIdFire>-1;}

    public Joystick() {
        TextureAtlas ta = GameAssets.getInstance().assetManager.get("atlas.pack",TextureAtlas.class);
        TextureRegion texture = ta.findRegion("joystick");
        this.back = new TextureRegion(texture, 0, 0, 200, 200);
        this.stick = new TextureRegion(texture, 0, 200, 50, 50);
        this.rectangleFire = new Rectangle(1080,100,100,100);
        this.rectangleDirection = new Rectangle(50, 50, 200, 200);
        joyCenterX = rectangleDirection.x + rectangleDirection.width / 2;
        joyCenterY = rectangleDirection.y + rectangleDirection.height / 2;
        vs = new Vector2(0, 0);
        normal = new Vector2(0, 0);
        lastIdDir = -1;
        lastIdFire = -1;
    }

    public void render(SpriteBatch batch) {
        batch.setColor(1, 1, 1, 0.5f);
        batch.draw(back, rectangleDirection.x, rectangleDirection.y);
        batch.draw(stick, joyCenterX + vs.x - 25, joyCenterY + vs.y - 25);
        batch.setColor(1, 1, 1, 0.8f);
        batch.draw(stick,rectangleFire.x,rectangleFire.y,0,0,stick.getRegionWidth(),stick.getRegionHeight(),2,2,0);
        batch.setColor(1, 1, 1, 1);
    }

    public void update() {
        MyInputProcessor mip = (MyInputProcessor) Gdx.input.getInputProcessor();
        if (lastIdDir == -1) {
            lastIdDir = mip.isTouchedInArea(rectangleDirection);
        }
        if (lastIdDir > -1) {
            float touchX = mip.getX(lastIdDir);
            float touchY = mip.getY(lastIdDir);
            vs.x = touchX - joyCenterX;
            vs.y = touchY - joyCenterY;
            if (vs.len() > 75) {
                vs.nor().scl(75);
            }
        }
        if (lastIdDir > -1 && !mip.isTouched(lastIdDir)) {
            lastIdDir = -1;
            vs.x = 0;
            vs.y = 0;
        }
        normal.set(vs);
        normal.nor();

        if (lastIdFire == -1) {
            lastIdFire = mip.isTouchedInArea(rectangleFire);
        }
        if (lastIdFire > -1 && !mip.isTouched(lastIdFire)) {
            lastIdFire = -1;
        }
    }
}