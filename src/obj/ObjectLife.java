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
        image = setup("/items/life0", game_panel.get_tile_size(), game_panel.get_tile_size());
        image2 = setup("/items/life1-red", game_panel.get_tile_size(), game_panel.get_tile_size());
        image3 = setup("/items/life2-red", game_panel.get_tile_size(), game_panel.get_tile_size());
    }
}
