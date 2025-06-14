package obj;

import javax.imageio.ImageIO;

public class ObjectBoot extends SuperObject
{
    public ObjectBoot()
    {
        name = "boot";
        try 
        {
            image = ImageIO.read(getClass().getResourceAsStream("/items/boot1.png"));
        } 
        catch(Exception e) 
        {
            e.printStackTrace();
        }
    }
}
