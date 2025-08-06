package obj;

import aff.Box;
import ent.Entity;

public class ObjectKey extends Entity
{

    public ObjectKey(Box game_panel)
    {
        super(game_panel);
        name = "key";

        set_down1(setup("/items/key1"));

    }
}
