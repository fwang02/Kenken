package Presentation;
import javax.swing.*;
import java.awt.*;

public class DrawCell extends JPanel {
    private int posX, posY;

    public DrawCell(int posX, int posY, int size) {
        super();
        this.posX = posX;
        this.posY = posY;

        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        setPreferredSize(new Dimension(size, size));
    }

    /** @brief Getter de posX
     *
     * @return la posición de la fila
     */
    public int getPosX() {
        return posX;
    }

    /** @brief Setter de posX
     *
     * @param posX representa la posición de la fila
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /** Getter de posY
     *
     * @return la posición de la columna
     */
    public int getPosY() {
        return posY;
    }

    /** @brief Setter de posY
     *
     * @param posY representa la posición de la columna
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }
}