package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GraphPanel extends JPanel {

    public GraphPanel() {
        initialize();
    }

    private void initialize() {
        setBackground(new Color(43, 45, 48));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setStroke(new BasicStroke(3));
        //g2d.drawLine(x1, y1, x2, y2);
        //g2d.fillOval(x1 - promien, y1 - promien, srednica, srednica);

    }
}
