package obj;

import aff.Box;
import ent.Entity;

public class ObjectBoot extends Entity
{
    Box game_panel;

    public ObjectBoot(Box game_panel)
    {
        super(game_panel);
        name = "boot";
        
        set_down1(setup("items/boot1"));
    }
}
