package Presentation.Views;

import Presentation.CtrlPresentation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class PlayOptionView extends JFrame {

    private final CtrlPresentation ctrlPresentation;
    private final JPanel panel = new JPanel();
    private final JButton createNewButton = new JButton("Crear");
    private final JButton openFileButton = new JButton("Abrir desde fichero");
    private final JButton playExistButton = new JButton("Jugar uno existente");

    public PlayOptionView(CtrlPresentation cp) {
        this.ctrlPresentation = cp;
        // Window
        setBounds(500, 300, 500, 300);
        setResizable(false);
        setTitle("Play Options");

        createNewButton.setBounds(150, 50, 200, 20);
        openFileButton.setBounds(150, 90, 200, 20);
        playExistButton.setBounds(150, 130, 200, 20);

        // Add buttons to the view
        add(createNewButton);
        add(openFileButton);
        add(playExistButton);
        add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        openFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("TXT files", "txt"));
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                    ctrlPresentation.openKenkenByFile(selectedFile);
                }
            }
        });

    }

    public void makeVisible() {
        setVisible(true);
    }

    public void makeInvisible() {
        setVisible(false);
    }
}