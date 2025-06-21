package ent;

import aff.*;
import fun.*;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.awt.Color;

public class Player extends Entity
{
    Box game_panel;
    Key cle;

    final int screen_x;
    final int screen_y;
    int nbr_key = 0;
    int compteur_move = 0;

    public Player(Box new_game_panel, Key new_cle)
    {
        this.game_panel = new_game_panel;
        this.cle = new_cle;

        solid_part = new Rectangle(17,20,15,27);

        screen_x = game_panel.get_screen_width()/2 - (game_panel.get_tile_size()/2);
        screen_y = game_panel.get_screen_height()/2 - (game_panel.get_tile_size()/2);

        solid_part_x = solid_part.x;
        solid_part_y = solid_part.y;

        set_default_values();
        get_player_image();
    }

    public int get_screen_x()
    {
        return this.screen_x;
    }

    public int get_screen_y()
    {
        return this.screen_y;
    }

    public int get_nbr_key()
    {
        return this.nbr_key;
    }

    public void set_default_values()
    {
        direction = "down";
        world_x = game_panel.get_tile_size() * 10;
        world_y = game_panel.get_tile_size() * 10;
        entity_speed = 3;
    }

    public void get_player_image()
    {

        up1 = setup("p1_up1");
        up2 = setup("p1_up2");
        down1 = setup("p1_down1");
        down2 = setup("p1_down2");
        left1 = setup("p1_left1");
        left2 = setup("p1_left2");
        right1 = setup("p1_right1");
        right2 = setup("p1_right2");

    }

    public BufferedImage setup(String image_name)
    {
        Utility utility = new Utility();
        BufferedImage image = null;

        try
        {
            image = ImageIO.read(getClass().getResourceAsStream("/Player/" + image_name + ".png"));
            image = utility.scale_image(image, game_panel.get_tile_size(), game_panel.get_tile_size());
        } 
        catch(Exception e) 
        {
            e.printStackTrace();
        }

        return image;
    }

    public void update()
    {
        if(cle.get_up_pressed() == true || cle.get_down_pressed() == true || cle.get_left_pressed() == true || cle.get_right_pressed() == true)
        {
            if(cle.get_up_pressed() == true)
            {
                direction = "up";
            }
            if(cle.get_down_pressed() == true)
            {
                direction = "down";
            }
            if(cle.get_left_pressed() == true)
            {
                direction = "left";
            }
            if(cle.get_right_pressed() == true)
            {
                direction = "right";
            }

            // Check tile collision
            collision = false;
            game_panel.get_collision().check_type_tile(this);

            // Check object collision
            int obj_index = game_panel.get_collision().check_object(this, true);
            pickup_object(obj_index);

            // if collision is false player can move
            if(collision == false)
            {
                switch (direction) {
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
            if(compteur_animation > 15)
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
        else
        {
            compteur_move++;

            if(compteur_move == 20)
            {
                id_animation = 1;
                compteur_move = 0;
            }
        }
    }

    public void pickup_object(int index)
    {
        if(index != 999)
        {
            String obj_name = game_panel.get_obj()[index].getName();

            switch(obj_name) 
            {
                case "key":
                    game_panel.get_obj()[index] = null;
                    game_panel.play_sound_effect(4);
                    nbr_key++;
                    game_panel.get_ui().show_message("You find a key!");
                    break;
                case "door":
                    if(nbr_key > 0)
                    {
                        game_panel.get_obj()[index] = null;
                        nbr_key--;
                        game_panel.get_ui().show_message("Door unlocked!");
                    }
                    break;
                case "boot":
                    game_panel.get_obj()[index] = null;
                    game_panel.play_sound_effect(4);
                    entity_speed = 5;
                    game_panel.get_ui().show_message("Speed up!");
                default:
                    break;
            }
        }
    }

    public void draw(Graphics2D g2)
    {
        BufferedImage image = null;

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

        g2.drawImage(image, screen_x, screen_y, null);

        // DEBUG affiche hit box
        // g2.setColor(Color.red);
        // g2.drawRect(screen_x + solid_part.x, screen_y + solid_part.y, solid_part.width, solid_part.height);
    }

}
