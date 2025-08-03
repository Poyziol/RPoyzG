package obj;

import javax.imageio.ImageIO;

import aff.Box;

public class ObjectLife extends SuperObject 
{
    Box game_panel;

    public ObjectLife(Box game_panel)
    {
        name = "life";
        try 
        {
            image = ImageIO.read(getClass().getResourceAsStream("/items/life0.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/items/life1-red.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/items/life2-red.png"));
            image = utility.scale_image(image, game_panel.get_tile_size(), game_panel.get_tile_size());
            image2 = utility.scale_image(image2, game_panel.get_tile_size(), game_panel.get_tile_size());
            image3 = utility.scale_image(image3, game_panel.get_tile_size(), game_panel.get_tile_size());
        } 
        catch(Exception e) 
        {
            e.printStackTrace();
        }
    }
}
