package ru.gb.space_shooter.game;

public class LevelInfo {
    private int level;
    private float asteroidGenerationTime;
    private float botGenerationTime;
    private int asteroidHpMin;
    private int asteroidHpMax;
    private float asteroidSpeedMin;
    private float asteroidSpeedMax;

    public int getLevel() {
        return level;
    }

    public float getAsteroidGenerationTime() {
        return asteroidGenerationTime;
    }

    public float getBotGenerationTime() {
        return botGenerationTime;
    }

    public int getAsteroidHpMin() {
        return asteroidHpMin;
    }

    public int getAsteroidHpMax() {
        return asteroidHpMax;
    }

    public float getAsteroidSpeedMin() {
        return asteroidSpeedMin;
    }

    public float getAsteroidSpeedMax() {
        return asteroidSpeedMax;
    }

    public LevelInfo(int level, float asteroidGenerationTime, float botGenerationTime,int asteroidHpMin, int asteroidHpMax, float asteroidSpeedMin, float asteroidSpeedMax) {
        this.level = level;
        this.asteroidGenerationTime = asteroidGenerationTime;
        this.botGenerationTime = botGenerationTime;
        this.asteroidHpMin = asteroidHpMin;
        this.asteroidHpMax = asteroidHpMax;
        this.asteroidSpeedMin = asteroidSpeedMin;
        this.asteroidSpeedMax = asteroidSpeedMax;
    }
}
