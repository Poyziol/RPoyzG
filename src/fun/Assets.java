package fun;

import aff.Box;
import obj.ObjectBoot;
import obj.ObjectChest;
import obj.ObjectDoor;
import obj.ObjectKey;

public class Assets 
{
    Box box;

    public Assets(Box box1) 
    {
        this.box = box1;
    }

    public void set_object() 
    {
        int tileSize = box.get_tile_size();

        box.obj[0] = new ObjectKey(box);
        box.obj[0].setWorld_x(69 * tileSize);
        box.obj[0].setWorld_y(96 * tileSize);

        box.obj[1] = new ObjectKey(box);
        box.obj[1].setWorld_x(62 * tileSize);
        box.obj[1].setWorld_y(11 * tileSize);

        box.obj[2] = new ObjectKey(box);
        box.obj[2].setWorld_x(23 * tileSize);
        box.obj[2].setWorld_y(17 * tileSize);

        box.obj[3] = new ObjectDoor(box);
        box.obj[3].setWorld_x(33 * tileSize);
        box.obj[3].setWorld_y(75 * tileSize);

        box.obj[4] = new ObjectDoor(box);
        box.obj[4].setWorld_x(15 * tileSize);
        box.obj[4].setWorld_y(80 * tileSize);

        box.obj[5] = new ObjectDoor(box);
        box.obj[5].setWorld_x(36 * tileSize);
        box.obj[5].setWorld_y(93 * tileSize);

        box.obj[6] = new ObjectBoot(box);
        box.obj[6].setWorld_x(69 * tileSize);
        box.obj[6].setWorld_y(95 * tileSize);

        box.obj[7] = new ObjectChest(box);
        box.obj[7].setWorld_x(1 * tileSize);
        box.obj[7].setWorld_y(1 * tileSize);
    }
}
