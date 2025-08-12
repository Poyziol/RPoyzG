package obj;

import aff.Box;
import ent.Entity;

public class ObjectShield2 extends Entity
{
    Box game_panel;

    public ObjectShield2(Box game_panel)
    {
        super(game_panel);
        name = "Stone shield";
        set_type(type_shield);
        
        set_down1(setup("/items/shield2", game_panel.get_tile_size(), game_panel.get_tile_size()));

        set_defense_value(2);
        set_entity_speed(getEntitySpeed() - 1);
        set_description("[" + name + "] \nHas good defense but is heavy. \nDefense: +" + get_defense_value() + "\nSpeed: -1");
    }
}
