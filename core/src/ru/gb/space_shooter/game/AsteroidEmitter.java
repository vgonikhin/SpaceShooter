package ru.gb.space_shooter.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static ru.gb.space_shooter.game.GameScreen.*;

public class AsteroidEmitter extends ObjectPool<Asteroid> {
    private GameScreen game;

    @Override
    protected Asteroid newObject() {
        return new Asteroid();
    }

    public AsteroidEmitter(GameScreen game) {
        super(1);
        this.game = game;
        makeBatch(0);
    }

    private void addAsteroid(){
        Asteroid a = activateElement();
        a.remake(game);
    }

    public void makeBatch(int number){
        for (int i = 0; i < number; i++) {
            addAsteroid();
        }
    }

    public void render(SpriteBatch batch){
        for (int i = 0; i < activeList.size(); i++) {
                activeList.get(i).render(batch);
        }
    }

    public void update(float dt){
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
            if (activeList.get(i).getCoord().x < -ASTEROID_SIZE) {
                //activeList.get(i).remake(game);
                deactivateElement(i);
            }
        }
        checkPool();
        //if (activeList.size() < 5) makeBatch(5);
    }
}
