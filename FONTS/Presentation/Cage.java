package Presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Cage {
    private List<DrawCell> cells;
    private DrawCell opCell;
    private String operator = "+";
    private int number = 0;

    static private JRadioButton[] opButtons = new JRadioButton[6];
    private ButtonGroup buttonGroup;

    public Cage(char operator, int result) {
        this.cells = new ArrayList<>();
        this.operator = String.valueOf(operator);
        this.number = result;

        initButtons();
    }

    public Cage() {
        this.cells = new ArrayList<>();
        initButtons();
    }

    private void initButtons() {
        String[] operators = {"+", "-", "*", "/", "%", "^"};
        opButtons = new JRadioButton[operators.length];
        buttonGroup = new ButtonGroup();

        for (int i = 0; i < operators.length; i++) {
            opButtons[i] = new JRadioButton(operators[i]);
            buttonGroup.add(opButtons[i]);
        }
        opButtons[0].setSelected(true);
    }

    public void cageConfig() {
        // Create the panel
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label for the number input
        JLabel numberLabel = new JLabel("Enter a number:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(numberLabel, gbc);

        // Text field for the number input
        JTextField numberField = new JTextField(null, (number + ""), 10);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(numberField, gbc);

        // Panel for buttons
        JPanel buttonsPanel = new JPanel();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;



        buttonsPanel.setLayout(new GridLayout(0, 3, 5, 5)); // Adjust layout as needed

        for (JRadioButton button : opButtons) {
            buttonsPanel.add(button);
        }

        // deactivate operators that are not possible
        if (cells.size() > 2) {
        opButtons[1].setEnabled(false);
        opButtons[3].setEnabled(false);
        opButtons[4].setEnabled(false);
        opButtons[5].setEnabled(false);
        }
        else if (cells.size() == 1) {
            opButtons[1].setEnabled(false);
            opButtons[2].setEnabled(false);
            opButtons[3].setEnabled(false);
            opButtons[4].setEnabled(false);
            opButtons[5].setEnabled(false);
        }

        panel.add(buttonsPanel, gbc);

        // Action listener for the number field to generate buttons dynamically
        numberField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    number = Integer.parseInt(numberField.getText());
                    opCell.setOp(operator + number + "");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(panel, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Show the custom JOptionPane with Accept and Cancel options
        int result = JOptionPane.showConfirmDialog(null, panel, "Cage Editor", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            number = Integer.parseInt(numberField.getText());
            for (JRadioButton button : opButtons) {
                if (button.isSelected()) {
                    operator = button.getText();
                    break;
                }
            }

            opCell.setOp(operator + number + "");

        } else {
            cageErase();
        }
    }

    public void addCell(DrawCell cell) {
        cells.add(cell);
        cell.setCage(this);
    }

    public List<DrawCell> getCells() {
        return cells;
    }

    public char getOperator() {
        return operator.charAt(0);
    }

    public int getOperatorAsNum() {
        switch (operator) {
            case "+":
                return 1;
            case "-":
                return 2;
            case "*":
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


    public int getResult() {
        return number;
    }

    public void cageErase() {
        for (DrawCell c : cells) {
            c.setCage(null);
            c.setBorder(new CustomBorder(Color.LIGHT_GRAY, 1, Color.LIGHT_GRAY, 1, Color.LIGHT_GRAY, 1, Color.LIGHT_GRAY, 1));
        }
        opCell.setOp("");
    }

    public void setOpCell(DrawCell opCell) {
        this.opCell = opCell;
    }

    public int countCells() {return cells.size();}

    public void setCage(char op, int res) {
        operator = String.valueOf(op);
        number = res;

        opCell.setOp(operator + number);
    }
}