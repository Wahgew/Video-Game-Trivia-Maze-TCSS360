package View;

import Model.Maze;
import Model.Room;
import Model.Tile;

import java.awt.*;

public class TileManager {
    private static final int Tile_Size = ScreenSetting.TILE_SIZE;
    private static final int World_Column = ScreenSetting.Max_Screen_Column;
    private static final int World_Row = ScreenSetting.Max_Screen_Row;
    private final static Maze maze = new Maze(World_Column, World_Row);
    private final GamePanel myGamePanel;

    public TileManager(GamePanel theGamePanel) {
        if (theGamePanel == null) {
            throw new IllegalArgumentException("The GamePanel cannot be null");
        }
        myGamePanel = theGamePanel;

    }
    public void draw(final Graphics2D theGraphic) {
        int worldCol = 0;
        int worldRow = 0;
        while (worldCol < World_Column && worldRow < World_Row) {

            //Maze maze1 = new Maze(worldCol, worldRow);
            int tileNum = maze.getMyMazeCols();
            int tileNum2 = maze.getMyMazeRows();
            Room maze3 = maze.getMyRoom(tileNum,tileNum2);

            //World x and y is position of the map
            int worldX = worldCol * Tile_Size;
            int worldY = worldRow * Tile_Size;

            //screen x and y is the position of the screen
            int screenX = worldX - myGamePanel.getMyPlayerManager().getX() + myGamePanel.getMyPlayerManager().getX();
            int screenY = worldY - myGamePanel.getMyPlayerManager().getY()+ myGamePanel.getMyPlayerManager().getY();

            /*
            This if statement is creating the boundaries from screen and player position
            so while the tile is within the boundary it will be drawn, which mean the tiles
            are the only being drawn around the player so i think it shouldn't slow our
            game down.
             */
            if (worldCol + Tile_Size > myGamePanel.getMyPlayerManager().getX() - myGamePanel.getMyPlayerManager().getX() &&
                worldX - Tile_Size < myGamePanel.getMyPlayerManager().getX() + myGamePanel.getMyPlayerManager().getX() &&
                worldY + Tile_Size > myGamePanel.getMyPlayerManager().getY() - myGamePanel.getMyPlayerManager().getY() &&
                worldY - Tile_Size < myGamePanel.getMyPlayerManager().getY() + myGamePanel.getMyPlayerManager().getY()){

                //theGraphic.drawImage(maze.getTile(maze3).,screenX,screenY,Tile_Size,Tile_Size, null);
            }
            worldCol++;
            if (worldCol == World_Column) {
                worldCol = 0;
                worldRow++;
            }

        }
    }
}
