package obj;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import aff.Box;
import fun.Utility;

public class SuperObject 

{
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int world_x;
    public int world_y;
    public Rectangle solid_part = new Rectangle(0, 0, 48, 48);
    public int solid_part_x = 0;
    public int solid_part_y = 0;
    Utility utility = new Utility();

    public BufferedImage getImage() 
    {
        return image;
    }

    public void setImage(BufferedImage image) 
    {
        this.image = image;
    }

    public String getName() 
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public boolean isCollision() 
    {
        return collision;
    }

    public void setCollision(boolean collision) 
    {
        this.collision = collision;
    }

    public int getWorld_x() 
    {
        return world_x;
    }

    public void setWorld_x(int world_x) 
    {
        this.world_x = world_x;
    }

    public int getWorld_y() 
    {
        return world_y;
    }

    public void setWorld_y(int world_y) 
    {
        this.world_y = world_y;
    }

    public Rectangle get_solid_part()
    {
        return this.solid_part;
    }

    public int get_solid_part_x()
    {
        return this.solid_part_x;
    }

    public int get_solid_part_y()
    {
        return this.solid_part_y;
    }

    public void draw(Graphics g, Box box)
    {
        int screen_x = world_x - box.p1.get_world_x() + box.p1.get_screen_x();
        int screen_y = world_y - box.p1.get_world_y() + box.p1.get_screen_y();

        if(world_x + box.get_tile_size() > box.get_player().get_world_x() - box.get_player().get_screen_x() &&
           world_x - box.get_tile_size() < box.get_player().get_world_x() + box.get_player().get_screen_x() &&
           world_y + box.get_tile_size() > box.get_player().get_world_y() - box.get_player().get_screen_y() &&
           world_y - box.get_tile_size() < box.get_player().get_world_y() + box.get_player().get_screen_y())
        {
            g.drawImage(image, screen_x, screen_y, box.get_tile_size(),box.get_tile_size(),null);
        }
    }
}
