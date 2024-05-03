package View;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;

public class KeyboardsHandler implements KeyListener , Serializable {
    private boolean myUpKeyPressed, myDownKeyPressed, myLeftKeyPressed, myRightKeyPressed;


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_W) {
            setMyUpKeyPressed(true);
        }
        if (keyCode == KeyEvent.VK_S) {
            setMyDownKeyPressed(true);
        }
        if (keyCode == KeyEvent.VK_A) {
            setMyLeftKeyPressed(true);
        }
        if (keyCode == KeyEvent.VK_D) {
            setMyRightKeyPressed(true);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_W) {
            setMyUpKeyPressed(false);
        }
        if (keyCode == KeyEvent.VK_S) {
            setMyDownKeyPressed(false);
        }
        if (keyCode == KeyEvent.VK_A) {
            setMyLeftKeyPressed(false);
        }
        if (keyCode == KeyEvent.VK_D) {
            setMyRightKeyPressed(false);
        }

    }
    public void setAllKeys() {
        myLeftKeyPressed = false;
        myRightKeyPressed = false;
        myUpKeyPressed = false;
        myDownKeyPressed = false;
    }
    public boolean isMyUpKeyPressed() {
        return myUpKeyPressed;
    }
    public void setMyUpKeyPressed(boolean myUpKeyPressed) {
        this.myUpKeyPressed = myUpKeyPressed;
    }
    public boolean isMyDownKeyPressed() {
        return myDownKeyPressed;
    }
    public void setMyDownKeyPressed(boolean myDownKeyPressed) {
        this.myDownKeyPressed = myDownKeyPressed;
    }
    public boolean isMyLeftKeyPressed() {
        return myLeftKeyPressed;
    }
    public void setMyLeftKeyPressed(boolean myLeftKeyPressed) {
        this.myLeftKeyPressed = myLeftKeyPressed;
    }
    public boolean isMyRightKeyPressed() {
        return myRightKeyPressed;
    }
    public void setMyRightKeyPressed(boolean myRightKeyPressed) {
        this.myRightKeyPressed = myRightKeyPressed;
    }


}

