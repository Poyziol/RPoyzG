package fun;

import ent.*;
import aff.*;

public class Collision 
{
    Box game_panel;

    public Collision(Box new_game_panel)
    {
        this.game_panel = new_game_panel;
    }

    public void check_type_tile(Entity entity)
    {
        int world_x_solid_left = entity.get_world_x() + entity.get_solid_part().x;
        int world_x_solid_right = entity.get_world_x() + entity.get_solid_part().x + entity.get_solid_part().width;
        int world_y_solid_up = entity.get_world_y() + entity.get_solid_part().y;
        int world_y_solid_down = entity.get_world_y() + entity.get_solid_part().y + entity.get_solid_part().height;

        int num_column_left = world_x_solid_left/game_panel.get_tile_size();
        int num_column_right = world_x_solid_right/game_panel.get_tile_size();
        int num_line_up = world_y_solid_up/game_panel.get_tile_size();
        int num_line_down = world_y_solid_down/game_panel.get_tile_size();

        int tile_num1 , tile_num2;

        switch (entity.getDirection()) {
            case "up":
                num_line_up = (world_y_solid_up - entity.getEntitySpeed())/game_panel.get_tile_size();
                tile_num1 = game_panel.get_world_template().get_nbr_map_tile()[num_column_left][num_line_up];
                tile_num2 = game_panel.get_world_template().get_nbr_map_tile()[num_column_right][num_line_up];
                if(game_panel.get_world_template().get_tab_block().get(tile_num1).get_collision() == true || game_panel.get_world_template().get_tab_block().get(tile_num2).get_collision() == true)
                {
                    entity.set_collision(true);
                }
                break;
            case "down":
                num_line_down = (world_y_solid_down - entity.getEntitySpeed())/game_panel.get_tile_size();
                tile_num1 = game_panel.get_world_template().get_nbr_map_tile()[num_column_left][num_line_down];
                tile_num2 = game_panel.get_world_template().get_nbr_map_tile()[num_column_right][num_line_down];
                if(game_panel.get_world_template().get_tab_block().get(tile_num1).get_collision() == true || game_panel.get_world_template().get_tab_block().get(tile_num2).get_collision() == true)
                {
                    entity.set_collision(true);
                }
                break;
            case "left":
                num_column_left = (world_x_solid_left - entity.getEntitySpeed())/game_panel.get_tile_size();
                tile_num1 = game_panel.get_world_template().get_nbr_map_tile()[num_column_left][num_line_up];
                tile_num2 = game_panel.get_world_template().get_nbr_map_tile()[num_column_left][num_line_down];
                if(game_panel.get_world_template().get_tab_block().get(tile_num1).get_collision() == true || game_panel.get_world_template().get_tab_block().get(tile_num2).get_collision() == true)
                {
                    entity.set_collision(true);
                }
                break;
            case "right":
                num_column_right = (world_x_solid_right - entity.getEntitySpeed())/game_panel.get_tile_size();
                tile_num1 = game_panel.get_world_template().get_nbr_map_tile()[num_column_right][num_line_up];
                tile_num2 = game_panel.get_world_template().get_nbr_map_tile()[num_column_right][num_line_down];
                if(game_panel.get_world_template().get_tab_block().get(tile_num1).get_collision() == true || game_panel.get_world_template().get_tab_block().get(tile_num2).get_collision() == true)
                {
                    entity.set_collision(true);
                }
                break;
            default:
                break;
        }
    }

}
