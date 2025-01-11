package ent;

import java.awt.image.BufferedImage;

public class Tile 
{
    BufferedImage image;
    boolean collision = false;

    public boolean get_collision()
    {
        return this.collision;
    }

    public BufferedImage get_image()
    {
        return this.image;
    }

    public void set_image(BufferedImage new_image)
    {
        this.image = new_image;
    }

    public void set_collision(boolean new_collision)
    {
        this.collision = new_collision;
    }

}
