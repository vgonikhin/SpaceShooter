package ru.gb.space_shooter.game;

abstract class Ship extends SpaceObject {
    float enginePower;
    float fireRate;
    float fireReady;

    void tryFire() {
        if ((fireReady += fireRate) > 1) {
            game.weapons.fire(this);
            fireReady--;
        }
    }
}
