package obj;

import aff.Box;
import ent.Entity;

public class ObjectSword3 extends Entity
{
    Box game_panel;

    public ObjectSword3(Box game_panel)
    {
        super(game_panel);
        name = "Iron sword";
        set_type(type_sword);

        set_down1(setup("/items/sword3", game_panel.get_tile_size(), game_panel.get_tile_size()));

        set_attack_value(17);
        get_attack_part().x = 16;
        get_attack_part().y = 0;
        get_attack_part().width = 25;
        get_attack_part().height = 40;
        set_description("[" + name + "] \nIron sword, sharp and powerful. \nAttack: +" + get_attack_value());
    }
}
