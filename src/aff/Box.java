package aff;

import ent.*;
import fun.*;
import obj.SuperObject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
    final int max_world_h = 100;                            // nombre de cube horizontale de la map
    final int max_world_v = 100;                            // nombre de cube verticale de la map

    //Game State
    int game_state;                                         // Variable state
    final int pause_state = 0;                              // State pour le pause
    final int play_state = 1;                               // State pour le play
    final int dialogue_state = 2;                           // State pour le dialogue
    final int menu_state = 3;                               // State pour menu principal

    int fps = 60;                                           
    Thread game_thread;                                 
    Key cle = new Key(this);

    UI ui = new UI(this);                                   // Ajout de l UI
    Collision collision = new Collision(this);              // collision 
    Manager world_template = new Manager(this);             // gestion de jeu
    Sound sound = new Sound();                              // son du jeu (Background)
    Sound sound_effect = new Sound();                       // son du jeu (Effet du jeu)

    public Player p1 = new Player(this,cle);
    public Assets asset = new Assets(this);
    public SuperObject obj[] = new SuperObject[10];
    public Entity npc[] = new Entity[5];

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

    public Manager get_world_template() 
    {
        return this.world_template;
    }

    public SuperObject[] get_obj()
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

    public int get_play_state()
    {
        return this.play_state;
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
        asset.set_object();
        asset.set_npc();
        //play_music(2);
        game_state = menu_state;
    }

    public void start_game_thread()
    {
        game_thread = new Thread(this);
        game_thread.start();
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
            for(int ni = 0; ni < npc.length;ni++)
            {
                if(npc[ni] != null)
                {
                    npc[ni].update();
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
        if(cle.get_check_draw_time() == true)
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

            // OBJECT
            for(int ni = 0;ni < obj.length;ni++)
            {
                if(obj[ni] != null)
                {
                    obj[ni].draw(g2, this);
                }
            }

            //NPC
            for(int ji = 0;ji < npc.length;ji++)
            {
                if(npc[ji] != null)
                {
                    npc[ji].draw(g2);
                }
            }

            // PLAYER
            p1.draw(g2);

            // UI
            ui.draw(g2);
        }

        // DEBUG
        if(cle.get_check_draw_time() == true)
        {
            long draw_end = System.nanoTime();
            long time_passed = draw_end - draw_start;
            g2.setColor(Color.white);
            g2.drawString("Draw time: " + time_passed, 10, 400); 
            System.out.println("Draw time: " + time_passed);
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
