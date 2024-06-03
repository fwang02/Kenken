package Presentation.Views;

import Presentation.CtrlPresentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Clase para la vista del menú principal.
 * @author Romeu Esteve
 */
public class MainMenuView extends View {
    private final JPanel panel = new JPanel();

    private final JLabel title = new JLabel("Kenken PROP");

    private final JButton bLogin    = new JButton("Iniciar sesión");
    private final JButton bRegister = new JButton("Registrarse");
    private final JButton bRanking  = new JButton("Consultar ranking");
    private final JButton bExit = new JButton("Salir");
    private final JButton bExitRanking = new JButton("Salir");
    private RankingPanel rankingPanel;
    private final JPanel pExitRanking = new JPanel(new FlowLayout());

    private final CtrlPresentation ctrlPresentation;

    /**
     * Construye una nueva instancia de MainMenuView.
     * @param cp El controlador de presentación.
     */
    public MainMenuView(CtrlPresentation cp) {
        ctrlPresentation = cp;
        // Ventana
        setBounds(500, 300, 500, 300);
        setResizable(false);
        setTitle("KenKen");
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Título de la ventana
        title.setBounds(10, 5, 120, 30);

        // Botón de iniciar sesión
        bLogin.setBounds(150, 50, 200, 20);

        // Botón de registrarse
        bRegister.setBounds(150, 90, 200, 20);

        // Botón de ranking
        bRanking.setBounds(150, 130, 200, 20);

        // Botón de salida
        bExit.setBounds(150, 235, 200, 20);

        addCompLogMenu();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ActionListener Login = e -> {

            JTextField username = new JTextField(1);
            JTextField password = new JPasswordField(1);

            JPanel loginMenu = new JPanel();
            loginMenu.setLayout(new BoxLayout(loginMenu, BoxLayout.Y_AXIS));

            loginMenu.add(new JLabel("Nombre de usuario"));
            loginMenu.add(username);
            loginMenu.add(new JLabel("Contraseña"));
            loginMenu.add(password);

            int result = JOptionPane.showConfirmDialog(null, loginMenu,
                    "Iniciar Sesión", JOptionPane.OK_CANCEL_OPTION);
            String usr = username.getText();
            String pwd = password.getText();
            if (result == JOptionPane.OK_OPTION) {
                if (ctrlPresentation.loginUser(usr,pwd)) {
                    // Mover a la siguiente vista
                    ctrlPresentation.mainViewToPlayOptionView();
                }
                else {
                    JOptionPane.showMessageDialog(null, "Contraseña incorrecta","Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        ActionListener Register = e -> {

            JTextField username = new JTextField(1);
            JTextField password = new JPasswordField(1);

            JPanel registerMenu = new JPanel();
            registerMenu.setLayout(new BoxLayout(registerMenu, BoxLayout.Y_AXIS));

            registerMenu.add(new JLabel("Nombre de usuario"));
            registerMenu.add(username);
            registerMenu.add(new JLabel("Contraseña"));
            registerMenu.add(password);

            int result = JOptionPane.showConfirmDialog(null, registerMenu,
                    "Registrarse", JOptionPane.OK_CANCEL_OPTION);
            String usr = username.getText();
            String pwd = password.getText();
            if (result == JOptionPane.OK_OPTION) {
                if(usr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El nombre de usuario no puede ser vacío","Error", JOptionPane.ERROR_MESSAGE);
                }
                else if (pwd.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "La contraseña no puede ser vacía","Error", JOptionPane.ERROR_MESSAGE);
                }
                else if (ctrlPresentation.isUserExist(usr)) {
                    JOptionPane.showMessageDialog(null, "El nombre del usuario ya existe","Error", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    if (ctrlPresentation.registerUser(usr, pwd)) JOptionPane.showMessageDialog(null, "¡Se ha registrado con éxito!");
                }
            }
        };

        ActionListener Ranking = e -> {
            rankingPanel = new RankingPanel(ctrlPresentation);
            remove(title);
            remove(bLogin);
            remove(bRanking);
            remove(bRegister);
            remove(bExit);
            remove(panel);
            pExitRanking.add(bExitRanking);
            add(rankingPanel,BorderLayout.CENTER);
            add(pExitRanking,BorderLayout.SOUTH);
            revalidate();
            repaint();
        };

        ActionListener ExitRanking = e -> {
            remove(rankingPanel);
            remove(pExitRanking);
            addCompLogMenu();
            revalidate();
            repaint();
        };

        ActionListener Salir = e -> System.exit(0);

        bRanking.addActionListener(Ranking);
        bLogin.addActionListener(Login);
        bRegister.addActionListener(Register);
        bExit.addActionListener(Salir);
        bExitRanking.addActionListener(ExitRanking);
    }

    /**
     * Añade los componentes en la vista
     */
    private void addCompLogMenu() {
        add(title);
        add(bLogin);
        add(bRegister);
        add(bRanking);
        add(bExit);
        add(panel);
    }
}