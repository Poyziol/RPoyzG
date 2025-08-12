package obj;

import aff.Box;
import ent.Entity;

public class ObjectSword4 extends Entity
{
    Box game_panel;

    public ObjectSword4(Box game_panel)
    {
        super(game_panel);
        name = "Mighty sword";
        set_type(type_sword);

        set_down1(setup("/items/sword4", game_panel.get_tile_size(), game_panel.get_tile_size()));

        set_attack_value(25);
        get_attack_part().x = 16;
        get_attack_part().y = 0;
        get_attack_part().width = 25;
        get_attack_part().height = 40;
        set_description("[" + name + "] \nMighty sword who belonged to the most \nvaliant hero. \nAttack: +" + get_attack_value());
    }
}
