package aff;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import obj.ObjectKey;

public class UI 
{
    Box game_panel;
    Font arial_25, arial_50B;
    BufferedImage key_image;
    boolean message_is_active = false;
    String message = "";
    int nbr_message = 0;
    boolean game_is_finished = false;
    double play_time;
    DecimalFormat df = new DecimalFormat("#0.0");

    public UI(Box game_panel)
    {
        this.game_panel = game_panel;

        arial_25 = new Font("Arial", Font.PLAIN, 25);
        arial_50B = new Font("Arial", Font.BOLD, 50);
        ObjectKey key = new ObjectKey(game_panel);
        key_image = key.getImage();
    }

    public void show_message(String text)
    {
        message = text;
        message_is_active = true;
    }

    public void draw(Graphics2D g2)
    {
        if(game_is_finished == true)
        {
            String text;
            int text_length;
            int x;
            int y;

            text = "Your time is: " + df.format(play_time) + "!";
            text_length = (int) g2.getFontMetrics().getStringBounds(text,g2).getWidth();

            x = game_panel.get_screen_width()/2 - text_length/2;
            y = game_panel.get_screen_height()/2 - game_panel.get_tile_size()*2;

            g2.drawString(text, x, y);
            g2.setFont(arial_25);
            g2.setColor(Color.white);

            text = "Congratulation! You finish the game!";
            text_length = (int) g2.getFontMetrics().getStringBounds(text,g2).getWidth();

            x = game_panel.get_screen_width()/2 - text_length/2;
            y = game_panel.get_screen_height()/2 - game_panel.get_tile_size()*3;

            g2.drawString(text, x, y);
            g2.setFont(arial_50B);
            g2.setColor(Color.yellow);

            game_panel.game_thread = null;
        }

        g2.setFont(arial_25);
        g2.setColor(Color.white);
        g2.drawImage(key_image, game_panel.get_tile_size()/2, game_panel.get_tile_size()/2, game_panel.get_tile_size(), game_panel.get_tile_size(), null);
        g2.drawString(" x " + game_panel.get_player().get_nbr_key(), 74, 55);

        // TIME
        play_time += (double)1/60;
        g2.drawString("Time: " + df.format(play_time), game_panel.get_tile_size()*13, 55);

        if(message_is_active == true)
        {
            g2.setFont(arial_25);
            g2.drawString(message, 25, game_panel.get_tile_size()*12 - 25);;

            nbr_message++;

            if(nbr_message > 120)
            {
                nbr_message = 0;
                message_is_active = false;
            }
        }
    }
}
