package ru.gb.space_shooter.game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Enemies extends ObjectPool<Bot> {
    private GameScreen game;
    private Vector2 botPosition;

    @Override
    protected Bot newObject() {
        return new Bot();
    }

    public Enemies(GameScreen game) {
        super(1);
        this.game = game;
        makeBatch(0);
    }

    private void addAsteroid(){
        Bot b = activateElement();
        b.remake(game);
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
            botPosition = activeList.get(i).getCoord();
            if (botPosition.x < -64) {
                deactivateElement(i);
            }
        }
        checkPool();
        //if (activeList.size() == 0) makeBatch(3);
    }
}