package obj;

import javax.imageio.ImageIO;

import aff.Box;

public class ObjectChest extends SuperObject
{
    Box game_panel;

    public ObjectChest(Box game_panel)
    {
        name = "chest";
        try 
        {
            image = ImageIO.read(getClass().getResourceAsStream("/items/chest1.png"));
            utility.scale_image(image, game_panel.get_tile_size(), game_panel.get_tile_size());
        } 
        catch(Exception e) 
        {
            e.printStackTrace();
        }

        collision = true;
    }
}
