package managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class ScoreManager {

    private int score;
    private int highscore;
    private int level;
    private int multiplier = 1;

    public ScoreManager() {
        loadHighscore();
    }

    public void update() {

    }

    private void loadHighscore () {

        File file = new File("highscore.txt");

        if (!file.exists()) {
                highscore = 0;
                return;
            }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            highscore = Integer.parseInt(br.readLine());
        } catch (Exception e) {
            highscore = 0;
        }
        
    }

    public void addPoints() {
        score = score + (1 * multiplier);
        updateLevel();
        if (score > highscore) saveNewHighScore();
    }

    public void addPoints(int num) {
        score = score + num;
        updateLevel();
        if (score > highscore) saveNewHighScore();
    }

    private void updateLevel() {
        level = score / 5;
    }

    private void saveNewHighScore() {
        try (FileWriter fw = new FileWriter("highscore.txt")) {
            fw.write(String.valueOf(score));
            highscore = score;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMultiplier(int value) {
        multiplier = value;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public int getScore () {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHighscore() {
        return highscore;
    }

    public int getLevel() {
        return level;
    }

    public void reset() {
        score = 0;
        multiplier = 1;
        level = 1;
    }
}
