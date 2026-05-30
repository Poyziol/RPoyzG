package fun;

import ent.*;
import aff.*;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Vector;
import javax.imageio.ImageIO;

public class Manager 
{
    Box game_panel;
    Vector<Tile> tab_block;
    int nbr_map_tile[][];
    ArrayList<String> file_name = new ArrayList<>();
    ArrayList<String> collision_status = new ArrayList<>();

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
        load_map("/Map/Map0.txt");
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
        setup(0, "000", false);
        setup(1, "001", false);
        setup(2, "002", false);
        setup(3, "003", false);
        setup(4, "003", false);
        setup(5, "003", false);
        setup(6, "003", false);
        setup(7, "007", false);
        setup(8, "008", false);
        setup(9, "009", false);

        setup(10, "010", false);
        setup(11, "011", false);

        setup(12, "012", false);
        setup(13, "013", false);

        setup(14, "014", false);
        setup(15, "015", false);

        setup(16, "016", true);
        setup(17, "017", true);
        setup(18, "018", true);
        setup(19, "019", true);
        setup(20, "020", true);
        setup(21, "021", true);
        setup(22, "022", true);
        setup(23, "023", true);
        setup(24, "024", true);
        setup(25, "025", true);
        setup(26, "026", true);
        setup(27, "027", true);
        setup(28, "028", true);
        setup(29, "029", true);
        setup(30, "030", true);
        setup(31, "031", true);
        setup(32, "032", true);

        setup(33, "033", true);
        setup(34, "034", true);
        setup(35, "035", true);
        setup(36, "036", false);

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

        int cam_x = game_panel.get_camera_x();
        int cam_y = game_panel.get_camera_y();

        while(map_horizontale < game_panel.get_nbr_tile_world_max_h() && map_vertiale < game_panel.get_nbr_tile_world_max_v())
        {
            int nbr_tile = nbr_map_tile[map_horizontale][map_vertiale];

            int world_x = map_horizontale * game_panel.get_tile_size();
            int world_y = map_vertiale * game_panel.get_tile_size();

            int screen_x = world_x - cam_x + game_panel.get_player().get_screen_x();
            int screen_y = world_y - cam_y + game_panel.get_player().get_screen_y();

            if(world_x + game_panel.get_tile_size() > cam_x - game_panel.get_player().get_screen_x() &&
               world_x - game_panel.get_tile_size() < cam_x + game_panel.get_player().get_screen_x() &&
               world_y + game_panel.get_tile_size() > cam_y - game_panel.get_player().get_screen_y() &&
               world_y - game_panel.get_tile_size() < cam_y + game_panel.get_player().get_screen_y())
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