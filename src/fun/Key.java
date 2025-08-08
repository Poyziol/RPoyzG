package fun;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import aff.Box;

public class Key implements KeyListener
{
    Box game_panel;

    boolean up_pressed,down_pressed,left_pressed,right_pressed,enter_pressed;

    // DEBUG
    boolean check_draw_time = false;

    public Key(Box game_panel) 
    {
        this.game_panel = game_panel;
    }

    public Key() {}

    public boolean get_check_draw_time()
    {
        return this.check_draw_time;
    }

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

    public boolean get_enter_pressed()
    {
        return this.enter_pressed;
    }

    public void set_enter_pressed(boolean new_enter_pressed)
    {
        this.enter_pressed = new_enter_pressed;
    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
        int code = e.getKeyCode();

        // Main menu
        if(game_panel.get_game_state() == game_panel.get_menu_state())
        {
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_Z || code == KeyEvent.VK_UP)
            {
                game_panel.get_ui().set_command_num(game_panel.get_ui().get_command_num() - 1);
                if(game_panel.get_ui().get_command_num() < 0)
                {
                    game_panel.get_ui().set_command_num(2);
                }
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN)
            {
                game_panel.get_ui().set_command_num(game_panel.get_ui().get_command_num() + 1);
                if(game_panel.get_ui().get_command_num() > 2)
                {
                    game_panel.get_ui().set_command_num(0);
                }
            }

            if(code == KeyEvent.VK_ENTER)
            {
                if(game_panel.get_ui().get_command_num() == 0)
                {
                    game_panel.set_game_state(game_panel.get_play_state());
                    game_panel.play_music(3);
                }
                if(game_panel.get_ui().get_command_num() == 1)
                {
                    
                }
                if(game_panel.get_ui().get_command_num() == 2)
                {
                    System.exit(0);
                }
            }
        }

        // Play state
        else if(game_panel.get_game_state() == game_panel.get_play_state())
        {
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
            if(code == KeyEvent.VK_H)
            {
                if(check_draw_time == false)
                {
                    check_draw_time = true;
                }
                if(check_draw_time == true)
                {
                    check_draw_time = false;
                }
            }
            if(code == KeyEvent.VK_P)
            {
                game_panel.set_game_state(game_panel.get_pause_state());
            }
            if(code == KeyEvent.VK_ENTER)
            {
                enter_pressed = true;
            }
        }

        // Play state
        if(game_panel.get_game_state() == game_panel.get_pause_state())
        {
            if(code == KeyEvent.VK_P)
            {
                game_panel.set_game_state(game_panel.get_play_state());
            }
        }

        // Dialogue state
        if(game_panel.get_game_state() == game_panel.get_dialogue_state())
        {
            if(code == KeyEvent.VK_ENTER)
            {
                game_panel.set_game_state(game_panel.get_play_state());
            }
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
