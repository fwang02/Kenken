package Presentation.Views;

import Domain.Operation.*;
import Domain.TypeDifficulty;
import Presentation.CtrlPresentation;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashSet;

/**
 * @author Feiyang Wang
 */
public class PlayOptionView extends View {

    private CtrlPresentation ctrlPresentation;
    //Componentes de opciones para jugar
    private JPanel playOptionPanel = new JPanel();
    private JButton createNewButton = new JButton("Crear");
    private JButton openFileButton = new JButton("<html>Abrir desde<br>fichero</html>");
    private JButton playExistButton = new JButton("<html>Jugar uno<br>existente</html>");
    private JPanel textPanel = new JPanel();
    private JLabel title = new JLabel("Opciones para jugar",SwingConstants.CENTER);
    private JLabel loginWelcome = new JLabel();
    private JButton logoutButton = new JButton("Cerrar sesión");

    //Componentes para la ventana de creación
    private JPanel creationPanel = new JPanel();
    private JPanel sizePanel = new JPanel();
    private JSpinner sizeSpinner = new JSpinner(new SpinnerNumberModel(3, 3, 9, 1));
    private JPanel operationPanel = new JPanel();
    private JCheckBox[] operationSelect = new JCheckBox[6];
    private JPanel difficultyPanel = new JPanel();
    private JComboBox<String> difficultySelect = new JComboBox<>(new String[]{"Fácil","Medio","Difícil","Experto"});
    private JPanel upPanel = new JPanel();
    private JPanel buttonsPanel = new JPanel();
    private JButton bConfirmCreation = new JButton("Confirmar");
    private JButton exitButton = new JButton("Salir");



    public PlayOptionView(CtrlPresentation cp) {
        this.ctrlPresentation = cp;
        // Ventana
        initFrame();
        initTextPanel();
        initPlayOptionPanel();

        initSizePanel();
        initDifficultyPanel();
        initUpPanel();
        initOperationPanel();
        initButtonsPanel();
        initCreationPanel();

        initActionListener();
    }

    private void initTextPanel() {
        textPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        textPanel.setLayout(new BorderLayout());
        textPanel.add(title,BorderLayout.SOUTH);
        textPanel.add(loginWelcome,BorderLayout.WEST);

        logoutButton.setForeground(Color.BLUE);
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setContentAreaFilled(false);
        logoutButton.setOpaque(false);
        logoutButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                logoutButton.setForeground(Color.RED);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                logoutButton.setForeground(Color.BLUE);
            }
        });

        textPanel.add(logoutButton,BorderLayout.EAST);
        add(textPanel,BorderLayout.NORTH);

    }

    private void initButtonsPanel() {
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel,BoxLayout.X_AXIS));
        exitButton.setForeground(Color.RED);
        buttonsPanel.add(Box.createHorizontalGlue());
        buttonsPanel.add(bConfirmCreation);
        buttonsPanel.add(Box.createHorizontalStrut(30));
        buttonsPanel.add(exitButton);
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
        setLayout(new BorderLayout());
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
        ((JSpinner.DefaultEditor) sizeSpinner.getEditor()).getTextField().setEditable(false); //para no entrar valor por teclado al selector de tamaño
        Border titledBorder = BorderFactory.createTitledBorder("Tamaño");
        sizePanel.setBorder(titledBorder);
        sizePanel.add(sizeSpinner,CENTER_ALIGNMENT);
    }

    private void initUpPanel() {
        upPanel.setLayout(new BoxLayout(upPanel,BoxLayout.X_AXIS));
        upPanel.add(sizePanel);
        upPanel.add(difficultyPanel);
    }

    private void initCreationPanel() {
        creationPanel.setLayout(new BoxLayout(creationPanel,BoxLayout.Y_AXIS));
        creationPanel.add(upPanel);
        creationPanel.add(operationPanel);
        creationPanel.add(buttonsPanel);
    }

    private void initPlayOptionPanel() {
        Dimension buttonSize = new Dimension(100, 100);

        createNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        createNewButton.setMinimumSize(buttonSize);
        createNewButton.setMaximumSize(buttonSize);
        createNewButton.setPreferredSize(buttonSize);

        openFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        openFileButton.setMaximumSize(buttonSize);

        playExistButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playExistButton.setMinimumSize(buttonSize);
        playExistButton.setMaximumSize(buttonSize);
        playExistButton.setPreferredSize(buttonSize);

        playOptionPanel.setLayout(new BoxLayout(playOptionPanel,BoxLayout.X_AXIS));
        // Añadir botones a la vista
        playOptionPanel.add(Box.createHorizontalGlue());
        playOptionPanel.add(createNewButton);
        playOptionPanel.add(Box.createHorizontalStrut(10));
        playOptionPanel.add(openFileButton);
        playOptionPanel.add(Box.createHorizontalStrut(10));
        playOptionPanel.add(playExistButton);
        playOptionPanel.add(Box.createHorizontalGlue());
        add(playOptionPanel, BorderLayout.CENTER);
    }

    private void initActionListener() {
        createNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(playOptionPanel);
                remove(textPanel);
                add(creationPanel);
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
                    if(ctrlPresentation.openKenkenByFile(selectedFile)) {
                        ctrlPresentation.playOptionViewToGameView();
                    }
                    else {
                        JOptionPane.showMessageDialog(null,"El fichero seleccionado no es válido","Error de lectura de fichero",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        playExistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ctrlPresentation.openKenkenByFile(new File("../DATA/input.txt"));
                ctrlPresentation.playOptionViewToGameView();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeToPlayOptionPanel();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ctrlPresentation.playOptionViewToMainMenuView();
            }
        });

        bConfirmCreation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashSet<Operation> selectedOp = new HashSet<>();
                for(JCheckBox op : operationSelect) {
                    if(op.isSelected()) {
                        addOpSet(op, selectedOp);
                    }
                }
                if(selectedOp.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No has seleccionado ninguna operación", "Error de creación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int size = (int) sizeSpinner.getValue();

                String nameDiff = (String) difficultySelect.getSelectedItem();
                if (nameDiff == null) {
                    JOptionPane.showMessageDialog(null, "No has seleccionado ninguna dificultad", "Error de creación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                TypeDifficulty diff = null;
                switch (nameDiff) {
                    case "Fácil":
                        diff = TypeDifficulty.EASY;
                        break;
                    case "Medio":
                        diff = TypeDifficulty.MEDIUM;
                        break;
                    case "Difícil":
                        diff = TypeDifficulty.HARD;
                        break;
                    case "Experto":
                        diff = TypeDifficulty.EXPERT;
                        break;
                }

                if(ctrlPresentation.createKenken(size,selectedOp,diff)) {
                    int result = JOptionPane.showConfirmDialog(null, "¿Quieres jugarlo ahora?",
                            "Opción de jugar", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        ctrlPresentation.playOptionViewToGameView();
                    }
                    else {
                        changeToPlayOptionPanel();
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Se ha producido algún error", "Error de creación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        });


    }

    private void changeToPlayOptionPanel() {
        resetCreation();
        remove(creationPanel);
        add(textPanel,BorderLayout.NORTH);
        add(playOptionPanel,BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void addOpSet(JCheckBox op, HashSet<Operation> selectedOp) {
        switch (op.getText()) {
            case "ADD":
                selectedOp.add(new ADD());
                break;
            case "SUB":
                selectedOp.add(new SUB());
                break;
            case "MULT":
                selectedOp.add(new MULT());
                break;
            case "DIV":
                selectedOp.add(new DIV());
                break;
            case "POW":
                selectedOp.add(new POW());
                break;
            case "MOD":
                selectedOp.add(new MOD());
                break;
        }
    }

    private void resetCreation() {
        sizeSpinner.setValue(3);

        for (JCheckBox checkBox : operationSelect) {
            checkBox.setSelected(false);
        }

        difficultySelect.setSelectedIndex(0);
    }

    public void addLoggedUser() {
        String welcomeInfo = "¡Bienvenido, " + ctrlPresentation.getLoggedUserName() + "!";
        loginWelcome.setText(welcomeInfo);
    }
}