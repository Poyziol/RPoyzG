package ent;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity 
{
    int world_x, world_y;
    int entity_speed;

    BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    String direction;

    int compteur_animation = 0;
    int id_animation = 1;

    Rectangle solid_part;
    boolean collision = false;

    public int get_world_x() {
        return world_x;
    }

    public int get_world_y() {
        return world_y;
    }

    public int getEntitySpeed() {
        return entity_speed;
    }

    public BufferedImage getUp1() {
        return up1;
    }

    public BufferedImage getUp2() {
        return up2;
    }

    public BufferedImage getDown1() {
        return down1;
    }

    public BufferedImage getDown2() {
        return down2;
    }

    public BufferedImage getLeft1() {
        return left1;
    }

    public BufferedImage getLeft2() {
        return left2;
    }

    public BufferedImage getRight1() {
        return right1;
    }

    public BufferedImage getRight2() {
        return right2;
    }

    public String getDirection() {
        return direction;
    }

    public void set_collision(boolean new_collision)
    {
        this.collision = new_collision;
    }

    public boolean get_collision()
    {
        return this.collision;
    }

    public Rectangle get_solid_part()
    {
        return this.solid_part;
    }

}
