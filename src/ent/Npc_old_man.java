package ent;

import java.util.Random;

import aff.Box;

public class Npc_old_man extends Entity 
{
    private static final String[] DIALOGUE_INTRO = {
        "HOHO! Bonjour cher aventurier!",
        "Il semblerait que vous soyiez reveille d'un\n long sommeil",
        "C'est plutot rare de trouver des personnes\n dans les environs",
        "Moi? mon nom est Moz, j etais jadis le \ndoyen de Artaziol avant le grand desastre.",
        "Il faut ouvrir la porte du recif du heros.",
        "Pour cela, trouve une cle et approche la porte."
    };

    private static final String[] DIALOGUE_DOOR = {
        "Tu dois ouvrir la porte du recif du heros.",
        "Trouve une cle puis approche la porte."
    };

    private static final String[] DIALOGUE_POST_DOOR = {
        "La porte est ouverte, bien joue.",
        "Des slimes se sont caches tout autour.",
        "Elimine-les, puis reviens me voir."
    };

    private static final String[] DIALOGUE_SLIMES = {
        "Elimine des slimes pour nettoyer la zone.",
        "Utilise ENTRER pour attaquer."
    };
    
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
        set_dialogue_for_progress();
    }

    private void set_dialogue_for_progress()
    {
        clear_dialogues();
        compteur_dialogue = 0;

        int quest_set = game_panel.get_quest_set_index();
        boolean door_opened = game_panel.get_door_opened();
        boolean slime_phase = quest_set >= 3;

        if(quest_set == 0)
        {
            apply_dialogue(DIALOGUE_INTRO);
        }
        else if(door_opened == false)
        {
            apply_dialogue(DIALOGUE_DOOR);
        }
        else if(slime_phase == false)
        {
            apply_dialogue(DIALOGUE_POST_DOOR);
        }
        else
        {
            apply_dialogue(DIALOGUE_SLIMES);
        }
    }

    private void clear_dialogues()
    {
        for(int ni = 0; ni < dialogues.length; ni++)
        {
            dialogues[ni] = null;
        }
    }

    private void apply_dialogue(String[] lines)
    {
        for(int ni = 0; ni < lines.length && ni < dialogues.length; ni++)
        {
            dialogues[ni] = lines[ni];
        }
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
        set_dialogue_for_progress();
        super.speak();
    }
}
