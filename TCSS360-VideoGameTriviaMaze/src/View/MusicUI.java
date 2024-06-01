package View;

import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;

public class MusicUI {
    private final SoundManager mySoundManager;
    private JSlider mySlider;
    private static JFrame myFrame;
    private float currentVolume = 0;
    private FloatControl floatControl;
    private boolean myShowUI;

    MusicUI(SoundManager theSoundManager) {
        if (myFrame != null) {
            myFrame.dispose();
        }
        mySoundManager = theSoundManager;
        initializeUI();
    }
    public void showUI() {
        if (myFrame != null) {
            myFrame.dispose();
        }
        initializeUI();
    }
    public void hideUI() {
        if (myFrame != null) {
            myFrame.dispose();
            myFrame = null;
        }
    }
    private void initializeUI() {
        myFrame = new JFrame("Music UI");
        myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        myFrame.setLayout(new GridLayout(1, 3));
        myFrame.setResizable(false);

        JButton playButton = new JButton("PLAY");
        JButton stopButton = new JButton("STOP");
        JButton muteButton = new JButton("MUTE");

        myFrame.add(playButton);
        myFrame.add(stopButton);
        myFrame.add(muteButton);

        playButton.addActionListener(e -> {
            mySoundManager.play();
            floatControl = mySoundManager.getMyFloatControl();
            mySlider.setEnabled(true);
        });

        stopButton.addActionListener(e -> {
            mySoundManager.stop();
            mySlider.setEnabled(false);
        });

        muteButton.addActionListener(e -> mySoundManager.mute());

        mySlider = new JSlider(-40, 6);
        mySlider.setEnabled(false); // Initially disable the slider
        mySlider.addChangeListener(e -> {
            if (floatControl != null) {
                currentVolume = mySlider.getValue();
                if (currentVolume == -40) {
                    currentVolume = -80;
                }
                System.out.println("Current Volume: " + currentVolume);
                floatControl.setValue(currentVolume);
                mySoundManager.setVolume(currentVolume);
                SoundManager.getInstance().setSlider(mySlider);
            }
        });
        myFrame.add(mySlider);

        myFrame.pack();
        myFrame.setVisible(true);
    }
}