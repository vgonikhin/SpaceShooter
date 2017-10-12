package ru.gb.space_shooter.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

class AsteroidEmitter extends ObjectPool<Asteroid> {
    private GameScreen game;

    @Override
    protected Asteroid newObject() {
        return new Asteroid();
    }

    AsteroidEmitter(GameScreen game) {
        super(1);
        this.game = game;
        makeBatch(0);
    }

    private void addAsteroid(){
        Asteroid a = activateElement();
        a.remake(game);
    }

    void makeBatch(int number){
        for (int i = 0; i < number; i++) addAsteroid();
    }

    public void render(SpriteBatch batch){ for (int i = 0; i < activeList.size(); i++) activeList.get(i).render(batch); }

    public void update(float dt){
        for (int i = 0; i < activeList.size(); i++) {
            activeList.get(i).update(dt);
            if (activeList.get(i).getCoord().x < -(activeList.get(i).getHitArea().radius*2)) deactivateElement(i);
        }
        checkPool();
    }
}
