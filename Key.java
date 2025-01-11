package fun;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Key implements KeyListener
{
    boolean up_pressed,down_pressed,left_pressed,right_pressed;

    public boolean get_up_pressed()
    {
        return this.up_pressed;
    }

    public boolean get_down_pressed()
    {
        return this.down_pressed;
    }

    public boolean get_left_pressed()
    {
        return this.left_pressed;
    }

    public boolean get_right_pressed()
    {
        return this.right_pressed;
    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W)
        {
            up_pressed = true;
        }
        if(code == KeyEvent.VK_Z)
        {
            up_pressed = true;
        }
        if(code == KeyEvent.VK_S)
        {
            down_pressed = true;
        }
        if(code == KeyEvent.VK_Q)
        {
            left_pressed = true;
        }
        if(code == KeyEvent.VK_D)
        {
            right_pressed = true;
        }
        if(code == KeyEvent.VK_UP)
        {
            up_pressed = true;
        }
        if(code == KeyEvent.VK_DOWN)
        {
            down_pressed = true;
        }
        if(code == KeyEvent.VK_LEFT)
        {
            left_pressed = true;
        }
        if(code == KeyEvent.VK_RIGHT)
        {
            right_pressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W)
        {
            up_pressed = false;
        }
        if(code == KeyEvent.VK_Z)
        {
            up_pressed = false;
        }
        if(code == KeyEvent.VK_S)
        {
            down_pressed = false;
        }
        if(code == KeyEvent.VK_Q)
        {
            left_pressed = false;
        }
        if(code == KeyEvent.VK_D)
        {
            right_pressed = false;
        }
        if(code == KeyEvent.VK_UP)
        {
            up_pressed = false;
        }
        if(code == KeyEvent.VK_DOWN)
        {
            down_pressed = false;
        }
        if(code == KeyEvent.VK_LEFT)
        {
            left_pressed = false;
        }
        if(code == KeyEvent.VK_RIGHT)
        {
            right_pressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) { }
    
}
