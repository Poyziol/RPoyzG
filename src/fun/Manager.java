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

        for (int i = 0; i < 50; i++) 
        {
            tab_block.add(new Tile());
        }

        nbr_map_tile = new int[game_panel.get_nbr_tile_world_max_h()][game_panel.get_nbr_tile_world_max_v()];
        get_tile_image();
        load_map("/Map/Map.txt");
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
        // old (Test)
        setup(0, "dirt1", false);
        setup(1, "dirt2", false);
        setup(2, "grass1", false);
        setup(3, "grass2", false);
        setup(4, "stone1", true);
        setup(5, "stone2", true);
        setup(6, "water1", true);
        setup(7, "sand1", false);
        setup(8, "tree1", true);
        setup(9, "wood1", false);

        // New 
        setup(10, "dirt1", false);
        setup(11, "dirt2", false);

        setup(12, "grass1", false);
        setup(13, "grass2", false);

        setup(14, "stone1", true);
        setup(15, "stone2", true);

        setup(16, "water1", true);
        setup(17, "water2-top", true);
        setup(18, "water2-bottom", true);
        setup(19, "water2-left", true);
        setup(20, "water2-right", true);
        setup(21, "water2-top-left", true);
        setup(22, "water2-top-right", true);
        setup(23, "water2-bottom-left", true);
        setup(24, "water2-bottom-right", true);
        setup(25, "water3-top", true);
        setup(26, "water3-bottom", true);
        setup(27, "water3-left", true);
        setup(28, "water3-right", true);
        setup(29, "water3-top-left", true);
        setup(30, "water3-top-right", true);
        setup(31, "water3-bottom-left", true);
        setup(32, "water3-bottom-right", true);

        setup(33, "sand1", false);
        setup(34, "sand2-top", false);
        setup(35, "sand2-bottom", false);
        setup(36, "sand2-left", false);
        setup(37, "sand2-right", false);
        setup(38, "sand2-top-left", false);
        setup(39, "sand2-top-right", false);
        setup(40, "sand2-bottom-left", false);
        setup(41, "sand2-bottom-right", false);

        setup(42, "tree1", true);

        setup(43, "wood1", false);

    }

    public void setup(int index, String image_name, boolean collision)
    {
        Utility utility = new Utility();

        try 
        {
           tab_block.get(index).set_image(ImageIO.read(getClass().getResourceAsStream("/Background/" + image_name + ".png")));
           tab_block.get(index).set_image(utility.scale_image(tab_block.get(index).get_image(), game_panel.get_tile_size(), game_panel.get_tile_size()));
           tab_block.get(index).set_collision(collision);
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

            if(world_x + game_panel.get_tile_size() > game_panel.get_player().get_world_x() - game_panel.get_player().get_screen_x() &&
               world_x - game_panel.get_tile_size() < game_panel.get_player().get_world_x() + game_panel.get_player().get_screen_x() &&
               world_y + game_panel.get_tile_size() > game_panel.get_player().get_world_y() - game_panel.get_player().get_screen_y() &&
               world_y - game_panel.get_tile_size() < game_panel.get_player().get_world_y() + game_panel.get_player().get_screen_y())
            {
                g2.drawImage(tab_block.get(nbr_tile).get_image(), screen_x, screen_y,null);
            }
            
            map_horizontale++;

            if(map_horizontale == game_panel.get_nbr_tile_world_max_h())
            {
                map_horizontale = 0;
                map_vertiale++;
            }
        }
    }

}