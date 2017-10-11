package ru.gb.space_shooter.game;

public abstract class Ship extends SpaceObject {
    protected float enginePower;
    protected float fireRate;
    protected float fireReady;

    public void tryFire() {
        if ((fireReady += fireRate) > 1) {
            game.weapons.fire(this);
            fireReady--;
        }
    }
}
