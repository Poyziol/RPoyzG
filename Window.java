package aff;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame
{
    public Window() 
    {
        JPanel game_panel = new Box();
        this.add(game_panel);

        this.pack();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("RPoyzG Game");
        this.setVisible(true);
    }
}
