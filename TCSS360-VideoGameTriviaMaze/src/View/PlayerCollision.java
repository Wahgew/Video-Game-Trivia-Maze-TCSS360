package View;

import Model.Maze;

public class PlayerCollision {
    private static final int TILE_SIZE = ScreenSetting.TILE_SIZE;
    //private final static Maze MAZE = new Maze();
    private GamePanel myGamePanel;
    //private QuestionPanel myQuestionPanel;

    public PlayerCollision(final GamePanel theGamePanel) {
        myGamePanel = theGamePanel;
    }
}

