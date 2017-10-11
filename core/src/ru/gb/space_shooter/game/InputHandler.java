package ru.gb.space_shooter.game;

import com.badlogic.gdx.Gdx;

import static ru.gb.space_shooter.game.GameClass.SCREEN_HEIGHT;

public class InputHandler {
    public static boolean isTouched() {
        return Gdx.input.isTouched();
    }

    public static int getX() {
            return Gdx.input.getX();
    }

    public static int getY() {
        return SCREEN_HEIGHT - Gdx.input.getY();
    }

    public static boolean isKeyPressed(int key){
        return Gdx.input.isKeyPressed(key);
    }
}
