package obj;

import aff.Box;
import ent.Entity;

public class ObjectLife extends Entity
{
    Box game_panel;

    public ObjectLife(Box game_panel)
    {
        super(game_panel);
        name = "life";
        image = setup("/items/life0");
        image2 = setup("/items/life1-red");
        image3 = setup("/items/life2-red");
    }
}
