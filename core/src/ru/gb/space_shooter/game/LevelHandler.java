package ru.gb.space_shooter.game;

class LevelHandler {
    private GameScreen game;
    private float currentTime;
    private float botGenerationTime;
    private float asteroidGenerationTime;
    private int currentLevel;

    LevelHandler(GameScreen game) {
        this.currentTime = 0.0f;
        this.currentLevel = 1;
        this.game = game;
        this.botGenerationTime = game.levels.get(currentLevel).getBotGenerationTime();
        this.asteroidGenerationTime = game.levels.get(currentLevel).getAsteroidGenerationTime();
    }

    public void update(float dt){
        currentTime += dt;
        if(currentTime>=asteroidGenerationTime){
            game.ae.makeBatch(1);
            asteroidGenerationTime += game.levels.get(currentLevel).getAsteroidGenerationTime();
        }
        if(currentTime>=botGenerationTime){
            game.e.makeBatch(1);
            botGenerationTime += game.levels.get(currentLevel).getBotGenerationTime();
        }
    }

    void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    int getCurrentLevel() {
        return currentLevel;
    }


}
