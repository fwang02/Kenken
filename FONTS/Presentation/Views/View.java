package Presentation.Views;

import javax.swing.*;
import java.util.Enumeration;

/**
 * Clase para definir atributos comunes dentro de las vistas.
 * @author Romeu Esteve
 */
public class View extends JFrame {

    /**
     * Establece la fuente de la interfaz de usuario para todas las componentes Swing.
     * @param f La fuente a establecer.
     */
    public static void setUIFont(javax.swing.plaf.FontUIResource f) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put(key, f);
        }
    }

    /**
     * Construye una nueva vista.
     */
    public View() {
        // Establecer el ícono en todas las vistas
        ImageIcon icon = new ImageIcon("../DATA/resource/kenkenicon.png");
        setIconImage(icon.getImage());
        setUIFont(new javax.swing.plaf.FontUIResource("Corbel", 1, 14));
        setTitle("KenKen");

        setBounds(500, 300, 500, 300);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Asegurar que la ventana pueda cerrarse
    }

    /**
     * Hace visible la vista.
     */
    public void makeVisible() {
        setVisible(true);
    }

    /**
     * Hace invisible la vista.
     */
    public void makeInvisible() {
        setVisible(false);
    }
}
