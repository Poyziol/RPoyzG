package obj;

import aff.Box;
import ent.Entity;

public class ObjectChest extends Entity
{
    Box game_panel;

    public ObjectChest(Box game_panel)
    {
        super(game_panel);
        name = "chest";
        
        set_down1(setup("/items/chest1"));
        set_object_collision(true);
    }
}
