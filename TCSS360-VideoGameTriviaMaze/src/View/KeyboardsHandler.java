package View;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;

/**
 * The KeyboardsHandler class handles keyboard input for the game.
 *
 * @author Peter W Madin, Ken Egawa, Sopheanith Ny
 * @version 6/7/2024
 */
public class KeyboardsHandler implements KeyListener , Serializable {
    /**
     * A boolean indicating whether the up arrow key is pressed, down arrow key is pressed,
     * left arrow key is pressed, and the right arrow key is pressed.
     */
    private boolean myUpKeyPressed, myDownKeyPressed, myLeftKeyPressed, myRightKeyPressed;

    /**
     * Invoked when a key has been typed.
     * @param e The KeyEvent object containing information about the event.
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Invoked when a key has been pressed.
     * @param e The KeyEvent object containing information about the event.
     */
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

    /**
     * Invoked when a key has been released.
     * @param e The KeyEvent object containing information about the event.
     */
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

    /**
     * Sets all key states to false.
     */
    public void setAllKeys() {
        myLeftKeyPressed = false;
        myRightKeyPressed = false;
        myUpKeyPressed = false;
        myDownKeyPressed = false;
    }

    /**
     * Checks if the up arrow key is pressed.
     * @return True if the up arrow key is pressed, otherwise false.
     */
    public boolean isMyUpKeyPressed() {
        return myUpKeyPressed;
    }

    /**
     * Sets the state of the up arrow key.
     * @param myUpKeyPressed The state of the up arrow key.
     */
    public void setMyUpKeyPressed(boolean myUpKeyPressed) {
        this.myUpKeyPressed = myUpKeyPressed;
    }

    /**
     * Checks if the down arrow key is pressed.
     * @return True if the down arrow key is pressed, otherwise false.
     */
    public boolean isMyDownKeyPressed() {
        return myDownKeyPressed;
    }

    /**
     * Sets the state of the down arrow key.
     * @param myDownKeyPressed The state of the down arrow key.
     */
    public void setMyDownKeyPressed(boolean myDownKeyPressed) {
        this.myDownKeyPressed = myDownKeyPressed;
    }

    /**
     * Checks if the left arrow key is pressed.
     * @return True if the left arrow key is pressed, otherwise false.
     */
    public boolean isMyLeftKeyPressed() {
        return myLeftKeyPressed;
    }

    /**
     * Sets the state of the left arrow key.
     * @param myLeftKeyPressed The state of the left arrow key.
     */
    public void setMyLeftKeyPressed(boolean myLeftKeyPressed) {
        this.myLeftKeyPressed = myLeftKeyPressed;
    }

    /**
     * Checks if the right arrow key is pressed.
     * @return True if the right arrow key is pressed, otherwise false.
     */
    public boolean isMyRightKeyPressed() {
        return myRightKeyPressed;
    }

    /**
     * Sets the state of the right arrow key.
     * @param myRightKeyPressed The state of the right arrow key.
     */
    public void setMyRightKeyPressed(boolean myRightKeyPressed) {
        this.myRightKeyPressed = myRightKeyPressed;
    }


}

