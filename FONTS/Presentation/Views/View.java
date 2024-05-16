package Presentation.Views;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
* Class to define common attributes within views
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
    }

    public void makeVisible() {
        setVisible(true);
    }

    public void makeInvisible() {
        setVisible(false);
    }
}
