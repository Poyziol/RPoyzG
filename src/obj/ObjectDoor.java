package obj;

import aff.Box;
import ent.Entity;

public class ObjectDoor extends Entity
{
    Box game_panel;

    public ObjectDoor(Box game_panel)
    {
        super(game_panel);
        name = "door";
        
        set_down1(setup("/items/door1"));
        set_object_collision(true);

        get_solid_part().x = 0;
        get_solid_part().y = 16;
        get_solid_part().width = 48;
        get_solid_part().height = 32;
        set_solid_part_x(get_solid_part().x);
        set_solid_part_y(get_solid_part().y);
    }
}
