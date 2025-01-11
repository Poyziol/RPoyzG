package aff;

import fun.*;
import ent.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxUI.KeyHandler;

public class Box extends JPanel implements Runnable
{
    //Screen settings
    final int original_tile_size = 16;                      ///taille 16x16 du perso
    final int scale = 3;                                    //volume de resolution sur l ecran

    final int tile_size = original_tile_size * scale;       //48x48
    final int nbr_tile_max_h = 16;
    final int nbr_tile_max_v = 12;

    final int screen_width = tile_size * nbr_tile_max_h;    //768
    final int screen_height = tile_size * nbr_tile_max_v;   //576

    //World srrings
    final int max_world_h = 40;
    final int max_world_v = 50;
    final int max_world_width = tile_size * max_world_h;
    final int max_world_height = tile_size * max_world_v;

    int fps = 60;
    Thread game_thread;
    Key cle = new Key();
    Collision collision = new Collision(this);

    public Player p1 = new Player(this,cle);
    Manager world_template = new Manager(this);

    public int get_nbr_tile_world_max_h()
    {
        return this.max_world_h;
    }

    public int get_nbr_tile_world_max_v()
    {
        return this.max_world_v;
    }

    public int get_max_world_width()
    {
        return this.max_world_width;
    }

    public int get_max_world_height()
    {
        return this.max_world_height;
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

    public Manager get_world_template() {
        return this.world_template;
    }

    public Box()
    {
        this.setPreferredSize(new Dimension(screen_width,screen_height));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(cle);
        this.setFocusable(true);
        start_game_thread();
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
                System.out.println("FPS: " + draw_time);
                timer = 0;
                draw_time = 0;
            }
        }
    }

    public void update()
    {
        p1.update();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        world_template.draw(g2);
        p1.draw(g2);

        g2.dispose();
    }

}
