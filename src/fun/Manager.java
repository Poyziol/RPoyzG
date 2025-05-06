package fun;

import ent.*;
import aff.*;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;
import javax.imageio.ImageIO;

public class Manager 
{
    Box game_panel;
    Vector<Tile> tab_block;
    int nbr_map_tile[][];

    public Manager(Box new_game_panel)
    {
        this.game_panel = new_game_panel;
        tab_block = new Vector<Tile>(); 

        nbr_map_tile = new int[game_panel.get_nbr_tile_world_max_h()][game_panel.get_nbr_tile_world_max_v()];
        get_tile_image();
        load_map("/Map/GiantMap.txt");
    }

    public Box get_game_panel()
    {
        return this.game_panel;
    }

    public int[][] get_nbr_map_tile()
    {
        return this.nbr_map_tile;
    }

    public Vector<Tile> get_tab_block()
    {
        return this.tab_block;
    }

    public void get_tile_image()
    {
        try 
        {
            tab_block.add(new Tile());
            tab_block.get(0).set_image(ImageIO.read(getClass().getResourceAsStream("/Background/dirt1.png")));
            tab_block.add(new Tile());
            tab_block.get(1).set_image(ImageIO.read(getClass().getResourceAsStream("/Background/dirt2.png")));
            tab_block.add(new Tile());
            tab_block.get(2).set_image(ImageIO.read(getClass().getResourceAsStream("/Background/grass1.png")));
            tab_block.add(new Tile());
            tab_block.get(3).set_image(ImageIO.read(getClass().getResourceAsStream("/Background/grass2.png")));
            tab_block.add(new Tile());
            tab_block.get(4).set_image(ImageIO.read(getClass().getResourceAsStream("/Background/stone1.png")));
            tab_block.add(new Tile());
            tab_block.get(5).set_image(ImageIO.read(getClass().getResourceAsStream("/Background/stone2.png")));
            tab_block.get(5).set_collision(true);
            tab_block.add(new Tile());
            tab_block.get(6).set_image(ImageIO.read(getClass().getResourceAsStream("/Background/water1.png")));
            tab_block.get(6).set_collision(true);
            tab_block.add(new Tile());
            tab_block.get(7).set_image(ImageIO.read(getClass().getResourceAsStream("/Background/sand1.png")));
            tab_block.add(new Tile());
            tab_block.get(8).set_image(ImageIO.read(getClass().getResourceAsStream("/Background/tree1.png")));
            tab_block.get(8).set_collision(true);
            tab_block.add(new Tile());
            tab_block.get(9).set_image(ImageIO.read(getClass().getResourceAsStream("/Background/wood1.png")));
        } 
        catch(IOException e) 
        {
            e.printStackTrace();
        }
    }

    public void load_map(String chemin_map)
    {
        try {
            InputStream in = getClass().getResourceAsStream(chemin_map);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            int horizontale = 0;
            int vertiale = 0;

            while(horizontale < game_panel.get_nbr_tile_world_max_h() && vertiale < game_panel.get_nbr_tile_world_max_v())
            {
                String line = reader.readLine();

                while(horizontale < game_panel.get_nbr_tile_world_max_h())
                {
                    String[] string_number = line.split(" ");
                    int number = Integer.parseInt(string_number[horizontale]);

                    nbr_map_tile[horizontale][vertiale] = number;
                    horizontale++;
                }
                if(horizontale == game_panel.get_nbr_tile_world_max_h())
                {
                    horizontale = 0;
                    vertiale++;
                }
            }
            reader.close();

        } 
        catch(IOException e) 
        {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2)
    {
        int map_horizontale = 0;
        int map_vertiale = 0;

        while(map_horizontale < game_panel.get_nbr_tile_world_max_h() && map_vertiale < game_panel.get_nbr_tile_world_max_v())
        {
            int nbr_tile = nbr_map_tile[map_horizontale][map_vertiale];

            int world_x = map_horizontale * game_panel.get_tile_size();
            int world_y = map_vertiale * game_panel.get_tile_size();

            int screen_x = world_x - game_panel.p1.get_world_x() + game_panel.p1.get_screen_x();
            int screen_y = world_y - game_panel.p1.get_world_y() + game_panel.p1.get_screen_y();

            g2.drawImage(tab_block.get(nbr_tile).get_image(), screen_x, screen_y, game_panel.get_tile_size(),game_panel.get_tile_size(),null);
            map_horizontale++;

            if(map_horizontale == game_panel.get_nbr_tile_world_max_h())
            {
                map_horizontale = 0;
                map_vertiale++;
            }
        }
    }

}