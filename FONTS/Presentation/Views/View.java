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
        setTitle("Kenken PROP");

        setBounds(0, 0, 500, 300);
    }

    public void makeVisible() {
        setVisible(true);
    }

    public void makeInvisible() {
        setVisible(false);
    }
}
