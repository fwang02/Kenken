package Presentation.Views;

import Presentation.CtrlPresentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @author Feiyang Wang
 */
public class PlayOptionView extends View {

    private final CtrlPresentation ctrlPresentation;
    private final JPanel panel = new JPanel();
    private final JPanel creationPanel = new JPanel();
    private final JButton createNewButton = new JButton("Crear");
    private final JButton openFileButton = new JButton("Abrir desde fichero");
    private final JButton playExistButton = new JButton("Jugar uno existente");
    private final JScrollBar sizeScrollBar = new JScrollBar(Adjustable.HORIZONTAL,3,1,3,9);
    private final JCheckBox[] operationSelect = new JCheckBox[6];
    private final JComboBox<String> difficultySelect = new JComboBox<>(new String[]{"Fácil","Medio","Difícil","Experto"});


    public PlayOptionView(CtrlPresentation cp) {
        this.ctrlPresentation = cp;
        // Ventana
        setBounds(500, 300, 500, 300);
        setResizable(false);
        setTitle("Play Options");
        setLocationRelativeTo(null);

        createNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        openFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playExistButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        // Añadir botones a la vista
        panel.add(Box.createVerticalGlue());
        panel.add(createNewButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(openFileButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(playExistButton);
        panel.add(Box.createVerticalGlue());

        add(panel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ctrlPresentation.playOptionViewToGameView();
            }
        });

        openFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser("../DATA");
                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("TXT files", "txt"));
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                    ctrlPresentation.openKenkenByFile(selectedFile);
                    ctrlPresentation.playOptionViewToGameView();
                }
            }
        });

        playExistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ctrlPresentation.openKenkenByFile(new File("../DATA/basico3x3.txt"));
                ctrlPresentation.playOptionViewToGameView();
            }
        });

    }

}