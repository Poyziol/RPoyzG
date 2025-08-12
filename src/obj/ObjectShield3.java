package obj;

import aff.Box;
import ent.Entity;

public class ObjectShield3 extends Entity
{
    Box game_panel;

    public ObjectShield3(Box game_panel)
    {
        super(game_panel);
        name = "Iron shield";
        set_type(type_shield);
        
        set_down1(setup("/items/shield3", game_panel.get_tile_size(), game_panel.get_tile_size()));

        set_defense_value(5);
        set_description("[" + name + "] \nIron shield. \nDefense: +" + get_defense_value());
    }
}
