package ru.gb.space_shooter.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import static ru.gb.space_shooter.game.GameScreen.*;
import static ru.gb.space_shooter.game.GameClass.SCREEN_WIDTH;

class Weapons extends ObjectPool<Round>{
    private TextureRegion[] textureWeapons;
    private Sound soundFire;


    Weapons(int size){
        super(size);
        TextureAtlas ta = GameAssets.getInstance().assetManager.get("atlas.pack",TextureAtlas.class);
        TextureRegion textureWeapon = ta.findRegion("shots");
        textureWeapons = new TextureRegion[10];
        textureWeapons[0] = new TextureRegion(textureWeapon,0,0,ROUND_WIDTH,ROUND_HEIGHT);
        textureWeapons[9] = new TextureRegion(textureWeapon,0,ROUND_HEIGHT,ROUND_WIDTH,ROUND_HEIGHT);
        this.soundFire = GameAssets.getInstance().assetManager.get("laser.wav",Sound.class);
    }

    public void update (float dt){ for (int i = 0; i < activeList.size(); i++) if (activeList.get(i).update(dt).x > (SCREEN_WIDTH - ROUND_WIDTH / 2)) activeList.get(i).setActive(false); }

    public void render (SpriteBatch batch){ for (int i = 0; i < activeList.size(); i++) activeList.get(i).render(batch); }

    void fire(Ship ship){
        if(ship instanceof Player) {
            Round r1 = activateElement();
            r1.setCoordAndSpeed(new Vector2(ship.getCoord().x + SHIP_SIZE, ship.getCoord().y + SHIP_SIZE / 2 - ROUND_HEIGHT / 2), new Vector2(500f, 0f), 1, textureWeapons[0]);
            soundFire.play();
        }
        else if(ship instanceof Bot) {
            Round r = activateElement();
            r.setCoordAndSpeed(new Vector2(ship.getCoord().x - ROUND_WIDTH, ship.getCoord().y + SHIP_SIZE / 2 - ROUND_HEIGHT / 2), new Vector2(-500f, 0f), 10, textureWeapons[9]);
        }
    }

    @Override
    protected Round newObject() {
        return new Round();
    }
}
