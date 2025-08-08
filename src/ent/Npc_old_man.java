package ent;

import java.util.Random;

import aff.Box;

public class Npc_old_man extends Entity 
{
    public Npc_old_man(Box game_panel) 
    {
        super(game_panel);

        direction = "down";
        entity_speed = 1;

        get_image();
        set_dialogue();
    }

    public void get_image()
    {
        up1 = setup("/NPC/npc2-top1", game_panel.get_tile_size(), game_panel.get_tile_size());
        up2 = setup("/NPC/npc2-top2", game_panel.get_tile_size(), game_panel.get_tile_size());
        down1 = setup("/NPC/npc2-bottom1", game_panel.get_tile_size(), game_panel.get_tile_size());
        down2 = setup("/NPC/npc2-bottom2", game_panel.get_tile_size(), game_panel.get_tile_size());
        left1 = setup("/NPC/npc2-left1", game_panel.get_tile_size(), game_panel.get_tile_size());
        left2 = setup("/NPC/npc2-left2", game_panel.get_tile_size(), game_panel.get_tile_size());
        right1 = setup("/NPC/npc2-right1", game_panel.get_tile_size(), game_panel.get_tile_size());
        right2 = setup("/NPC/npc2-right2", game_panel.get_tile_size(), game_panel.get_tile_size());
    }

    public void set_dialogue()
    {
        dialogues[0] = "HOHO! Bonjour cher aventurier!";
        dialogues[1] = "Il semblerait que vous soyiez reveille d'un\n long sommeil";
        dialogues[2] = "C'est plutot rare de trouver des personnes\n dans les environs";
        dialogues[3] = "Moi? mon nom est Moz, j etais jadis le \ndoyen de Artaziol";
        dialogues[4] = "Les chemins par ici gargouille de monstres\n et sont semer d'embuche!";
        dialogues[5] = "Soyez pret a tout heure! Ces molusques vert \nont fait bien trop de ravages!";
    }

    public void action()
    {
        npc_compteur_animation++;

        if(npc_compteur_animation == 120)
        {
            Random random = new Random();
            int random_number = random.nextInt(100) + 1;

            if(random_number <= 25)
            {
                direction = "up";
            }
            if(random_number > 25 && random_number <= 50)
            {
                direction = "down";
            }
            if(random_number > 50 && random_number <= 75)
            {
                direction = "left";
            }
            if(random_number > 75)
            {
                direction = "right";
            }

            npc_compteur_animation = 0;
        }
    }

    public void speak()
    {
        super.speak();
    }
}
