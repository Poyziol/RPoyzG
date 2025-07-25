package obj;

import javax.imageio.ImageIO;

import aff.Box;

public class ObjectKey extends SuperObject
{
    Box game_panel;

    public ObjectKey(Box game_panel)
    {
        name = "key";
        try 
        {
            image = ImageIO.read(getClass().getResourceAsStream("/items/key1.png"));
            utility.scale_image(image, game_panel.get_tile_size(), game_panel.get_tile_size());
        } 
        catch(Exception e) 
        {
            e.printStackTrace();
        }
    }
}
