package Presentation.Views;

import Presentation.CtrlPresentation;
import Presentation.DrawCell;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**/
public class MainMenuView extends View {
    // PROVISIONAL
    //CtrlDomainUser CDU = new CtrlDomainUser();

    private final JPanel panel = new JPanel();

    private final JLabel title = new JLabel("Kenken PROP");
    private JLabel popup;

    private final JButton bLogin    = new JButton("Iniciar sesión");
    private final JButton bRegister = new JButton("Registrarse");
    private final JButton bRanking  = new JButton("Consultar rankings");
    private final JButton bSalir  = new JButton("Salir");
    private RankingView rankingView;
    private final CtrlPresentation ctrlPresentation;

    public MainMenuView(CtrlPresentation cp) {
        ctrlPresentation = cp;
        // Window
        setBounds(500, 300, 500, 300);
        setResizable(false);
        setTitle("Kenken PROP");

        // Title window
        title.setBounds(10, 5, 120, 30);
        add(title);

        // Iniciar sesión button
        bLogin.setBounds(150, 50, 200, 20);
        add(bLogin);

        // Registrarse button
        bRegister.setBounds(150, 90, 200, 20);
        add(bRegister);

        // Ranking button
        bRanking.setBounds(150, 130, 200, 20);
        add(bRanking);

        // Exit button
        bSalir.setBounds(150, 235, 200, 20);
        add(bSalir);

        add(panel);

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
                        JOptionPane.showMessageDialog(null, "Username already in use","Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else if (pwd.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Password can't be empty","Error", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        if (ctrlPresentation.registerUser(usr, pwd)) JOptionPane.showMessageDialog(null, "User registered!");
                    }
                }
            }
        };

        ActionListener Ranking = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rankingView == null) rankingView = new RankingView();
                else {
                    rankingView.dispose();
                    rankingView = new RankingView();
                }
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
    }

}