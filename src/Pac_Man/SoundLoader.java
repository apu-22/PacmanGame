//package Pac_Man;
//
//import javax.sound.sampled.*;
//import java.io.IOException;
//import java.net.URL;
//
//public class SoundLoader {
//
//    private Clip clip;
//
//    public SoundLoader(String soundFilePath) {
//        try {
//            URL soundURL = getClass().getResource(soundFilePath);
//            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
//            clip = AudioSystem.getClip();
//            clip.open(audioStream);
//        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void playLoop() {
//        if (clip != null) {
//            clip.loop(Clip.LOOP_CONTINUOUSLY); // loop infinitely
//            clip.start();
//        }
//    }
//
//    public void stop() {
//        if (clip != null) {
//            clip.stop();
//        }
//    }
//}


//package Pac_Man;
//
//import javax.sound.sampled.*;
//import java.io.IOException;
//import java.net.URL;
//
//public class SoundLoader {
//
//    public static void play(String path) {
//        try {
//            URL soundURL = SoundLoader.class.getResource(path);
//            if (soundURL == null) {
//                System.out.println("Sound not found: " + path);
//                return;
//            }
//
//            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundURL);
//            Clip clip = AudioSystem.getClip();
//            clip.open(audioIn);
//            clip.start();
//
//        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
//            e.printStackTrace();
//        }
//    }
//}


package Pac_Man;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundLoader {

    private Clip clip;

    // Constructor for looping sounds (like background music)
    public SoundLoader(String path) {
        try {
            URL soundURL = getClass().getResource(path);
            if (soundURL == null) {
                System.out.println("Sound not found: " + path);
                return;
            }
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundURL);
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Play the clip once (used for background music start or ambient effects)
    public void playOnce() {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    // Play the clip in loop
    public void playLoop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        }
    }

    // Stop playing the clip
    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }

    // Static method for short sound effects (like eating food)
    public static void play(String path) {
        try {
            URL soundURL = SoundLoader.class.getResource(path);
            if (soundURL == null) {
                System.out.println("Sound not found: " + path);
                return;
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundURL);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
