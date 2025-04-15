package ui;

import javax.swing.JFrame;

public class UISuperCalculator extends JFrame {
    public UISuperCalculator() {
        setTitle("Super Calculator");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}
