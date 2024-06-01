package Presentation.Views;

import Presentation.CtrlPresentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainMenuView extends View {
    private final JPanel panel = new JPanel();

    private final JLabel title = new JLabel("Kenken PROP");
    private JLabel popup;

    private final JButton bLogin    = new JButton("Iniciar sesión");
    private final JButton bRegister = new JButton("Registrarse");
    private final JButton bRanking  = new JButton("Consultar rankings");
    private final JButton bSalir  = new JButton("Salir");
    private JButton bExitRanking = new JButton("Salir");
    private RankingPanel rankingPanel;
    private JPanel pExitRanking = new JPanel(new FlowLayout());

    private final CtrlPresentation ctrlPresentation;

    public MainMenuView(CtrlPresentation cp) {
        ctrlPresentation = cp;
        // Window
        setBounds(500, 300, 500, 300);
        setResizable(false);
        setTitle("Kenken PROP");
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Title window
        title.setBounds(10, 5, 120, 30);

        // Iniciar sesión button
        bLogin.setBounds(150, 50, 200, 20);

        // Registrarse button
        bRegister.setBounds(150, 90, 200, 20);

        // Ranking button
        bRanking.setBounds(150, 130, 200, 20);

        // Exit button
        bSalir.setBounds(150, 235, 200, 20);

        addCompLogMenu();

        //setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ActionListener Login = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JTextField username = new JTextField(1);
                JTextField password = new JPasswordField(1);

                JPanel loginMenu = new JPanel();
                loginMenu.setLayout(new BoxLayout(loginMenu, BoxLayout.Y_AXIS));

                loginMenu.add(new JLabel("Username"));
                loginMenu.add(username);
                loginMenu.add(new JLabel("Password"));
                loginMenu.add(password);

                int result = JOptionPane.showConfirmDialog(null, loginMenu,
                        "Log In", JOptionPane.OK_CANCEL_OPTION);
                String usr = username.getText();
                String pwd = password.getText();
                if (result == JOptionPane.OK_OPTION) {
                    if (ctrlPresentation.loginUser(usr,pwd)) {
                        // Move to next view
                        ctrlPresentation.mainViewToPlayOptionView();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Incorrect user or password","Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        };

        ActionListener Register = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JTextField username = new JTextField(1);
                JTextField password = new JPasswordField(1);

                JPanel registerMenu = new JPanel();
                registerMenu.setLayout(new BoxLayout(registerMenu, BoxLayout.Y_AXIS));

                registerMenu.add(new JLabel("Username"));
                registerMenu.add(username);
                registerMenu.add(new JLabel("Password"));
                registerMenu.add(password);

                int result = JOptionPane.showConfirmDialog(null, registerMenu,
                        "Register", JOptionPane.OK_CANCEL_OPTION);
                String usr = username.getText();
                String pwd = password.getText();
                if (result == JOptionPane.OK_OPTION) {
                    if (ctrlPresentation.isUserExist(usr)) {
                        JOptionPane.showMessageDialog(null, "El nombre del usuario ya existe","Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else if (pwd.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "La contraseña no puede ser vacía","Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        if (ctrlPresentation.registerUser(usr, pwd)) JOptionPane.showMessageDialog(null, "¡Se ha registrado con éxito!");
                    }
                }
            }
        };

        ActionListener Ranking = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rankingPanel = new RankingPanel(ctrlPresentation);
                remove(title);
                remove(bLogin);
                remove(bRanking);
                remove(bRegister);
                remove(bSalir);
                remove(panel);
                pExitRanking.add(bExitRanking);
                add(rankingPanel,BorderLayout.CENTER);
                add(pExitRanking,BorderLayout.SOUTH);
                revalidate();
                repaint();
            }
        };

        ActionListener ExitRanking = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(rankingPanel);
                remove(pExitRanking);
                addCompLogMenu();
                revalidate();
                repaint();
            }
        };

        ActionListener Salir = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };

        bRanking.addActionListener(Ranking);
        bLogin.addActionListener(Login);
        bRegister.addActionListener(Register);
        bSalir.addActionListener(Salir);
        bExitRanking.addActionListener(ExitRanking);
    }

    private void addCompLogMenu() {
        add(title);
        add(bLogin);
        add(bRegister);
        add(bRanking);
        add(bSalir);
        add(panel);
    }

}