package Presentation;

import Presentation.CtrlPresentation;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**/
public class ViewMainMenu extends JFrame {
    private final JPanel panel = new JPanel();

    private final JLabel title = new JLabel("Kenken PROP");
    private JLabel popup;

    private final JButton bLogin    = new JButton("Iniciar sesión");
    private final JButton bRegister = new JButton("Registrarse");
    private final JButton bRanking  = new JButton("Consultar rankings");



    public ViewMainMenu() {
        // Window
        setBounds(500, 300, 500, 300);
        setResizable(true);
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

        add(panel);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ActionListener Login = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JTextField username = new JTextField(1);
                JTextField password = new JTextField(1);

                JPanel loginMenu = new JPanel();
                loginMenu.setLayout(new BoxLayout(loginMenu, BoxLayout.Y_AXIS));

                loginMenu.add(new JLabel("Username"));
                loginMenu.add(username);
                loginMenu.add(new JLabel("Password"));
                loginMenu.add(password);

                int result = JOptionPane.showConfirmDialog(null, loginMenu,
                        "Log In", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    System.out.println("user: " + username.getText());
                    System.out.println("pswrd: " + password.getText());
                }
            }
        };

        ActionListener Register = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JTextField username = new JTextField(1);
                JTextField password = new JTextField(1);

                JPanel registerMenu = new JPanel();
                registerMenu.setLayout(new BoxLayout(registerMenu, BoxLayout.Y_AXIS));

                registerMenu.add(new JLabel("Username"));
                registerMenu.add(username);
                registerMenu.add(new JLabel("Password"));
                registerMenu.add(password);

                int result = JOptionPane.showConfirmDialog(null, registerMenu,
                        "Register", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    System.out.println("user: " + username.getText());
                    System.out.println("pswrd: " + password.getText());
                }
            }
        };
        bLogin.addActionListener(Login);
        bRegister.addActionListener(Register);
    }
}
