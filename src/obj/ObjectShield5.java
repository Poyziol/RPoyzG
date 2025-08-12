package obj;

import aff.Box;
import ent.Entity;

public class ObjectShield5 extends Entity
{
    Box game_panel;

    public ObjectShield5(Box game_panel)
    {
        super(game_panel);
        name = "Doritos shield";
        set_type(type_shield);
        
        set_down1(setup("/items/shield5", game_panel.get_tile_size(), game_panel.get_tile_size()));

        set_defense_value(20);
        set_attack_value(3);
        set_description("[" + name + "] \nPower of triangle that gives the \nhightest resistance. \nDefense: +" + get_defense_value() + "\nAttack: +" + get_attack_value());
    }
}
