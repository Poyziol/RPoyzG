package obj;

import aff.Box;
import ent.Entity;

public class ObjectShield4 extends Entity
{
    Box game_panel;

    public ObjectShield4(Box game_panel)
    {
        super(game_panel);
        name = "Mighty shield";
        set_type(type_shield);
        
        set_down1(setup("/items/shield4", game_panel.get_tile_size(), game_panel.get_tile_size()));

        set_defense_value(10);
        set_description("[" + name + "] \nMighty shield used by heros. \nDefense: +" + get_defense_value());
    }
}
