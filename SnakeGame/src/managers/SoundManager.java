package managers;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundManager {

    public enum SoundEffect {

        EAT_FOOD("eat.wav"),
        POWERUP("powerUp.wav"),
        COLLISION("collision.wav"),
        GAME_OVER("gameover.wav"),
        BACKGROUND_MUSIC("backgroundMusic.wav");

        private final String fileName;

        SoundEffect(String fileName) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }

    }

    private static final String SOUND_FOLDER = "/resources/sounds/";

    public static void playSound(SoundEffect sound) {
        new Thread(() -> {
            try {

                URL soundURL = SoundManager.class.getResource(SOUND_FOLDER + sound.getFileName());
                if (soundURL == null) {
                    System.out.println("Sound file not found: " + sound.getFileName());
                    return;
                }

                AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();

            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e ) {
                System.err.println("Error playing sound: " + e.getMessage());
            }

        }).start();

    }

}
