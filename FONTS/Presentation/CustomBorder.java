package Presentation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import javax.swing.border.AbstractBorder;

/**
 * This class is a modified Border to allow custom stroke sizes and colors
 *
 * @author Romeu Esteve
 */
public class CustomBorder extends AbstractBorder {
    private final Color topColor;
    private final Color leftColor;
    private final Color bottomColor;
    private final Color rightColor;
    private final int topWidth;
    private final int leftWidth;
    private final int bottomWidth;
    private final int rightWidth;

    public CustomBorder(Color topColor, int topWidth, 
                        Color leftColor, int leftWidth, 
                        Color bottomColor, int bottomWidth, 
                        Color rightColor, int rightWidth) {
        this.topColor = topColor;
        this.topWidth = topWidth;
        this.leftColor = leftColor;
        this.leftWidth = leftWidth;
        this.bottomColor = bottomColor;
        this.bottomWidth = bottomWidth;
        this.rightColor = rightColor;
        this.rightWidth = rightWidth;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;

        // Draw top border
        if (topColor != null) {
            g2.setColor(topColor);
            g2.setStroke(new BasicStroke(topWidth));
            g2.drawLine(x, y, x + width - 1, y);
        }

        // Draw left border
        if (leftColor != null) {
            g2.setColor(leftColor);
            g2.setStroke(new BasicStroke(leftWidth));
            g2.drawLine(x, y, x, y + height - 1);
        }

        // Draw bottom border
        if (bottomColor != null) {
            g2.setColor(bottomColor);
            g2.setStroke(new BasicStroke(bottomWidth));
            g2.drawLine(x, y + height - 1, x + width - 1, y + height - 1);
        }

        // Draw right border
        if (rightColor != null) {
            g2.setColor(rightColor);
            g2.setStroke(new BasicStroke(rightWidth));
            g2.drawLine(x + width - 1, y, x + width - 1, y + height - 1);
        }
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(topWidth, leftWidth, bottomWidth, rightWidth);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.top = topWidth;
        insets.left = leftWidth;
        insets.bottom = bottomWidth;
        insets.right = rightWidth;
        return insets;
    }
}
