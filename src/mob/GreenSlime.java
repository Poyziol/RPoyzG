package mob;

import java.util.Random;

import aff.Box;
import ent.Entity;

public class GreenSlime extends Entity
{
    public GreenSlime(Box game_panel) 
    {
        super(game_panel);
        name = "green slime";
        set_type(2);
        set_entity_speed(1);
        set_max_life(4);
        set_life(get_max_life());

        get_solid_part().x = 0;
        get_solid_part().y = 18;
        get_solid_part().width = 48;
        get_solid_part().height = 20;
        set_solid_part_x(get_solid_part().x);
        set_solid_part_y(get_solid_part().y);

        get_image();
    }
    
    public void get_image()
    {
        set_up1(setup("/Monsters/slime-down1"));
        set_up2(setup("/Monsters/slime-down2"));
        set_left1(setup("/Monsters/slime-down1"));
        set_left2(setup("/Monsters/slime-down2"));
        set_right1(setup("/Monsters/slime-down1"));
        set_right2(setup("/Monsters/slime-down2"));
        set_down1(setup("/Monsters/slime-down1"));
        set_down2(setup("/Monsters/slime-down2"));
    }

    public void action()
    {
        set_npc_compteur_animation(get_npc_compteur_animation() + 1);

        if(get_npc_compteur_animation() == 120)
        {
            Random random = new Random();
            int random_number = random.nextInt(100) + 1;

            if(random_number <= 25)
            {
                set_direction("up");
            }
            if(random_number > 25 && random_number <= 50)
            {
                set_direction("down");
            }
            if(random_number > 50 && random_number <= 75)
            {
                set_direction("left");
            }
            if(random_number > 75)
            {
                set_direction("right");
            }

            set_npc_compteur_animation(0);
        }
    }
}
