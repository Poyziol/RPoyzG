package obj;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import aff.Box;

public class SuperObject 

{
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int world_x;
    public int world_y;

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
