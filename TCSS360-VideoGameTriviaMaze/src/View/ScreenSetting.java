package View;
/**
 * This class defines settings related to the screen display, such as screen dimensions,
 * tile size, sprite dimensions, and frames per second (FPS).
 *
 * @author Peter W Madin, Ken Egawa, Sopheanith Ny
 * @version 6/7/2024
 */
public class ScreenSetting {

    /**
     * Represents the maximum number of columns on the screen.
     */
    public static final int Max_Screen_Column = 16;

    /**
     * Represents the maximum number of rows on the screen.
     */
    public static final int Max_Screen_Row = 12;

    /**
     * Represents the scaling factor for the screen.
     */
    public static final int SCALE = 2;

    /**
     * Represents the original size of a tile before scaling.
     */
    public static final int ORIGINAL_TILE_SIZE = 32;

    /**
     * Represents the size of a tile after scaling.
     */
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;

    /**
     * Represents the width of the screen in pixels.
     */
    public static final int Screen_Width = 60 * Max_Screen_Column;

    /**
     * Represents the height of the screen in pixels.
     */
    public static final int Screen_Height = 50 * Max_Screen_Row;

    /**
     * Represents the width of the sprite center.
     */
    public static final int SPRITE_CENTER_WIDTH = 1600;

    /**
     * Represents the height of the sprite center.
     */
    public static final int SPRITE_CENTER_HEIGHT = 1034;

    /**
     * Represents the frames per second for screen rendering.
     */
    public static final int FPS = 60;
}
