package Model;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Tile implements Serializable {
    private transient BufferedImage myTileMapImage;
    private boolean myCollision;

    public Tile() {
        myCollision = false;
    }
    public BufferedImage getImage() {
        return myTileMapImage;
    }
    public void setImage(final BufferedImage read) {
        myTileMapImage = read;
    }
    public void setCollision(final boolean theCollision) {
        myCollision = theCollision;
    }
    public boolean isCollsion() {
        return myCollision;
    }

}
