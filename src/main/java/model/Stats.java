package model;

public class Stats {

    private static int score = 0;
    private static int lives = 3;

    public static int getScore() {
        return score;
    }

    public static void increaseScore() {
        Stats.score++;
    }

    public static int getLives() {
        return lives;
    }

    public static void decreaseLives() {
        Stats.lives -= 1;
    }

    public static void reset() {
        Stats.lives = 3;
        Stats.score = 0;
    }

}
