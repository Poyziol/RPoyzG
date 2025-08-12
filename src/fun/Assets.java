package fun;

import aff.Box;
import ent.Npc_old_man;
import mob.GreenSlime;
import obj.ObjectAxe1;
import obj.ObjectDoor;
import obj.ObjectKey;
import obj.ObjectPotion1;
import obj.ObjectShield2;
@SuppressWarnings("unused")
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

        box.obj[0] = new ObjectDoor(box);
        box.obj[0].setWorld_x(23 * tileSize);
        box.obj[0].setWorld_y(24 * tileSize);

        box.obj[1] = new ObjectDoor(box);
        box.obj[1].setWorld_x(14 * tileSize);
        box.obj[1].setWorld_y(75 * tileSize);

        box.obj[2] = new ObjectDoor(box);
        box.obj[2].setWorld_x(33 * tileSize);
        box.obj[2].setWorld_y(75 * tileSize);

        box.obj[4] = new ObjectAxe1(box);
        box.obj[4].setWorld_x(26 * tileSize);
        box.obj[4].setWorld_y(50 * tileSize);

        box.obj[5] = new ObjectPotion1(box);
        box.obj[5].setWorld_x(22 * tileSize);
        box.obj[5].setWorld_y(50 * tileSize);

        box.obj[6] = new ObjectPotion1(box);
        box.obj[6].setWorld_x(30 * tileSize);
        box.obj[6].setWorld_y(50 * tileSize);

        box.obj[7] = new ObjectKey(box);
        box.obj[7].setWorld_x(43 * tileSize);
        box.obj[7].setWorld_y(51 * tileSize);

        box.obj[8] = new ObjectKey(box);
        box.obj[8].setWorld_x(27 * tileSize);
        box.obj[8].setWorld_y(3 * tileSize);

        box.obj[8] = new ObjectShield2(box);
        box.obj[8].setWorld_x(16 * tileSize);
        box.obj[8].setWorld_y(3 * tileSize);
    }

    public void set_npc()
    {
        box.npc[0] = new Npc_old_man(box);
        box.npc[0].setWorld_x(box.get_tile_size()*15);
        box.npc[0].setWorld_y(box.get_tile_size()*24);

    }

    public void set_mob()
    {
        box.mob[0] = new GreenSlime(box);
        box.mob[0].setWorld_x(box.get_tile_size()*25);
        box.mob[0].setWorld_y(box.get_tile_size()*30);

        box.mob[1] = new GreenSlime(box);
        box.mob[1].setWorld_x(box.get_tile_size()*28);
        box.mob[1].setWorld_y(box.get_tile_size()*26);

        box.mob[2] = new GreenSlime(box);
        box.mob[2].setWorld_x(box.get_tile_size()*2);
        box.mob[2].setWorld_y(box.get_tile_size()*44);

        box.mob[3] = new GreenSlime(box);
        box.mob[3].setWorld_x(box.get_tile_size()*2);
        box.mob[3].setWorld_y(box.get_tile_size()*45);

        box.mob[4] = new GreenSlime(box);
        box.mob[4].setWorld_x(box.get_tile_size()*2);
        box.mob[4].setWorld_y(box.get_tile_size()*46);

        box.mob[5] = new GreenSlime(box);
        box.mob[5].setWorld_x(box.get_tile_size()*5);
        box.mob[5].setWorld_y(box.get_tile_size()*40);

        box.mob[6] = new GreenSlime(box);
        box.mob[6].setWorld_x(box.get_tile_size()*6);
        box.mob[6].setWorld_y(box.get_tile_size()*41);

        box.mob[7] = new GreenSlime(box);
        box.mob[7].setWorld_x(box.get_tile_size()*6);
        box.mob[7].setWorld_y(box.get_tile_size()*48);

        box.mob[8] = new GreenSlime(box);
        box.mob[8].setWorld_x(box.get_tile_size()*8);
        box.mob[8].setWorld_y(box.get_tile_size()*51);

        box.mob[9] = new GreenSlime(box);
        box.mob[9].setWorld_x(box.get_tile_size()*3);
        box.mob[9].setWorld_y(box.get_tile_size()*51);

        box.mob[10] = new GreenSlime(box);
        box.mob[10].setWorld_x(box.get_tile_size()*12);
        box.mob[10].setWorld_y(box.get_tile_size()*53);

        box.mob[11] = new GreenSlime(box);
        box.mob[11].setWorld_x(box.get_tile_size()*14);
        box.mob[11].setWorld_y(box.get_tile_size()*4);

        box.mob[12] = new GreenSlime(box);
        box.mob[12].setWorld_x(box.get_tile_size()*15);
        box.mob[12].setWorld_y(box.get_tile_size()*4);

        box.mob[13] = new GreenSlime(box);
        box.mob[13].setWorld_x(box.get_tile_size()*16);
        box.mob[13].setWorld_y(box.get_tile_size()*4);

        box.mob[14] = new GreenSlime(box);
        box.mob[14].setWorld_x(box.get_tile_size()*45);
        box.mob[14].setWorld_y(box.get_tile_size()*6);

        box.mob[15] = new GreenSlime(box);
        box.mob[15].setWorld_x(box.get_tile_size()*22);
        box.mob[15].setWorld_y(box.get_tile_size()*32);

        box.mob[16] = new GreenSlime(box);
        box.mob[16].setWorld_x(box.get_tile_size()*22);
        box.mob[16].setWorld_y(box.get_tile_size()*31);

        box.mob[17] = new GreenSlime(box);
        box.mob[17].setWorld_x(box.get_tile_size()*23);
        box.mob[17].setWorld_y(box.get_tile_size()*32);
    }
}
