package Presentation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase {@code Cage} representa una región en un rompecabezas KenKen, que tiene un operador común y un resultado.
 * @author Romeu Esteve
 */
public class Cage {
    private final List<DrawCell> cells;
    private DrawCell opCell;
    private String operator = "+";
    private int number = 0;
    private JRadioButton[] opButtons = new JRadioButton[6];

    /**
     * Construye una {@code Cage} con valores predeterminados.
     */
    public Cage() {
        this.cells = new ArrayList<>();
        initButtons();
    }

    /**
     * Inicializa los botones de operador.
     */
    private void initButtons() {
        String[] operators = {"+", "-", "x", "/", "%", "^"};
        opButtons = new JRadioButton[operators.length];
        ButtonGroup buttonGroup = new ButtonGroup();

        for (int i = 0; i < operators.length; i++) {
            opButtons[i] = new JRadioButton(operators[i]);
            buttonGroup.add(opButtons[i]);
        }
        opButtons[0].setSelected(true);
    }

    /**
     * Configura la región mediante una interfaz gráfica.
     */
    public void cageConfig() {
        // Crear el panel
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Etiqueta para la entrada de número
        JLabel numberLabel = new JLabel("Indica un número:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(numberLabel, gbc);

        // Campo de texto para la entrada de número
        JTextField numberField = new JTextField(null, (number + ""), 10);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(numberField, gbc);

        // Panel para los botones
        JPanel buttonsPanel = new JPanel();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        buttonsPanel.setLayout(new GridLayout(0, 3, 5, 5)); // Ajustar el diseño según sea necesario

        for (JRadioButton button : opButtons) {
            buttonsPanel.add(button);
        }

        // Desactivar operadores que no son posibles
        if (cells.size() > 2) {
            opButtons[1].setEnabled(false);
            opButtons[3].setEnabled(false);
            opButtons[4].setEnabled(false);
            opButtons[5].setEnabled(false);
        } else if (cells.size() == 1) {
            opButtons[1].setEnabled(false);
            opButtons[2].setEnabled(false);
            opButtons[3].setEnabled(false);
            opButtons[4].setEnabled(false);
            opButtons[5].setEnabled(false);
        }

        panel.add(buttonsPanel, gbc);

        // Listener para el campo de número para generar botones dinámicamente
        numberField.addActionListener(e -> {
            try {
                number = Integer.parseInt(numberField.getText());
                opCell.setOp(operator + number);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Indica un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Mostrar el JOptionPane personalizado con opciones de Aceptar y Cancelar
        int result = JOptionPane.showConfirmDialog(null, panel, "Editor de Región", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            if (!numberField.getText().matches("\\d+")) {
                JOptionPane.showMessageDialog(null, "Indica un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            number = Integer.parseInt(numberField.getText());
            for (JRadioButton button : opButtons) {
                if (button.isSelected()) {
                    operator = button.getText();
                    break;
                }
            }
            opCell.setOp(operator + number);
        } else {
            cageErase();
        }
    }

    /**
     * Agrega una celda a la región.
     *
     * @param cell la celda a agregar
     */
    public void addCell(DrawCell cell) {
        cells.add(cell);
        cell.setCage(this);
    }

    /**
     * Obtiene la lista de celdas en la región.
     *
     * @return la lista de celdas
     */
    public List<DrawCell> getCells() {
        return cells;
    }

    /**
     * Obtiene el operador de la región como un número.
     *
     * @return el número correspondiente al operador
     */
    public int getOperatorAsNum() {
        switch (operator) {
            case "+":
                return 1;
            case "-":
                return 2;
            case "x":
                return 3;
            case "/":
                return 4;
            case "%":
                return 5;
            case "^":
                return 6;
        }
        return 0;
    }

    /**
     * Obtiene el resultado de la región.
     *
     * @return el resultado
     */
    public int getResult() {
        return number;
    }

    /**
     * Borra la configuración de la región.
     */
    public void cageErase() {
        for (DrawCell c : cells) {
            c.setCage(null);
            c.setBorder(new CustomBorder(Color.LIGHT_GRAY, 1, Color.LIGHT_GRAY, 1, Color.LIGHT_GRAY, 1, Color.LIGHT_GRAY, 1));
        }
        DrawCell.eraseCage(this);
        opCell.setOp("");
    }

    /**
     * Establece la celda de operación para la región.
     *
     * @param opCell la celda de operación
     */
    public void setOpCell(DrawCell opCell) {
        this.opCell = opCell;
    }

    /**
     * Configura la región con un operador y un resultado especificados.
     *
     * @param op el operador
     * @param res el resultado
     */
    public void setCage(char op, int res) {
        operator = String.valueOf(op);
        number = res;
        opCell.setOp(operator + number);
    }
}