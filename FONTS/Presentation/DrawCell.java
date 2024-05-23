package Presentation;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/**
 * This class represents a cell on the Kenken
 *
 * @author Romeu Esteve
 */
public class DrawCell extends JPanel {
    private int x;      // X position in game.
    private int y;      // Y position in game.
    private JLabel mainLabel;
    private JLabel smallLabel;

    /**
     * Constructs the label and sets x and y positions in game
     *
     * @param x     X position in game
     * @param y     Y position in game
     */
    public DrawCell(int x, int y) {
        //super("", CENTER);
        this.x = x;
        this.y = y;

        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setLayout(new GridBagLayout());
        setOpaque(true);
        setBackground(Color.WHITE);

        mainLabel = new JLabel("", JLabel.CENTER);
        mainLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
        mainLabel.setOpaque(false);

        smallLabel = new JLabel("");
        smallLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 8));
        smallLabel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(smallLabel, gbc);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(2, 2, 0, 0);
        add(mainLabel, gbc);

        //setBorder(new CustomBorder(Color.GRAY, 1, Color.GRAY, 1, Color.GRAY, 1, Color.GRAY, 1));

        // Add mouse listener
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(Color.WHITE);
            }
        });
    }

    /**
     * Sets number and foreground color according to userInput
     *
     * @param number        Number to be set
     * @param userInput     Boolean indicating number is user input or not
     */
    public void setNumber(int number, boolean userInput) {
        mainLabel.setForeground(userInput ? Color.BLUE : Color.BLACK);
        mainLabel.setText(number > 0 ? number + "" : "");
    }

    public void setOp(String op, int res) {
        smallLabel.setText(op + res);
    }

    /**
     * Returns x position in game
     *
     * @return  X position in game
     */
    public int getPosX() {
        return x;
    }

    /**
     * Return y position in game
     *
     * @return  Y position in game
     */
    public int getPosY() {
        return y;
    }
}
