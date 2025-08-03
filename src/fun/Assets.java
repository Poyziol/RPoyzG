package fun;

import aff.Box;
import ent.Npc_old_man;
import obj.ObjectDoor;
public class Assets 
{
    Box box;

    public Assets(Box box1) 
    {
        this.box = box1;
    }

    public void set_object() 
    {
        
    }

    public void set_npc()
    {
        int tileSize = box.get_tile_size();

        box.npc[0] = new Npc_old_man(box);
        box.npc[0].setWorld_x(box.get_tile_size()*14);
        box.npc[0].setWorld_y(box.get_tile_size()*19);

        box.obj[1] = new ObjectDoor(box);
        box.obj[1].setWorld_x(15 * tileSize);
        box.obj[1].setWorld_y(80 * tileSize);

        box.obj[2] = new ObjectDoor(box);
        box.obj[2].setWorld_x(36 * tileSize);
        box.obj[2].setWorld_y(93 * tileSize);

        box.obj[3] = new ObjectDoor(box);
        box.obj[3].setWorld_x(33 * tileSize);
        box.obj[3].setWorld_y(75 * tileSize);
    }
}
