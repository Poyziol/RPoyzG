package ent;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import aff.Box;
import fun.Utility;

public class Entity 
{
    Box game_panel;

    int world_x, world_y;
    int entity_speed;

    BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    String direction = "down";

    int compteur_animation = 0;
    int npc_compteur_animation = 0;
    int id_animation = 1;

    Rectangle solid_part = new Rectangle(0, 16, 48, 32);
    int solid_part_x;
    int solid_part_y;
    boolean collision = false;

    // Object options
    String dialogues[] = new String[20];
    int compteur_dialogue = 0;
    Utility utility = new Utility();
    boolean object_collision = false;

    public BufferedImage image, image2, image3;
    public String name;

    // Status
    int max_life;
    int life;
    
    public Entity(Box game_panel) 
    {
        this.game_panel = game_panel;
    }

    public void set_down1(BufferedImage new_down1)
    {
        this.down1 = new_down1;
    }

    public boolean get_object_collision()
    {
        return this.object_collision;
    }

    public void set_object_collision(boolean new_object_collision)
    {
        this.object_collision = new_object_collision;
    }

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

    public int get_solid_part_x()
    {
        return this.solid_part_x;
    }

    public int get_solid_part_y()
    {
        return this.solid_part_y;
    }

    public void set_solid_part_x(int new_solid_part_x)
    {
        this.solid_part_x = new_solid_part_x;
    }

    public void set_solid_part_y(int new_solid_part_y)
    {
        this.solid_part_y = new_solid_part_y;
    } 

    public void setWorld_x(int new_world_x)
    {
        this.world_x = new_world_x;
    }

    public void setWorld_y(int new_world_y)
    {
        this.world_y = new_world_y;
    }

    public int get_max_life()
    {
        return this.max_life;
    }

    public void set_max_life(int new_max_life)
    {
        this.max_life = new_max_life;
    }

    public int get_life()
    {
        return this.life;
    }

    public void set_life(int new_life)
    {
        this.life = new_life;
    }

    public BufferedImage setup(String image_path)
    {
        Utility utility = new Utility();
        BufferedImage image = null;

        try
        {
            image = ImageIO.read(getClass().getResourceAsStream(image_path + ".png"));
            image = utility.scale_image(image, game_panel.get_tile_size(), game_panel.get_tile_size());
        } 
        catch(Exception e) 
        {
            e.printStackTrace();
        }

        return image;
    }

    public void speak() 
    {
        if(dialogues[compteur_dialogue] == null)
        {
            compteur_dialogue = 0;
        }

        game_panel.get_ui().set_dialogue(dialogues[compteur_dialogue]);
        compteur_dialogue++;

        switch (game_panel.p1.direction) 
        {
            case "up":
                direction = "down";
                break;

            case "down":
                direction = "up";
                break;

            case "left":
                direction = "right";
                break;

            case "right":
                direction = "left";
                break;
        
            default:
                break;
        }
    }

    public void action() { }

    public void update()
    {
        action();

        collision = false;
        game_panel.get_collision().check_type_tile(this);
        game_panel.get_collision().check_object(this, false);
        game_panel.get_collision().check_player(this);

        if(collision == false)
        {
            switch (direction) 
            {
                case "up":
                world_y -= entity_speed;
                    break;
                case "down":
                world_y += entity_speed;
                    break;
                case "left":
                world_x -= entity_speed;
                    break;
                case "right":
                world_x += entity_speed;
                    break;
                default:
                    break;
            }
        }
        compteur_animation++;
        if(compteur_animation > 12)
        {
            if(id_animation == 1)
            {
                id_animation = 2;
            }
            else if(id_animation == 2)
            {
                id_animation = 1;
            }
            compteur_animation = 0;
        }
    }

    public void draw(Graphics2D g2)
    {
        BufferedImage image = null;

        int screen_x = world_x - game_panel.p1.get_world_x() + game_panel.p1.get_screen_x();
        int screen_y = world_y - game_panel.p1.get_world_y() + game_panel.p1.get_screen_y();

        if(world_x + game_panel.get_tile_size() > game_panel.get_player().get_world_x() - game_panel.get_player().get_screen_x() &&
           world_x - game_panel.get_tile_size() < game_panel.get_player().get_world_x() + game_panel.get_player().get_screen_x() &&
           world_y + game_panel.get_tile_size() > game_panel.get_player().get_world_y() - game_panel.get_player().get_screen_y() &&
           world_y - game_panel.get_tile_size() < game_panel.get_player().get_world_y() + game_panel.get_player().get_screen_y())
        {
            switch(direction) 
            {
                case "up":
                    if(id_animation == 1)
                    {
                        image = up1;
                    }
                    if(id_animation == 2)
                    {
                        image = up2;
                    }
                    break;
                case "down":
                    if(id_animation == 1)
                    {
                        image = down1;
                    }
                    if(id_animation == 2)
                    {
                        image = down2;
                    }
                    break;
                case "left":
                    if(id_animation == 1)
                    {
                        image = left1;
                    }
                    if(id_animation == 2)
                    {
                        image = left2;
                    }
                    break;
                case "right":
                    if(id_animation == 1)
                    {
                        image = right1;
                    }
                    if(id_animation == 2)
                    {
                        image = right2;
                    }
                    break;
                default:
                    break;
            }
            
            g2.drawImage(image, screen_x, screen_y, game_panel.get_tile_size(),game_panel.get_tile_size(),null);
        }
    }

}
