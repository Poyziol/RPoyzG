package obj;

import aff.Box;
import ent.Entity;

public class ObjectSword5 extends Entity
{
    Box game_panel;

    public ObjectSword5(Box game_panel)
    {
        super(game_panel);
        name = "Sword of devotion";
        set_type(type_sword);

        set_down1(setup("/items/sword1", game_panel.get_tile_size(), game_panel.get_tile_size()));

        set_attack_value(51);
        get_attack_part().x = 16;
        get_attack_part().y = 0;
        get_attack_part().width = 25;
        get_attack_part().height = 45;
        set_description("[" + name + "] \nLegendary sword made of wood but has \npotential. \nAttack: +" + get_attack_value());
        set_description("[" + name + "] \nThe power of the perfect triangle \nmix with the Lengendary Sword of \nthe largest hero of all time. \nAttack: +" + get_attack_value());
    }
}
