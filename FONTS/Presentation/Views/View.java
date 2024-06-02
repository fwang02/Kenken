package Presentation.Views;

import javax.swing.*;


/**
* Class to define common attributes within views
 * @author Romeu Esteve
 */
public class View extends JFrame {
    private final ImageIcon icon = new ImageIcon("../DATA/resource/kenkenicon.png");

    public static void setUIFont (javax.swing.plaf.FontUIResource f){
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (key, f);
        }
    }

    public View() {
        // Set logo in all views
        setIconImage(icon.getImage());
        setUIFont (new javax.swing.plaf.FontUIResource("Corbel", 1, 14));
        setTitle("KenKen");

        setBounds(500, 300, 500, 300);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensure the window can be closed
    }

    public void makeVisible() {
        setVisible(true);
    }

    public void makeInvisible() {
        setVisible(false);
    }
}
