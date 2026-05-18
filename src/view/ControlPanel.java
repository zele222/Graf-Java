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
    private JButton skipAllButton;
    private JButton resetButton;

    private Runnable onNextStep;
    private Runnable onSkipAll;
    private Runnable onReset;

    public ControlPanel(Runnable onNextStep, Runnable onSkipAll, Runnable onReset)
    {
        this.onNextStep = onNextStep;
        this.onSkipAll = onSkipAll;
        this.onReset = onReset;

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
        resetButton.addActionListener(e->{ if (onReset != null) onReset.run();});
        panel.add(resetButton);

        nextStepButton = new JButton("Następny krok");
        nextStepButton.setFocusable(false);
        nextStepButton.addActionListener(e->{ if (onNextStep != null) onNextStep.run();});
        panel.add(nextStepButton);

        skipAllButton = new JButton("Przejdź do końca");
        skipAllButton.setFocusable(false);
        skipAllButton.addActionListener(e ->{ if (onSkipAll != null) onSkipAll.run();});
        panel.add(skipAllButton);
    }

    public JPanel getPanel()
    {
        return panel;
    }
}
