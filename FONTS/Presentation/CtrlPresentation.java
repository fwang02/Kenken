package Presentation;

import Domain.Controllers.CtrlDomain;
import Domain.PlayerScore;
import Presentation.Views.GameCreatorView;
import Presentation.Views.GameView;
import Presentation.Views.PlayOptionView;
import Presentation.Views.MainMenuView;

import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.io.File;
import java.util.PriorityQueue;

/**
 * @author feiyang.wang
 */
public class CtrlPresentation {

    private CtrlDomain ctrlDomain;
    private MainMenuView mainMenuView;
    private PlayOptionView playOptionView;
    private GameView gameView;

    private GameCreatorView gameCreatorView;


    public CtrlPresentation (){
        ctrlDomain = new CtrlDomain();
        mainMenuView = new MainMenuView(this);
        playOptionView = new PlayOptionView(this);
        gameView = new GameView(this);
        gameCreatorView = new GameCreatorView(this);
    }

    public void initPresentation() {
        mainMenuView.makeVisible();
        //gameView.makeVisible();
        gameCreatorView.makeVisible();
    }

    public void mainViewToPlayOptionView() {
        mainMenuView.makeInvisible();
        playOptionView.makeVisible();
        //gameView.makeVisible();
    }

    public void playOptionViewToGameView() {
        playOptionView.makeInvisible();
        gameView.startPlay();
        gameView.makeVisible();

    }

    public PriorityQueue<PlayerScore> getRanking() {
        return ctrlDomain.getRanking();
    }

    public boolean openKenkenByFile(File file) {
        return ctrlDomain.importKenkenByFile(file);
    }

    public int getKenkenSize() {
        return ctrlDomain.getKenkenSize();
    }

    public boolean loginUser(String username, String password) {
        return ctrlDomain.loginUser(username,password);
    }

    public boolean isUserExist(String username) {
        return ctrlDomain.isUserExist(username);
    }

    public boolean registerUser(String username, String password) {
        return ctrlDomain.registerUser(username,password);
    }

    public static class CustomBorder extends AbstractBorder {
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
}
