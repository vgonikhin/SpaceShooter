package ru.gb.space_shooter.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import static ru.gb.space_shooter.game.GameClass.*;

public class Bot extends Ship implements Poolable {
    private TextureRegion botTexture;
    private boolean active;

    private Vector2 nextCoord = new Vector2();
    private Vector2[] route;
    private int nextPoint;

    public Bot() {
        this.coord = new Vector2(0, 0);
        this.enginePower = 200;
        this.route = new Vector2[5];
        route[0] = new Vector2(1280f, 700f);
        route[1] = new Vector2(1080f, 200f);
        route[2] = new Vector2(800f, 200f);
        route[3] = new Vector2(320f, 570f);
        route[4] = new Vector2(-700f, 570f);
        this.nextPoint = 0;
        this.hitArea = new Circle(coord, 32f);
        this.maxHP = 3;
        this.currentHP = this.maxHP;
        this.active = false;
        this.fireRate = 0;
        this.fireReady = 0;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(botTexture, coord.x, coord.y, 32, 32, 64, 64, 1, 1, 0);
    }

    @Override
    public void update(float dt) {
        nextCoord.set(route[nextPoint]);
        Vector2 direction = nextCoord.sub(coord);
        this.coord.mulAdd(direction.nor(), enginePower * dt);
        if (coord.x < route[nextPoint].x) nextPoint++;
        hitArea.setPosition(coord);
        tryFire();
    }

    public void remake(GameScreen game) {
        this.game = game;
        TextureAtlas ta = GameAssets.getInstance().assetManager.get("atlas.pack",TextureAtlas.class);
        this.botTexture = ta.findRegion("alien64");
        float x = MathUtils.random(1, 1.5f) * SCREEN_WIDTH;
        float y = MathUtils.random() * SCREEN_HEIGHT;
        this.nextPoint = 0;
        this.coord = new Vector2(x, y);
        this.maxHP = 3;
        this.currentHP = this.maxHP;
        this.fireRate = 0.025f;
        this.fireReady = 0;
    }

    @Override
    public void takeDamage(int dmg) {
        if((currentHP -= dmg)<0)
            currentHP = 0;
    }

    @Override
    public void setActive(boolean active) {
        this.active = true;
    }

    @Override
    public boolean isActive() {
        return active;
    }
}