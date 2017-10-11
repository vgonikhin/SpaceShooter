package ru.gb.space_shooter.game;

public interface Killable {
    void takeDamage(int dmg);
    int getCurrentHP();
}
