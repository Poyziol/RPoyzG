package obj;

import aff.Box;
import ent.Entity;

public class ObjectSword1 extends Entity
{
    Box game_panel;

    public ObjectSword1(Box game_panel)
    {
        super(game_panel);
        name = "Wooden sword";
        set_type(type_sword);

        set_down1(setup("/items/sword1", game_panel.get_tile_size(), game_panel.get_tile_size()));

        set_attack_value(1);
        get_attack_part().x = 16;
        get_attack_part().y = 5;
        get_attack_part().width = 25;
        get_attack_part().height = 35;
        set_description("[" + name + "] \nBasic sword made of wood but has \npotential. \nAttack: +" + get_attack_value());
    }
}
