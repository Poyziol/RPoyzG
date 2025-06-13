package obj;

import javax.imageio.ImageIO;

public class ObjectKey extends SuperObject
{
    public ObjectKey()
    {
        name = "key";
        try 
        {
            image = ImageIO.read(getClass().getResourceAsStream("/items/key1.png"));
        } 
        catch(Exception e) 
        {
            e.printStackTrace();
        }
    }
}
