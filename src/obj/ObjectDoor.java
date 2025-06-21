package obj;

import javax.imageio.ImageIO;

import aff.Box;

public class ObjectDoor extends SuperObject
{
    Box game_panel;

    public ObjectDoor(Box game_panel)
    {
        name = "door";
        try 
        {
            image = ImageIO.read(getClass().getResourceAsStream("/items/door1.png"));
            utility.scale_image(image, game_panel.get_tile_size(), game_panel.get_tile_size());
        } 
        catch(Exception e) 
        {
            e.printStackTrace();
        }

        collision = true;
    }
}
