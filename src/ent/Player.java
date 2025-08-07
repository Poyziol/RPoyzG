package ent;

import aff.*;
import fun.*;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

@SuppressWarnings("unused")
public class Player extends Entity
{
    Key cle;

    final int screen_x;
    final int screen_y;
    int compteur_move = 0;

    public Player(Box new_game_panel, Key new_cle)
    {
        super(new_game_panel);

        this.cle = new_cle;

        solid_part = new Rectangle(17,20,18,23);

        screen_x = game_panel.get_screen_width()/2 - (game_panel.get_tile_size()/2);
        screen_y = game_panel.get_screen_height()/2 - (game_panel.get_tile_size()/2);

        solid_part_x = solid_part.x;
        solid_part_y = solid_part.y;

        set_default_values();
        get_image();
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
        world_x = game_panel.get_tile_size() * 10;
        world_y = game_panel.get_tile_size() * 10;
        entity_speed = 3;

        // Status
        max_life = 6;
        life = max_life;
    }

    public void get_image()
    {

        up1 = setup("/Player/p1_up1");
        up2 = setup("/Player/p1_up2");
        down1 = setup("/Player/p1_down1");
        down2 = setup("/Player/p1_down2");
        left1 = setup("/Player/p1_left1");
        left2 = setup("/Player/p1_left2");
        right1 = setup("/Player/p1_right1");
        right2 = setup("/Player/p1_right2");

    }

    public void update()
    {
        if(cle.get_up_pressed() == true || cle.get_down_pressed() == true || cle.get_left_pressed() == true || cle.get_right_pressed() == true || cle.get_enter_pressed() == true)
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

            // Check NPC collision
            int npc_index = game_panel.get_collision().check_entity(this, game_panel.npc);
            npc_interaction(npc_index);

            // Check Monsters collision
            int mob_index = game_panel.get_collision().check_entity(this, game_panel.mob);
            monster_interaction(mob_index);

            // Check events collision
            game_panel.get_events().check_events();

            // if collision is false player can move
            if(collision == false && cle.get_enter_pressed() == false)
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

            game_panel.get_key().set_enter_pressed(false);

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

        // Compteur invincible
        if(invincible == true)
        {
            compteur_invinsible++;
            if(compteur_invinsible > 60)
            {
                invincible = false;
                compteur_invinsible = 0;
            }
        }
    }

    public void pickup_object(int index)
    {
        if(index != 999)
        {
           
        }
    }

    public void npc_interaction(int index)
    {
        if(index != 999)
        {
            if(game_panel.get_key().get_enter_pressed() == true)
            {
                game_panel.set_game_state(game_panel.get_dialogue_state());
                game_panel.npc[index].speak();
            }   
        }
    }

    public void monster_interaction(int index)
    {
        if(index != 999)
        {
            if(invincible == false)
            {
                life -= 1;
                invincible = true;
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

        if(invincible == true)
        {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(image, screen_x, screen_y, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        // DEBUG affiche hit box
        //g2.setFont(new Font("Arial", Font.PLAIN, 26));
        //g2.setColor(Color.WHITE);
        //g2.drawString("Invincible:" + compteur_invinsible, 10, 400);
        //g2.setColor(Color.red);
        //g2.drawRect(screen_x + solid_part.x, screen_y + solid_part.y, solid_part.width, solid_part.height);

    }

}
