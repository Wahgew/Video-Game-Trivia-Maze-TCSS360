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
    private Clip myClip;
    private float myPreviousVolume = 0;
    private float myCurrentVolume = 0;
    private FloatControl myFloatControl;
    private boolean myMute = false;
    private URL myURLSound;
    private JSlider mySlider;
    private final String[] mySoundURL;
    private static SoundManager mySingletonMusic;

    private SoundManager() {
        mySoundURL = new String[5];
        mySoundURL[0] = "/Resource/Sounds2/IntroGame.wav";
        mySoundURL[1] = "/Resource/Sounds2/LongTime.wav";
        mySoundURL[2] = "/Resource/Sounds2/GamePlay.wav";
        mySoundURL[3] = "/Resource/Sounds2/Lose.wav";
        mySoundURL[4] = "/Resource/Sounds2/Miami_original_mix.wav";
    }

    public static SoundManager getInstance() {
        if (mySingletonMusic == null) {
            mySingletonMusic = new SoundManager();
        }
        return mySingletonMusic;
    }

    public void setFile(final int theFile) {
        try {
            InputStream is = getClass().getResourceAsStream(mySoundURL[theFile]);
            AudioInputStream sound = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
            myClip = AudioSystem.getClip();
            myClip.open(sound);
            myFloatControl = (FloatControl) myClip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (Exception exception) {
            System.out.println("Error: Audio file could not be opened.");
            exception.printStackTrace();
        }
    }

    public void setSlider(JSlider theSlider) {
        mySlider = theSlider;
    }


    public void play() {
        myFloatControl.setValue(myCurrentVolume);
        if (myClip != null) {
            myClip.setFramePosition(0);
            myClip.start();
        } else {
            System.out.println("Clip is not initialized for play.");
        }
    }
    public void loop() {
        if (myClip != null) {
            myClip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            System.out.println("Clip is not initialized for loop.");
        }
    }
    public void stop() {
        if (myClip != null) {
            myClip.stop();
        } else {
            System.out.println("Clip is not initialized for stop.");
        }
    }
    public void setVolume(float volume) {
        myCurrentVolume = volume;
        if (myFloatControl != null) {
            myFloatControl.setValue(myCurrentVolume);
        }
    }
    public void volumeUp() {
        if (myFloatControl != null) {
            myCurrentVolume += 1.0f;
            if (myCurrentVolume > 6.0f) {
                myCurrentVolume = 6.0f;
            }
            myFloatControl.setValue(myCurrentVolume);
            System.out.println("Current Volume: " + myCurrentVolume);
        }
    }
    public void volumeDown() {
        myCurrentVolume -= 1.0f;
        System.out.println("Current Volume:" + myCurrentVolume);
        if (myCurrentVolume < -80.0f) {
            myCurrentVolume = -80.0f;
        }
        myFloatControl.setValue(myCurrentVolume);
    }
    public void mute() {
        if (!myMute) {
            myPreviousVolume = myCurrentVolume;
            System.out.println("Current Volume:" + myCurrentVolume);
            myCurrentVolume = -80.0f;
            if (myFloatControl != null) {
                myFloatControl.setValue(myCurrentVolume);
            }
            myMute = true;

            if (mySlider != null) {
                mySlider.setValue(mySlider.getMinimum());
            }
        } else {
            myCurrentVolume = myPreviousVolume;
            if (mySlider != null) {
                mySlider.setValue((int) myCurrentVolume);
            }
            System.out.println("Current Volume:" + myCurrentVolume);
            if (myFloatControl != null) {
                myFloatControl.setValue(myCurrentVolume);
            }
            myMute = false;
        }
    }
    public void playMusic(final int theIndex, final float theVolume) {
        setFile(theIndex);
        setVolume(theVolume);
        play();
        loop();
    }
    public FloatControl getMyFloatControl() {
        return myFloatControl;
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
