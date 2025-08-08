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

        attack_part.x = 16;
        attack_part.y = 5;
        attack_part.width = 25;
        attack_part.height = 35;

        set_default_values();
        get_image();
        get_attack_image();
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

        up1 = setup("/Player/p1_up1",game_panel.get_tile_size(), game_panel.get_tile_size());
        up2 = setup("/Player/p1_up2",game_panel.get_tile_size(), game_panel.get_tile_size());
        down1 = setup("/Player/p1_down1",game_panel.get_tile_size(), game_panel.get_tile_size());
        down2 = setup("/Player/p1_down2",game_panel.get_tile_size(), game_panel.get_tile_size());
        left1 = setup("/Player/p1_left1",game_panel.get_tile_size(), game_panel.get_tile_size());
        left2 = setup("/Player/p1_left2",game_panel.get_tile_size(), game_panel.get_tile_size());
        right1 = setup("/Player/p1_right1",game_panel.get_tile_size(), game_panel.get_tile_size());
        right2 = setup("/Player/p1_right2",game_panel.get_tile_size(), game_panel.get_tile_size());

    }

    public void get_attack_image()
    {
        attack_up1 = setup("/Player/attack-up1", game_panel.get_tile_size(), game_panel.get_tile_size() * 2);
        attack_up2 = setup("/Player/attack-up2", game_panel.get_tile_size(), game_panel.get_tile_size() * 2);
        attack_down1 = setup("/Player/attack-down1", game_panel.get_tile_size(), game_panel.get_tile_size() * 2);
        attack_down2 = setup("/Player/attack-down2", game_panel.get_tile_size(), game_panel.get_tile_size() * 2);
        attack_left1 = setup("/Player/attack-left1", game_panel.get_tile_size() * 2, game_panel.get_tile_size());
        attack_left2 = setup("/Player/attack-left2", game_panel.get_tile_size() * 2, game_panel.get_tile_size());
        attack_right1 = setup("/Player/attack-right1", game_panel.get_tile_size() * 2, game_panel.get_tile_size());
        attack_right2 = setup("/Player/attack-right2", game_panel.get_tile_size() * 2, game_panel.get_tile_size());
    }

    public void update()
    {
        if(is_attacking == true)
        {
            attacking();
        }

        else if(cle.get_up_pressed() == true || cle.get_down_pressed() == true || cle.get_left_pressed() == true || cle.get_right_pressed() == true || cle.get_enter_pressed() == true)
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

    public void attacking()
    {
        compteur_animation++;

        if(compteur_animation <= 5)
        {
            id_animation = 1;
        }
        if(compteur_animation > 5 && compteur_animation <= 40)
        {
            id_animation = 2;

            // Save current data
            int current_world_x = world_x;
            int current_world_y = world_y;
            int current_solid_part_width = solid_part.width;
            int current_solid_part_height = solid_part.height; 

            // Adjust data
            switch(direction) 
            {
                case "up":
                    world_y -= attack_part.height;
                    break;
                case "down":
                    world_y += attack_part.height;
                    break;
                case "left":
                    world_x -= attack_part.width;
                    break;
                case "right":
                    world_x += attack_part.width;
                    break;
                default:
                    break;
            }

            // Attack becomes solid
            solid_part.width = attack_part.width;
            solid_part.height = attack_part.height;

            // Monster collision
            int monster_index = game_panel.get_collision().check_entity(this, game_panel.mob);
            monster_damage(monster_index);

            // Back data
            world_x = current_world_x;
            world_y = current_world_y;
            solid_part.width = current_solid_part_width;
            solid_part.height = current_solid_part_height;
        }
        if(compteur_animation > 40)
        {
            id_animation = 1;
            compteur_animation = 0;
            is_attacking = false;
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
        if(game_panel.get_key().get_enter_pressed() == true)
        {
            if(index != 999)
            {
                game_panel.set_game_state(game_panel.get_dialogue_state());
                game_panel.npc[index].speak(); 
            }
            else
            {
                game_panel.play_sound_effect(7);
                is_attacking = true;
            }
        }
    }

    public void monster_interaction(int index)
    {
        if(index != 999)
        {
            if(invincible == false)
            {
                game_panel.play_sound_effect(5);
                life -= 1;
                invincible = true;
            }
        }
    }

    public void monster_damage(int index)
    {
        if(index != 999)
        {
            if(game_panel.mob[index].invincible == false)
            {
                game_panel.play_sound_effect(6);
                game_panel.mob[index].life -= 1;
                game_panel.mob[index].invincible = true;
                game_panel.mob[index].damage_reaction();

                if(game_panel.mob[index].life <= 0)
                {
                    game_panel.mob[index].is_dead = true;
                }
            }
        }
    }

    public void draw(Graphics2D g2)
    {
        BufferedImage image = null;
        int tmp_screen_x = screen_x;
        int tmp_screen_y = screen_y;

        switch(direction) 
        {
            case "up":
                if(is_attacking == false)
                {
                    if(id_animation == 1)
                    {
                        image = up1;
                    }
                    if(id_animation == 2)
                    {
                        image = up2;
                    }
                }
                if(is_attacking == true)
                {
                    tmp_screen_y = tmp_screen_y - game_panel.get_tile_size();

                    if(id_animation == 1)
                    {
                        image = attack_up1;
                    }
                    if(id_animation == 2)
                    {
                        image = attack_up2;
                    }
                }
                break;
            case "down":
                if(is_attacking == false)
                {
                    if(id_animation == 1)
                    {
                        image = down1;
                    }
                    if(id_animation == 2)
                    {
                        image = down2;
                    }
                }
                if(is_attacking == true)
                {
                    if(id_animation == 1)
                    {
                        image = attack_down1;
                    }
                    if(id_animation == 2)
                    {
                        image = attack_down2;
                    }
                }
                break;
            case "left":
                if(is_attacking == false)
                {
                    if(id_animation == 1)
                    {
                        image = left1;
                    }
                    if(id_animation == 2)
                    {
                        image = left2;
                    }
                }
                if(is_attacking == true)
                {
                    tmp_screen_x = tmp_screen_x - game_panel.get_tile_size();

                    if(id_animation == 1)
                    {
                        image = attack_left1;
                    }
                    if(id_animation == 2)
                    {
                        image = attack_left2;
                    }
                }
                break;
            case "right":
                if(is_attacking == false)
                {
                    if(id_animation == 1)
                    {
                        image = right1;
                    }
                    if(id_animation == 2)
                    {
                        image = right2;
                    }
                }
                if(is_attacking == true)
                {
                    if(id_animation == 1)
                    {
                        image = attack_right1;
                    }
                    if(id_animation == 2)
                    {
                        image = attack_right2;
                    }
                }
                break;
            default:
                break;
        }

        if(invincible == true)
        {
            change_opacity(g2, 0.5f);
        }

        g2.drawImage(image, tmp_screen_x, tmp_screen_y, null);

        change_opacity(g2, 1f);

        // DEBUG affiche hit box
        //g2.setFont(new Font("Arial", Font.PLAIN, 26));
        //g2.setColor(Color.WHITE);
        //g2.drawString("Invincible:" + compteur_invinsible, 10, 400);
        //g2.setColor(Color.red);
        //g2.drawRect(screen_x + solid_part.x, screen_y + solid_part.y, solid_part.width, solid_part.height);
        //g2.drawRect(screen_x + attack_part.x, screen_y + attack_part.y, attack_part.width, attack_part.height);
    }

}
