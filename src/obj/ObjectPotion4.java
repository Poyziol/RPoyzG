package obj;

import aff.Box;
import ent.Entity;

public class ObjectPotion4 extends Entity
{
    Box game_panel;
    int value = get_max_life();

    public ObjectPotion4(Box game_panel)
    {
        super(game_panel);
        this.game_panel = game_panel;
        name = "Fanta ananas";
        set_type(type_consumable);

        
        set_down1(setup("/items/potion4", game_panel.get_tile_size(), game_panel.get_tile_size()));
        set_description("[" + name + "] \nRestore the 100% of your life and mana");
    }

    public void use(Entity entity)
    {
        value = entity.get_max_life();

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
