package View;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

public class SoundManager {
    private Clip clip;
    private float previousVolume = 0;
    private float currentVolume = 0;
    private FloatControl floatControl;
    private boolean mute = false;
    private URL soundURL;
    private JSlider slider;
    private final String[] mySoundURL;

    public SoundManager() {
        mySoundURL = new String[5];
        mySoundURL[0] = "/Resource/Sounds2/IntroGame.wav";
        mySoundURL[1] = "/Resource/Sounds2/LongTime.wav";
        mySoundURL[2] = "/Resource/Sounds2/GamePlay.wav";
        mySoundURL[3] = "/Resource/Sounds2/Lose.wav";
        mySoundURL[4] = "/Resource/Sounds2/GamePlay.wav";
    }

    public void setFile(final int theFile) {
        try {
            InputStream is = getClass().getResourceAsStream(mySoundURL[theFile]);
            AudioInputStream sound = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
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
            System.out.println("Clip is not initialized for play.");
        }
    }
    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            System.out.println("Clip is not initialized for loop.");
        }
    }
    public void stop() {
        if (clip != null) {
            clip.stop();
        } else {
            System.out.println("Clip is not initialized for stop.");
        }
    }
    public void setVolume(float volume) {
        currentVolume = volume;
        if (floatControl != null) {
            floatControl.setValue(currentVolume);
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
    public void playMusic(final int theIndex, final float theVolume) {
        setFile(theIndex);
        setVolume(theVolume);
        play();
        loop();
    }
    public FloatControl getFloatControl() {
        return floatControl;
    }
//    private void volumeButton() {
//        JFrame frame = new JFrame();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setLayout(new GridLayout(1,3));
//
//        JButton playButton = new JButton("PLAY");
//        JButton stopButton = new JButton("STOP");
//        JButton muteButton = new JButton("muteIcon");
//
//        frame.add(playButton);
//        frame.add(stopButton);
//        frame.add(muteButton);
//        frame.pack();
//        frame.setVisible(true);
//
//        playButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                playMusic(0);
//            }
//        });
//
//        stopButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                stop();
//            }
//        });
//
//        muteButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                mute();
//            }
//        });
//
//        slider = new JSlider(-40, 6);
//        slider.addChangeListener(new ChangeListener() {
//            public void stateChanged(ChangeEvent e) {
//                currentVolume = slider.getValue();
//                if (currentVolume == -40)  {
//                    currentVolume = -80;
//                }
//                System.out.println("Current Volume: " + currentVolume);
//                floatControl.setValue(currentVolume);
//            }
//        });
//        frame.add(slider);
//
//        frame.pack();
//        frame.setVisible(true);
//    }

}
