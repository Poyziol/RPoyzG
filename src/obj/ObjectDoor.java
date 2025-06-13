package obj;

import javax.imageio.ImageIO;

public class ObjectDoor extends SuperObject
{
    public ObjectDoor()
    {
        name = "door";
        try 
        {
            image = ImageIO.read(getClass().getResourceAsStream("/items/door1.png"));
        } 
        catch(Exception e) 
        {
            e.printStackTrace();
        }
    }
}
