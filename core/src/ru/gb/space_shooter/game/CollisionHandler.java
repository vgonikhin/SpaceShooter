package ru.gb.space_shooter.game;

import com.badlogic.gdx.math.Vector2;

import static ru.gb.space_shooter.game.GameScreen.ASTEROID_SIZE;
import static ru.gb.space_shooter.game.GameScreen.ROUND_HEIGHT;
import static ru.gb.space_shooter.game.GameScreen.ROUND_WIDTH;

public class CollisionHandler {
    private static Vector2 helper1 = new Vector2(0,0);
    private static Vector2 helper2 = new Vector2(0,0);
    private static Vector2 bv = new Vector2(0,0);
    private static Round round;

    public static void checkCollisions(GameScreen game){
        for (int i = 0; i < game.ae.activeList.size(); i++) {
            if (checkPlayerAsteroidCollision(game.ae, game.player, i)) {
                game.player.takeDamage((int) (game.ae.activeList.get(i).getCurrentHP() * (game.ae.activeList.get(i).getSpeed().len() / 100)));
                game.ae.activeList.get(i).takeDamage(1);
                if (game.ae.activeList.get(i).getCurrentHP()<1) {
                    bv.set(game.ae.activeList.get(i).getCoord().x+ASTEROID_SIZE/2,game.ae.activeList.get(i).getCoord().y+ASTEROID_SIZE/2);
                    game.be.setup(bv,game.player.getCoord(),0);
                    game.ae.deactivateElement(i);
                    game.player.addScore(10);
                }
                break;
            }
            int n = checkAsteroidRoundCollision(game.ae, game.weapons, i);
            if (n >= 0) {
                game.ae.activeList.get(i).takeDamage(1);
                if (game.ae.activeList.get(i).getCurrentHP()<1) {
                    if(game.weapons.activeList.get(n).getType()==1) {
                        game.player.addScore(10);
                        game.pue.generate(game.ae.activeList.get(i).getCoord(),0.1f);
                    }
                    bv.set(game.ae.activeList.get(i).getCoord().x+ASTEROID_SIZE/2,game.ae.activeList.get(i).getCoord().y+ASTEROID_SIZE/2);
                    game.be.setup(bv,game.player.getCoord(),0);
                    game.ae.deactivateElement(i);
                }
                //game.getParticleEmitter().setup(6,game.weapons.activeList.get(n).getCoord().x+ROUND_WIDTH/2, game.weapons.activeList.get(n).getCoord().y+ROUND_HEIGHT/2, 0, 0, 0.25f, 1.6f, 0.8f, 1.0f, 0.75f, 0.0f, 1.0f, 1.0f, 0.25f, 0.125f, 0.35f);
                game.weapons.deactivateElement(n);
                break;
            }
        }
        for (int i = 0; i < game.e.activeList.size(); i++) {
            int n = checkBotRoundCollision(game.e, game.weapons, i);
            if (n >= 0) {
                game.e.activeList.get(i).takeDamage(1);
                if(game.e.activeList.get(i).getCurrentHP()<=0) {
                    game.pue.generate(game.e.activeList.get(i).getCoord(),0.5f);
                    bv.set(game.e.activeList.get(i).getCoord().x+32,game.e.activeList.get(i).getCoord().y+32);
                    game.be.setup(bv,game.player.getCoord(),1);
                    game.e.deactivateElement(i);
                    game.player.addScore(50);
                }
                game.getParticleEmitter().setup(6,game.weapons.activeList.get(n).getCoord().x+ROUND_WIDTH, game.weapons.activeList.get(n).getCoord().y+ROUND_HEIGHT/2, 0, 0, 0.25f, 1.6f, 0.8f, 1.0f, 0.75f, 0.0f, 0.5f, 1.0f, 0.25f, 0.125f, 0.2f);
                game.weapons.deactivateElement(n);
                break;
            }
        }
        int prc = checkPlayerRoundCollision(game.player, game.weapons);
        if (prc >= 0) {
            game.player.takeDamage(3);
            game.getParticleEmitter().setup(6,game.weapons.activeList.get(prc).getCoord().x+ROUND_WIDTH*0.6f, game.weapons.activeList.get(prc).getCoord().y+ROUND_HEIGHT/2, 0, 0, 0.25f, 1.6f, 0.8f, 1.0f, 0.75f, 0.0f, 0.4f, 0.0f, 1.0f, 0.0f, 0.2f);
            game.weapons.deactivateElement(prc);
        }
        int ppuc = checkPlayerPowerUpCollision(game.player, game.pue);
        if (ppuc >= 0) game.pue.getPowerUps()[ppuc].use(game.player);
    }

    private static boolean checkPlayerAsteroidCollision(AsteroidEmitter ae, Player player, int i){
        helper1.set(ae.activeList.get(i).getHitArea().x, ae.activeList.get(i).getHitArea().y);
        helper2.set(player.getHitArea().x, player.getHitArea().y);
        if(player.getHitArea().overlaps(ae.activeList.get(i).getHitArea())){
            float dist = helper1.dst(helper2);
            float overlap = ae.activeList.get(i).getHitArea().radius+ player.getHitArea().radius - dist;
            Vector2 nd = helper1.sub(helper2).nor();
            ae.activeList.get(i).getCoord().mulAdd(new Vector2(0, nd.y), overlap/2);
            player.getCoord().mulAdd(new Vector2(0, nd.y), -overlap/2);
            ae.activeList.get(i).getSpeed().y*=-1;
            return true;
        } else
        return false;
    }

    private static int checkAsteroidRoundCollision(AsteroidEmitter ae, Weapons weapons, int index){
        for (int i = 0; i < weapons.activeList.size(); i++) {
            if (ae.activeList.get(index).getHitArea().contains(weapons.activeList.get(i).getCoord().x, weapons.activeList.get(i).getCoord().y - ROUND_HEIGHT-6))
                return i;
        }
        return -1;
    }

    private static int checkBotRoundCollision(Enemies e, Weapons weapons, int index){
        for (int i = 0; i < weapons.activeList.size(); i++) {
            round = weapons.activeList.get(i);
            if(round.getType()==1)
                if (e.activeList.get(index).getHitArea().contains(round.getCoord().x, round.getCoord().y - ROUND_HEIGHT-6))
                    return i;
        }
        return -1;
    }

    private static int checkPlayerRoundCollision(Player player, Weapons weapons){
        for (int i = 0; i < weapons.activeList.size(); i++) {
            round = weapons.activeList.get(i);
            if(round.getType()==10)
                if (player.getHitArea().contains(round.getCoord().x-ROUND_WIDTH/2, round.getCoord().y - ROUND_HEIGHT-6))
                    return i;
        }
        return -1;
    }

    private static int checkPlayerPowerUpCollision(Player player, PowerUpsEmitter pue){
        for (int i = 0; i < pue.getPowerUps().length; i++) {
            if(pue.getPowerUps()[i].isActive()) {
                PowerUp pu = pue.getPowerUps()[i];
                if (player.getHitArea().contains(pu.getCoord().x-32, pu.getCoord().y-32))
                    return i;
            }
        }
        return -1;
    }
}