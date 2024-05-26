package View;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class SoundManager {
    private Clip clip;
    private float previousVolume = 0;
    private float currentVolume = 0;
    private FloatControl floatControl;
    private boolean mute = false;
    private URL soundURL;

    JSlider slider;

    public SoundManager() {
        volumeButton();
    }

    public void setFile(URL url) {
        try {
            AudioInputStream sound = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(sound);
            floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (Exception exception) {
            System.out.println("Error: Audio file could not be opened.");
            exception.printStackTrace();
        }
    }
    public void play() {
        floatControl.setValue(currentVolume);
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        } else {
            System.out.println("Clip is not initialized.");
        }
    }
    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            System.out.println("Clip is not initialized.");
        }
    }
    public void stop() {
        if (clip != null) {
            clip.stop();
        } else {
            System.out.println("Clip is not initialized.");
        }
    }
    public void volumeUp() {
        if (floatControl != null) {
            currentVolume += 1.0f;
            if (currentVolume > 6.0f) {
                currentVolume = 6.0f;
            }
            floatControl.setValue(currentVolume);
            System.out.println("Current Volume: " + currentVolume);
        }
    }
    public void volumeDown() {
        currentVolume -= 1.0f;
        System.out.println("Current Volume:" + currentVolume);
        if (currentVolume < -80.0f) {
            currentVolume = -80.0f;
        }
        floatControl.setValue(currentVolume);
    }
    public void mute() {
        if (mute == false) {
            previousVolume = currentVolume;
            System.out.println("Current Volume:" + currentVolume);
            currentVolume =  -80.0f;
            floatControl.setValue(currentVolume);
            mute = true;

            slider.setValue(slider.getMinimum());
        } else if (mute == true) {
            currentVolume = previousVolume;
            slider.setValue((int) currentVolume);
            System.out.println("Current Volume:" + currentVolume);
            floatControl.setValue(currentVolume);
            mute = false;
        }
    }
    public void playMusic(URL url) {
        setFile(url);
        play();
        loop();
    }
    private void volumeButton() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1,3));

        JButton playButton = new JButton("PLAY");
        JButton stopButton = new JButton("STOP");
        JButton muteButton = new JButton("Mute");

        frame.add(playButton);
        frame.add(stopButton);
        frame.add(muteButton);
        frame.pack();
        frame.setVisible(true);

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playMusic(soundURL);
            }
        });
        frame.add(playButton);
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stop();
            }
        });
        frame.add(stopButton);
        muteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mute();
            }
        });
        slider = new JSlider(-40, 6);
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                currentVolume = slider.getValue();
                if (currentVolume == -40)  {
                    currentVolume = -80;
                }
                System.out.println("Current Volume: " + currentVolume);
                floatControl.setValue(currentVolume);
            }
        });
        frame.add(muteButton);
        frame.add(slider);


        frame.pack();
        frame.setVisible(true);

        soundURL = getClass().getResource("/Resource/Sounds2/LongTime.wav");
    }
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new SoundManager();
//            }
//        });
//    }
}
