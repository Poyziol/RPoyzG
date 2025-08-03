package aff;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

import obj.ObjectLife;
import obj.SuperObject;

public class UI 
{
    Box game_panel;
    Font arial_25, arial_50B, arial_12;
    Graphics2D g2;
    BufferedImage full_life, half_life, no_life;
    boolean message_is_active = false;
    String message = "";
    int nbr_message = 0;
    boolean game_is_finished = false;
    double play_time;
    DecimalFormat df = new DecimalFormat("#0.0");
    String dialogue = "";
    int command_num = 0;

    public UI(Box game_panel)
    {
        this.game_panel = game_panel;

        arial_12 = new Font("Terminal", Font.PLAIN, 12);
        arial_25 = new Font("Terminal", Font.PLAIN, 25);
        arial_50B = new Font("Terminal", Font.BOLD, 50);

        SuperObject life = new ObjectLife(game_panel);
        full_life = life.image3;
        half_life = life.image2;
        no_life = life.image;

    }

    public String get_dialogue()
    {
        return this.dialogue;
    }

    public int get_command_num()
    {
        return this.command_num;
    }

    public void set_command_num(int new_command_num)
    {
        this.command_num = new_command_num;
    }

    public void set_dialogue(String new_dialogue)
    {
        this.dialogue = new_dialogue;
    }

    public void show_message(String text)
    {
        message = text;
        message_is_active = true;
    }

    public void draw(Graphics2D g2)
    {
        this.g2 = g2;
        g2.setFont(arial_50B);
        g2.setColor(Color.WHITE);

        // Main menu state
        if(game_panel.get_game_state() == game_panel.get_menu_state())
        {
            draw_main_menu_screen();
        }

        // Dialogue state
        if(game_panel.get_game_state() == game_panel.get_dialogue_state())
        {
            draw_player_life();
            draw_dialogue_screen();
        }

        // Play state
        if(game_panel.get_game_state() == game_panel.get_play_state())
        {
            draw_player_life();
        }

        // Pause state
        if(game_panel.get_game_state() == game_panel.get_pause_state())
        {
            draw_player_life();
            draw_pause_screen();
        }
    }

    public void draw_player_life()
    {
        int x = game_panel.get_tile_size() / 2;
        int y = game_panel.get_tile_size() / 2;
        int ni = 0;

        while(ni < game_panel.get_player().get_max_life() / 2)
        {
            g2.drawImage(no_life, x, y, null);
            ni++;
            x += game_panel.get_tile_size();
        }

        x = game_panel.get_tile_size() / 2;
        y = game_panel.get_tile_size() / 2;
        ni = 0;

        while(ni < game_panel.get_player().get_life())
        {
            g2.drawImage(half_life, x, y, null);
            ni++;
            if(ni < game_panel.get_player().get_life())
            {
                g2.drawImage(full_life, x, y, null);
            }
            ni++;
            x += game_panel.get_tile_size();
        }
    }

    public void draw_main_menu_screen()
    {
        try 
        {
            g2.drawImage(ImageIO.read(getClass().getResourceAsStream("/Background/Menu.jpg")), 0, 0, game_panel.get_screen_width(), game_panel.get_screen_height(), game_panel);
        } 
        catch(IOException e) 
        {
            e.printStackTrace();
        }

        // Title
        g2.setFont(arial_50B);
        String text = "RPoyzG Adventure";
        int x = get_x_centered(text);
        int y = game_panel.get_tile_size() * 3;

        g2.setColor(Color.black);
        g2.drawString(text, x + 3, y + 3);

        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        // Character
        x = game_panel.get_screen_width()/2 - (game_panel.get_tile_size() * 2) / 2;
        y += game_panel.get_tile_size();
        g2.drawImage(game_panel.get_player().getDown1(), x, y, game_panel.get_tile_size() * 2, game_panel.get_tile_size() * 2, null);

        g2.setFont(arial_25);
        text = "New Game";
        x = get_x_centered(text);
        y += game_panel.get_tile_size() * 5;
        g2.drawString(text, x, y);
        if(command_num == 0)
        {
            g2.drawString(">", x - game_panel.get_tile_size() + 10, y);
        }

        text = "Load Game";
        y += game_panel.get_tile_size();
        g2.drawString(text, x, y);
        if(command_num == 1)
        {
            g2.drawString(">", x - game_panel.get_tile_size() + 10, y);
        }

        text = "Quit";
        y += game_panel.get_tile_size();
        g2.drawString(text, x, y);
        if(command_num == 2)
        {
            g2.drawString(">", x - game_panel.get_tile_size() + 10, y);
        }

        g2.setFont(arial_12);
        text = "V.0.0.1";
        x = game_panel.get_screen_width() - game_panel.get_tile_size();
        y += game_panel.get_tile_size() - 5;
        g2.drawString(text, x, y);
    }

    public void draw_pause_screen()
    {
        String text = "PAUSED";

        int x = get_x_centered(text);
        int y = game_panel.getHeight()/2;

        g2.drawString(text, x, y);
    }

    public void draw_dialogue_screen()
    {
        int x = game_panel.get_tile_size() * 2;
        int y = game_panel.get_tile_size() / 2;
        int width = game_panel.get_screen_width() - (game_panel.get_tile_size() * 4);
        int height = game_panel.get_tile_size() * 3;

        draw_dialogue_window(x, y, width, height);

        x += game_panel.get_tile_size();
        y += game_panel.get_tile_size();

        g2.setFont(arial_25);

        for(String line : dialogue.split("\n"))
        {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void draw_dialogue_window(int x, int y, int width, int height)
    {
        Color c = new Color(0, 0, 0, 175);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255, 175);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(4));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }
    
    public int get_x_centered(String text)
    {
        int x;
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        x = game_panel.getWidth()/2 - length/2;
        return x;
    }
}
