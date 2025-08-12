package obj;

import aff.Box;
import ent.Entity;

public class ObjectShield1 extends Entity
{
    Box game_panel;

    public ObjectShield1(Box game_panel)
    {
        super(game_panel);
        name = "Wooden shield";
        set_type(type_shield);
        
        set_down1(setup("/items/shield1", game_panel.get_tile_size(), game_panel.get_tile_size()));

        set_defense_value(1);
        set_description("[" + name + "] \nBasic shield made of wood but has \npotential. \nDefense: +" + get_defense_value());
    }
}
