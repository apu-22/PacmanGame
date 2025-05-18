package Pac_Man;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundLoader {

    private Clip clip;

    public SoundLoader(String soundFilePath) {
        try {
            URL soundURL = getClass().getResource(soundFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playLoop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY); // loop infinitely
            clip.start();
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }
}
