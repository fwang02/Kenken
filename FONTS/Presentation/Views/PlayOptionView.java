package Presentation.Views;

import Presentation.CtrlPresentation;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
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
    private JButton generateNewButton = new JButton("Generar");
    private JButton openFileButton = new JButton("<html>Abrir desde<br>fichero</html>");
    private JButton playExistButton = new JButton("<html>Jugar uno<br>existente</html>");
    private JButton createButton = new JButton("Crear");
    private JPanel textPanel = new JPanel();
    private JLabel title = new JLabel("Opciones para jugar",SwingConstants.CENTER);
    private JLabel loginWelcome = new JLabel();
    private JButton logoutButton = new JButton("Cerrar sesión");
    
    //Componentes del ranking
    private JButton rankingButton = new JButton("Ranking");
    private JButton bExitRanking = new JButton("Volver");
    private RankingPanel rankingPanel;
    private JPanel pExitRanking = new JPanel(new FlowLayout());

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
    private JButton exitButton = new JButton("Volver");

    //componentes para la vista de jugar juegos predefinidos
    private JPanel defaultGamesPanel = new JPanel();
    private JButton[] basicGamesB = new JButton[7];
    private JButton[] specialGamesB = new JButton[2];
    private JButton exitDef = new JButton("Volver");

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

        initDefaultGamesPanel();

        initActionListener();
    }

    private void initTextPanel() {
        textPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        textPanel.setLayout(new BorderLayout());
        //textPanel.add(title,BorderLayout.SOUTH);
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
        setBounds(750, 450, 750, 450);
        setResizable(false);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
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

        generateNewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        generateNewButton.setMinimumSize(buttonSize);
        generateNewButton.setMaximumSize(buttonSize);
        generateNewButton.setPreferredSize(buttonSize);

        openFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        openFileButton.setMaximumSize(buttonSize);

        playExistButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playExistButton.setMinimumSize(buttonSize);
        playExistButton.setMaximumSize(buttonSize);
        playExistButton.setPreferredSize(buttonSize);

        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        createButton.setMinimumSize(buttonSize);
        createButton.setMaximumSize(buttonSize);
        createButton.setPreferredSize(buttonSize);

        rankingButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        rankingButton.setMinimumSize(buttonSize);
        rankingButton.setMaximumSize(buttonSize);
        rankingButton.setPreferredSize(buttonSize);

        playOptionPanel.setLayout(new BoxLayout(playOptionPanel,BoxLayout.X_AXIS));
        // Añadir botones a la vista
        playOptionPanel.add(Box.createHorizontalGlue());
        playOptionPanel.add(generateNewButton);
        playOptionPanel.add(Box.createHorizontalStrut(10));
        playOptionPanel.add(openFileButton);
        playOptionPanel.add(Box.createHorizontalStrut(10));
        playOptionPanel.add(playExistButton);
        playOptionPanel.add(Box.createHorizontalStrut(10));
        playOptionPanel.add(createButton);
        playOptionPanel.add(Box.createHorizontalStrut(10));
        playOptionPanel.add(rankingButton);
        playOptionPanel.add(Box.createHorizontalGlue());
        add(playOptionPanel, BorderLayout.CENTER);
    }

    private void initDefaultGamesPanel() {
        defaultGamesPanel.setLayout(new BorderLayout());

        JPanel basicPanel = new JPanel(new GridLayout(2,0));
        Border titledBorder = BorderFactory.createTitledBorder("Básicos");
        basicPanel.setBorder(titledBorder);
        String textBut;
        for(int i = 3; i <= 9; ++i) {
            textBut = i + " x " + i;
            basicGamesB[i-3] = new JButton(textBut);
            basicPanel.add(basicGamesB[i-3]);
        }

        JPanel specialGamesPanel = new JPanel(new GridLayout());
        titledBorder = BorderFactory.createTitledBorder("Especiales");
        specialGamesPanel.setBorder(titledBorder);
        String[] specialNames = {"Brick Wall", "Cacatua"};
        for(int i = 0; i < 2; i++) {
            specialGamesB[i] = new JButton(specialNames[i]);
            specialGamesPanel.add(specialGamesB[i]);
        }

        JPanel exitPanel = new JPanel(new BorderLayout());

        exitPanel.add(exitDef,BorderLayout.EAST);
        defaultGamesPanel.add(basicPanel,BorderLayout.CENTER);
        defaultGamesPanel.add(specialGamesPanel,BorderLayout.NORTH);
        defaultGamesPanel.add(exitPanel,BorderLayout.SOUTH);
    }

    private void initActionListener() {
        generateNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Has clicado el botón "+ ((JButton) e.getSource()).getText());
                playOptionToCreation();
            }
        });

        openFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Has clicado el botón "+ ((JButton) e.getSource()).getText());
                JFileChooser fileChooser = new JFileChooser("../DATA");
                fileChooser.setFileFilter(new FileNameExtensionFilter("TXT files", "txt"));
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
                System.out.println("Has clicado el botón "+ ((JButton) e.getSource()).getText());
                playOptionToDefGames();
            }
        });

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Has clicado el botón 'Crear'");
                Integer[] sizes = {3, 4, 5, 6, 7, 8, 9};
                Integer selectedSize = (Integer) JOptionPane.showInputDialog(
                        null,
                        "Seleccione el tamaño del KenKen:",
                        "Seleccionar Tamaño",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        sizes,
                        sizes[0]
                );

                if (selectedSize != null) {
                    System.out.println("Tamaño seleccionado: " + selectedSize);
                    // Proceed with the selected size
                    ctrlPresentation.setGameCreatorSize(selectedSize);
                    ctrlPresentation.playOptionViewToGameCreatorView();
                }
            }
        });

        rankingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rankingPanel = new RankingPanel(ctrlPresentation);
                remove(title);
                remove(playOptionPanel);
                remove(generateNewButton);
                remove(openFileButton);
                remove(playExistButton);
                remove(createButton);
                remove(textPanel);
                remove(rankingButton);
                remove(loginWelcome);
                remove(logoutButton);
                remove(textPanel);
                pExitRanking.add(bExitRanking);
                add(rankingPanel,BorderLayout.CENTER);
                add(pExitRanking,BorderLayout.SOUTH);
                revalidate();
                repaint();
            }
        });
        
        bExitRanking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(rankingPanel);
                remove(pExitRanking);
                addCompPlayMenu();
                revalidate();
                repaint();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Has clicado el botón "+ ((JButton) e.getSource()).getText());
                creationToPlayOption();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Has clicado el botón "+ ((JButton) e.getSource()).getText());
                ctrlPresentation.playOptionViewToMainMenuView();
            }
        });

        bConfirmCreation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Has clicado el botón "+ ((JButton) e.getSource()).getText());
                HashSet<String> selectedOp = new HashSet<>();

                System.out.println("Has seleccionado las siguientes operaciones: ");

                for(JCheckBox op : operationSelect) {
                    if(op.isSelected()) {
                        System.out.println(op.getText());
                        selectedOp.add(op.getText());
                    }
                }
                System.out.println();

                if(selectedOp.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No has seleccionado ninguna operación", "Error de creación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int size = (int) sizeSpinner.getValue();
                System.out.println("Has seleccionado el tamaño: "+size);

                String nameDiff = (String) difficultySelect.getSelectedItem();
                if (nameDiff == null) {
                    JOptionPane.showMessageDialog(null, "No has seleccionado ninguna dificultad", "Error de creación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String diff = "";
                switch (nameDiff) {
                    case "Fácil":
                        diff = "EASY";
                        break;
                    case "Medio":
                        diff = "MEDIUM";
                        break;
                    case "Difícil":
                        diff = "HARD";
                        break;
                    case "Experto":
                        diff = "EXPERT";
                        break;
                }
                System.out.println("Has seleccionado la dificultad: " + diff);

                if(ctrlPresentation.createKenken(size,selectedOp, diff)) {
                    int result = JOptionPane.showConfirmDialog(null, "¿Quieres jugarlo ahora?",
                            "Opción de jugar", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        ctrlPresentation.playOptionViewToGameView();
                    }
                    else {
                        // creationToPlayOption();
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "Se ha producido algún error", "Error de creación", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        exitDef.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Has clicado el botón "+ ((JButton) e.getSource()).getText());
                defGamesToPlayOption();
            }
        });

        //añadir actionListener para los botones de juegos básicos
        for(int i = 0; i < 7; ++i) {
            int finalI = i+3;
            basicGamesB[i].addActionListener(e -> {
                System.out.println("Has clicado el botón "+ ((JButton) e.getSource()).getText());
                String fileName = "/defaultGames/basico"+ finalI + "x" + finalI;
                if(ctrlPresentation.openKenkenByFile(fileName)) {
                    ctrlPresentation.playOptionViewToGameView();
                }
                else {
                    JOptionPane.showMessageDialog(null,"El juego no existe","Error de lectura de fichero",JOptionPane.ERROR_MESSAGE);
                }
            });
        }
    }
    
    private void addCompPlayMenu() {
        add(textPanel,BorderLayout.NORTH);
        add(playOptionPanel,BorderLayout.CENTER);
    }

    private void playOptionToCreation() {
        remove(playOptionPanel);
        remove(textPanel);
        add(creationPanel);
        revalidate();
        repaint();
    }

    private void creationToPlayOption() {
        resetCreation();
        remove(creationPanel);
        add(textPanel,BorderLayout.NORTH);
        add(playOptionPanel,BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void playOptionToDefGames() {
        remove(playOptionPanel);
        remove(textPanel);
        add(defaultGamesPanel);
        revalidate();
        repaint();
    }

    private void defGamesToPlayOption() {
        remove(defaultGamesPanel);
        add(textPanel,BorderLayout.NORTH);
        add(playOptionPanel,BorderLayout.CENTER);
        revalidate();
        repaint();

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