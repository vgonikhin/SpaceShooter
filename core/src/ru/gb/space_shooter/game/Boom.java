package ru.gb.space_shooter.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Boom {
    private TextureRegion[] frames;
    private TextureRegion[] framesbw;
    private Vector2 position;
    private boolean active;
    private float time;
    private float maxFrames;
    private float timePerFrame;
    private float angle;
    private int type;

    public boolean isActive() {
        return active;
    }

    public Boom(TextureRegion[] frames,TextureRegion[] framesbw) {
        this.frames = frames;
        this.framesbw = framesbw;
        this.position = new Vector2(0, 0);
        this.active = false;
        this.maxFrames = frames.length;
        this.timePerFrame = 0.02f;
    }

    public void render(SpriteBatch batch) {
        if(type==1)
            batch.draw(frames[(int) (time / timePerFrame)], position.x - 32, position.y - 32, 32, 32, 64, 64, 2.0f, 2.0f, angle);
        else if(type==0)
            batch.draw(framesbw[(int) (time / timePerFrame)], position.x - 32, position.y - 32, 32, 32, 64, 64, 2.0f, 2.0f, angle);
    }

    public void update(float dt) {
        time += dt;
        if (time > maxFrames * timePerFrame) {
            active = false;
        }
    }

    public void activate(Vector2 position, int type) {
        this.active = true;
        this.time = 0.0f;
        this.position.set(position);
        this.angle = (float)Math.random() * 360.0f;
        this.type = type;
    }
}
