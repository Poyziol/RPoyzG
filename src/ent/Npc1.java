package ent;

import java.util.Random;

import aff.Box;

public class Npc1 extends Entity
{
    public Npc1(Box game_panel) 
    {
        super(game_panel);

        direction = "down";
        entity_speed = 1;

        get_image();
        set_dialogue();
    }

    public void get_image()
    {
        up1 = setup("/NPC/npc1-top1", game_panel.get_tile_size(), game_panel.get_tile_size());
        up2 = setup("/NPC/npc1-top2", game_panel.get_tile_size(), game_panel.get_tile_size());
        down1 = setup("/NPC/npc1-bottom1", game_panel.get_tile_size(), game_panel.get_tile_size());
        down2 = setup("/NPC/npc1-bottom2", game_panel.get_tile_size(), game_panel.get_tile_size());
        left1 = setup("/NPC/npc1-left1", game_panel.get_tile_size(), game_panel.get_tile_size());
        left2 = setup("/NPC/npc1-left2", game_panel.get_tile_size(), game_panel.get_tile_size());
        right1 = setup("/NPC/npc1-right1", game_panel.get_tile_size(), game_panel.get_tile_size());
        right2 = setup("/NPC/npc1-right2", game_panel.get_tile_size(), game_panel.get_tile_size());
    }

    public void set_dialogue()
    {
        dialogues[0] = "HOHO! Bonjour cher aventurier!";
        dialogues[1] = "Il semblerait que vous soyiez reveille d'un\n long sommeil";
        dialogues[2] = "C'est plutot rare de trouver des personnes\n dans les environs";
        dialogues[3] = "Moi? mon nom est Moz, j etais jadis le \ndoyen de Artaziol avant le grand desastre.";
        dialogues[4] = "Les chemins par ici gargouille de monstres\n faites attention.";
        dialogues[5] = "Soyez pret a tout heure! Ces molusques vert \nont fait bien trop de ravages!";
        dialogues[6] = "Si vous voulez les attaquer, utilisez la \ntouche ENTRER";
        dialogues[7] = "Appuyer sur E pour voir le menu status et \noutils de votre personnage";
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
