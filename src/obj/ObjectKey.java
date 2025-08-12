package obj;

import aff.Box;
import ent.Entity;

public class ObjectKey extends Entity
{

    public ObjectKey(Box game_panel)
    {
        super(game_panel);
        name = "key";
        set_type(type_consumable);

        set_down1(setup("/items/key1", game_panel.get_tile_size(), game_panel.get_tile_size()));
        set_description("[" + name + "] \nCan open a door or maybe not....");
    }
}
