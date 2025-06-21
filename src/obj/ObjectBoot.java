package obj;

import javax.imageio.ImageIO;

import aff.Box;

public class ObjectBoot extends SuperObject
{
    Box game_panel;

    public ObjectBoot(Box game_panel)
    {
        name = "boot";
        try 
        {
            image = ImageIO.read(getClass().getResourceAsStream("/items/boot1.png"));
            utility.scale_image(image, game_panel.get_tile_size(), game_panel.get_tile_size());
        } 
        catch(Exception e) 
        {
            e.printStackTrace();
        }
    }
}
