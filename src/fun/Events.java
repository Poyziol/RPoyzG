package fun;

import aff.Box;

public class Events 
{
    Box game_panel;
    RectangleEvents event_rectangle[][];

    int previous_event_x, previous_event_y;
    boolean can_touch_event = true;

    public Events(Box game_panel)
    {
        this.game_panel = game_panel;

        event_rectangle = new RectangleEvents[game_panel.get_nbr_tile_world_max_h()][game_panel.get_nbr_tile_max_v()];

        int col = 0;
        int row = 0;

        while(col < game_panel.get_nbr_tile_max_h() && row < game_panel.get_nbr_tile_max_v())
        {
            event_rectangle[col][row] = new RectangleEvents();
            event_rectangle[col][row].x = 23;
            event_rectangle[col][row].y = 23;
            event_rectangle[col][row].width = 2;
            event_rectangle[col][row].height = 2;
            event_rectangle[col][row].event_rectangle_x = event_rectangle[col][row].x;
            event_rectangle[col][row].event_rectangle_y = event_rectangle[col][row].y;

            col++;
            if(col == game_panel.get_nbr_tile_max_h())
            {
                col = 0;
                row++;
            }
        }
    }

    public void check_events()
    {
        int x_distance = Math.abs(game_panel.get_player().get_world_x() - previous_event_x);
        int y_distance = Math.abs(game_panel.get_player().get_world_y() - previous_event_y);
        int distance = Math.max(x_distance, y_distance);
        if(distance > game_panel.get_tile_size())
        {
            can_touch_event = true;
        } 


        if(can_touch_event == true)
        {
            if(hit(11, 11, "any") == true) { take_damage(11, 11, game_panel.get_dialogue_state()); }
            if(hit(11, 11, "right") == true) { heal(11, 11, game_panel.get_dialogue_state()); }
            //if(hit(11, 11, "right") == true) { teleport(11, 11, game_panel.get_dialogue_state()); }
        }
    }

    public boolean hit(int col, int row, String request_direction)
    {
        boolean hit = false;

        game_panel.get_player().get_solid_part().x = game_panel.get_player().get_world_x() + game_panel.get_player().get_solid_part().x;
        game_panel.get_player().get_solid_part().y = game_panel.get_player().get_world_y() + game_panel.get_player().get_solid_part().y;

        event_rectangle[col][row].x = col * game_panel.get_tile_size() + event_rectangle[col][row].x;
        event_rectangle[col][row].y = row * game_panel.get_tile_size() + event_rectangle[col][row].y;

        if(game_panel.get_player().get_solid_part().intersects(event_rectangle[col][row]) && event_rectangle[col][row].event_done == false)
        {
            if(game_panel.get_player().getDirection().contentEquals(request_direction) || request_direction.contentEquals("any"))
            {
                hit = true;
                previous_event_x = game_panel.get_player().get_world_x();
                previous_event_y = game_panel.get_player().get_world_y();
            }
        }

        game_panel.get_player().get_solid_part().x = game_panel.get_player().get_solid_part_x();
        game_panel.get_player().get_solid_part().y = game_panel.get_player().get_solid_part_y();
        event_rectangle[col][row].x = event_rectangle[col][row].event_rectangle_x;
        event_rectangle[col][row].y = event_rectangle[col][row].event_rectangle_y;

        return hit;
    }

    public void take_damage(int col, int row, int game_state)
    {
        game_panel.set_game_state(game_state);
        game_panel.get_ui().set_dialogue("You took damage: -0.5");
        game_panel.get_player().set_life(game_panel.get_player().get_life() - 1);

        //event_rectangle[col][row].event_done = true;
        can_touch_event = false;
    }

    public void heal(int col, int row, int game_state)
    {
        if(game_panel.get_key().get_enter_pressed() == true)
        {
            game_panel.set_game_state(game_state);
            game_panel.get_ui().set_dialogue("You gain life: +1");
            game_panel.get_player().set_life(game_panel.get_player().get_life() + 2);
        }
    }

    public void teleport(int col, int row, int game_state)
    {
        game_panel.set_game_state(game_state);
        game_panel.get_ui().set_dialogue("Teleported!");
        game_panel.get_player().setWorld_x(game_panel.get_tile_size() * 10);
        game_panel.get_player().setWorld_y(game_panel.get_tile_size() * 10);
    }
}
