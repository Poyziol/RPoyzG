package obj;

import aff.Box;
import ent.Entity;

public class ObjectAxe3 extends Entity
{
    Box game_panel;

    public ObjectAxe3(Box game_panel)
    {
        super(game_panel);
        name = "Iron axe";
        set_type(type_axe);

        set_down1(setup("/items/axe3", game_panel.get_tile_size(), game_panel.get_tile_size()));

        set_attack_value(5);
        get_attack_part().x = 16;
        get_attack_part().y = 5;
        get_attack_part().width = 25;
        get_attack_part().height = 35;
        set_description("[" + name + "] \nAn axe made of iron. you can cut \ntrees with \nAttack: +" + get_attack_value());
    }
}
