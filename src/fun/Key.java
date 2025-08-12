package fun;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import aff.Box;

public class Key implements KeyListener
{
    Box game_panel;

    boolean up_pressed,down_pressed,left_pressed,right_pressed,enter_pressed;

    // DEBUG
    boolean debug_time = false;

    public Key(Box game_panel) 
    {
        this.game_panel = game_panel;
    }

    public Key() {}

    public boolean get_debug_time()
    {
        return this.debug_time;
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

        // Menu State
        if(game_panel.get_game_state() == game_panel.get_menu_state())
        {
            menu_state(code);
        }

        // Play state
        else if(game_panel.get_game_state() == game_panel.get_play_state())
        {
            play_state(code);
        }

        // Pause state
        else if(game_panel.get_game_state() == game_panel.get_pause_state())
        {
            pause_state(code);
        }

        // Dialogue state
        else if(game_panel.get_game_state() == game_panel.get_dialogue_state())
        {
            dialogue_state(code);
        }

        // Status state
        else if(game_panel.get_game_state() == game_panel.get_status_state())
        {
            status_state(code);
        } 

        // Game over state
        else if(game_panel.get_game_state() == game_panel.get_game_over_state())
        {
            game_over_state(code);
        }
    }   

    public void menu_state(int code)
    {
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_Z || code == KeyEvent.VK_UP)
        {
            game_panel.get_ui().set_command_num(game_panel.get_ui().get_command_num() - 1);
            game_panel.play_sound_effect(8);
            if(game_panel.get_ui().get_command_num() < 0)
            {
                game_panel.get_ui().set_command_num(2);
            }
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN)
        {
            game_panel.get_ui().set_command_num(game_panel.get_ui().get_command_num() + 1);
            game_panel.play_sound_effect(8);
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

    public void play_state(int code)
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
        if(code == KeyEvent.VK_F1)
        {
            if(debug_time == false)
            {
                debug_time = true;
            }
            else if(debug_time == true)
            {
                debug_time = false;
            }
        }
        if(code == KeyEvent.VK_F5)
        {

                game_panel.get_world_template().load_map("/Map/Map.txt");
        }
        if(code == KeyEvent.VK_E)
        {
            game_panel.set_game_state(game_panel.get_status_state());
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

    public void pause_state(int code)
    {
        if(code == KeyEvent.VK_P)
        {
            game_panel.set_game_state(game_panel.get_play_state());
        }
    }

    public void game_over_state(int code)
    {
        game_panel.stop_music();

        if(code == KeyEvent.VK_W || code == KeyEvent.VK_Z || code == KeyEvent.VK_UP)
        {
            game_panel.get_ui().set_command_num(game_panel.get_ui().get_command_num() - 1);
            game_panel.play_sound_effect(8);
            if(game_panel.get_ui().get_command_num() < 0)
            {
                game_panel.get_ui().set_command_num(1);
            }
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN)
        {
            game_panel.get_ui().set_command_num(game_panel.get_ui().get_command_num() + 1);
            game_panel.play_sound_effect(8);
            if(game_panel.get_ui().get_command_num() > 1)
            {
                game_panel.get_ui().set_command_num(0);
            }
        }
        if(code == KeyEvent.VK_ENTER)
        {
            if(game_panel.get_ui().get_command_num() == 0)
            {
                game_panel.play_music(3);
                game_panel.set_game_state(game_panel.get_play_state());
                game_panel.retry();

            }
            if(game_panel.get_ui().get_command_num() == 1)
            {
                game_panel.set_game_state(game_panel.get_menu_state());
                game_panel.restart();
            }
        }
    }

    public void dialogue_state(int code)
    {
        if(code == KeyEvent.VK_ENTER)
        {
            game_panel.set_game_state(game_panel.get_play_state());
        }
    }

    public void status_state(int code)
    {
        if(code == KeyEvent.VK_E)
        {
            game_panel.set_game_state(game_panel.get_play_state());
        }
        if(code == KeyEvent.VK_W)
        {
            if(game_panel.get_ui().get_slot_row() != 0)
            {
                game_panel.get_ui().set_slot_row(game_panel.get_ui().get_slot_row() - 1);
                game_panel.play_sound_effect(8);
            }
           
        }
        if(code == KeyEvent.VK_Z)
        {
            if(game_panel.get_ui().get_slot_row() != 0)
            {
                game_panel.get_ui().set_slot_row(game_panel.get_ui().get_slot_row() - 1);
                game_panel.play_sound_effect(8);
            }
        }
        if(code == KeyEvent.VK_S)
        {
            if(game_panel.get_ui().get_slot_row() < 3)
            {
                game_panel.get_ui().set_slot_row(game_panel.get_ui().get_slot_row() + 1);
                game_panel.play_sound_effect(8);
            }
        }
        if(code == KeyEvent.VK_Q)
        {
            if(game_panel.get_ui().get_slot_col() != 0)
            {
                game_panel.get_ui().set_slot_col(game_panel.get_ui().get_slot_col() - 1);
                game_panel.play_sound_effect(8);
            }
        }
        if(code == KeyEvent.VK_D)
        {
            if(game_panel.get_ui().get_slot_col() < 6)
            {
                game_panel.get_ui().set_slot_col(game_panel.get_ui().get_slot_col() + 1);
                game_panel.play_sound_effect(8);
            }
        }
        if(code == KeyEvent.VK_UP)
        {
            if(game_panel.get_ui().get_slot_row() != 0)
            {
                game_panel.get_ui().set_slot_row(game_panel.get_ui().get_slot_row() - 1);
                game_panel.play_sound_effect(8);
            }
        }
        if(code == KeyEvent.VK_DOWN)
        {
            if(game_panel.get_ui().get_slot_row() < 3)
            {
                game_panel.get_ui().set_slot_row(game_panel.get_ui().get_slot_row() + 1);
                game_panel.play_sound_effect(8);
            }
        }
        if(code == KeyEvent.VK_LEFT)
        {
            if(game_panel.get_ui().get_slot_col() != 0)
            {
                game_panel.get_ui().set_slot_col(game_panel.get_ui().get_slot_col() - 1);
                game_panel.play_sound_effect(8);
            }
        }
        if(code == KeyEvent.VK_RIGHT)
        {
            if(game_panel.get_ui().get_slot_col() < 6)
            {
                game_panel.get_ui().set_slot_col(game_panel.get_ui().get_slot_col() + 1);
                game_panel.play_sound_effect(8);
            }
        }
        if(code == KeyEvent.VK_ENTER)
        {
            game_panel.get_player().select_item();
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
