package View;

import javax.sound.sampled.FloatControl;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MusicUI {
    private SoundManager mySoundManager;
    private JSlider slider;
    private float currentVolume = 0;
    private FloatControl floatControl;

    public MusicUI() {
        mySoundManager = new SoundManager();
        volumeButton();
    }

    private void volumeButton() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(1, 3));

        JButton playButton = new JButton("PLAY");
        JButton stopButton = new JButton("STOP");
        JButton muteButton = new JButton("MUTE");

        frame.add(playButton);
        frame.add(stopButton);
        frame.add(muteButton);
        frame.pack();
        frame.setVisible(true);

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mySoundManager.playMusic(0);
                floatControl = mySoundManager.getFloatControl();
                slider.setEnabled(true);
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mySoundManager.stop();
                slider.setEnabled(false);
            }
        });

        muteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mySoundManager.mute();
            }
        });

        slider = new JSlider(-40, 6);
        slider.setEnabled(false); // Initially disable the slider
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (floatControl != null) {
                    currentVolume = slider.getValue();
                    if (currentVolume == -40) {
                        currentVolume = -80;
                    }
                    System.out.println("Current Volume: " + currentVolume);
                    floatControl.setValue(currentVolume);
                }
            }
        });
        frame.add(slider);
        frame.pack();
        frame.setVisible(true);
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new MusicUI();
//            }
//        });
//    }
}