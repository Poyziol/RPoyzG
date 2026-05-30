package aff;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import obj.ObjectLife;
import ent.Entity;

public class UI 
{
    Box game_panel;
    Font arial_25, arial_50B, arial_12, arial_11B, arial_20;
    Graphics2D g2;
    BufferedImage full_life, half_life, no_life;
    boolean message_is_active = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> nbr_message = new ArrayList<>();
    boolean game_is_finished = false;
    double play_time;
    DecimalFormat df = new DecimalFormat("#0.0");
    String dialogue = "";
    int command_num = 0;
    int slot_col = 0;
    int slot_row = 0;

    public UI(Box game_panel)
    {
        this.game_panel = game_panel;

        arial_12 = new Font("Terminal", Font.PLAIN, 12);
        arial_25 = new Font("Terminal", Font.PLAIN, 25);
        arial_50B = new Font("Terminal", Font.BOLD, 50);
        arial_11B = new Font("Terminal", Font.BOLD, 11);
        arial_20 = new Font("Terminal", Font.PLAIN, 20);

        Entity life = new ObjectLife(game_panel);
        full_life = life.image3;
        half_life = life.image2;
        no_life = life.image;

    }

    public int get_slot_col()
    {
        return this.slot_col;
    }

    public int get_slot_row()
    {
        return this.slot_row;
    }

    public void set_slot_col(int new_slot_col)
    {
        this.slot_col = new_slot_col;
    }

    public void set_slot_row(int new_slot_row)
    {
        this.slot_row = new_slot_row;
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
        message.add(text);
        nbr_message.add(0);
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
            draw_exp_bar();
            draw_dialogue_screen();
        }

        // Play state
        if(game_panel.get_game_state() == game_panel.get_play_state())
        {
            draw_player_life();
            draw_exp_bar();
            draw_message();
        }

        // Quest history state
        if(game_panel.get_game_state() == game_panel.get_quest_history_state())
        {
            draw_quest_history_screen();
        }

        // Pause state
        if(game_panel.get_game_state() == game_panel.get_pause_state())
        {
            draw_player_life();
            draw_pause_screen();
        }

        // Status state
        if(game_panel.get_game_state() == game_panel.get_status_state())
        {
            draw_charactere_status();
            draw_inventory_screen();
        }

        // Game over state
        if(game_panel.get_game_state() == game_panel.get_game_over_state())
        {
            draw_game_over_screen();
        }
    }

    public void draw_charactere_status()
    {
        // create frame
        final int frame_x = game_panel.get_tile_size();
        final int frame_y = game_panel.get_tile_size() / 2;
        final int frame_width = game_panel.get_tile_size() * 5;
        final int frame_height = game_panel.get_tile_size() * 11;

        draw_dialogue_window(frame_x, frame_y, frame_width, frame_height);

        // text
        g2.setColor(Color.white);
        g2.setFont(arial_20);
        int text_x = frame_x + 20;
        int text_y = frame_y + game_panel.get_tile_size();
        final int line_height = 35;
        int tail_x = (frame_x + frame_width) - 30;
        String value;

        // names
        g2.drawString("Level", text_x, text_y);
        text_y += line_height;
        g2.drawLine(text_x, text_y, tail_x + 5, text_y);
        text_y += line_height;
        g2.drawString("Life", text_x, text_y);
        text_y += line_height;
        g2.drawString("Strength", text_x, text_y);
        text_y += line_height;
        g2.drawString("Dexterity", text_x, text_y);
        text_y += line_height;
        g2.drawString("Speed", text_x, text_y);
        text_y += line_height;
        g2.drawString("Attack", text_x, text_y);
        text_y += line_height;
        g2.drawString("Defense", text_x, text_y);
        text_y += line_height;
        g2.drawString("Exp", text_x, text_y);
        text_y += line_height;
        g2.drawString("Next Level", text_x, text_y);
        text_y += line_height;
        g2.drawString("Coin", text_x, text_y);
        text_y += line_height + 10;
        g2.drawString("Weapon", text_x, text_y);
        text_y += line_height + 10;
        g2.drawString("shield", text_x, text_y);

        // values
        text_y = frame_y + game_panel.get_tile_size();

        value = String.valueOf(game_panel.get_player().getLevel());
        text_x = get_x_right(value, tail_x);
        g2.drawString(value, text_x, text_y);

        text_y += line_height;
        text_y += line_height;

        value = String.valueOf(game_panel.get_player().get_life() + "/" + game_panel.get_player().get_max_life());
        text_x = get_x_right(value, tail_x);
        g2.drawString(value, text_x, text_y);

        text_y += line_height;

        value = String.valueOf(game_panel.get_player().getStrength());
        text_x = get_x_right(value, tail_x);
        g2.drawString(value, text_x, text_y);

        text_y += line_height;

        value = String.valueOf(game_panel.get_player().getDexterity());
        text_x = get_x_right(value, tail_x);
        g2.drawString(value, text_x, text_y);

        text_y += line_height;

        value = String.valueOf(game_panel.get_player().getEntitySpeed());
        text_x = get_x_right(value, tail_x);
        g2.drawString(value, text_x, text_y);

        text_y += line_height;

        value = String.valueOf(game_panel.get_player().getAttack());
        text_x = get_x_right(value, tail_x);
        g2.drawString(value, text_x, text_y);

        text_y += line_height;

        value = String.valueOf(game_panel.get_player().getDefense());
        text_x = get_x_right(value, tail_x);
        g2.drawString(value, text_x, text_y);

        text_y += line_height;

        value = String.valueOf(game_panel.get_player().getExp());
        text_x = get_x_right(value, tail_x);
        g2.drawString(value, text_x, text_y);

        text_y += line_height;

        value = String.valueOf(game_panel.get_player().getNext_lvl_exp());
        text_x = get_x_right(value, tail_x);
        g2.drawString(value, text_x, text_y);

        text_y += line_height;

        value = String.valueOf(game_panel.get_player().getCoin());
        text_x = get_x_right(value, tail_x);
        g2.drawString(value, text_x, text_y);

        text_y += line_height;

        g2.drawImage(game_panel.get_player().getCurrent_weapon().getDown1(), tail_x - game_panel.get_tile_size() + 10, text_y - 30, null);

        text_y += line_height;

        g2.drawImage(game_panel.get_player().getCurrent_shield().getDown1(), tail_x - game_panel.get_tile_size() + 10, text_y - 15, null);
    }

    public void draw_message()
    {
        int message_x = game_panel.get_tile_size();
        int message_y = (game_panel.get_tile_size() * 11) - 25;
        g2.setFont(arial_12);

        for(int ni = 0;ni < message.size();ni++)
        {
            if(message.get(ni) != null)
            {
                g2.setColor(Color.black);
                g2.drawString(message.get(ni), message_x + 2, message_y + 2);
                g2.setColor(Color.white);
                g2.drawString(message.get(ni), message_x, message_y);

                int compteur = nbr_message.get(ni) + 1;
                nbr_message.set(ni, compteur);
                message_y += 13;

                if(nbr_message.get(ni) > 100)
                {
                    message.remove(ni);
                    nbr_message.remove(ni);
                }
            }
        }
    }

    public void draw_player_life()
    {
        int x = game_panel.get_tile_size() / 2;
        int y = game_panel.get_tile_size() / 2;
        int ni = 0;

        
        while(ni < game_panel.get_player().get_max_life() / 2)
        {
            g2.drawImage(no_life, x, y, game_panel.get_tile_size() / 2, game_panel.get_tile_size() / 2,null);
            ni++;
            x += game_panel.get_tile_size() / 2;
        }

        x = game_panel.get_tile_size() / 2;
        y = game_panel.get_tile_size() / 2;
        ni = 0;

        while(ni < game_panel.get_player().get_life())
        {
            g2.drawImage(half_life, x, y, game_panel.get_tile_size() / 2, game_panel.get_tile_size() / 2,null);
            ni++;
            if(ni < game_panel.get_player().get_life())
            {
                g2.drawImage(full_life, x, y, game_panel.get_tile_size() / 2, game_panel.get_tile_size() / 2, null);
            }
            ni++;
            x += game_panel.get_tile_size() / 2;
        }

        draw_quest_status();
    }

    public void draw_quest_status()
    {
        int steps = game_panel.get_quest_step_count();
        if(steps > 3)
        {
            steps = 3;
        }

        int active = game_panel.get_active_quest_index();

        int title_x = game_panel.get_tile_size() / 2;
        int title_y = game_panel.get_tile_size() / 2 + (game_panel.get_tile_size() / 2) + 16;

        g2.setFont(arial_12);
        g2.setColor(Color.white);
        g2.drawString("Objectifs actuels", title_x, title_y);

        int size = game_panel.get_tile_size() / 4;
        int spacing = size + 6;
        int x = title_x;
        int y = title_y + 6;

        for(int ni = 0; ni < steps; ni++)
        {
            Color color = new Color(60, 60, 60);

            if(game_panel.is_quest_done(ni))
            {
                color = new Color(50, 200, 60);
            }
            else if(ni == active)
            {
                color = new Color(230, 210, 40);
            }

            int diamond_x = x;
            int diamond_y = y + (ni * spacing);
            int half = size / 2;
            int[] px = { diamond_x + half, diamond_x + size, diamond_x + half, diamond_x };
            int[] py = { diamond_y, diamond_y + half, diamond_y + size, diamond_y + half };

            g2.setColor(color);
            g2.fillPolygon(px, py, 4);

            g2.setColor(Color.white);
            g2.drawString(game_panel.get_quest_text(ni), diamond_x + size + 8, diamond_y + size - 1);
        }
    }

    public void draw_quest_history_screen()
    {
        final int frame_x = game_panel.get_tile_size() * 2;
        final int frame_y = game_panel.get_tile_size();
        final int frame_width = game_panel.get_screen_width() - (game_panel.get_tile_size() * 4);
        final int frame_height = game_panel.get_screen_height() - (game_panel.get_tile_size() * 2);

        draw_dialogue_window(frame_x, frame_y, frame_width, frame_height);

        g2.setColor(Color.white);
        g2.setFont(arial_20);
        g2.drawString("Historique des quetes", frame_x + 20, frame_y + game_panel.get_tile_size());

        g2.setFont(arial_12);
        int text_x = frame_x + 20;
        int text_y = frame_y + game_panel.get_tile_size() + 20;
        int line_height = 16;

        ArrayList<String> lines = new ArrayList<>();
        ArrayList<String> history = game_panel.get_quest_history();

        lines.add("Faites:");
        for(int ni = 0; ni < history.size(); ni++)
        {
            lines.add("  " + history.get(ni));
        }

        lines.add("A venir:");
        int current_set = game_panel.get_quest_set_index();
        int set_count = game_panel.get_quest_set_count();
        for(int si = current_set; si < set_count; si++)
        {
            int set_size = game_panel.get_quest_set_size(si);
            for(int qi = 0; qi < set_size; qi++)
            {
                if(si == current_set && game_panel.is_quest_done(qi))
                {
                    continue;
                }
                lines.add("  " + game_panel.get_quest_set_text(si, qi));
            }
        }

        int max_lines = (frame_height - (game_panel.get_tile_size() * 2)) / line_height;
        int start = lines.size() - max_lines;
        if(start < 0)
        {
            start = 0;
        }

        for(int ni = start; ni < lines.size(); ni++)
        {
            g2.drawString(lines.get(ni), text_x, text_y);
            text_y += line_height;
        }

        g2.setFont(arial_11B);
        g2.drawString("A pour fermer", frame_x + 20, frame_y + frame_height - 12);
    }

    public void draw_exp_bar()
    {
        int x = 0;
        int y = (game_panel.get_tile_size() * 12) - 8;
        int width = game_panel.get_screen_width();
        int height = (game_panel.get_tile_size() * 12) - 7;

        double one_scale = (double)(width) / game_panel.get_player().getNext_lvl_exp();
        double exp_value = one_scale * game_panel.get_player().getExp();
        
        g2.setColor(new Color(12,12,12));
        g2.fillRect(x - 1, y - 1, width + 2, height + 2);
        g2.setColor(new Color(50,0,255));
        g2.fillRect(x, y, (int)exp_value, height);

        g2.setColor(Color.white);
        g2.setFont(arial_11B);
        g2.drawString("lvl " + game_panel.get_player().getLevel(), x + 8, y - 2);

        String value = game_panel.get_player().getExp() + "/" + game_panel.get_player().getNext_lvl_exp();
        int x_right = get_x_right(value, width - 10);
        g2.drawString(value, x_right, y - 2);
    }

    public void draw_main_menu_screen()
    {
        try 
        {
            g2.drawImage(ImageIO.read(getClass().getResourceAsStream("/Background/006.jpg")), 0, 0, game_panel.get_screen_width(), game_panel.get_screen_height(), game_panel);
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
        g2.setColor(Color.black);
        g2.drawString(text, x + 2, y + 2);

        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        g2.setColor(Color.BLACK);
        if(command_num == 0)
        {
            g2.drawString(">", x - game_panel.get_tile_size() + 12, y + 2);
        }
        g2.setColor(Color.white);
        if(command_num == 0)
        {
            g2.drawString(">", x - game_panel.get_tile_size() + 10, y);
        }

        text = "Load Game";
        y += game_panel.get_tile_size();
        g2.setColor(Color.black);
        g2.drawString(text, x + 2, y + 2);

        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        g2.setColor(Color.BLACK);
        if(command_num == 1)
        {
            g2.drawString(">", x - game_panel.get_tile_size() + 12, y + 2);
        }
        g2.setColor(Color.white);
        if(command_num == 1)
        {
            g2.drawString(">", x - game_panel.get_tile_size() + 10, y);
        }

        text = "Quit";
        y += game_panel.get_tile_size();
        g2.setColor(Color.black);
        g2.drawString(text, x + 2, y + 2);

        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        g2.setColor(Color.BLACK);
        if(command_num == 2)
        {
            g2.drawString(">", x - game_panel.get_tile_size() + 12, y + 2);
        }
        g2.setColor(Color.white);
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

    public void draw_inventory_screen()
    {
        // create inventory frame
        final int frame_x = game_panel.get_tile_size() * 7;
        final int frame_y = game_panel.get_tile_size() / 2;
        final int frame_width = game_panel.get_tile_size() * 8;
        final int frame_height = game_panel.get_tile_size() * 5;

        draw_dialogue_window(frame_x, frame_y, frame_width, frame_height);

        // create slot
        final int slot_x_start = frame_x + 20;
        final int slot_y_start = frame_y + 20;
        int slot_x = slot_x_start;
        int slot_y = slot_y_start;
        int slot_size = game_panel.get_tile_size() + 1;

        // items
        for(int ni = 0;ni < game_panel.get_player().get_inventory().size(); ni++)
        {
            // Equip cursor
            if(game_panel.get_player().get_inventory().get(ni) == game_panel.get_player().getCurrent_weapon()
            || game_panel.get_player().get_inventory().get(ni) == game_panel.get_player().getCurrent_shield())
            {
                g2.setColor(new Color(240, 190,60));
                g2.fillRoundRect(slot_x, slot_y, game_panel.get_tile_size(), game_panel.get_tile_size(), 10, 10);
            }
            
            g2.drawImage(game_panel.get_player().get_inventory().get(ni).getDown1(), slot_x, slot_y, null);
            slot_x += slot_size;
            if(ni == 6 || ni == 13 || ni == 20)
            {
                slot_x = slot_x_start;
                slot_y += slot_size;
            }
        }

        // cursor
        int cursor_x = slot_x_start + (slot_size * slot_col);
        int cursor_y = slot_y_start + (slot_size * slot_row);
        int cursor_width = game_panel.get_tile_size();
        int cursor_height = game_panel.get_tile_size();

        g2.setColor(Color.yellow);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursor_x, cursor_y, cursor_width, cursor_height, 10, 10);

        // create description frame 
        final int d_frame_x = frame_x;
        final int d_frame_y = frame_y + frame_height + 10;
        final int d_frame_width = frame_width;
        final int d_frame_height = game_panel.get_tile_size() * 4;
        int item_index = get_item_index();

        draw_dialogue_window(d_frame_x, d_frame_y, d_frame_width, d_frame_height);

        // description text
        int text_x = d_frame_x + 20;
        int text_y = d_frame_y + game_panel.get_tile_size();
        g2.setFont(arial_20);

        try 
        {
            if(item_index < game_panel.get_player().get_inventory_size())
            {
                for(String line : game_panel.get_player().get_inventory().get(item_index).get_description().split("\n"))
                {
                    g2.drawString(line, text_x, text_y);
                    text_y += 32;
                }
            }
        } catch (Exception e) 
        {
            e.getMessage();
        }
    }

    public int get_item_index()
    {
        int item_index = slot_col + (slot_row * 7);
        return item_index;
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

    public void draw_game_over_screen()
    {
        g2.setColor(new Color(0 , 0, 0, 150));
        g2.fillRect(0, 0, game_panel.get_screen_width(), game_panel.get_screen_height());

        int x;
        int y;
        String text;
        g2.setFont(arial_50B);

        text = "Game Over";

        // Shadow
        g2.setColor(Color.black);
        x = get_x_centered(text);
        y = game_panel.get_tile_size() * 4;
        g2.drawString(text, x + 2, y + 2);

        // Main
        g2.setColor(Color.red);
        g2.drawString(text, x, y);

        // Retry
        g2.setFont(arial_20);
        g2.setColor(Color.white);
        text = "Respawn";
        x = get_x_centered(text);
        y = game_panel.get_tile_size() * 6;
        g2.drawString(text, x, y);
        if(command_num == 0)
        {
            g2.drawString(">", x - game_panel.get_tile_size() + 12, y + 2);
        }

        // Back to title screen
        text = "Quit";
        x = get_x_centered(text);
        y = game_panel.get_tile_size() * 7;
        g2.drawString(text, x, y);
        if(command_num == 1)
        {
            g2.drawString(">", x - game_panel.get_tile_size() + 12, y + 2);
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

    public int get_x_right(String text, int tail_x)
    {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tail_x - length;
        return x;
    }
}
