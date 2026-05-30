package aff;

import ent.*;
import fun.*;
import obj.ObjectKey;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

public class Box extends JPanel implements Runnable
{
    //Screen settings
    final int original_tile_size = 16;                      // taille 16x16 du perso
    final int scale = 3;                                    // volume de resolution sur l ecran

    final int tile_size = original_tile_size * scale;       // 48x48
    final int nbr_tile_max_h = 16;                          // vue de l ecran horizontale
    final int nbr_tile_max_v = 12;                          // vue de l ecran verticale

    final int screen_width = tile_size * nbr_tile_max_h;    // 768
    final int screen_height = tile_size * nbr_tile_max_v;   // 576

    //World srrings
    final int max_world_h = 250;                            // nombre de cube horizontale de la map
    final int max_world_v = 250;                            // nombre de cube verticale de la map

    //Game State
    int game_state;                                         // Variable state
    final int pause_state = 0;                              // State pour le pause
    final int play_state = 1;                               // State pour le play
    final int dialogue_state = 2;                           // State pour le dialogue
    final int menu_state = 3;                               // State pour menu principal
    final int status_state = 4;                             // State pour les stats du joueur
    final int game_over_state = 5;                          // State pour le game over
    final int quest_history_state = 6;                      // State pour l'historique des quetes

    int fps = 60;                                           
    Thread game_thread;                               
    Key cle = new Key(this);

    UI ui = new UI(this);                                   // Ajout de l UI
    Events events = new Events(this);                       // Ajout des events du jeu
    Collision collision = new Collision(this);              // collision 
    Manager world_template = new Manager(this);             // gestion de jeu
    Sound sound = new Sound();                              // son du jeu (Background)
    Sound sound_effect = new Sound();                       // son du jeu (Effet du jeu)

    public Player p1 = new Player(this,cle);
    public Assets asset = new Assets(this);
    public Entity obj[] = new Entity[10];
    public Entity npc[] = new Entity[5];
    public Entity mob[] = new Entity[50];
    ArrayList<Entity> entity_list = new ArrayList<>();

    boolean[] quest_done = new boolean[3];
    int quest_set_index = 0;
    int quest_kill_count = 0;
    final int quest_xp_reward = 30;
    final int[] quest_kill_targets = {3, 6, 10};
    ArrayList<String> quest_history = new ArrayList<>();
    final String[][] quest_sets = {
        {"Parler au vieux sage"},
        {"Trouver une cle", "Ouvrir la porte du recif du heros"}, 
        {"Reparler au vieux sage"},
        {"Eliminer 3 slimes", "Eliminer 6 slimes", "Eliminer 10 slimes"}
    };

    Entity dialogue_entity = null;
    boolean door_opened = false;

    public int get_nbr_tile_world_max_h()
    {
        return this.max_world_h;
    }

    public int get_nbr_tile_world_max_v()
    {
        return this.max_world_v;
    }

    public int get_screen_width()
    {
        return this.screen_width;
    }

    public int get_screen_height()
    {
        return this.screen_height;
    }

    public int get_world_width()
    {
        return this.max_world_h * this.tile_size;
    }

    public int get_world_height()
    {
        return this.max_world_v * this.tile_size;
    }

    public int get_tile_size()
    {
        return this.tile_size;
    }

    public int get_nbr_tile_max_h()
    {
        return this.nbr_tile_max_h;
    }

    public int get_nbr_tile_max_v()
    {
        return this.nbr_tile_max_v;
    }

    public Collision get_collision()
    {
        return this.collision;
    }

    public Player get_player()
    {
        return this.p1;
    }

    public Events get_events()
    {
        return this.events;
    }

    public Manager get_world_template() 
    {
        return this.world_template;
    }

    public Entity[] get_obj()
    {
        return this.obj;
    }

    public UI get_ui()
    {
        return this.ui;
    }

    public Key get_key()
    {
        return this.cle;
    }

    public int get_camera_x()
    {
        int cam_x = p1.get_world_x();
        int min_cam_x = p1.get_screen_x();
        int max_cam_x = get_world_width() - p1.get_screen_x();

        if(cam_x < min_cam_x)
        {
            cam_x = min_cam_x;
        }
        if(cam_x > max_cam_x)
        {
            cam_x = max_cam_x;
        }

        return cam_x;
    }

    public int get_camera_y()
    {
        int cam_y = p1.get_world_y();
        int min_cam_y = p1.get_screen_y();
        int max_cam_y = get_world_height() - p1.get_screen_y();

        if(cam_y < min_cam_y)
        {
            cam_y = min_cam_y;
        }
        if(cam_y > max_cam_y)
        {
            cam_y = max_cam_y;
        }

        return cam_y;
    }

    public int get_quest_step_count()
    {
        return quest_done.length;
    }

    public boolean is_quest_done(int index)
    {
        return quest_done[index];
    }

    public int get_active_quest_index()
    {
        for(int ni = 0; ni < quest_done.length; ni++)
        {
            if(quest_done[ni] == false)
            {
                return ni;
            }
        }

        return -1;
    }

    public String get_quest_text(int index)
    {
        String base = quest_sets[quest_set_index][index];

        if(quest_set_index == 3)
        {
            int target = quest_kill_targets[index];
            int count = quest_kill_count;
            if(count > target)
            {
                count = target;
            }
            return base + " (" + count + "/" + target + ")";
        }

        return base;
    }

    public int get_quest_set_index()
    {
        return quest_set_index;
    }

    public int get_quest_set_count()
    {
        return quest_sets.length;
    }

    public int get_quest_set_size(int set_index)
    {
        if(set_index < 0 || set_index >= quest_sets.length)
        {
            return 0;
        }

        int size = quest_sets[set_index].length;
        return size > 3 ? 3 : size;
    }

    public String get_quest_set_text(int set_index, int step_index)
    {
        return quest_sets[set_index][step_index];
    }

    public ArrayList<String> get_quest_history()
    {
        return quest_history;
    }

    public boolean get_door_opened()
    {
        return door_opened;
    }

    public void set_door_opened(boolean new_door_opened)
    {
        door_opened = new_door_opened;
    }

    public void start_intro_dialogue(String text)
    {
        dialogue_entity = null;
        ui.set_dialogue(text);
        game_state = dialogue_state;
    }

    public void start_dialogue(Entity entity)
    {
        dialogue_entity = entity;
        if(entity != null)
        {
            entity.speak();
            game_state = dialogue_state;
        }
        else
        {
            dialogue_entity = null;
            game_state = play_state;
        }
    }

    public void advance_dialogue()
    {
        if(dialogue_entity == null)
        {
            game_state = play_state;
            return;
        }

        if(dialogue_entity.next_dialogue() == false)
        {
            if(dialogue_entity instanceof Npc_old_man)
            {
                if(quest_set_index == 0)
                {
                    complete_quest_step(0);
                }
                else if(quest_set_index == 2)
                {
                    complete_quest_step(0);
                }
            }

            dialogue_entity = null;
            game_state = play_state;
        }
    }

    public void complete_quest_step(int index)
    {
        if(index < 0 || index >= quest_done.length)
        {
            return;
        }

        if(quest_done[index] == false)
        {
            quest_done[index] = true;
            quest_history.add("Set " + (quest_set_index + 1) + ": " + quest_sets[quest_set_index][index]);
        }

        if(is_quest_set_complete())
        {
            award_quest_set();
            next_quest_set();
        }
    }

    public void register_monster_kill(String monster_name)
    {
        if(quest_set_index != 3)
        {
            return;
        }
        if(monster_name == null || monster_name.equals("green slime") == false)
        {
            return;
        }

        quest_kill_count++;

        if(quest_kill_count >= quest_kill_targets[0])
        {
            complete_quest_step(0);
        }
        if(quest_kill_count >= quest_kill_targets[1])
        {
            complete_quest_step(1);
        }
        if(quest_kill_count >= quest_kill_targets[2])
        {
            complete_quest_step(2);
        }
    }

    public void reset_quests()
    {
        quest_set_index = 0;
        quest_done = new boolean[get_quest_set_size(quest_set_index)];
        quest_kill_count = 0;
        quest_history.clear();
        door_opened = false;
    }

    private boolean is_quest_set_complete()
    {
        for(int ni = 0; ni < quest_done.length; ni++)
        {
            if(quest_done[ni] == false)
            {
                return false;
            }
        }

        return true;
    }

    private void award_quest_set()
    {
        int reward = (int)Math.floor(p1.get_max_life() * 0.75);
        if(reward < 1)
        {
            reward = 1;
        }

        p1.setExp(p1.getExp() + reward);
        p1.check_level_up();
        ui.show_message("Objectifs termines! +" + reward + " XP");
    }

    private void next_quest_set()
    {
        quest_set_index++;
        if(quest_set_index >= quest_sets.length)
        {
            quest_set_index = 0;
        }

        quest_done = new boolean[get_quest_set_size(quest_set_index)];
        quest_kill_count = 0;

        ui.show_message("Nouveaux objectifs");

        if(quest_set_index == 1 && player_has_key() == true)
        {
            complete_quest_step(0);
        }
    }

    private boolean player_has_key()
    {
        for(int ni = 0; ni < p1.get_inventory().size(); ni++)
        {
            if(p1.get_inventory().get(ni) instanceof ObjectKey)
            {
                return true;
            }
        }

        return false;
    }

    public int get_play_state()
    {
        return this.play_state;
    }

    public int get_status_state()
    {
        return this.status_state;
    }

    public int get_game_over_state()
    {
        return this.game_over_state;
    }

    public int get_quest_history_state()
    {
        return this.quest_history_state;
    }

    public int get_pause_state()
    {
        return this.pause_state;
    }

    public int get_dialogue_state()
    {
        return this.dialogue_state;
    }

    public int get_menu_state()
    {
        return this.menu_state;
    }

    public int get_game_state()
    {
        return this.game_state;
    }

    public void set_game_state(int new_game_state)
    {
        this.game_state = new_game_state;
    }

    public Box()
    {
        this.setPreferredSize(new Dimension(screen_width,screen_height));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(cle);
        this.setFocusable(true);
        setup_game();
        start_game_thread();
    }

    public void setup_game()
    {
        reset_quests();
        asset.set_object();
        asset.set_npc();
        asset.set_mob();
        //play_music(2);
        game_state = menu_state;
    }

    public void start_game_thread()
    {
        game_thread = new Thread(this);
        game_thread.start();
    }

    public void retry()
    {
        p1.set_default_positions();
        p1.restore_life_and_mana();
        asset.set_npc();
        asset.set_mob();
        p1.setExp(0);
    }

    public void restart()
    {
        p1.set_default_values();
        p1.set_items();
        asset.set_object();
        asset.set_npc();
        asset.set_mob();
        reset_quests();
    }

    @Override
    public void run() 
    {
        double intervalle = 1000000000/fps;
        double delta = 0;
        long last_Time = System.nanoTime();
        long actual_time;
        long timer = 0;
        int draw_time = 0;
        System.out.println("FPS: " + fps);

        while(game_thread != null)
        {
            actual_time = System.nanoTime();
            delta += (actual_time - last_Time) / intervalle;
            timer += actual_time - last_Time;
            last_Time = actual_time;

            if(delta >= 1) 
            {
                update();
                repaint();
                delta--;
                draw_time++;
            }

            if(timer >= 1000000000)
            {
                if(draw_time != 60)
                {
                    System.out.println("FPS: " + draw_time);
                }
                timer = 0;
                draw_time = 0;
            }
        }
    }

    public void update()
    {
        if(game_state == play_state)
        {   
            //PLAYER
            p1.update();
            //NPC
            for(int ni = 0;ni < npc.length;ni++)
            {
                if(npc[ni] != null)
                {
                    npc[ni].update();
                }
            }
            //MOB
            for(int ji = 0;ji < mob.length;ji++)
            {
                if(mob[ji] != null)
                {
                    if(mob[ji].get_is_alive() == true && mob[ji].get_is_dead() == false)
                    {
                        mob[ji].update();
                    }
                    if(mob[ji].get_is_alive() == false)
                    {
                        mob[ji] = null;
                    }
                }
            }
        }
        if(game_state == pause_state)
        {

        }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        // DEBUG
        long draw_start = 0;
        if(cle.get_debug_time() == true)
        {
            draw_start = System.nanoTime();
        }

        // MAIN MENU
        if(game_state == menu_state)
        {
            ui.draw(g2);
        }

        // OTHERS
        else
        {
            // TILE
            world_template.draw(g2);

            // ADD TO ENTITYLIST
            entity_list.add(p1);
            for(int ni = 0;ni < npc.length;ni++)
            {
                if(npc[ni] != null)
                {
                    entity_list.add(npc[ni]);
                }
            }
            for(int ji = 0;ji < obj.length;ji++)
            {
                if(obj[ji] != null)
                {
                    entity_list.add(obj[ji]);
                }
            }
            for(int ka = 0;ka < mob.length;ka++)
            {
                if(mob[ka] != null)
                {
                    entity_list.add(mob[ka]);
                }
            }

            // SORT
            Collections.sort(entity_list, new Comparator<Entity>() 
            {

                @Override
                public int compare(Entity e1, Entity e2) 
                {
                    int result = Integer.compare(e1.get_world_y(), e2.get_world_y());
                    return result;
                }
                
            });
            
            // DRAW ENTITIES
            for(int ka = 0;ka < entity_list.size();ka++)
            {
                entity_list.get(ka).draw(g2);
            }

            // EMPTY ENTITY
            entity_list.clear();

            // UI
            ui.draw(g2);
        }

        // DEBUG
        if(cle.get_debug_time() == true)
        {
            long draw_end = System.nanoTime();
            long time_passed = draw_end - draw_start;

            g2.setFont(ui.arial_12);
            g2.setColor(Color.white);
            
            int x = 10;
            int y = 400;
            final int line_height = 15;

            y += line_height;
            g2.drawString("World X" + p1.get_world_x(), x, y);
            y += line_height;
            g2.drawString("World Y" + p1.get_world_y(), x, y);
            y += line_height;
            g2.drawString("Col" + (p1.get_world_x() + p1.get_solid_part().x) / tile_size, x, y);
            y += line_height;
            g2.drawString("Row" + (p1.get_world_y() + p1.get_solid_part().y) / tile_size, x, y);
            y += line_height;
            g2.drawString("Draw time: " + time_passed, 10, 400); 
        }

        g2.dispose();
    }

    public void play_music(int ni)
    {
        sound.set_file(ni);
        sound.play();
        sound.loop();
    }

    public void stop_music()
    {
        sound.stop();
    }

    public void play_sound_effect(int ni)
    {
        sound_effect.set_file(ni);
        sound_effect.play();
    }

    public native void salama();
}
