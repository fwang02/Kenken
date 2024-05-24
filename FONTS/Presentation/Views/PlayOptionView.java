package Presentation.Views;

import Presentation.CtrlPresentation;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * @author Feiyang Wang
 */
public class PlayOptionView extends View {

    private CtrlPresentation ctrlPresentation;
    private JPanel playOptionPanel = new JPanel();
    private JButton createNewButton = new JButton("Crear");
    private JButton openFileButton = new JButton("Abrir desde fichero");
    private JButton playExistButton = new JButton("Jugar uno existente");

    private JPanel creationPanel = new JPanel();
    private JPanel sizePanel = new JPanel();
    private SpinnerNumberModel model = new SpinnerNumberModel(3, 3, 9, 1);
    private JSpinner sizeSpinner = new JSpinner(model);
    private JPanel operationPanel = new JPanel();
    private JCheckBox[] operationSelect = new JCheckBox[6];
    private JPanel difficultyPanel = new JPanel();
    private JComboBox<String> difficultySelect = new JComboBox<>(new String[]{"Fácil","Medio","Difícil","Experto"});
    private JPanel upPanel = new JPanel();
    private JPanel buttonsPanel = new JPanel();
    private JButton bConfirmCreation = new JButton("Confirmar");
    private JButton bExit = new JButton("Salir");



    public PlayOptionView(CtrlPresentation cp) {
        this.ctrlPresentation = cp;
        // Ventana
        initFrame();
        initPlayOptionPanel();

        initSizePanel();
        initDifficultyPanel();
        initUpPanel();
        initOperationPanel();
        initButtonsPanel();
        initCreationPanel();

        initActionListener();
    }

    private void initButtonsPanel() {
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel,BoxLayout.X_AXIS));
        bExit.setForeground(Color.RED);
        buttonsPanel.add(Box.createHorizontalGlue());
        buttonsPanel.add(bConfirmCreation);
        buttonsPanel.add(Box.createHorizontalStrut(30));
        buttonsPanel.add(bExit);
        buttonsPanel.add(Box.createHorizontalStrut(10));
    }

    private void initDifficultyPanel() {
        Border titledBorder = BorderFactory.createTitledBorder("Dificultad");
        difficultyPanel.setBorder(titledBorder);
        difficultyPanel.add(difficultySelect,CENTER_ALIGNMENT);
    }

    private void initFrame() {
        setBounds(500, 300, 500, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        //setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private void initOperationPanel() {
        operationPanel.setLayout(new GridLayout(2,3));
        Border titledBorder = BorderFactory.createTitledBorder("Operaciones");
        operationPanel.setBorder(titledBorder);
        String[] operations = {"ADD","SUB","MULT","DIV","POW","MOD"};
        for(int i = 0; i < 6; ++i) {
            operationSelect[i] = new JCheckBox(operations[i]);
            operationPanel.add(operationSelect[i]);
        }

    }

    private void initSizePanel() {
        sizePanel.setLayout(new FlowLayout());
        Border titledBorder = BorderFactory.createTitledBorder("Tamaño");
        sizePanel.setBorder(titledBorder);
        sizePanel.add(sizeSpinner,CENTER_ALIGNMENT);
    }

    private void initUpPanel() {
        upPanel.setLayout(new BoxLayout(upPanel,BoxLayout.X_AXIS));
        upPanel.add(sizePanel);
        upPanel.add(difficultyPanel);
        //Dimension panelSize = new Dimension(300, upPanel.getMinimumSize().height);
        //upPanel.setPreferredSize(panelSize);
    }

    private void initCreationPanel() {
        creationPanel.setLayout(new BoxLayout(creationPanel,BoxLayout.Y_AXIS));
        creationPanel.add(upPanel);
        creationPanel.add(operationPanel);
        creationPanel.add(buttonsPanel);
    }

    private void initPlayOptionPanel() {
        createNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        openFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playExistButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playOptionPanel.setLayout(new BoxLayout(playOptionPanel,BoxLayout.Y_AXIS));
        // Añadir botones a la vista
        playOptionPanel.add(Box.createVerticalGlue());
        playOptionPanel.add(createNewButton);
        playOptionPanel.add(Box.createVerticalStrut(10));
        playOptionPanel.add(openFileButton);
        playOptionPanel.add(Box.createVerticalStrut(10));
        playOptionPanel.add(playExistButton);
        playOptionPanel.add(Box.createVerticalGlue());
        add(playOptionPanel, BorderLayout.CENTER);
    }

    private void initActionListener() {
        createNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(playOptionPanel);
                add(creationPanel,BorderLayout.CENTER);
                revalidate();
                repaint();

                //ctrlPresentation.playOptionViewToGameView();
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