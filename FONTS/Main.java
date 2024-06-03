import Presentation.CtrlPresentation;

/**
 * Clase main, donde ejecuta el programa
 */
public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            CtrlPresentation CP = new CtrlPresentation();
            CP.initPresentation();
        });

    }
}
