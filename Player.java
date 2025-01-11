package ent;

import aff.*;
import fun.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Rectangle;

public class Player extends Entity
{
    Box game_panel;
    Key cle;

    final int screen_x;
    final int screen_y;

    public Player(Box new_game_panel, Key new_cle)
    {
        this.game_panel = new_game_panel;
        this.cle = new_cle;

        solid_part = new Rectangle(10,10,25,38);

        screen_x = game_panel.get_screen_width()/2 - (game_panel.get_tile_size()/2);
        screen_y = game_panel.get_screen_height()/2 - (game_panel.get_tile_size()/2);

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

    public void set_default_values()
    {
        direction = "down";
        world_x = game_panel.get_tile_size() * 24;
        world_y = game_panel.get_tile_size() * 13;
        entity_speed = 5;
    }

    public void get_player_image()
    {
        try 
        {
            up1 = ImageIO.read(getClass().getResourceAsStream("/Player/p1_up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/Player/p1_up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/Player/p1_down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/Player/p1_down2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/Player/p1_left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/Player/p1_left2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/Player/p1_right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/Player/p1_right2.png"));
        } 
        catch(IOException e) 
        {
            e.printStackTrace();
        }
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

        //Check tile collision
        collision = false;
        game_panel.get_collision().check_type_tile(this);

        //if collision is false player can move
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

        g2.drawImage(image, screen_x, screen_y, game_panel.get_tile_size(),game_panel.get_tile_size(), null);
    }

}
