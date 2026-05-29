package obj;

import aff.Box;
import ent.Entity;

public class ObjectPotion2 extends Entity
{
    Box game_panel;
    int value = get_max_life();

    public ObjectPotion2(Box game_panel)
    {
        super(game_panel);
        name = "Blue potion";
        this.game_panel = game_panel;
        set_type(type_consumable);

        set_down1(setup("/items/potion2", game_panel.get_tile_size(), game_panel.get_tile_size()));
        set_description("[" + name + "] \nRestore the 50% of your mana");
    }

    public void use(Entity entity)
    {
        game_panel.set_game_state(game_panel.get_dialogue_state());
        game_panel.get_ui().set_dialogue("You drink a " + name + "\nyour life is increased by " + value + "points!");
        entity.set_life(entity.get_life() + value);

        if(entity.get_life() > entity.get_max_life())
        {
            entity.set_life(entity.get_max_life());
        }

        game_panel.play_sound_effect(1);
    }
}
