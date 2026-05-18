package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ControlPanel {

    private JPanel panel;
    private JButton nextStepButton;
    private JSpinner marginSpinner;
    private JButton skipAllButton;
    private JButton resetButton;

    public ControlPanel()
    {
        initialize();
        addElements();
    }

    private void initialize()
    {
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
    }

    private void addElements()
    {
        resetButton = new JButton("Resetuj");
        resetButton.setFocusable(false);
        resetButton.addActionListener(e->{ System.out.println("reset");});
        panel.add(resetButton);

        nextStepButton = new JButton("Następny krok");
        nextStepButton.setFocusable(false);
        nextStepButton.addActionListener(e->{ System.out.println("next step");});
        panel.add(nextStepButton);

        skipAllButton = new JButton("Przejdź do końca");
        skipAllButton.setFocusable(false);
        skipAllButton.addActionListener(e ->{ System.out.println("skip all");});
        panel.add(skipAllButton);
    }

    public JPanel getPanel()
    {
        return panel;
    }
}
