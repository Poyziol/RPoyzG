package ent;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import aff.Box;
import fun.Utility;

@SuppressWarnings("unused")
public class Entity 
{
    Box game_panel;

    int world_x, world_y;
    int entity_speed;

    BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    BufferedImage attack_up1, attack_up2, attack_down1, attack_down2, attack_left1, attack_left2, attack_right1, attack_right2;
    String direction = "down";

    int compteur_animation = 0;
    int npc_compteur_animation = 0;
    int id_animation = 1;

    // Player:0 - Entity:1 - Monster:2
    int type;

    Rectangle solid_part = new Rectangle(0, 16, 48, 32);
    Rectangle attack_part = new Rectangle(0, 0, 0, 0);
    int solid_part_x;
    int solid_part_y;
    boolean collision = false;
    boolean is_attacking = false;

    // Object options
    String dialogues[] = new String[20];
    int compteur_dialogue = 0;
    Utility utility = new Utility();
    boolean object_collision = false;

    // Monster options
    public BufferedImage image, image2, image3;
    public String name;
    boolean invincible = false;
    int compteur_invinsible = 0;
    boolean is_alive = true;
    boolean is_dead = false;
    int compteur_dead = 0;
    boolean hp_bar_on = false;
    int compteur_hp_bar = 0;

    // Status
    int max_life;
    int life;
    
    public Entity(Box game_panel) 
    {
        this.game_panel = game_panel;
    }

    public Box get_game_panel()
    {
        return this.game_panel;
    }

    public boolean get_is_alive()
    {
        return this.is_alive;
    }

    public void set_is_alive(boolean new_is_alive)
    {
        this.is_alive = new_is_alive;
    }

    public boolean get_is_dead()
    {
        return this.is_dead;
    }

    public void set_is_dead(boolean new_is_dead)
    {
        this.is_dead = new_is_dead;
    }

    public int get_type()
    {
        return this.type;
    }

    public void set_type(int new_type)
    {
        this.type = new_type;
    }

    public boolean get_invincible()
    {
        return this.invincible;
    }

    public void set_invincible(boolean new_invincible)
    {
        this.invincible = new_invincible;
    }

    public int get_compteur_invincible()
    {
        return this.compteur_invinsible;
    }

    public void set_compteur_invincible(int new_compteur_invincible)
    {
        this.compteur_invinsible = new_compteur_invincible;
    }

    public void set_down1(BufferedImage new_down1)
    {
        this.down1 = new_down1;
    }

    public void set_down2(BufferedImage new_down2)
    {
        this.down2 = new_down2;
    }

    public void set_up1(BufferedImage new_up1)
    {
        this.up1 = new_up1;
    }

    public void set_up2(BufferedImage new_up2)
    {
        this.up2 = new_up2;
    }

    public void set_left1(BufferedImage new_left1)
    {
        this.left1 = new_left1;
    }

    public void set_left2(BufferedImage new_left2)
    {
        this.left2 = new_left2;
    }

    public void set_right1(BufferedImage new_right1)
    {
        this.right1 = new_right1;
    }

    public void set_right2(BufferedImage new_right2)
    {
        this.right2 = new_right2;
    }

    public int get_npc_compteur_animation()
    {
        return this.npc_compteur_animation;
    }

    public void set_npc_compteur_animation(int new_npc_compteur_animation)
    {
        this.npc_compteur_animation = new_npc_compteur_animation;
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

    public void set_entity_speed(int new_entity_speed)
    {
        this.entity_speed = new_entity_speed;
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

    public void set_direction(String new_direction)
    {
        this.direction = new_direction;
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

    public BufferedImage setup(String image_path, int image_width, int image_height)
    {
        Utility utility = new Utility();
        BufferedImage image = null;

        try
        {
            image = ImageIO.read(getClass().getResourceAsStream(image_path + ".png"));
            image = utility.scale_image(image,image_width, image_height);
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

    public void damage_reaction() { }

    public void update()
    {
        action();

        collision = false;
        game_panel.get_collision().check_type_tile(this);
        game_panel.get_collision().check_object(this, false);
        game_panel.get_collision().check_entity(this, game_panel.npc);
        game_panel.get_collision().check_entity(this, game_panel.mob);
        boolean contact_player = game_panel.get_collision().check_player(this);

        if(this.type == 2 && contact_player == true)
        {
            if(game_panel.get_player().get_invincible() == false)
            {
                game_panel.play_sound_effect(5);
                game_panel.get_player().set_life(game_panel.get_player().get_life() - 1);
                game_panel.get_player().set_invincible(true);
            }
        }

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

        // Compteur invincible
        if(invincible == true)
        {
            compteur_invinsible++;
            if(compteur_invinsible > 40)
            {
                invincible = false;
                compteur_invinsible = 0;
            }
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

            // Monster health bar
            if(type == 2 && hp_bar_on == true)
            {
                double one_scale = (double)game_panel.get_tile_size() / max_life;
                double hp_par_value = one_scale * life;

                g2.setColor(new Color(12,12,12));
                g2.fillRect(screen_x - 1, screen_y - 13, game_panel.get_tile_size() + 2, 10);

                g2.setColor(new Color(255,0,30));
                g2.fillRect(screen_x, screen_y - 12, (int)hp_par_value, 8);

                compteur_hp_bar++;

                if(compteur_hp_bar > 600)
                {
                    compteur_hp_bar = 0;
                    hp_bar_on = false;
                }
            }

            if(invincible == true)
            {
                hp_bar_on = true;
                compteur_hp_bar = 0;
                change_opacity(g2, 0.5f);
            }
            if(is_dead == true)
            {
                dying_animation(g2);
            }
            
            g2.drawImage(image, screen_x, screen_y, game_panel.get_tile_size(),game_panel.get_tile_size(),null);

            change_opacity(g2, 1f);

            // DEBUG
            //g2.setColor(Color.red);
            //g2.drawRect(screen_x +solid_part.x, screen_y + solid_part.y, solid_part.width, solid_part.height);
        }
    }

    public void dying_animation(Graphics2D g2)
    {
        compteur_dead++;
        int ni = 5;

        if(compteur_dead <= ni)
        {
            change_opacity(g2, 0f);
        }
        if(compteur_dead > ni && compteur_dead <= ni * 2)
        {
            change_opacity(g2, 1f);
        }
        if(compteur_dead > ni * 2 && compteur_dead <= ni * 3)
        {
            change_opacity(g2, 0f);
        }
        if(compteur_dead > ni * 3 && compteur_dead <= ni * 4)
        {
            change_opacity(g2, 1f);
        }
        if(compteur_dead > ni * 4 && compteur_dead <= ni * 5)
        {
            change_opacity(g2, 0f);
        }
        if(compteur_dead > ni * 5 && compteur_dead <= ni * 6)
        {
            change_opacity(g2, 1f);
        }
        if(compteur_dead > ni * 6 && compteur_dead <= ni * 7)
        {
            change_opacity(g2, 0f);
        }
        if(compteur_dead > ni * 7 && compteur_dead <= ni * 8)
        {
            change_opacity(g2, 1f);
        }
        if(compteur_dead > ni * 8)
        {
            is_dead = false;
            is_alive = false;
        }
    }

    public void change_opacity(Graphics2D g2, float alpha_value)
    {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha_value));
    }
}
